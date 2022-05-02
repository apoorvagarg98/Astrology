package com.example.astrology.models;

public class Post {
   public Float  year, month, day, hour, minute; //latitude, longitude,ayanamsha, timezone;
  // public String sunrise, sunset;


    public Post(Float year, Float month, Float day, Float hour, Float minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public Float getYear() {
        return year;
    }

    public Float getMonth() {
        return month;
    }

    public Float getDay() {
        return day;
    }

    public Float getHour() {
        return hour;
    }

    public Float getMinute() {
        return minute;
    }
}
