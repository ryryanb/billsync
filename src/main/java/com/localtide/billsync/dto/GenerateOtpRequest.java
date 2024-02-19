package com.localtide.billsync.dto;

import lombok.Data;

@Data
public class GenerateOtpRequest {

	private String userName;
	private String token;

}
