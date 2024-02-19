package com.localtide.billsync.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localtide.billsync.entity.Audit;
import com.localtide.billsync.entity.Audit.AuditAction;
import com.localtide.billsync.entity.User;
import com.localtide.billsync.repository.AuditRepository;

import jakarta.transaction.Transactional;

@Service
public class AuditService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private AuditRepository auditRepository;

	
	@Transactional
	public void audit(User user, AuditAction action,String actionData) {
		Audit audit = new Audit();
		audit.setUser(user);
		audit.setAction(action);
		audit.setDetails(actionData);
		audit.setCreated(new Date());
		auditRepository.save(audit);
	}
	

}
