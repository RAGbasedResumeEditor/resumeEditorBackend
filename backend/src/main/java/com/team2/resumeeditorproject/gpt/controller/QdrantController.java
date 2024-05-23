//package com.team2.resumeeditorproject.gpt.controller;
//
//import com.team2.resumeeditorproject.gpt.service.QdrantService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import io.qdrant.client.SearchRequest;
////import io.qdrant.client.SearchResponse;
//import io.qdrant.client.grpc.Points.SearchResponse;
//
//@RestController
//@RequestMapping("/qdrant")
//public class QdrantController {
//    private final QdrantService qdrantService;
//
//    @Autowired
//    public QdrantController(QdrantService qdrantService) {
//        this.qdrantService = qdrantService;
//    }
//
//    @GetMapping("/search")
//    public SearchResponse search() {
//        SearchRequest request = SearchRequest.newBuilder().build();
//        return qdrantService.search(request);
//    }
//}