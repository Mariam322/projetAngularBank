package tn.iit.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.nio.file.StandardCopyOption;

import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.Document;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tn.iit.entites.TypeDocument;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.drive.DriveScopes;
import org.springframework.core.io.ClassPathResource;
import tn.iit.repository.DocumentRepository;
import tn.iit.request.CreateDocumentRequest;
import tn.iit.request.UpdateDocumentRequest;
import tn.iit.response.DocumentResponse;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService implements IDocumentService {
     private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    @Autowired

    private final DocumentRepository documentRepository;
   private static final String DOCUMENTS_DIR = "/var/documents/"; 
      @Value("${storage.base.path}")
    private String storageBasePath; 

    public void enregistrerDocument(String factureId, byte[] pdf, String nomfichier,String depsneId,String operationId ) {
        try {
            // Créer le répertoire s'il n'existe pas
            Path documentsPath = Paths.get(DOCUMENTS_DIR);
            if (!Files.exists(documentsPath)) {
                Files.createDirectories(documentsPath);
            }

            // Enregistrer le fichier
            Path filePath = documentsPath.resolve(nomfichier);
            Files.write(filePath, pdf);

            // Sauvegarder les métadonnées
            Document doc = new Document();
            doc.setNomfichier(nomfichier);
           
            doc.setType(TypeDocument.FACTURE);
            doc.setFilepath(filePath.toString());
            doc.setDateAjout(new Date());
            doc.setFactureId(Long.parseLong(factureId));
      doc.setDepsneId(Long.parseLong(depsneId));
         doc.setOperationId(Long.parseLong(operationId));
    
            
            documentRepository.save(doc);
            
        } catch (IOException e) {
            throw new DocumentStorageException("Erreur de stockage du document", e);
        } catch (NumberFormatException e) {
            throw new DocumentStorageException("Format d'ID de facture invalide", e);
        }
    }
    
    public static class DocumentStorageException extends RuntimeException {
        public DocumentStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    
}
 @Transactional
    public void enregistrerDocument(String factureId, String driveFileId, String nomfichier,String depsneId,String operationId) {
        Document doc = new Document();
        doc.setNomfichier(nomfichier);
        doc.setDriveFileId(driveFileId);
        doc.setType(TypeDocument.FACTURE);
        doc.setFactureId(Long.parseLong(factureId));
                 doc.setDepsneId(Long.parseLong(depsneId));
         doc.setOperationId(Long.parseLong(operationId));
     doc.setDateAjout(new Date());
        
        documentRepository.save(doc);
    }
    public String getDriveFileIdByFactureId(Long factureId) {
    List<Document> documents = documentRepository.findByFactureId(factureId);
if (documents.isEmpty()) {
    throw new RuntimeException("Document non trouvé");
}
return documents.get(0).getDriveFileId();
}

 @Override
    public DocumentResponse createDocument(CreateDocumentRequest createDocumentRequest) {
        Document document = new Document();
        document.setNomfichier(createDocumentRequest.getNomfichier());
        document.setContenuPdf(createDocumentRequest.getContenuPdf());
        document.setType(createDocumentRequest.getType());
        document.setFilepath(createDocumentRequest.getFilepath());
        document.setDateAjout(createDocumentRequest.getDateAjout());
        document.setFactureId(createDocumentRequest.getFactureId());
        document.setDepsneId(createDocumentRequest.getDepsneId());
        document.setOperationId(createDocumentRequest.getOperationId());
        document.setDriveFileId(createDocumentRequest.getDriveFileId());

        document = documentRepository.save(document);

        return new DocumentResponse(document);
    }

    @Override
    public DocumentResponse updateDocument(Long id, UpdateDocumentRequest updateDocumentRequest) {
        logger.info("Updating document with ID: {}", id);

        Document document = documentRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Document with ID {} not found for update", id);
                return new RuntimeException("Document not found");
            });

        if (updateDocumentRequest.getNomfichier() != null) {
            document.setNomfichier(updateDocumentRequest.getNomfichier());
            logger.info("Updated Nomfichier: {}", updateDocumentRequest.getNomfichier());
        }
        if (updateDocumentRequest.getContenuPdf() != null) {
            document.setContenuPdf(updateDocumentRequest.getContenuPdf());
            logger.info("Updated ContenuPdf");
        }
        if (updateDocumentRequest.getType() != null) {
            document.setType(updateDocumentRequest.getType());
            logger.info("Updated Type: {}", updateDocumentRequest.getType());
        }
        if (updateDocumentRequest.getFilepath() != null) {
            document.setFilepath(updateDocumentRequest.getFilepath());
            logger.info("Updated Filepath: {}", updateDocumentRequest.getFilepath());
        }
        if (updateDocumentRequest.getDateAjout() != null) {
            document.setDateAjout(updateDocumentRequest.getDateAjout());
            logger.info("Updated DateAjout: {}", updateDocumentRequest.getDateAjout());
        }
        if (updateDocumentRequest.getFactureId() != null) {
            document.setFactureId(updateDocumentRequest.getFactureId());
            logger.info("Updated FactureId: {}", updateDocumentRequest.getFactureId());
        }
        if (updateDocumentRequest.getDepsneId() != null) {
            document.setDepsneId(updateDocumentRequest.getDepsneId());
            logger.info("Updated DepsneId: {}", updateDocumentRequest.getDepsneId());
        }
        if (updateDocumentRequest.getOperationId() != null) {
            document.setOperationId(updateDocumentRequest.getOperationId());
            logger.info("Updated OperationId: {}", updateDocumentRequest.getOperationId());
        }
        if (updateDocumentRequest.getDriveFileId() != null) {
            document.setDriveFileId(updateDocumentRequest.getDriveFileId());
            logger.info("Updated DriveFileId: {}", updateDocumentRequest.getDriveFileId());
        }

        document = documentRepository.save(document);

        return new DocumentResponse(document);
    }

    @Override
    public DocumentResponse getById(Long id) {
        return new DocumentResponse(
            documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"))
        );
    }

    @Override
    public void deleteDocument(Long id) {
        try {
            logger.info("Attempting to delete document with ID: {}", id);
            Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

            documentRepository.delete(document);
            logger.info("Document with ID {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Failed to delete document with ID: {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DocumentResponse> getAllDocuments() {
        return DocumentResponse.toArrayList(documentRepository.findAll());
    }

    @Override
    public List<DocumentResponse> getDocumentsByType(TypeDocument type) {
        List<Document> documents = documentRepository.findByType(type);
        return documents.stream()
                      .map(DocumentResponse::new)
                      .collect(Collectors.toList());
    }

 @Transactional
public void processFacturePdf(Long factureId, String fileName, byte[] content) throws IOException {
    Path destPath = Paths.get(storageBasePath, "facture");
    Files.createDirectories(destPath);

    String uniqueFileName = "facture_" + factureId + "_" + System.currentTimeMillis() + ".pdf";
    Path destFilePath = destPath.resolve(uniqueFileName);

    // Ecrire le contenu binaire directement depuis content
    Files.write(destFilePath, content);

    Document doc = new Document();
    doc.setNomfichier(fileName);
    doc.setFilepath(destFilePath.toString());
    doc.setType(TypeDocument.FACTURE);
    doc.setFactureId(factureId);
    doc.setDateAjout(new Date());
    doc.setContenuPdf(content);

    documentRepository.save(doc);

    log.info("Document enregistré: {}", doc);
}
}