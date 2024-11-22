package com.monk_commerce.coupon_management_system.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk_commerce.coupon_management_system.entities.CartWiseCoupon;
import com.monk_commerce.coupon_management_system.entities.ProductWiseCoupon;
import com.monk_commerce.coupon_management_system.models.CouponObj;
import com.monk_commerce.coupon_management_system.models.Items;
import com.monk_commerce.coupon_management_system.repositories.CartWiseCouponRepository;
import com.monk_commerce.coupon_management_system.repositories.ProductWiseCouponRepository;

@Service
public class CalculationService {
	
	@Autowired
	CartWiseCouponRepository cwcrepo;
	@Autowired
	ProductWiseCouponRepository pwcrepo;
	
	public CouponObj returnCartWiseCoupon(Items[] cartItems) throws Exception{
		Long totalCartAmount = 0L;
		if(cartItems != null && cartItems.length > 0) {
			for(Items eachItem: cartItems) {
				totalCartAmount += eachItem.getPrice()*eachItem.getQuantity();
			}
		}	
		CartWiseCoupon c =  cwcrepo.findCouponByCartValue(totalCartAmount);
		if(c.equals(null)) {
			throw new Exception("No coupons Found");
		}
		CouponObj coupon = new CouponObj(c.getCouponId(),"cart-wise",c.getDiscount());
		return coupon;
	}
	
	public List<CouponObj> returnProductWiseCoupon(Items[] cartItems) {
		List<CouponObj> coupons = new ArrayList<CouponObj>();
		for(Items eachItem:cartItems) {
			ProductWiseCoupon p = pwcrepo.findCouponByProductId(eachItem.getProduct_id());
			CouponObj coupon = new CouponObj(p.getCouponId(),"product-wise",p.getDiscount()*eachItem.getQuantity());
			coupons.add(coupon);
		}
		return coupons;
	}
}
