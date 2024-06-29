package com.team2.resumeeditorproject.exception;

import java.util.List;

public class DelDateException extends RuntimeException {
    public DelDateException(List<String> message){
        super(message.toString());
    }
}
