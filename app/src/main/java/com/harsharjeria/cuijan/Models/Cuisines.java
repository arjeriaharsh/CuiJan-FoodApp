package com.harsharjeria.cuijan.Models;

public class Cuisines {
    public String websiteName, websiteID, websiteLink, websiteIconLink;

    public Cuisines(String websiteName, String websiteID, String websiteLink, String websiteIconLink) {
        this.websiteName = websiteName;
        this.websiteID = websiteID;
        this.websiteLink = websiteLink;
        this.websiteIconLink = websiteIconLink;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getWebsiteID() {
        return websiteID;
    }

    public void setWebsiteID(String websiteID) {
        this.websiteID = websiteID;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getWebsiteIconLink() {
        return websiteIconLink;
    }

    public void setWebsiteIconLink(String websiteIconLink) {
        this.websiteIconLink = websiteIconLink;
    }
}
