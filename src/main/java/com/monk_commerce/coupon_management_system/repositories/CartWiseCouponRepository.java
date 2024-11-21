package com.monk_commerce.coupon_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.monk_commerce.coupon_management_system.models.CartWiseCoupon;

import jakarta.transaction.Transactional;

public interface CartWiseCouponRepository extends JpaRepository<CartWiseCoupon,Long> {

	@Query("SELECT COUNT(c) > 0 FROM CartWiseCoupon c WHERE c.threshold = :thresholdValue")
	Boolean findByThreshold(@Param("thresholdValue") Long thresholdValue);
	
	@Query("FROM CartWiseCoupon c WHERE c.threshold <= :cartValue ORDER BY c.threshold DESC LIMIT 1")
	CartWiseCoupon findCouponByCartValue(@Param("cartValue") Long cartValue);
	
	@Query("FROM CartWiseCoupon c WHERE c.couponId = :couponId")
	CartWiseCoupon findCouponById(Long couponId);

	@Transactional
	@Query("UPDATE CartWiseCoupon c SET c.threshold=:threshold, c.discount=:discount WHERE c.couponId=:couponId")
	void updateCouponByCouponId(Long couponId, Long threshold, Long discount);
	
	@Query(value="Select * FROM cart_wise_coupon c WHERE c.coupon_id = :couponId", nativeQuery=true)
	CartWiseCoupon findByCouponId(Long couponId);
}
