package com.harsharjeria.cuijan.Models;

public class Restaurants {

    public String namecourse, idcourse, linkcourse, iconlink, priceAmount, courseDesc, cityID, coverLink;
    public double ratings, views;
    public int website;

    public Restaurants() {}



    public Restaurants(String namecourse, String idcourse, String linkcourse, String iconlink, int website, double ratings, double views, String priceAmount, String courseDesc, String cityID, String coverLink) {
        this.namecourse = namecourse;
        this.idcourse = idcourse;
        this.linkcourse = linkcourse;
        this.iconlink = iconlink;
        this.website = website;
        this.ratings = ratings;
        this.priceAmount = priceAmount;
        this.courseDesc = courseDesc;
        this.views = views;
        this.cityID = cityID;
        this.coverLink = coverLink;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getNamecourse() {
        return namecourse;
    }

    public void setNamecourse(String namecourse) {
        this.namecourse = namecourse;
    }

    public String getIdcourse() {
        return idcourse;
    }

    public void setIdcourse(String idcourse) {
        this.idcourse = idcourse;
    }

    public String getLinkcourse() {
        return linkcourse;
    }

    public void setLinkcourse(String linkcourse) {
        this.linkcourse = linkcourse;
    }

    public String getIconlink() {
        return iconlink;
    }

    public void setIconlink(String iconlink) {
        this.iconlink = iconlink;
    }

    public String getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(String priceAmount) {
        this.priceAmount = priceAmount;
    }

    public int getWebsite() {
        return website;
    }

    public void setWebsite(int website) {
        this.website = website;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public double getViews() {
        return views;
    }

    public void setViews(double views) {
        this.views = views;
    }
}
