package com.localtide.billsync.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Signup {

	@NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Confirm Password cannot be blank")
    @Size(min = 8, message = "Confirm Password must be at least 8 characters long")
    private String confirmPassword;

    @NotBlank(message = "Biller ID cannot be blank")
    @Size(max = 255, message = "Biller ID must not exceed 255 characters")
    private String billerId;

}
