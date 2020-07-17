package com.harsharjeria.cuijan.Models;

public class FoodType {

    public String  nameclass, imageclasslink;
    public int idclass;

    public FoodType(){}
    public FoodType(int idclass, String nameclass, String imageclasslink) {
        this.idclass = idclass;
        this.nameclass = nameclass;
        this.imageclasslink = imageclasslink;
    }

    public int getIdclass() {
        return idclass;
    }

    public void setIdclass(int idclass) {
        this.idclass = idclass;
    }

    public String getNameclass() {
        return nameclass;
    }

    public void setNameclass(String nameclass) {
        this.nameclass = nameclass;
    }

    public String getImageclasslink() {
        return imageclasslink;
    }

    public void setImageclasslink(String imageclasslink) {
        this.imageclasslink = imageclasslink;
    }
}
