package com.monk_commerce.coupon_management_system.models;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BxGyWiseCoupon {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long couponId;
	private byte[] buy_products;
	private byte[] get_products;
	private Long repitationLimit;
	
    public List<Product> getBuyProductsAsList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new String(buy_products), new TypeReference<List<Product>>() {});
    }
    public List<Product> getGetProductsAsList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new String(get_products), new TypeReference<List<Product>>() {});
    }

    public void setBuyProductsFromList(List<Product> buyProductList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.buy_products = objectMapper.writeValueAsString(buyProductList).getBytes();
    }
    public void setGetProductsFromList(List<Product> GetProductList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.get_products = objectMapper.writeValueAsString(GetProductList).getBytes();
    }
    
}
