package com.localtide.billsync.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BillSearch {
//Allow Third party provider to search on customer third party bill account number,
//and get back a list of payment history of the bank customer. 
	//Each element of the list will contain the Biller ID, Customer account number, 
	//Amount, Date-Time payment made and payment details.
	//4. Allow third party provider to search by Date (defaults to current) 
	//and get back a list of the transactions made (similar to point 3) , the total amount paid on that date etc
	//


	    @NotBlank(message = "Bill Account Number cannot be blank")
	    @Size(max = 255, message = "Bill Account Number must not exceed 255 characters")
	    private String billAccountNumber;

	    @NotBlank(message = "Biller ID cannot be blank")
	    @Size(max = 255, message = "Biller ID must not exceed 255 characters")
	    private String billerId;

	    // Assuming that the date format is a string (adjust accordingly based on your actual date handling)
	    private String fromDate;

	    // Assuming that the date format is a string (adjust accordingly based on your actual date handling)
	    private String toDate;

	    // You might want to validate the sortBy field based on your specific criteria
	    private String sortBy;

	    private String search;

	    @PositiveOrZero(message = "Page number must be a positive or zero integer")
	    private int page;

	    // You might want to validate the sortDirection field based on your specific criteria
	    private String sortDirection;

	    @PositiveOrZero(message = "Per page value must be a positive or zero integer")
	    private int perPage;

	    @Size(max = 255, message = "Reference Number must not exceed 255 characters")
	    private String refNum;
	}


