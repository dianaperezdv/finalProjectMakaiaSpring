package com.example.finalProject.Exception;

public class ApiRequestException  extends RuntimeException{

    public ApiRequestException(String message){
        super(message);
    }
}