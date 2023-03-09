package com.levelupcluster.go4lunch.domain.models;

import java.io.Serializable;

public class RestaurantDetails extends Restaurant implements Serializable {
    String formatted_phone_number, website;

    public RestaurantDetails() {}

    public String getFormattedPhoneNumber() {
        return formatted_phone_number;
    }

    public void setFormattedPhoneNumber(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
