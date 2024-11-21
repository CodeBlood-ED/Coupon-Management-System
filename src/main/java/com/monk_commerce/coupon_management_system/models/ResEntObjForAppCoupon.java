package com.monk_commerce.coupon_management_system.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResEntObjForAppCoupon {
	private String message;
	private String status;
	private List<CouponObj> applicable_coupons;
	
}
