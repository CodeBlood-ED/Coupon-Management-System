package com.monk_commerce.coupon_management_system.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monk_commerce.coupon_management_system.models.Coupon;
import com.monk_commerce.coupon_management_system.models.CouponObj;
import com.monk_commerce.coupon_management_system.models.RequestEntityObjCart;
import com.monk_commerce.coupon_management_system.models.ResEntObjForAppCoupon;
import com.monk_commerce.coupon_management_system.models.ResponseObjectEntityForCoupon;
import com.monk_commerce.coupon_management_system.models.UpdatedCart;
import com.monk_commerce.coupon_management_system.services.CalculationService;
import com.monk_commerce.coupon_management_system.services.CouponService;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
	
	@Autowired
	CouponService couponService;
	@Autowired
	CalculationService calculationService;
	
	@PostMapping("")
	public ResponseEntity<String> createCoupon(@RequestBody Coupon coupon) {
	    try {
	        if (coupon == null || coupon.getType() == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid coupon data");
	        }
	        switch (coupon.getType().toLowerCase()) {
	            case "cart-wise":
	                return couponService.handleCartWiseCoupon(coupon);
	            case "product-wise":
	                return couponService.handleProductWiseCoupon(coupon);
	            case "bxgy":
	            	return couponService.handleBxgyCoupon(coupon);
	            default:
	                throw new Exception("Unknown coupon type");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
	    }
	}
	
	@GetMapping("{type}")
	public ResponseEntity<List<ResponseObjectEntityForCoupon>> retrieveCoupons(@PathVariable("type") String type) throws Exception{
		List<ResponseObjectEntityForCoupon> coupons = new ArrayList<ResponseObjectEntityForCoupon>();
		try {
			if (type == null ) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ArrayList<ResponseObjectEntityForCoupon>());
			}
			switch(type.toLowerCase()) {
			case "cart-wise":
				coupons = couponService.retrieveCartSpecificCoupons();
				break;
			case "product-wise":
				coupons = couponService.retrieveProductSpecificCoupons();
				break;
			default:
				throw new Exception ("Unknown coupon type");
			}
			if (coupons.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(coupons);
	        }
	        return ResponseEntity.ok(coupons);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(coupons);
	    }
	}
	
	@GetMapping("{type}/{id}")
	public ResponseEntity<ResponseObjectEntityForCoupon> getCouponByCouponId(@PathVariable("type") String type, @PathVariable("id") Long couponId) {
		try {
			ResponseObjectEntityForCoupon coupon = new ResponseObjectEntityForCoupon();
			switch(type) {
				case "cart-wise":
					coupon = couponService.getCouponByCouponId(couponId);
					break;
				case "product-wise":
					coupon = couponService.getCouponByCouponIdInProductWiseCoupons(couponId);
					break;
				default:
					throw new Exception("Unknown type of coupon"+ type);
			}
			return ResponseEntity.status(HttpStatus.OK).body(coupon);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@PutMapping("{type}/{id}")
	public ResponseEntity<String> updateCouponDetails(@PathVariable("type") String type, @PathVariable("id") Long couponId, @RequestBody Coupon coupon){
		try {
			switch(type) {
				case "cart-wise":
					couponService.updateCouponByCouponId(couponId, coupon.getDetails().getThreshold(), coupon.getDetails().getDiscount());
					break;
				case "product-wise":
					couponService.updateCouponByCouponIdInProductWiseCoupons(couponId, coupon.getDetails().getDiscount());
					break;
				default:
					throw new Exception("Unknown type of coupon"+ type);
			}
			return ResponseEntity.status(HttpStatus.OK).body("Coupon Id : "+couponId+" has been updated");
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500 Internal Server Error");
		}
	}
	
	@PostMapping("applicable-coupons")
	public ResponseEntity<ResEntObjForAppCoupon> applicableCoupons(@RequestBody RequestEntityObjCart cart) {
		try {
			ResEntObjForAppCoupon obj = new ResEntObjForAppCoupon();
			List<CouponObj> productWiseCoupons = calculationService.returnProductWiseCoupon(cart.getCart().getItems());
			CouponObj cartWiseCoupon = calculationService.returnCartWiseCoupon(cart.getCart().getItems());
			List<CouponObj> coupons = new ArrayList<CouponObj>();
			coupons.add(cartWiseCoupon);
			for(CouponObj coupon: productWiseCoupons) {
				coupons.add(coupon);
			}
			obj.setApplicable_coupons(new ArrayList<CouponObj>(coupons));
			obj.setMessage("success");
			obj.setStatus("200");
			return ResponseEntity.status(HttpStatus.OK).body(obj);
		} catch(Exception e) {
			ResEntObjForAppCoupon obj = new ResEntObjForAppCoupon();
			obj.setMessage("No coupons found");
			obj.setStatus("500 Internal Server Error");
			obj.setApplicable_coupons(new ArrayList<CouponObj>());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(obj);
		}
		
	}
	
	@PostMapping("apply-coupon/{type}/{id}")
	public ResponseEntity<UpdatedCart> returnUpdatedCart(@PathVariable("type") String type, @PathVariable("id") Long couponId,
			@RequestBody RequestEntityObjCart cart) throws Exception{
		try {
			UpdatedCart errorObj = new UpdatedCart();
			switch(type) {
				case "cart-wise":
					return couponService.applyCartWiseCoupon(cart, couponId);
				case "product-wise":
					return couponService.applyProductWiseCoupon(cart, couponId);
				default:
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorObj);
			}
		} catch(Exception e) {
			UpdatedCart errorObj = new UpdatedCart();
			errorObj.setMessage("Error : "+e.getMessage());
			errorObj.setUpdated_cart(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorObj);
		}
	}
}

