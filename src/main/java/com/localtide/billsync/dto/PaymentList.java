package com.localtide.billsync.dto;

import java.util.Map;


public class PaymentList extends BaseList<Payment>{

	private Map<String, String> totalAmount;

	private Map<String, String> getTotalAmount() {
		return totalAmount;
	}

	private void setTotalAmount(Map<String, String> totalAmount) {
		this.totalAmount = totalAmount;
	}

}
