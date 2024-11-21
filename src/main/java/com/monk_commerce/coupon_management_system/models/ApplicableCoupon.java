package com.monk_commerce.coupon_management_system.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApplicableCoupon {
	
	private List<CouponObj> coupons;
}
