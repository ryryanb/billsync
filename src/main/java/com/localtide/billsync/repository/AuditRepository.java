package com.localtide.billsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localtide.billsync.entity.Audit;

public interface AuditRepository extends JpaRepository<Audit, Long> {
	
}
