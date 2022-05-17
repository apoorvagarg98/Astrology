package com.example.astrology.models;

public class userModel {

    String gender,name,dateofbirth,placeofbirth,birthtime,email,mobile,link;

    public userModel(String name, String dateofbirth, String placeofbirth, String birthtime, String email, String mobile,String gender,String link) {
        this.name = name;
        this.dateofbirth = dateofbirth;
        this.placeofbirth = placeofbirth;
        this.birthtime = birthtime;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.link = link;
    }
    public userModel()
    {

    }

    public String getName() {
        return name;
    }
    public String getLink() {
        return link;
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
