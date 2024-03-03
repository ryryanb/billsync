package com.localtide.billsync.service;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.localtide.billsync.entity.TransferHistory;
import com.localtide.billsync.repository.TransferHistoryRepository;

@Service
public class PaymentService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	//private final String BILL_COMMENT ="BILL_PAY:";
	private final String BILL_COMMENT ="BP";
	
	@Autowired
	private TransferHistoryRepository transferHistoryRepository;
	
	public Page<TransferHistory> getPaymentHistory(int position, int pageSize, String sortBy, String direction,
			String toAccount, Date startDate, Date endDate, String billerId) {
		
		Pageable pageRequest = getPageRequest(position, pageSize, sortBy, direction);
		return getPaymentHistory(pageRequest, toAccount, startDate, endDate, billerId);
	}
 
	@SuppressWarnings("unchecked")
	private Page<TransferHistory> getPaymentHistory(Pageable pageRequest,
			String toAccount, Date startDate, Date endDate, String billerId) {
		final String prefix = "findByBillerId";
		String methodName = prefix;
		List<Object> params = new ArrayList<Object>();
		if (!StringUtils.isEmpty(toAccount)) {
			methodName += "AndRefNum";
			params.add(toAccount);
		}
		if (startDate != null || endDate != null) {
			logger.info("debug1");
			if(startDate != null && endDate != null) {
				methodName += "AndCreatedBetween";
				params.add(startDate);
				params.add(endDate);
				logger.info("debug2");
			} else if (startDate == null && endDate!= null) {
				methodName += "AndCreatedLessThanEqual";
				params.add(endDate);
				logger.info("debug3");
			}
			//NOTE: add here for more search criteria
		} 
		
		
		
		Method[] methods = TransferHistoryRepository.class.getMethods();
		Method m = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				m = method;
				logger.info("debug4" + m.toString() + m.getName());
				break;
			}
		}
		
		if (m != null) {
			logger.info("invoke method:" + methodName + " with parameters:"
					+ org.apache.commons.lang3.StringUtils.join(params, ","));
			try {
				logger.debug(billerId + " params size, billerid" + params.size());
				if (params.size() == 3) {
					return (Page<TransferHistory>) m.invoke(transferHistoryRepository, billerId, params.get(0), params.get(1),
							params.get(2), pageRequest);
				} else if (params.size() == 2) {
					return (Page<TransferHistory>) m.invoke(transferHistoryRepository, billerId, params.get(0), params.get(1), pageRequest);
				} else if (params.size() == 1) {
					return (Page<TransferHistory>) m.invoke(transferHistoryRepository, billerId, params.get(0), pageRequest);
				}

			} catch (Exception e) {
				logger.error("error on call method:" + methodName, e);
			}

		} else {
			logger.info("Not found the method:" + methodName);
		}
		return transferHistoryRepository.findByBillerId(billerId, pageRequest);
	}
	
	private Pageable getPageRequest(int position, int pageSize, String sortBy, String direction) {
		/*int page = 0;
		if (position > 0) {
			page = position / pageSize;
		}*/
		int page = position;
		logger.debug("getPageRequest" + sortBy);
		if (StringUtils.isEmpty(sortBy)) {
			logger.debug(direction);
			return PageRequest.of(page, pageSize);
		} else {
			return PageRequest.of(page, pageSize, Sort.Direction.fromString(direction), sortBy);
		}
	}
}
