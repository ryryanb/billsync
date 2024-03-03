package com.localtide.billsync.resource;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localtide.billsync.dto.BillSearch;
import com.localtide.billsync.dto.Payment;
import com.localtide.billsync.dto.PaymentList;
import com.localtide.billsync.dto.Result;
import com.localtide.billsync.entity.Audit.AuditAction;
import com.localtide.billsync.entity.TransferHistory;
import com.localtide.billsync.entity.User;
import com.localtide.billsync.service.AuditService;
import com.localtide.billsync.service.CurrencyService;
import com.localtide.billsync.service.PaymentService;
import com.localtide.billsync.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class PaymentHistoryResource {
	private final Log logger = LogFactory.getLog(getClass());
	
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired 
	private AuditService auditService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CurrencyService currencyService;
	
	/*@PostMapping(path = "/api/payment/history")
	public ResponseEntity<Result> history(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "acc", required = false) String toAccount,
			@RequestParam(value = "from", required = false) String fromDate, 
			@RequestParam(value = "to", required = false) String toDate) {
		Result result = new Result();
		try {
			Date startDate = null;
			Date endDate = null;
			DateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			if(StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
				//get start of current date
				startDate = DateUtils.truncate(new Date(), Calendar.DATE);
				//get end of current date
				endDate = DateUtils.addMilliseconds(DateUtils.ceiling(new Date(), Calendar.DATE), -1);
			} else {
				if(StringUtils.isNotBlank(fromDate)) {
					startDate = sdf.parse(fromDate);
				}
				if(StringUtils.isNotBlank(toDate)) {
					//get end of end date
					endDate = DateUtils.addMilliseconds(DateUtils.ceiling(sdf.parse(toDate), Calendar.DATE), -1);
				}
			}
			List<TransferHistory> hist = paymentService.getPaymentHistory(0,1000,"","", toAccount, startDate, endDate);
			List<Payment> payments = new ArrayList<Payment>();
			for (TransferHistory t: hist) {
				payments.add(new Payment(t));
			}
			PaymentList list = new PaymentList();
			list.addAllRows(payments);
			list.setTotal(payments.size());
			result.setDetails(list);
			
		}catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.ordinal());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}*/
	
	@PostMapping(path = "/api/payment/history")
	public ResponseEntity<Result> history(@RequestBody BillSearch billSearch,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		try {
			Date startDate = null;
			Date endDate = null;
			logger.debug("fromdate" + billSearch.getFromDate());
			logger.debug("todate" + billSearch.getToDate());
			
			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			if(StringUtils.isBlank(billSearch.getFromDate()) && StringUtils.isBlank(billSearch.getToDate())) {
				logger.debug("both dates blank");
				//get start of current date
				//startDate = DateUtils.truncate(new Date(), Calendar.DATE);
				//get end of current date
				endDate = DateUtils.addMilliseconds(DateUtils.ceiling(new Date(), Calendar.DATE), -1);
				logger.debug("From date:" + startDate + " to date:" + endDate);
			} else {
				if(StringUtils.isNotBlank(billSearch.getFromDate())) {
					startDate = sdf.parse(billSearch.getFromDate());
				}
				if(StringUtils.isNotBlank(billSearch.getToDate())) {
					//get end of end date
					endDate = DateUtils.addMilliseconds(DateUtils.ceiling(sdf.parse(billSearch.getToDate()), Calendar.DATE), -1);
				}
				logger.debug("2-From date:" + startDate + " to date:" + endDate);
			}
			User user = userService.getCurrentUser();
			
			
			if(user == null) {
				result.setMessage("User does not exist!");
				result.setCode(401);
				return new ResponseEntity<Result>(result, HttpStatus.UNAUTHORIZED);
			}
			List<Payment> payments = new ArrayList<Payment>();
			Map<String, String> totalAmountMap = new HashMap<String, String>();
			Page<TransferHistory> pageTransfer = null;
			if(user.getBillerId() != null) {
				int perPage = billSearch.getPerPage() >= 0 ? billSearch.getPerPage() : Integer.MAX_VALUE;
				pageTransfer = paymentService.getPaymentHistory(billSearch.getPage(),perPage, billSearch.getSortBy(),billSearch.getSortDirection(), billSearch.getRefNum(), startDate, endDate, user.getBillerId());
				logger.info("pagetransfer" +pageTransfer);
				List<TransferHistory> hist = pageTransfer.getContent();
				logger.info("histsize" + hist.size());
				for (TransferHistory t: hist) {
					Payment payment = new Payment(t);
					String currency = currencyService.getCurrencySymbol(t.getCurrencyCode());
					payment.setCurrency(currency);
					payment.setAmountText(StringUtils.stripToEmpty(currency) + " " + payment.getAmount());
					payment.setBillerId(user.getBillerId());
					payments.add(payment);
					Double totalAmount = NumberUtils
							.toDouble(StringUtils.stripToEmpty(totalAmountMap.get(currency)).replaceAll(",", ""), 0d);
					totalAmount += t.getAmount();
					totalAmountMap.put(currency, new DecimalFormat("#,###.00").format(totalAmount));
				
				    logger.info("payment" + payment);
				}
			}
			logger.info("Total Amount: " + Arrays.asList(totalAmountMap));
			PaymentList list = new PaymentList();
			list.addAllRows(payments);
			list.setTotal(pageTransfer.getTotalElements());
			//list.setTotalAmount(totalAmountMap);
			result.setDetails(list);
			
			ObjectMapper objectMapper = new ObjectMapper();
			auditService.audit(userService.getCurrentUser(), AuditAction.PAYMENT_QUERY, objectMapper.writeValueAsString(billSearch));
			
		}catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.ordinal());
			return new ResponseEntity<Result>(result, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
}
