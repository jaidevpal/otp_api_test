package com.example.otpandlayouts.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.otpandlayouts.Repositories.ProfileData_Repo;
import com.example.otpandlayouts.data_models.ProfileData_Model;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ProfileData_VM extends AndroidViewModel {

    private ProfileData_Repo profileDataRepo;
    public ProfileData_VM(@NonNull @NotNull Application application) {
        super(application);
    }

    private MutableLiveData<ProfileData_Model> liveData;

    public LiveData<ProfileData_Model> getProfileData_LiveData(Map<String, String> header){

        if (liveData==null){
            profileDataRepo = new ProfileData_Repo();
            liveData = profileDataRepo.getLiveData_ProfileData_Repo(header);
        }

        return liveData;
    }
}
