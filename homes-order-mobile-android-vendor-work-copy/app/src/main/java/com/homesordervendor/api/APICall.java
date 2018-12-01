package com.homesordervendor.api;

import com.homesordervendor.bankinformation.model.BankInfoRequest;
import com.homesordervendor.orders.model.CancelOrder;
import com.homesordervendor.orders.model.OrderItem;
import com.homesordervendor.product.addproduct.model.AddProductResponse;
import com.homesordervendor.product.addproduct.model.CategoryResposnse;
import com.homesordervendor.product.addproduct.model.ProductSubmit;
import com.homesordervendor.product.featured.model.BlockedDates;
import com.homesordervendor.product.featured.model.FeatureEligible;
import com.homesordervendor.product.featured.model.FeaturedRequest;
import com.homesordervendor.product.featured.model.SubcategoryID;
import com.homesordervendor.product.productlist.model.ProductListResponse;
import com.homesordervendor.product.promotion.model.ProductID;
import com.homesordervendor.product.promotion.model.Promotion;
import com.homesordervendor.product.promotion.model.PromotionResponse;
import com.homesordervendor.user.accountinformation.model.AccountInfo;
import com.homesordervendor.user.accountinformation.model.CitiesResponse;
import com.homesordervendor.user.dashboard.OrderCount;
import com.homesordervendor.user.deliveryslot.model.DeliverySlot;
import com.homesordervendor.user.deliveryslot.model.DeliverySlotResponse;
import com.homesordervendor.user.forgotpassword.model.ForgotPassword;
import com.homesordervendor.user.holidayplanner.model.HolidayPlanner;
import com.homesordervendor.user.holidayplanner.model.HolidayPlannerResponse;
import com.homesordervendor.user.login.model.Login;
import com.homesordervendor.user.login.model.User;
import com.homesordervendor.user.register.model.Register;
import com.homesordervendor.user.resetpassword.model.ResetPassword;
import com.homesordervendor.user.shippingarea.model.Country;
import com.homesordervendor.user.shippingarea.model.ShippingAreaResponse;
import com.homesordervendor.user.subscribe.model.PlanResponse;
import com.homesordervendor.user.subscribe.model.Subscribe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by mac on 2/21/18.
 */

public interface APICall {

    @POST("vendor/login")
    Observable<Response<User>> login(@Body Login login);

    @POST("vendor/me/register")
    Observable<Response<User>> register(@Body Register register);

    @POST("vendor/forgotpassword")
    Observable<Response<GeneralResponse>> forgotPassword(@Body ForgotPassword forgotPassword);

    @GET("vendor/subscription")
    Observable<Response<PlanResponse>> plans();

    @POST("vendor/subscription/{token}")
    Observable<Response<GeneralResponse>> subscribe(@Path("token") String token, @Body Subscribe subscribe);

    @GET("vendor/getdeliveryslot/{token}")
    Observable<Response<DeliverySlotResponse>> deliverySlotInformation(@Path("token") String token);

    @POST("vendor/adddeliveryslot/{token}")
    Observable<Response<GeneralResponse>> deliverySlotUpdate(@Path("token") String token,@Body DeliverySlot deliverySlot);

    @GET("vendor/me/coverarea/{token}")
    Observable<Response<ShippingAreaResponse>> coverArea(@Path("token") String token);

    @POST("vendor/me/coverarea/update/{token}")
    Observable<Response<ShippingAreaResponse>> coverAreaUpdate(@Path("token") String token, @Body List<Country> countries);

    @GET("vendor/product/category")
    Observable<Response<CategoryResposnse>> category();

    @GET("vendor/me/{token}")
    Observable<Response<User>> accountInformation(@Path("token") String token);

    @POST("vendor/me/update/{token}")
    Observable<Response<User>> updateUser(@Path("token") String token,@Body Register
            register);

    @GET("vendor/cities")
    Observable<Response<CitiesResponse>> cities();

    @POST("vendor/me/addressupdate/{token}")
    Observable<Response<User>> addressUpdate(@Path("token") String token,@Body AccountInfo accountInfo);

    @POST("vendor/product/add/{token}")
    Observable<Response<AddProductResponse>> addProduct(@Path("token") String token, @Body ProductSubmit productSubmit);

    @POST("vendor/product/update/{token}")
    Observable<Response<AddProductResponse>> updateProduct(@Path("token") String token, @Body
            ProductSubmit productSubmit);

    @GET("vendor/me/product/{token}")
    Observable<Response<ProductListResponse>> productList(@Path("token") String token);

    @POST("setpromotion/{token}")
    Observable<Response<GeneralResponse>> setPromotion(@Path("token") String token,@Body Promotion promotion);

    @POST("promotiondetail/{token}")
    Observable<Response<PromotionResponse>> promotionDetail(@Path("token") String token, @Body ProductID productID);

    @GET("vendor/me/promotionproduct/{token}")
    Observable<Response<ProductListResponse>> promotionProductList(@Path("token") String token);

    @GET("vendor/feature/eligble/{productid}/{token}")
    Observable<Response<FeatureEligible>> featureEligble(@Path("productid") String productid, @Path("token") String token);

    @GET("vendor/product/feature/{token}")
    Observable<Response<ProductListResponse>> productFeatureList(@Path("token") String token);

    @POST("vendor/product/feature/available")
    Observable<Response<BlockedDates>> featureAvailable(@Body SubcategoryID subcategoryID);

    @POST("vendor/product/setfeature/{token}")
    Observable<Response<GeneralResponse>> setFeature(@Path("token") String token, @Body FeaturedRequest featuredRequest);

    @GET("stoppromotion/{productId}/{token}")
    Observable<Response<GeneralResponse>> stopPromotion(@Path("productId") String productid, @Path("token") String token);

    @GET("vendor/product/delete/{productId}/{token}")
    Observable<Response<GeneralResponse>> productDelete(@Path("productId") String productid, @Path("token") String token);

    @GET("vendor/me/order/{token}/{statuskey}")
    Observable<Response<List<OrderItem>>> orderList(@Path("token") String token, @Path
            ("statuskey") String statuskey);

    @GET("vendor/me/orderbyid/{id}/{token}")
    Observable<Response<OrderItem>> orderById(@Path("id") String id, @Path
            ("token") String token);

    @GET("vendor/me/orderitem/{itemid}/{statuskey}/{token}")
    Observable<Response<GeneralResponse>> changeOrderItemStatus(@Path("itemid") String itemid, @Path
            ("statuskey") String statuskey, @Path("token") String token);

    @POST("vendor/me/bankinfoupdate/{token}")
    Observable<Response<User>> bankInfoUpdate(@Path("token") String token, @Body BankInfoRequest bankInfo);

    @POST("vendor/me/passwordreset/{token}")
    Observable<Response<GeneralResponse>> passwordReset(@Path("token") String token, @Body ResetPassword resetPassword);

    @GET("vendor/me/order/{id}/changestatus/{statuskey}/{token}")
    Observable<Response<GeneralResponse>> changeOrderStatus(@Path("id") String id, @Path
            ("statuskey") String statuskey, @Path("token") String token);

    @POST("vendor/me/order/cancel/{id}/{token}")
    Observable<Response<GeneralResponse>> cancelOrder(@Path("id") String id, @Path("token")
            String token, @Body CancelOrder cancelOrder);

    @GET("vendor/me/order/complete/{id}/{token}")
    Observable<Response<GeneralResponse>> completeOrder(@Path("id") String id, @Path("token")
            String token);

    @POST("vendor/addoffdayplan/{token}")
    Observable<Response<GeneralResponse>> addOffDayPlan(@Path("token") String token, @Body
            HolidayPlanner holidayPlanner);

    @PUT("vendor/updateoffdayplan/{token}")
    Observable<Response<GeneralResponse>> updateOffDayPlan(@Path("token") String token, @Body
            HolidayPlanner holidayPlanner);

    @GET("vendor/getoffdayplan/{token}")
    Observable<Response<HolidayPlannerResponse>> getOffDayPlan(@Path("token")
            String token);

    @GET("vendor/deleteoffdayplan/{planId}/{token}")
    Observable<Response<GeneralResponse>> deleteOffDayPlan(@Path("planId") String planId, @Path("token")
            String token);

    @GET("vendor/me/ordercount/{token}")
    Observable<Response<OrderCount>> orderCount(@Path("token") String token);



}


