package com.localtide.billsync.dto;

import com.localtide.billsync.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInformation {

	private String login;
	private boolean forceChangePassword;
	private String token;
	private String status;
	private String otp;
	private String billerId;
	
	public UserInformation(User user) {
		this.login = user.getUserName();
		this.forceChangePassword = user.isForceChangePassowrd();
	}

}
