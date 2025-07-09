package tn.iit.service;

import java.util.List;
import tn.iit.entites.Document;
import tn.iit.entites.TypeDocument;
import tn.iit.request.CreateDocumentRequest;
import tn.iit.request.UpdateDocumentRequest;
import tn.iit.response.DocumentResponse;

public interface IDocumentService {
    DocumentResponse createDocument(CreateDocumentRequest createDocumentRequest);
    DocumentResponse updateDocument(Long id, UpdateDocumentRequest updateDocumentRequest);
    DocumentResponse getById(Long id);
    void deleteDocument(Long id);
    List<DocumentResponse> getAllDocuments();
    List<DocumentResponse> getDocumentsByType(TypeDocument type);
}