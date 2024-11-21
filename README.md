## <p style="text-align:center">__Coupon Management System by Monk Commerce__</p>

This task has been given to me by ___Monk Commerce___. The Task is to Build a RESTful API to manage and apply different types of discount coupons
(cart-wise, product-wise, and BxGy) for an e-commerce platform, with the ability to easily add
new types of coupons in the future.

__Teachnologies used :__

Java, Springboot, Spring Data JPA, MySQL

__Database schema :__

* Designed the database such that coupons will be stored in different tables based on specificity. For example cart-wise coupons will be stored in __cart_wise_coupon__ table. 
***

__Implemented RestAPIs (so far) :__

* POST __/api/coupons :__ to create coupons based on type present in the request body
* GET __/api/coupons/{type} :__ for fetching coupons based on type of coupon
* GET __/api/coupons/{type}/{id} :__ for fetching specific type of coupons e.g.
( __/api/coupons/cart-wise/1__ )
* PUT __/api/coupons/{type}/{id} :__ Implemented funtionality for updating cart-wise and product-wise coupons. In case admin wants to update a specific type existing coupon.
* POST  __/api/coupons/applicable-coupons :__ for feching applicable coupons based on _cart-wise_ as well as _product-wise_. Assuming an `Apply Coupons` button will be present in the Frontend and by clicking on it customer will see the applicable coupons based on the cart items. This api will fetch all the applicable coupons based on the products present in the cart and the total amount of the cart items.

* POST __/api/coupons/apply-coupon/{type}/{id}__ : Upon clicking on the `Apply Coupons` button customer will see all the applicable coupons with an `Apply` button. By clicking Apply, Frontend will form a POST request with type and id as path variable. This api is capable of calculating total_price, total_discount and final_price (_after applying discount as per coupon_). Finally in the response we will get to see the updated cart.

> Note : I was only able to implement above mentioned Restful api endpoints so far and I will continue implementing and updating the endpoints in future.

___

__Unimplemented RestAPIs and Functionalities:__

> Note : The cases which I have thought of implementing but due to less time of submission I was unable to implement these.

* GET __/api/coupons/bxgy :__ The implementaion of fetching a bxgy type coupon which includes deserialization of List<Product> object which I am sure I will be able to implement and further update the functionality of the endpoint.

* GET __/api/coupons/apply-coupon/bxgy/1 :__ In order to apply a bxgy type coupon again we need to fetch applicable by=xgy coupons and do calculations on it.

1. Introduced expiration_date field of type `Date()` to validate expiry of coupons in . At the time of creating coupons admin will have option to set expiration date for each coupon.



