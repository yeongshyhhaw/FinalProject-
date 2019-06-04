package com.example.goldenbooking;

public class User {
    static String Name,Phone,Email,Password,Longtitude,Latitude,Location;
    User(String name, String phoneNumber,String email,String password){
        this.Name = name;
        this.Phone= phoneNumber;
        this.Email = email;
        this.Password = password;



    }
    public static void setEmail(String email) {
        Email=email;
    }


    public static String getEmail() {
        return Email;
    }


}


