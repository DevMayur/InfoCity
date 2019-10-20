package com.example.infocity.models;

public class ShopReferenceModel {

    String shop_id,user_id;

    public ShopReferenceModel(String shop_id, String user_id) {
        this.shop_id = shop_id;
        this.user_id = user_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ShopReferenceModel() {
    }
}
