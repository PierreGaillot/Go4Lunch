package com.levelupcluster.go4lunch.domain.models;

public class Restaurant {
    String name;
    String cookStyle;

    public Restaurant(String name, String cookStyle) {
        this.name = name;
        this.cookStyle = cookStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookStyle() {
        return cookStyle;
    }

    public void setCookStyle(String cookStyle) {
        this.cookStyle = cookStyle;
    }
}
