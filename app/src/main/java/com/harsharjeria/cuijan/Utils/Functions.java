package com.harsharjeria.cuijan.Utils;

import android.widget.EditText;

import com.harsharjeria.cuijan.Models.FoodType;
import com.harsharjeria.cuijan.Models.Cuisines;

public class Functions {

    public Functions() {

    }

    public Double stringToDouble(String input){
        return Double.valueOf(input);
    }

    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public boolean validateEmail(String data){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(data).matches();
    }

    public Cuisines[] myCuisine(){
        return new Cuisines[]{
                new Cuisines("Chinese", "udemy", "https://www.ckmkb.com/", "https://hotemoji.com/images/dl/y/flag-of-taiwan-emoji-by-google.png"),
                new Cuisines("Japanese", "vedantu", "https://www.vedantu.com/", "https://icons.iconarchive.com/icons/icons-land/vista-flags/256/Japan-Flag-1-icon.png"),
                new Cuisines("North Indian", "meritnation", "https://www.meritnation.com/", "https://cdn.iconscout.com/icon/free/png-256/delhi-1-161357.png"),
                new Cuisines("South Indian", "byjus", "https://www.byjus.com/", "https://cdn.iconscout.com/icon/free/png-512/chennai-1-119692.png"),
                new Cuisines("Gujarati", "unacademy", "https://unacademy.com/", "https://www.clipartkey.com/mpngs/m/153-1538121_gujarat-logo-gujarat-in-hindi-font.png"),
                new Cuisines("City Special", "gwalior", "https://unacademy.com/", "https://static.toiimg.com/photo/59125248/.jpg")
        };
    }

    public FoodType[] myFoodType(){
        return new FoodType[]{
                new FoodType(1, "Fast Food", "https://www.udemy.com/"),
                new FoodType(2, "Healthy", "https://www.udemy.com/"),
                new FoodType(3, "Drinks", "https://www.udemy.com/"),
                new FoodType(4, "Alcoholic", "https://www.udemy.com/"),
                new FoodType(5, "Jain", "https://www.udemy.com/"),
                new FoodType(6, "Gym", "https://www.udemy.com/"),
                new FoodType(7, "Salads", "https://www.udemy.com/"),
                new FoodType(8, "Veg Only", "https://www.udemy.com/"),
                new FoodType(9, "Non Veg", "https://www.udemy.com/"),
                new FoodType(10, "Chains", "https://www.udemy.com/"),
        };
    }
}
