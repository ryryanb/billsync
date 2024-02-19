package com.localtide.billsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localtide.billsync.entity.AccreditedBiller;

public interface AccreditedBillerRepository extends JpaRepository<AccreditedBiller, Long> {
	
	public AccreditedBiller getById(Integer id);
	
	public AccreditedBiller getByIdAndToken(Integer id, String token);
}
