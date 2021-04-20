package com.example.store.exceptions;

public class UserDuplicateException extends RuntimeException{

    public UserDuplicateException(){
    }
    public UserDuplicateException(String message){
        super(message);
    }
}
