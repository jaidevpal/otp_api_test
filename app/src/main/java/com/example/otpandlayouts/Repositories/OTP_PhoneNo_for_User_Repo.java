package com.example.otpandlayouts.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.otpandlayouts.API.API_Interface;
import com.example.otpandlayouts.API.RetrofitClient;
import com.example.otpandlayouts.data_models.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTP_PhoneNo_for_User_Repo {

    private final String TAG = this.getClass().getSimpleName();
    private HashMap<String, String> hearder = new HashMap<>();


    private MutableLiveData<User> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getUserMutableLiveData_Repo(Map<String, String> hearder, User user) {
        API_Interface api_interface = RetrofitClient.getClient().create(API_Interface.class);
        Call<User> call = api_interface.setData_Raw(hearder, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                    Log.e(TAG, "Response Success: " + response.body().getStatus());
                } else if (!response.isSuccessful()){
                    Log.e(TAG, "Response Error: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mutableLiveData.setValue(null);
                Log.e(TAG, "Response Failure: " + t);
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<User> getMutableLiveDataOTP_Repo(Map<String, String> hearder, User user) {

        API_Interface api_interface = RetrofitClient.getClient().create(API_Interface.class);
        Call<User> call = api_interface.setData_Raw_OTP(hearder, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body().getToken() == null || response.body().getToken().isEmpty()) {
                        User user = new User(false);
                        mutableLiveData.setValue(user);
                        Log.e(TAG, "Response Error: Token: " + response.code());
                    } else {
                        mutableLiveData.setValue(response.body());
                        Log.e(TAG, "Response Success: Token: " + response.body().getToken());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (call.isExecuted()) {
                    mutableLiveData.setValue(null);
                    Log.e(TAG, "Response Failure: " + t);
                }
            }
        });

        return mutableLiveData;
    }
}
