package com.monk_commerce.coupon_management_system.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResEntObjBxGyWiseCoupon {
	private Long couponId;
	private List<Product> buy_products;
	private List<Product> get_products;
	private Long repitationLimit;
}
