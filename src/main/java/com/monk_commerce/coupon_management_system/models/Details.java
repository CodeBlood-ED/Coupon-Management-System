package com.monk_commerce.coupon_management_system.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Details {

	private Long threshold;
	private Long discount;
	private Long product_id;
	private List<Product> buy_products;
	private List<Product> get_products;
	private Long repitation_limit;
	
	public Details(Long threshold, String discount) {
		this.threshold = threshold;
		this.discount = convertStringToLong(discount);
	}
	public Details(Long discount, Long product_id) {
		this.product_id = product_id;
		this.discount = discount;
	}
	
	public Details(List<Product> bproducts, List<Product> gproducts, Long repitation_limit) {
		this.buy_products = bproducts;
		this.get_products = gproducts;
		this.repitation_limit = repitation_limit;
	}
	private Long convertStringToLong(String discount) {
		return Long.parseLong(discount);
		
	}
}
