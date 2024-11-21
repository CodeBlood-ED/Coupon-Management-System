package com.monk_commerce.coupon_management_system.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.monk_commerce.coupon_management_system.models.BxGyWiseCoupon;
import com.monk_commerce.coupon_management_system.models.Cart;
import com.monk_commerce.coupon_management_system.models.CartWiseCoupon;
import com.monk_commerce.coupon_management_system.models.Coupon;
import com.monk_commerce.coupon_management_system.models.Items;
import com.monk_commerce.coupon_management_system.models.ProductWiseCoupon;
import com.monk_commerce.coupon_management_system.models.RequestEntityObjCart;
import com.monk_commerce.coupon_management_system.models.ResponseObjectEntityForCoupon;
import com.monk_commerce.coupon_management_system.models.UpdatedCart;
import com.monk_commerce.coupon_management_system.repositories.BxGyWiseCouponRepository;
import com.monk_commerce.coupon_management_system.repositories.CartWiseCouponRepository;
import com.monk_commerce.coupon_management_system.repositories.ProductWiseCouponRepository;

@Service
public class CouponService {
	
	@Autowired
	CartWiseCouponRepository cwcrepo;
	@Autowired
	ProductWiseCouponRepository pwcrepo;
	@Autowired
	BxGyWiseCouponRepository bxgyrepo;
	
	public ResponseEntity<String> handleCartWiseCoupon(Coupon coupon) throws Exception {
	    if (coupon.getDetails() == null || coupon.getDetails().getThreshold() == null) {
	        throw new Exception("Missing threshold details for cart-wise coupon");
	    }
	    if(coupon.getExpiry_in() <= 0) {
	    	throw new Exception("Expiration limit should be greater that zero");
	    }

	    if (ifThresholdPresent(coupon.getDetails().getThreshold())) {
	        throw new Exception("Coupon with this threshold already exists");
	    }

	    CartWiseCoupon newCoupon = new CartWiseCoupon();
	    LocalDateTime currentDate = LocalDateTime.now();
	    LocalDateTime expiryDate = currentDate.plusDays((long)coupon.getExpiry_in());
	    newCoupon.setThreshold(coupon.getDetails().getThreshold());
	    newCoupon.setDiscount(coupon.getDetails().getDiscount());
	    newCoupon.setExpiration_date(expiryDate.toString());
	    createCartWiseCoupon(newCoupon);

	    return ResponseEntity.status(HttpStatus.CREATED).body("Cart-wise coupon successfully added");
	}
	
	public void createCartWiseCoupon(CartWiseCoupon coupon) {
		cwcrepo.save(coupon);
	}

	public Boolean ifThresholdPresent(Long thresholdValue) {
		return cwcrepo.findByThreshold(thresholdValue);
		
	}

	public ResponseEntity<String> handleProductWiseCoupon(Coupon coupon) throws Exception {
	    if (coupon.getDetails() == null || coupon.getDetails().getProduct_id() == null) {
	        throw new Exception("Missing product details for product-wise coupon");
	    }

	    if (isProductCouponExists(coupon.getDetails().getProduct_id())) {
	        throw new Exception("Coupon for this product already exists");
	    }

	    ProductWiseCoupon newCoupon = new ProductWiseCoupon();
	    newCoupon.setProduct_id(coupon.getDetails().getProduct_id());
	    newCoupon.setDiscount(coupon.getDetails().getDiscount());
	    createProductWiseCoupon(newCoupon);

	    return ResponseEntity.status(HttpStatus.CREATED).body("Product-wise coupon successfully added");
	}
	
	public void createProductWiseCoupon(ProductWiseCoupon coupon) {
		pwcrepo.save(coupon);
	}

	public Boolean isProductCouponExists(Long product_id) {
		return pwcrepo.findByProductId(product_id);
	}
	
	public List<ResponseObjectEntityForCoupon> retrieveCartSpecificCoupons() {
		List<CartWiseCoupon> coupons = cwcrepo.findAll();
	    List<ResponseObjectEntityForCoupon> response = new ArrayList<>();
	    for (CartWiseCoupon coupon : coupons) {
	        ResponseObjectEntityForCoupon responseObject = new ResponseObjectEntityForCoupon(coupon);
	        response.add(responseObject);
	    }
	    return response;
	}

	public List<ResponseObjectEntityForCoupon> retrieveProductSpecificCoupons() {
		List<ProductWiseCoupon> coupons = pwcrepo.findAll();
	    List<ResponseObjectEntityForCoupon> response = new ArrayList<>();
	    for (ProductWiseCoupon coupon : coupons) {
	        ResponseObjectEntityForCoupon responseObject = new ResponseObjectEntityForCoupon(coupon);
	        response.add(responseObject);
	    }
	    return response;
	}
	
	public ResponseObjectEntityForCoupon getCouponByCouponId(Long couponId) {
		return new ResponseObjectEntityForCoupon(cwcrepo.findCouponById(couponId));
	}

	public ResponseObjectEntityForCoupon getCouponByCouponIdInProductWiseCoupons(Long couponId) {
		return new ResponseObjectEntityForCoupon(pwcrepo.findCouponById(couponId));
	}

	public void updateCouponByCouponId(Long couponId, Long threshold, Long discount) {
		cwcrepo.updateCouponByCouponId(couponId, threshold, discount);
	}

	public void updateCouponByCouponIdInProductWiseCoupons(Long couponId, Long discount) {
		pwcrepo.updateCouponByCouponId(couponId, discount);
		
	}

	public ResponseEntity<String> handleBxgyCoupon(Coupon coupon) throws Exception {
		if (coupon.getDetails() == null) {
	        throw new Exception("Missing details for bxgy coupon");
	    }
		
		if(coupon.getDetails().getBuy_products() == null || coupon.getDetails().getGet_products() == null ) {
			throw new Exception("Null value for buy products or get products array");
		} else if(coupon.getDetails().getRepitation_limit() <= 0) {
			throw new Exception("Repitation Limit can not be less than or equals to 0");
		} else {
		    BxGyWiseCoupon newCoupon = new BxGyWiseCoupon();
		    newCoupon.setBuyProductsFromList(coupon.getDetails().getBuy_products());
		    newCoupon.setGetProductsFromList(coupon.getDetails().getGet_products());
		    newCoupon.setRepitationLimit(coupon.getDetails().getRepitation_limit());
		    createBxGyCoupon(newCoupon);
		}
	    
		return ResponseEntity.status(HttpStatus.OK).body("BxGy Coupon created succesfully");
	}
	public void createBxGyCoupon(BxGyWiseCoupon coupon) {
		bxgyrepo.save(coupon);
	}

	public ResponseEntity<UpdatedCart> applyCartWiseCoupon(RequestEntityObjCart cart, Long couponId) {
		UpdatedCart updatedCart = new UpdatedCart();
		CartWiseCoupon coupon = cwcrepo.findByCouponId(couponId);
		LocalDateTime currentDateAndTime = LocalDateTime.now();
		if(null!=coupon && LocalDateTime.parse(coupon.getExpiration_date()).isAfter(currentDateAndTime)) {
			Long total_price = 0L;
			Long final_price = 0L, total_discount = 0L;
			
			Items[] items = cart.getCart().getItems();
			for(Items eachItem: items) {
				total_price +=eachItem.getPrice()*eachItem.getQuantity();
			}
			if(total_price >= coupon.getThreshold()) {
				final_price = total_price - coupon.getDiscount();
				total_discount = coupon.getDiscount();
			}else {
				Cart value = new Cart(items);
				updatedCart.setUpdated_cart(value);
				updatedCart.setMessage("Coupon cannot be added as total price is less than the coupon threshold value : "+coupon.getThreshold());
				updatedCart.setTotal_price(total_price);
				updatedCart.setFinal_price(total_price);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCart);
			}
			Cart newCart = new Cart(items);
			updatedCart.setUpdated_cart(newCart);
			updatedCart.setTotal_price(total_price);
			updatedCart.setTotal_discount(total_discount);
			updatedCart.setFinal_price(final_price);
			updatedCart.setMessage("Coupon : " + couponId + " has been applied");
		} else {
			updatedCart.setMessage("Applied coupon has expired");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCart);
		}
		return ResponseEntity.status(HttpStatus.OK).body(updatedCart);
	}

	public ResponseEntity<UpdatedCart> applyProductWiseCoupon(RequestEntityObjCart cart, Long couponId) {
		
		return null;
	}
}
