package com.localtide.billsync.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VerifyOtpRequest {

    @NotBlank(message = "OTP cannot be blank")
    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid OTP format. Must be a 6-digit number.")
    private String otp;


    @NotBlank(message = "Username cannot be blank")
    private String userName;

}
