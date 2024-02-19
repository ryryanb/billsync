package com.localtide.billsync.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BILLER")
public class Biller implements Serializable {

	private static final long serialVersionUID = -2937682679280608909L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BILLER_ID")
	private Long billerId;

	@Column(name = "DESCRIPTION")
	private String billerName;

	@Column(name = "ACCT_NUM")
	private String accountNumber;

	@Column(name = "ACCOUNT_TYPE")
	private Integer accountType;

	@Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

	@Column(name = "PHONE_NUMBER")
    private String phoneNumber;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "BANK_IDENTIFIER")
	private String bankIdentifier;

	@Column(name = "BANK_ROUTINGNO")
	private String bankRoutingNo;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "BILLER_SHORT_NAME")
	private String billerShortName;

	@Column(name = "USER_CIF")
	private String userCif;
	
	@Column(name = "BILLER_TYPE")
	private Integer billerType;

}
