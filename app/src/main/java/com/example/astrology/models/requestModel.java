package com.example.astrology.models;

public class requestModel {
    private String name,email,phone,bookedYouFor,durationInMin,DateOfBooking,status,paymentStatus;
    public int totalAmount;

    public requestModel(String name, String email, String phone, String bookedYouFor, String durationInMin, String DateOfBooking, int totalAmount, String status, String paymentStatus) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bookedYouFor = bookedYouFor;
        this.durationInMin = durationInMin;
        this.DateOfBooking = DateOfBooking;

        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentStatus = paymentStatus;
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



    public int getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
