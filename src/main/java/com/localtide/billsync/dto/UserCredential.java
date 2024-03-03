package com.localtide.billsync.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCredential {
	
	@NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 20, message = "Username must be between 5 and 20 characters")
	private String username;
    
	@NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
    
}
