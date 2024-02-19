package com.localtide.billsync.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {

	private String oldPassword;
	private String newPassword;
	
}
