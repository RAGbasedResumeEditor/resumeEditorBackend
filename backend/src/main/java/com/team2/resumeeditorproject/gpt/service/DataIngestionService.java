package com.team2.resumeeditorproject.gpt.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections.Distance;
import io.qdrant.client.grpc.Collections.VectorParams;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.val;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


/**
 * Collection에 데이터 삽입
 *
 * @author : 김상휘
 * @fileName : DataIngestionService
 * @since : 05/08/24
 */

@Component
public class DataIngestionService {

    public void setupRagChatbot() {
        insertDocuments();
    }

    private QdrantClient getQdrantClient() {
        // Authentication Ref: https://qdrant.tech/documentation/cloud/quickstart-cloud/
        return new QdrantClient(
                QdrantGrpcClient.newBuilder(
                                ChatConstants.QDRANT_GRPC_HOST,
                                ChatConstants.QDRANT_GRPC_PORT,
                                true)
                        .withApiKey(ChatConstants.QDRANT_API_KEY)
                        .build());
    }

    private void createCollection() {
        val client = getQdrantClient();
        try {
            client.createCollectionAsync(ChatConstants.COLLECTION_NAME,
                    VectorParams.newBuilder().setDistance(Distance.Dot).setSize(
                            ChatConstants.OPENAI_EMBEDDING_SIZE).build()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }

    private void insertDocuments() {
        EmbeddingModel embeddingModel = getEmbeddingModel();
        DocumentSplitter documentSplitter = DocumentSplitters.recursive(1000, 150);
        String fileContent = getFileContent();
        Document doc = Document.from(fileContent, Metadata.from("document-type", "history-document"));

        // Ensure embeddingStore is correctly initialized
        EmbeddingStore<TextSegment> embeddingStore = getEmbeddingStore();
        if (embeddingStore == null) {
            throw new IllegalStateException("EmbeddingStore is not initialized");
        }

        // Split document into segments
        List<TextSegment> segments = documentSplitter.split(doc);
        if (segments == null || segments.isEmpty()) {
            throw new IllegalStateException("Document splitting resulted in no segments");
        }

        // Get embeddings for segments
        Response<List<Embedding>> embeddingResponse = embeddingModel.embedAll(segments);
        if (embeddingResponse == null || embeddingResponse.content() == null) {
            throw new IllegalStateException("Embedding response is null");
        }

        List<Embedding> embeddings = embeddingResponse.content();
        if (embeddings.size() != segments.size()) {
            throw new IllegalStateException("The number of embeddings does not match the number of segments");
        }

        // Add embeddings and segments to the embedding store
        embeddingStore.addAll(embeddings, segments);
    }

    private EmbeddingModel getEmbeddingModel() {
        String openaiApiKey = System.getenv("GPT_API_KEY");
        return OpenAiEmbeddingModel.withApiKey(openaiApiKey);
    }

    private static EmbeddingStore<TextSegment> getEmbeddingStore() {
        // Ref: https://qdrant.tech/documentation/frameworks/langchain4j/
        return QdrantEmbeddingStore.builder()
                .collectionName(ChatConstants.COLLECTION_NAME)
                .host(ChatConstants.QDRANT_GRPC_HOST)
                .port(ChatConstants.QDRANT_GRPC_PORT)
                .apiKey(ChatConstants.QDRANT_API_KEY)
                .useTls(true)
                .build();
    }

    /**
     * Read the data from the file
     */
    private String getFileContent() {
        Resource companyDataResource = new ClassPathResource("data/data.txt");
        try {
            File file = companyDataResource.getFile();
            String content = new String(Files.readAllBytes(file.toPath()));
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}