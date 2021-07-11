package com.example.otpandlayouts.UI.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.otpandlayouts.UI.HomeScreen_Activity;
import com.example.otpandlayouts.ViewModels.ProfileData_VM;
import com.example.otpandlayouts.View_Adapters.NotesScreen_Likes_Adapter;
import com.example.otpandlayouts.data_models.ProfileData_Model;
import com.example.otpandlayouts.databinding.NotesscreenFragmentLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class NotesScreen_Fragment extends Fragment {

    private NotesscreenFragmentLayoutBinding binding;

    private final String TAG = this.getClass().getSimpleName();
    private ProfileData_VM profileData_vm;

    private String Token;
    private GridLayoutManager gridLayoutManager;
    private HomeScreen_Activity activityBinding;

    public View onCreateView(@Nullable @org.jetbrains.annotations.Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = NotesscreenFragmentLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        loadingStateToggle(true);
        init();
        return view;
    }

    private void loadingStateToggle(boolean progressState) {
        if (progressState){
            binding.progressBar2.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar2.setVisibility(View.INVISIBLE);
        }
    }

    private void init() {
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        binding.recyclerView.setLayoutManager(gridLayoutManager);

        Token = getActivity().getIntent().getStringExtra("Token");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", Token);
        header.put("Cookie", "__cfduid=df9b865983bd04a5de2cf5017994bbbc71618565720");

        profileData_vm = new ViewModelProvider(this).get(ProfileData_VM.class);
        profileData_vm.getProfileData_LiveData(header).observe(getViewLifecycleOwner(), profileDataModel -> {

            if (profileDataModel != null) {
//                binding.name.setText(profileDataModel.getInvites().getProfiles().get(0).getGeneralInformation().getFirstName());
                Log.e(TAG, "Response Success: " + profileDataModel.getInvites().getProfiles().get(0).getGeneralInformation().getFirstName());
                Log.e(TAG, "Response Success: " + profileDataModel.getInvites().getProfiles().get(0).getGeneralInformation().getGender());

                updateUI(profileDataModel);
            } else {
                Log.e(TAG, "Response Fail: ");
            }

        });


    }

    private void updateUI(ProfileData_Model profileDataModel) {

//        UI will be updated here!

        String avatarUrl = profileDataModel.getInvites().getProfiles().get(0).getPhotos().get(0).getPhoto();
        String name = profileDataModel.getInvites().getProfiles().get(0).getGeneralInformation().getFirstName();
        String age = String.valueOf(profileDataModel.getInvites().getProfiles().get(0).getGeneralInformation().getAge());

        Picasso.get().load(avatarUrl).centerCrop().fit().into(binding.imageViewInvites);
        binding.tvNameInvite.setText(name + ", " + age);

        String avatarUrl0 = profileDataModel.getLikes().getProfiles().get(0).getAvatar();
        String name0 = profileDataModel.getLikes().getProfiles().get(0).getFirstName();
        String avatarUrl1 = profileDataModel.getLikes().getProfiles().get(1).getAvatar();
        String name1 = profileDataModel.getLikes().getProfiles().get(1).getFirstName();

        Picasso.get().load(avatarUrl0).centerCrop().fit().into(binding.imageAvatarBottom0);
        binding.tvNameBottom0.setText(name0);
        Picasso.get().load(avatarUrl1).centerCrop().fit().into(binding.imageAvatarBottom1);
        binding.tvNameBottom1.setText(name1);

        String anotherName = profileDataModel.getLikes().getProfiles().get(0).getFirstName();
        Log.e(TAG, "Response Name: " + anotherName);

//        binding.recyclerView.setAdapter(new NotesScreen_Likes_Adapter(getActivity().getApplicationContext(), profileDataModel.getLikes().getProfiles()));

//        Toast.makeText(getContext(), "UI updated!!!", Toast.LENGTH_SHORT).show();
        loadingStateToggle(false);
         binding.mainLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
