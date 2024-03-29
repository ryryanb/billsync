package com.localtide.billsync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localtide.billsync.entity.Properties;

public interface PropertiesRepository extends JpaRepository<Properties, Long> {

	List<Properties> findByPropertyNameIn(List<String> names);
	
	Properties findByPropertyName(String name);
}
