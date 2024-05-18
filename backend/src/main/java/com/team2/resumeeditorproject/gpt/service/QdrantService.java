//package com.team2.resumeeditorproject.gpt.service;
//
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.StatusRuntimeException;
//import io.qdrant.client.QdrantClient;
//import io.qdrant.client.SearchRequest;
//import io.qdrant.client.SearchResponse;
//
//public class QdrantService {
//    private final ManagedChannel channel;
//    private final QdrantClient client;
//
//    public QdrantService(String host, int port) {
//        // gRPC 클라이언트 설정
//        this.channel = ManagedChannelBuilder.forAddress(host, port)
//                .usePlaintext()
//                .build();
//        this.client = QdrantClient.newBlockingStub(channel);
//    }
//
//    public SearchResponse search(SearchRequest request) {
//        try {
//            // 서버로 요청 전송
//            return client.search(request);
//        } catch (StatusRuntimeException e) {
//            // 오류 처리
//            System.err.println("gRPC 오류: " + e.getStatus().getCode());
//            System.err.println("오류 메시지: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public void shutdown() {
//        channel.shutdown();
//    }
//}