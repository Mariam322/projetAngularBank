package tn.iit.consumer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.iit.service.DocumentService;
import java.util.Base64;
import tn.iit.entites.TypeDocument; // Adjust the package path as needed
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentListener {
	private final DocumentService documentService;

@Bean
public Consumer<Message<Map<String, Object>>> processPdf() {
    return message -> {
        try {
            Map<String, Object> event = message.getPayload();
            log.info("Message reçu - Headers: {}", message.getHeaders());
            
            Long factureId = Long.parseLong(event.get("factureId").toString());
            String fileName = event.get("fileName").toString();
            byte[] content = Base64.getDecoder().decode(event.get("content").toString());
            
            documentService.processFacturePdf(factureId, fileName, content);
        } catch (Exception e) {
            log.error("Erreur de traitement", e);
        }
    };
}
}
