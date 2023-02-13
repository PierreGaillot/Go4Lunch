package com.levelupcluster.go4lunch.domain.models;

public class Workmate {

    String displayName;
    String imageUrl;
    String email;

    public Workmate(String displayName, String imageUrl, String email) {
        this.displayName = displayName;
        this.imageUrl = imageUrl;
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
