package com.example.app_cosmetics.models;

import java.util.List;

public class UserModel {
    boolean success;
    String message;
     User result;

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
