package com.localtide.billsync.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccreditedBillerUser {

	@NotNull(message = "ID cannot be null")
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String userName;

    @NotBlank(message = "Biller Short Name cannot be blank")
    @Size(max = 50, message = "Biller Short Name must not exceed 50 characters")
    private String billerShortName;

}
