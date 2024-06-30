package com.team2.resumeeditorproject.admin.service;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Map<String, Object>> createResponse(Object msg){
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("response", msg);
        return ResponseEntity.ok().body(response);
    }

    public static ResponseEntity<Map<String, Object>> createPagedResponse(int totalPage, List<?> list) {
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", totalPage);
        response.put("response", list);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Map<String, Object>> createBadReqResponse(String errorMsg) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "Fail");
        errorResponse.put("time", new Date());
        errorResponse.put("response", errorMsg);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    public static ResponseEntity<Map<String, Object>> createServerErrResponse(){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("status","Fail");
        errorResponse.put("time",new Date());
        errorResponse.put("response", "서버 오류입니다.");
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
