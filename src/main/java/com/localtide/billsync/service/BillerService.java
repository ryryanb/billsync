package com.localtide.billsync.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localtide.billsync.entity.Biller;
import com.localtide.billsync.repository.BillerRepository;

@Service
public class BillerService {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private BillerRepository billerRepository;
	
	public Biller getByBillerId(Long id) {
		logger.debug("getByBillerId: " + id);
		return billerRepository.findByBillerId(id);
	}

	public List<Biller> getAllBillers() {
		logger.debug("getAllBillers");
		return billerRepository.findAll();
	}

}
