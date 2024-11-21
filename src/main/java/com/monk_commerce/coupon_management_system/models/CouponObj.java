package com.monk_commerce.coupon_management_system.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class CouponObj {

	private Long coupon_id;
	private String type;
	private Long discount;
}
