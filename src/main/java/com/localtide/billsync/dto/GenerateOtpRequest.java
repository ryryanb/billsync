package com.localtide.billsync.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GenerateOtpRequest {

	@NotBlank(message = "Username cannot be blank")
    private String userName;

    @NotBlank(message = "Token cannot be blank")
    @Pattern(
        regexp = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]*$",
        message = "Invalid token format"
    )
    private String token;

}
