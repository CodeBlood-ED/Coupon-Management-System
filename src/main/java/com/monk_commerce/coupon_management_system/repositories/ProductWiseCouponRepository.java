package com.monk_commerce.coupon_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monk_commerce.coupon_management_system.entities.ProductWiseCoupon;

@Repository
public interface ProductWiseCouponRepository extends JpaRepository<ProductWiseCoupon,Long> {

	@Query("SELECT COUNT(c) > 0 FROM ProductWiseCoupon c WHERE c.product_id = :productId")
	Boolean findByProductId(Long productId);

	@Query("FROM ProductWiseCoupon p where p.product_id = :product_id")
	ProductWiseCoupon findCouponByProductId(Long product_id);
	
	@Query("FROM ProductWiseCoupon p WHERE p.couponId = :couponId")
	ProductWiseCoupon findCouponById(Long couponId);

	@Query("UPDATE ProductWiseCoupon p SET p.discount=:discount WHERE p.couponId=:couponId")
	void updateCouponByCouponId(Long couponId, Long discount);
}
