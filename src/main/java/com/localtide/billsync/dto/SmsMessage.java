package com.localtide.billsync.dto;

import lombok.Data;

@Data
public class SmsMessage {

	private String phone;
	private String message;

}
