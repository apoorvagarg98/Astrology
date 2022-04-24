package com.example.astrology.models;

public class requestModel {
    String name,email,phone,bookedYouFor,durationInMin,DateOfBooking,timeOfBooking,totalAmount,status,paymenmtStatus;

    public requestModel(String name, String email, String phone, String bookedYouFor, String durationInMin, String DateOfBooking, String timeOfBooking, String totalAmount, String status, String paymenmtStatus) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bookedYouFor = bookedYouFor;
        this.durationInMin = durationInMin;
        this.DateOfBooking = DateOfBooking;
        this.timeOfBooking = timeOfBooking;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymenmtStatus = paymenmtStatus;
    }
    public requestModel(){}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBookedYouFor() {
        return bookedYouFor;
    }

    public String getDurationInMin() {
        return durationInMin;
    }

    public String getDateOfBooking() {
        return DateOfBooking;
    }

    public String getTimeOfBooking() {
        return timeOfBooking;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymenmtStatus() {
        return paymenmtStatus;
    }
}
