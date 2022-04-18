package org.example.exception;

public class CustomerAlreadyRegisteredException extends RuntimeException{
    public CustomerAlreadyRegisteredException(String message){
        super(message);
    }
}
