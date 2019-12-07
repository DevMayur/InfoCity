package com.example.infocity.models;

import com.example.infocity.BlogPostId;

public class SingleEntityModel extends com.example.infocity.BlogPostId {

    String address,gst_no,img_uri,name,other,phone,isVerified;
    double latitude,longitude;

    public SingleEntityModel(String address, String gst_no, String img_uri, String name, String other, String phone, String isVerified, double latitude, double longitude) {
        this.address = address;
        this.gst_no = gst_no;
        this.img_uri = img_uri;
        this.name = name;
        this.other = other;
        this.phone = phone;
        this.isVerified = isVerified;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SingleEntityModel() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGst_no() {
        return gst_no;
    }

    public void setGst_no(String gst_no) {
        this.gst_no = gst_no;
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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}