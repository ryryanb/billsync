package com.localtide.billsync.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {

	private String token;
	private String newPassword;

}
