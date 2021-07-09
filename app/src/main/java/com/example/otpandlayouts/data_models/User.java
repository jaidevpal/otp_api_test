package com.example.otpandlayouts.data_models;

public class User {

    private String number;
    private String otp;

    private Boolean status;
    private String token;

    public User(String number) {
        this.number = number;
    }

    public User(Boolean status) {
        this.status = status;
    }

    public User(String number, String otp){
        this.number = number;
        this.otp = otp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
