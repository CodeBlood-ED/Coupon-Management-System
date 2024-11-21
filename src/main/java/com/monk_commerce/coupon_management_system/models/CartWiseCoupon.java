package com.monk_commerce.coupon_management_system.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CartWiseCoupon {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long couponId;
	private Long threshold;
	private Long discount;
	private String expiration_date;
}
