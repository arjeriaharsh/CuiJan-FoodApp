package com.harsharjeria.cuijan.Models;

public class Users {
    public String uid, name, email, favfood;
    public int permLevel;

    public Users(){}

    public Users(String uid, String name, String email, String favfood, int permLevel) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.favfood = favfood;
        this.permLevel = permLevel;
    }

    public Users (String favfood){
        this.favfood = favfood;
    }

    public int getPermLevel() {
        return permLevel;
    }

    public void setPermLevel(int permLevel) {
        this.permLevel = permLevel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavfood() {
        return favfood;
    }

    public void setFavfood(String favfood) {
        this.favfood = favfood;
    }
}

