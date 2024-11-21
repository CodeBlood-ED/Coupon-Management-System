package com.monk_commerce.coupon_management_system.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coupon {

	private String type;
	private Details details;
	private int expiry_in;
}
