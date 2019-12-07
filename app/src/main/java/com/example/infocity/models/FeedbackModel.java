package com.example.infocity.models;

public class FeedbackModel {
    String email,feedback;

    public FeedbackModel(String email, String feedback) {
        this.email = email;
        this.feedback = feedback;
    }

    public FeedbackModel() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
