package com.levelupcluster.go4lunch.domain.models;

import org.jetbrains.annotations.Nullable;

public class Workmate extends User {

    @Nullable
    Restaurant restaurantChoice;

    public Workmate(String imageURL, String displayName, String email) {
        super(imageURL, displayName, email);
    }

    @Nullable
    public Restaurant getRestaurantChoice() {
        return restaurantChoice;
    }

    public void setRestaurantChoice(@Nullable Restaurant restaurantChoice) {
        this.restaurantChoice = restaurantChoice;
    }
}
