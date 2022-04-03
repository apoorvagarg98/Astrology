package com.example.astrology;

public class userModel {

    String gender,name,dateofbirth,placeofbirth,birthtime,email,mobile;

    public userModel(String name, String dateofbirth, String placeofbirth, String birthtime, String email, String mobile,String gender) {
        this.name = name;
        this.dateofbirth = dateofbirth;
        this.placeofbirth = placeofbirth;
        this.birthtime = birthtime;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public String getBirthtime() {
        return birthtime;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }
}
