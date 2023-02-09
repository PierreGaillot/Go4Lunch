package com.levelupcluster.go4lunch.domain.models;

public class Workmate {

    String displayName;
    String imageUrl;

    public Workmate(String displayName, String imageUrl) {
        this.displayName = displayName;
        this.imageUrl = imageUrl;
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
}
