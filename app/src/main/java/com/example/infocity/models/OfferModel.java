package com.example.infocity.models;

public class OfferModel {

    String offer_name,offer_desc,img_uri;

    public OfferModel(String offer_name, String offer_desc, String img_uri) {
        this.offer_name = offer_name;
        this.offer_desc = offer_desc;
        this.img_uri = img_uri;
    }

    public OfferModel() {
    }

    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }

    public String getOffer_desc() {
        return offer_desc;
    }

    public void setOffer_desc(String offer_desc) {
        this.offer_desc = offer_desc;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }
}
