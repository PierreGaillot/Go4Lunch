package com.levelupcluster.go4lunch.domain.models;

import org.jetbrains.annotations.Nullable;

public class User {

    private String imageURL;
    private String displayName;
    private String email;

    @Nullable
    String restaurantChoiceId;

    public User(String imageURL, String displayName, String email) {
        this.imageURL = imageURL;
        this.displayName = displayName;
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Nullable
    public String getRestaurantChoiceId() {
        return restaurantChoiceId;
    }

    public void setRestaurantChoiceId(@Nullable String restaurantChoiceId) {
        this.restaurantChoiceId = restaurantChoiceId;
    }

}
