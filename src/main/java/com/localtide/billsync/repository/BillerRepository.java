package com.localtide.billsync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localtide.billsync.entity.Biller;

public interface BillerRepository extends JpaRepository<Biller, Long> {
	
	Biller findByBillerId(Long id);

	Biller findBillerByBillerId(Long id);

	List<Biller> findAll();

}
