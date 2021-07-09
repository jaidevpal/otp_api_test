package com.example.otpandlayouts.UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.otpandlayouts.R;
import com.example.otpandlayouts.ViewModels.OTP_PhoneNo_for_User_VM;
import com.example.otpandlayouts.data_models.User;
import com.example.otpandlayouts.databinding.ActivityOtpAndPhoneNoBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class OTP_And_PhoneNoActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private ActivityOtpAndPhoneNoBinding binding;
    private User user;

    private String CountryCode, number, phoneNumber, OTP;

    private OTP_PhoneNo_for_User_VM phoneNoForUser_ViewModel;
    private Boolean loadingState = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpAndPhoneNoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        phoneNoForUser_ViewModel = new ViewModelProvider(this).get(OTP_PhoneNo_for_User_VM.class);

        binding.buttonSubmitPhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(OTP_And_PhoneNoActivity.this, HomeScreen_Activity.class));

                CountryCode = binding.EditTextCountryCode.getText().toString().trim();
                number = binding.EditTextPhoneNumber.getText().toString().trim();
                phoneNumber = CountryCode + number;

                if (CountryCode.isEmpty()||CountryCode==null){
                    ShowSnackBar("Please enter Country Code", getResources().getColor(R.color.red));
                }else if (CountryCode.length()<2){
                    ShowSnackBar("Incorrect Country Code", getResources().getColor(R.color.light_red));
                }

                if (number.isEmpty() || number == null) {

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    ShowSnackBar("Please Enter Phone Number", getResources().getColor(R.color.light_red));
//                    Snackbar.make(binding.mainLayout, "hey", Snackbar.LENGTH_SHORT).show();
                } else {

                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    loadingStateToggle(true);

                    user = new User(phoneNumber);
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");

                    phoneNoForUser_ViewModel.getUserLiveData(header, user).observe(OTP_And_PhoneNoActivity.this, user1 -> {

                        User u = user1;
                        Log.e(TAG, "Response Success: " + u.getStatus());

                        if (u.getStatus()) {
                            loadingStateToggle(false);
                            ShowSnackBar("OTP Sent", getResources().getColor(R.color.green));

                            binding.tvOtpToPhoneNumber.setText(CountryCode + " " + number);
                            binding.imageButtonEdit.setVisibility(View.VISIBLE);
                            binding.tvHeadingInstruction.setText("Enter The OTP");
                            binding.EditTextCountryCode.setVisibility(View.INVISIBLE);
                            binding.EditTextPhoneNumber.setVisibility(View.INVISIBLE);
                            binding.EditTextOTP.setVisibility(View.VISIBLE);
                            binding.EditTextTimer.setVisibility(View.VISIBLE);

                            binding.buttonSubmitPhoneNo.setVisibility(View.INVISIBLE);
                            binding.buttonSubmitOTP.setVisibility(View.VISIBLE);
                        } else {
                            loadingStateToggle(false);
                            ShowSnackBar("Incorrect Phone Number", getResources().getColor(R.color.red));
                        }

                    });
                }
            }
        });

        binding.imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.tvOtpToPhoneNumber.setText(CountryCode + " " + number);
                binding.imageButtonEdit.setVisibility(View.INVISIBLE);
                binding.tvHeadingInstruction.setText("Enter Your Phone Number");


                binding.EditTextCountryCode.setVisibility(View.VISIBLE);
                binding.EditTextPhoneNumber.setVisibility(View.VISIBLE);
                binding.EditTextCountryCode.setText(CountryCode);
                binding.EditTextPhoneNumber.setText(number);


                binding.EditTextOTP.setVisibility(View.INVISIBLE);
                binding.EditTextTimer.setVisibility(View.INVISIBLE);

                binding.buttonSubmitPhoneNo.setVisibility(View.VISIBLE);
                binding.buttonSubmitOTP.setVisibility(View.INVISIBLE);

            }
        });

        binding.buttonSubmitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CountryCode = binding.EditTextCountryCode.getText().toString().trim();
                number = binding.EditTextPhoneNumber.getText().toString().trim();
                OTP = binding.EditTextOTP.getText().toString().trim();

                phoneNumber = CountryCode + number;
                if (OTP == null || OTP.isEmpty()) {
                    ShowSnackBar("Please Enter OTP", getResources().getColor(R.color.light_red));
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    loadingStateToggle(true);

                    user = new User(phoneNumber, OTP);
                    Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Cookie", "__cfduid=df9b865983bd04a5de2cf5017994bbbc71618565720");

                    phoneNoForUser_ViewModel.getUserLiveDataOTP(header, user).observe(OTP_And_PhoneNoActivity.this, user1 -> {

                        String token = user1.getToken();

                        if (token != null && (!token.isEmpty())) {
                            loadingStateToggle(false);
                            ShowSnackBar("Phone Number Verified", getResources().getColor(R.color.green));
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1500);
                                        Intent intent = new Intent(OTP_And_PhoneNoActivity.this, HomeScreen_Activity.class);
                                        intent.putExtra("Token", token);
                                        startActivity(intent);
                                        finish();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            Log.e(TAG, "Response Success: Token: " + token);
                        } else if (!user1.getStatus()) {
                            loadingStateToggle(false);
                            ShowSnackBar("Incorrect OTP", getResources().getColor(R.color.red));
                        }
                    });
                }


            }
        });

//        api_interface = RetrofitClient.getClient().create(API_Interface.class);
//        Call<User> call = api_interface.setData_Raw(header,user);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//
//                if (!response.isSuccessful()) {
//                    Log.e("Main Response: ", "| Error: " + response.code());
//
//                }
//                if (response.isSuccessful()) {
//
////                    Log.e("Header: ", "onResponse: "+response.headers());
//                    Log.e("Success Response: ", "Response Status: " + response.body().getStatus());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//                Log.e("Response Body: ", t.getLocalizedMessage());
//            }
//        });
    }

    private void ShowSnackBar(String message, int color) {

        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).setBackgroundTint(color).show();
    }

    private void loadingStateToggle(Boolean loadingState) {
        if (loadingState) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}