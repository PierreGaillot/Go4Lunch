package com.levelupcluster.go4lunch.ui.uimodels;

public class DrawerHeaderUiModel {

    private String imageURL;
    private String userName;
    private String userEmail;

    public DrawerHeaderUiModel(String imageURL, String userName, String userEmail) {
        this.imageURL = imageURL;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
