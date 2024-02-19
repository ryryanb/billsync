package com.localtide.billsync.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PROPERTIES")
public class Properties implements Serializable {
	// PROPERTYNAME, PROPERTYVALUE, CREATEDDATE, MODIFIEDDATE, DESCRIPTION,
	// BANK_IDENTIFIER, PROPTYPE, STATUS
	private static final long serialVersionUID = -5266985080897018497L;

	@Id
	@Column(name = "PROPERTYNAME")
	private String propertyName;
	
	@Column(name = "PROPERTYVALUE")
	private String propertyValue;

}
