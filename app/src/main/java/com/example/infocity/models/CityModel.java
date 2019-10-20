package com.example.infocity.models;

public class CityModel  {

    String img_uri,name;

    public CityModel() {

    }

    public CityModel(String img_uri, String name) {
        this.img_uri = img_uri;
        this.name = name;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
