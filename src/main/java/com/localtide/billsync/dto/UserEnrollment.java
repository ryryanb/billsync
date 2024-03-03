package com.localtide.billsync.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserEnrollment {

	@NotBlank(message = "Login cannot be blank")
    @Size(max = 255, message = "Login must not exceed 255 characters")
    private String login;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 255, message = "Username must not exceed 255 characters")
    private String userName;

    @NotBlank(message = "Company Name cannot be blank")
    @Size(max = 255, message = "Company Name must not exceed 255 characters")
    private String companyName;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @NotBlank(message = "Contact Name cannot be blank")
    @Size(max = 255, message = "Contact Name must not exceed 255 characters")
    private String contactName;

    @NotBlank(message = "Phone cannot be blank")
    // Assuming a basic pattern for phone number, you might need to adjust based on your requirements
    @Pattern(regexp = "^\\d{10,15}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Bank ID cannot be blank")
    @Size(max = 255, message = "Bank ID must not exceed 255 characters")
    private String bankId;

    private boolean mustChangePassword;

    private boolean sendAccountInfo;

    private boolean generatePassword;
}
