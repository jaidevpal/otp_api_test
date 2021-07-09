package com.example.otpandlayouts.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.otpandlayouts.API.API_Interface;
import com.example.otpandlayouts.API.RetrofitClient;
import com.example.otpandlayouts.data_models.ProfileData_Model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileData_Repo {

    private final String TAG = this.getClass().getSimpleName();
    private MutableLiveData<ProfileData_Model> liveData = new MutableLiveData<>();

    public MutableLiveData<ProfileData_Model> getLiveData_ProfileData_Repo(Map<String, String> header) {

        API_Interface api_interface = RetrofitClient.getClient().create(API_Interface.class);
        Call<ProfileData_Model> call = api_interface.getProfileData(header);
        call.enqueue(new Callback<ProfileData_Model>() {
            @Override
            public void onResponse(Call<ProfileData_Model> call, Response<ProfileData_Model> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        liveData.setValue(response.body());
                        Log.e(TAG,"Response Success: " +response.body().getInvites().getProfiles().get(0).getGeneralInformation().getFirstName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileData_Model> call, Throwable t) {
                liveData.setValue(null);
                Log.e(TAG, "Response Failure: "+t);

            }
        });

        return liveData;
    }
}
