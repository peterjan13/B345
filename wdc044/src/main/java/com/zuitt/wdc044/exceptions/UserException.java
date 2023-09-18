package com.zuitt.wdc044.exceptions;

public class UserException extends Exception{
    public UserException (String message){
        //with the help of the super method it will inherit parents class properties and methods.
        super(message);
    }
}
