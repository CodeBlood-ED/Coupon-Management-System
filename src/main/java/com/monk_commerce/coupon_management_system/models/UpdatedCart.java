package com.monk_commerce.coupon_management_system.models;

public class UpdatedCart {
	private Cart updated_cart;
    private Long total_price;
    private Long total_discount;
    private Long final_price;
    private String message;
    
    public Cart getUpdated_cart() {
    	return updated_cart;
    }
    public void setUpdated_cart(Cart updated_cart) {
    	this.updated_cart = updated_cart;
    }
    public Long getTotal_price() {
    	return total_price;
    }
    public void setTotal_price(Long total_price) {
    	this.total_price = total_price;
    }
    public Long getTotal_discount() {
    	return total_discount;
    }
    public void setTotal_discount(Long total_discount) {
    	this.total_discount = total_discount;
    }
    public Long getFinal_price() {
    	return final_price;
    }
    public void setFinal_price(Long final_price) {
    	this.final_price = final_price;
    }
    public String getMessage() {
    	return message;
    }
    public void setMessage(String message) {
    	this.message = message;
    }
}
