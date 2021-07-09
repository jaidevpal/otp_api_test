package com.example.otpandlayouts.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.otpandlayouts.Repositories.OTP_PhoneNo_for_User_Repo;
import com.example.otpandlayouts.data_models.User;

import java.util.HashMap;
import java.util.Map;

public class OTP_PhoneNo_for_User_VM extends AndroidViewModel {

    private OTP_PhoneNo_for_User_Repo otpPhoneNoForUserRepo;
    private MutableLiveData<User> mutableLiveData;

    public OTP_PhoneNo_for_User_VM(@NonNull @org.jetbrains.annotations.NotNull Application application) {
        super(application);
    }

    public LiveData<User> getUserLiveData(Map<String, String> header, User user){

        otpPhoneNoForUserRepo = new OTP_PhoneNo_for_User_Repo();
        mutableLiveData = otpPhoneNoForUserRepo.getUserMutableLiveData_Repo(header, user);

        return mutableLiveData;
    }

    public LiveData<User> getUserLiveDataOTP(Map<String, String> header, User user){

        otpPhoneNoForUserRepo = new OTP_PhoneNo_for_User_Repo();
        mutableLiveData = otpPhoneNoForUserRepo.getMutableLiveDataOTP_Repo(header, user);

        return mutableLiveData;
    }
}
