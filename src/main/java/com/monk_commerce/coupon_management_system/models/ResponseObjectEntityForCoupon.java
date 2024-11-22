package com.monk_commerce.coupon_management_system.models;

import com.monk_commerce.coupon_management_system.entities.CartWiseCoupon;
import com.monk_commerce.coupon_management_system.entities.ProductWiseCoupon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseObjectEntityForCoupon {
	
	private Long threshold;
	private Long discount;
	private Long product_id;
	private Long couponId;
	private Long repitation_limit;

	public ResponseObjectEntityForCoupon(CartWiseCoupon cartWiseCoupons) {
		this.threshold = cartWiseCoupons.getThreshold();
		this.discount = cartWiseCoupons.getDiscount();
		this.couponId = cartWiseCoupons.getCouponId();
	}
	
	public ResponseObjectEntityForCoupon(ProductWiseCoupon productWiseCoupons) {
		this.product_id = productWiseCoupons.getProduct_id();
		this.discount = productWiseCoupons.getDiscount();
		this.couponId = productWiseCoupons.getCouponId();
	}
//	public ResponseObjectEntityForCoupon(BxGyWiseCoupon bxgyWiseCoupon) throws IOException{
//		this.buy_products = bxgyWiseCoupon.getBuyProductsAsList();
//		this.get_products = bxgyWiseCoupon.getGetProductsAsList();
//		this.couponId = bxgyWiseCoupon.getCouponId();
//		this.repitation_limit = bxgyWiseCoupon.getRepitationLimit();
//	}

}
