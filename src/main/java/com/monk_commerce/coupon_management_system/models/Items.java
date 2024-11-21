package com.monk_commerce.coupon_management_system.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Items {

	private Long product_id;
	private int quantity;
	private int price;
}
