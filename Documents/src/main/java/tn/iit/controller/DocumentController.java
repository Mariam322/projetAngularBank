package tn.iit.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import tn.iit.service.DocumentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import tn.iit.entites.Document;
import tn.iit.repository.DocumentRepository;
import java.io.IOException;
import java.sql.Date;
import org.springframework.http.ContentDisposition;
import java.util.HashMap;
import java.util.Map;
// Nouveaux imports ajoutés
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tn.iit.entites.TypeDocument;
import tn.iit.request.CreateDocumentRequest;
import tn.iit.request.UpdateDocumentRequest;
import tn.iit.response.DocumentResponse;
import tn.iit.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import org.springframework.util.FileCopyUtils;
import java.io.FileOutputStream;
import java.io.IOException;



@RestController

@RequiredArgsConstructor
@Tag(name = "Document Management", description = "API pour la gestion des documents")
public class DocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

   private final DocumentRepository documentRepository; // Ajoutez ce champ
    private final DocumentService documentService;

 

   
    
    


    @PostMapping("/document/add")
    @Operation(summary = "Creer un document", description = "Creer un document et l'enregistrer dans la base")
    @ApiResponse(responseCode = "200", description = "Document cree avec succes",
                content = @Content(schema = @Schema(implementation = DocumentResponse.class)))
  
    public DocumentResponse createDocument(@RequestBody CreateDocumentRequest createDocumentRequest) {
        return documentService.createDocument(createDocumentRequest);
    }

    @GetMapping("/document/{id}")
     @Operation(summary = "Recuperer un document par ID", description = "Retourne un document par son ID")

    public DocumentResponse getById(@PathVariable Long id) {
        return documentService.getById(id);
    }

    @GetMapping("/document")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lister tous les documents", description = "Retourne tous les documents enregistre´s")

    public List<DocumentResponse> getAll() {
        return documentService.getAllDocuments();
    }

    @PutMapping("/document/update/{id}")
    @Operation(summary = "Modifier un document", description = "Mettre a jour les informations d'un document")

    public DocumentResponse updateDocument(@PathVariable("id") Long id, @RequestBody UpdateDocumentRequest updateDocumentRequest) {
        logger.info("Received request to update document with ID: {}", id);
        return documentService.updateDocument(id, updateDocumentRequest);
    }

    @DeleteMapping("/document/delete/{id}")
       @Operation(summary = "Supprimer un document", description = "Supprimer un document par son ID")

    public void deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
    }

    @GetMapping("/document/type/{type}")
       @Operation(summary = "Lister les documents par type", description = "Retourne les documents filtres par type")

    public List<DocumentResponse> getDocumentsByType(@PathVariable TypeDocument type) {
        return documentService.getDocumentsByType(type);
    }
   @Value("${storage.base.path}")
    private String storageBasePath;

   @PostMapping(value = "/document/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
       @Operation(summary = "Uploader un document", description = "Uploader un document sur disque et base")

    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") TypeDocument type,
            @RequestParam(value = "factureId", required = false) Long factureId,
            @RequestParam(value = "depsneId", required = false) Long depsneId,
            @RequestParam(value = "operationId", required = false) Long operationId) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Le fichier est vide");
            }

            // 1. Sauvegarde en base
            Document document = new Document();
            document.setNomfichier(file.getOriginalFilename());
            document.setType(type);
             // Stockage en BLOB
            document.setDateAjout(new Date(System.currentTimeMillis()));
            document.setFactureId(factureId);
            document.setDepsneId(depsneId);
            document.setOperationId(operationId);

            document = documentRepository.save(document);

            // 2. Sauvegarde sur le disque
            Path typePath = Paths.get(storageBasePath, type.name().toLowerCase());
            Files.createDirectories(typePath);
            
            String filename = document.getId() + "_" + file.getOriginalFilename();
            Path filePath = typePath.resolve(filename);
            
            // Écriture du fichier sur le disque
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                FileCopyUtils.copy(file.getInputStream(), fos);
            }

            return ResponseEntity.ok(Map.of(
                "message", "Fichier uploadé avec succès",
                "id", document.getId(),
                "path", filePath.toString()
            ));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erreur IO: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }

    @GetMapping("/document/download/{id}")


    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        try {
            Document document = documentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Document non trouvé"));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, getContentType(document.getNomfichier()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                           "attachment; filename=\"" + document.getNomfichier() + "\"")
                    .body(document.getContenuPdf());
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String getContentType(String filename) {
        if (filename == null) return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        
        String lowerName = filename.toLowerCase();
        if (lowerName.endsWith(".pdf")) return "application/pdf";
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) return "image/jpeg";
        if (lowerName.endsWith(".png")) return "image/png";
        if (lowerName.endsWith(".docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }



@GetMapping("/document/view/{id}")
    @Operation(summary = "Afficher un document", description = "Afficher un document dans le navigateur (inline)")

public ResponseEntity<byte[]> viewDocument(@PathVariable Long id) {
    Document document = documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Document not found"));
    
    HttpHeaders headers = new HttpHeaders();
    
    // Détermination dynamique du Content-Type
    String contentType = determineContentType(document.getNomfichier());
    headers.setContentType(MediaType.parseMediaType(contentType));
    
    headers.setContentDisposition(ContentDisposition.builder("inline")
            .filename(document.getNomfichier())
            .build());
    
    return ResponseEntity.ok()
            .headers(headers)
            .body(document.getContenuPdf());
}

// Méthode utilitaire pour déterminer le Content-Type
private String determineContentType(String filename) {
    if (filename == null) {
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
    
    String lowerCaseFilename = filename.toLowerCase();
    
    if (lowerCaseFilename.endsWith(".pdf")) {
        return MediaType.APPLICATION_PDF_VALUE;
    } else if (lowerCaseFilename.endsWith(".jpg") || lowerCaseFilename.endsWith(".jpeg")) {
        return MediaType.IMAGE_JPEG_VALUE;
    } else if (lowerCaseFilename.endsWith(".png")) {
        return MediaType.IMAGE_PNG_VALUE;
    } else if (lowerCaseFilename.endsWith(".docx")) {
        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    } else if (lowerCaseFilename.endsWith(".xlsx")) {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    } else if (lowerCaseFilename.endsWith(".txt")) {
        return MediaType.TEXT_PLAIN_VALUE;
    } else if (lowerCaseFilename.endsWith(".zip")) {
        return "application/zip";
    }
    // Ajoutez d'autres types au besoin
    
    return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Type par défaut
}
}

