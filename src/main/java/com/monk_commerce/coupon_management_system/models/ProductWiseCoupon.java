package com.monk_commerce.coupon_management_system.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
public class ProductWiseCoupon {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long couponId;
	
	private Long product_id;
	private Long discount;
}
