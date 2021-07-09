package com.example.otpandlayouts.API;

import com.example.otpandlayouts.data_models.ProfileData_Model;
import com.example.otpandlayouts.data_models.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface API_Interface {

    @POST("phone_number_login")
    Call<User> setData_Raw(@HeaderMap Map<String, String> header,
                           @Body User user);

    @POST("verify_otp")
    Call<User> setData_Raw_OTP(@HeaderMap Map<String, String> header,
                               @Body User user);

    @GET("test_profile_list")
    Call<ProfileData_Model> getProfileData(@HeaderMap Map<String, String> header);
}
