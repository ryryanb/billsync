package com.localtide.billsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localtide.billsync.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
