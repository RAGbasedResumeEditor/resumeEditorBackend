package com.team2.resumeeditorproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("response", e.getMessage());
        response.put("time", new Date());
        response.put("status","Fail");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); //500
    }

    @ExceptionHandler
    public  ResponseEntity<Map<String, Object>> handleException(BadRequestException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("response", e.getMessage());
        response.put("time", new Date());
        response.put("status","Fail");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleException(DelDateException e) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> result = new HashMap<>();
        String delDate=e.getMessage();

        String[] dates = delDate.replace("[", "").replace("]", "").split(", ");
        result.put("deleted", dates[0]);
        result.put("available", dates[1]);

        response.put("response", result);
        response.put("time", new Date());
        response.put("status","Fail");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); //400
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleException(ForbiddenException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("response", e.getMessage());
        response.put("time", new Date());
        response.put("status","Fail");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 403
    }

    @ExceptionHandler
    public  ResponseEntity<Map<String, Object>> handleException(NotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("response", e.getMessage());
        response.put("time", new Date());
        response.put("status","Fail");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(StatisticsException.class)
    public ResponseEntity<ErrorResponse> handleStatisticsException(StatisticsException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .response("An error occurred while processing statistics")
                .time(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); // 500 Internal Server Error
    }

    /* 사용하지 않는 듯 하여 일단 주석 처리(피드백 받은 후 수정예정)
        static class ErrorResponse {
            String message;
            Date date;

            ErrorResponse(String message, Date date) {
                this.message = message;
                this.date=date;
            }

            public String getMessage() {
                return message;
            }
        }

     */
    }
