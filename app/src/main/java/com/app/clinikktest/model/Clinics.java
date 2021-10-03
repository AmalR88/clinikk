package com.app.clinikktest.model;

public class Clinics {

    private String image;
    private String name;
    private String description;
    private String address;
    private String timings;

    public Clinics(String image, String name, String description, String address, String timings) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.address = address;
        this.timings = timings;
    }

    public Clinics() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }


}
