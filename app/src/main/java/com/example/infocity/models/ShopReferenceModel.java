package com.example.infocity.models;

import com.example.infocity.BlogPostId;

public class ShopReferenceModel extends com.example.infocity.BlogPostId {

    String shop_id,user_id,category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ShopReferenceModel(String shop_id, String user_id, String category) {
        this.shop_id = shop_id;
        this.user_id = user_id;
        this.category = category;
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
