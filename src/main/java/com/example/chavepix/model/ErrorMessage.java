package com.example.chavepix.model;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {

    private Date currentDate;
    private String message;

    public ErrorMessage(Date currentDate, String message) {
        this.currentDate = currentDate;
        this.message = message;
    }
}
