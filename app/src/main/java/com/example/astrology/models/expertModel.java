package com.example.astrology.models;

import android.net.Uri;

public class expertModel {

    public String exnames,exmobile, exemails,userId ,selection ,exadress ,expincode ,exbirthdate,stamt,gender;
    public String imageUriadhar;
    public String imageUripan;
    public String imageUricertificate,stexperience;

    public expertModel(String exnames, String exmobile, String exemails, String userId, String selection, String exadress, String expincode, String exbirthdate, String stamt, String gender, String imageUriadhar, String imageUripan, String imageUricertificate, String stexperience) {
        this.exnames = exnames;
        this.exmobile = exmobile;
        this.exemails = exemails;
        this.userId = userId;
        this.selection = selection;
        this.exadress = exadress;
        this.expincode = expincode;
        this.exbirthdate = exbirthdate;
        this.stamt = stamt;
        this.gender = gender;
        this.imageUriadhar = imageUriadhar;
        this.imageUripan = imageUripan;
        this.imageUricertificate = imageUricertificate;
        this.stexperience = stexperience;
    }
    public expertModel(){

    }


    public String getExnames() {
        return exnames;
    }
    public String getExperience() {
        return stexperience;
    }

    public String getExmobile() {
        return exmobile;
    }

    public String getExemails() {
        return exemails;
    }

    public String getUserId() {
        return userId;
    }

    public String getSelection() {
        return selection;
    }

    public String getExadress() {
        return exadress;
    }

    public String getExpincode() {
        return expincode;
    }

    public String getExbirthdate() {
        return exbirthdate;
    }

    public String getStamt() {
        return stamt;
    }

    public String getGender() {
        return gender;
    }

    public String getImageUriadhar() {
        return imageUriadhar;
    }

    public String getImageUripan() {
        return imageUripan;
    }

    public String getImageUricertificate() {
        return imageUricertificate;
    }
}
