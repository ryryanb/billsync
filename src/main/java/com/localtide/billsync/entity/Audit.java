package com.localtide.billsync.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "b_audit")
public class Audit implements Serializable{

	private static final long serialVersionUID = 547403540710688785L;
	
	public enum AuditAction{
		LOGIN, PAYMENT_QUERY, CHANGE_PASSWORD, LOGOUT 
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
 
    //@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
    @Column(name = "created")
    private Date created;
    
    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private AuditAction action;
    
    @Column(name = "details")
    private String details;

}
