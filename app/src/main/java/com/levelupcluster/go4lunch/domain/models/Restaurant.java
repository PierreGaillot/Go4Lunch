package com.levelupcluster.go4lunch.domain.models;

import com.google.android.gms.maps.model.LatLng;

public class Restaurant {

    String name;
    String id;
    String cookStyle;
    LatLng latLng;
    String vicinity;
    String business_status;
    String rating;
    boolean open_now;

    String photoUrl;
    private static final String API_KEY = "AIzaSyCEDe9eFsSku4dpBjkjVjqfXjUsL5lQixg";



    public Restaurant() {
    }

    public Restaurant(String name, String id, String cookStyle, LatLng latLng, String vicinity, String business_status, String rating, boolean open_now) {
        this.name = name;
        this.id = id;
        this.cookStyle = cookStyle;
        this.latLng = latLng;
        this.vicinity = vicinity;
        this.business_status = business_status;
        this.rating = rating;
        this.open_now = open_now;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCookStyle() {
        return cookStyle;
    }

    public void setCookStyle(String cookStyle) {
        this.cookStyle = cookStyle;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isOpen_now() {
        return open_now;
    }

    public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
    }

    public void setPhoto(int size, String photoReference) {
        this.photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth="+size+"&photo_reference="+ photoReference+ "&key=" + API_KEY;
    }

    public void setPhotoPlaceHolder(){
        this.photoUrl = "https://karamathsrestaurant.com/wp-content/uploads/2022/02/image-coming-soon-placeholder.png";
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
