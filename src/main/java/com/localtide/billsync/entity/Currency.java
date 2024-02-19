package com.localtide.billsync.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CURRENCY")
public class Currency implements Serializable {

	// CURRENCYCODE, CURRENCYSYMBOL, BANK_IDENTIFIER, ISOCODE
	private static final long serialVersionUID = -7988859848027034600L;

	@Id
	@Column(name = "CURRENCYCODE")
	private String currencyCode;
	@Column(name = "CURRENCYSYMBOL")
	private String currencySymbol;
	@Column(name = "ISOCODE")
	private String isoCode;

}
