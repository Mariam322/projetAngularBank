package tn.iit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.iit.request.CreateFactureRequest;
import tn.iit.request.UpdateFactureRequest;
import tn.iit.response.FactureResponse;
import tn.iit.service.FactureService;
import tn.iit.service.GoogleDriveService;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import tn.iit.entites.Facture;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import org.springframework.http.MediaType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import tn.iit.entites.StatutFacture;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import tn.iit.entites.TypeDocument;
import java.util.HashMap;
import java.util.Base64;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;


import org.springframework.web.bind.annotation.*;




@RestController

public class FactureController {

    private static final Logger logger = LoggerFactory.getLogger(FactureController.class);
    

    
 
    private final GoogleDriveService googleDriveService;
    
      
    private final StreamBridge streamBridge;

       
    private final FactureService factureService;
 private static final String PDF_DIRECTORY = "./generated-pdfs/";

    public FactureController(FactureService factureService, 
                           GoogleDriveService googleDriveService,StreamBridge streamBridge
                          ) {
        this.factureService = factureService;
        this.googleDriveService = googleDriveService;
        this.streamBridge=streamBridge;
       
        
        // Cr�er le r�pertoire s'il n'existe pas
        try {
            Files.createDirectories(Paths.get(PDF_DIRECTORY));
        } catch (Exception e) {
            logger.error("Failed to create PDF directory", e);
        }
    }
/*@PostMapping("/{id}/generate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void generatePdf(@PathVariable Long id) {
        try {
            // Ensure directory exists
            Files.createDirectories(Paths.get(PDF_DIRECTORY));
            
            String pdfPath = PDF_DIRECTORY + "facture_" + id + ".pdf";
            
            // Generate PDF (simulated for now - you'll need to implement this properly)
            Files.write(Paths.get(pdfPath), "Contenu PDF simul�".getBytes());

            // Upload to Google Drive
            String driveFileId = googleDriveService.uploadFile(pdfPath, "facture_"+id+".pdf");

            // Send event
            Map<String, String> event = Map.of(
                "factureId", id.toString(),
                "driveFileId", driveFileId,
                "action", "PDF_GENERE"
            );
            streamBridge.send("pdf-ready-out-0", event);

            // Clean up
            Files.deleteIfExists(Paths.get(pdfPath));
            
        } catch (Exception e) {
            logger.error("Failed to generate PDF for facture {}", id, e);
            throw new RuntimeException("PDF generation failed");
        }
    }*/
@PostMapping("/facture/send-pdf")
@ResponseStatus(HttpStatus.ACCEPTED)
public void handlePdfUpload(@RequestBody PdfUploadRequest request) {
    // Validation
    if (request.getFactureId() == null || request.getContent() == null) {
        throw new IllegalArgumentException("Donn�es manquantes");
    }

    // Envoyer via Kafka
    Map<String, Object> event = new HashMap<>();
    event.put("factureId", request.getFactureId());
    event.put("fileName", "facture_" + request.getFactureId() + ".pdf");
    event.put("content", request.getContent());
    event.put("type", "FACTURE");
    
    streamBridge.send("pdf-ready-out-0", event);
}
// DTO
@Data
static class PdfUploadRequest {
    private Long factureId;
    private String fileName;
    private String content; // Base64 sans pr�fixe
    private String type;
}


   @PostMapping("/facture/{id}/generate-pdf")
   @Operation(summary = "G�n�rer un PDF de facture", 
               description = "G�n�re un PDF pour une facture sp�cifique")
    @ApiResponse(responseCode = "200", description = "PDF en cours de g�n�ration")
    @ApiResponse(responseCode = "500", description = "Erreur de g�n�ration")
    @ApiResponse(responseCode = "404", description = "Facture non trouv�e")
    public ResponseEntity<Map<String, String>> generatePdf(@PathVariable Long id) {
    try {
        factureService.generateAndSendPdf(id);
        return ResponseEntity.ok(
            Map.of("message", "PDF en cours de g�n�ration et stockage")
        );
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "Erreur g�n�ration PDF"));
    }
}

   @GetMapping(value = "/{id}/download", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "T�l�charger le PDF d'une facture", 
               description = "T�l�charge le PDF d'une facture sp�cifique")
    @ApiResponse(responseCode = "200", description = "PDF t�l�charg� avec succ�s",
                content = @Content(mediaType = "application/pdf"))
    @ApiResponse(responseCode = "404", description = "Facture non trouv�e")
    @ApiResponse(responseCode = "500", description = "Erreur de serveur")
public ResponseEntity<Resource> downloadPdf(@PathVariable Long id) {
    try {
        ResponseEntity<Resource> response = factureService.downloadPdf(id);
        
        // Ajoute des headers suppl�mentaires
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=facture_" + id + ".pdf");
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);
        
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(response.getBody());
                
    } catch (Exception e) {
        logger.error("Erreur t�l�chargement PDF", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ByteArrayResource(("Erreur: " + e.getMessage()).getBytes()));
    }
}


    @PostMapping("/facture/create")
    @Operation(summary = "Cr�er une nouvelle facture", 
               description = "Cr�e une nouvelle facture avec les donn�es fournies")
    @ApiResponse(responseCode = "200", description = "Facture cr��e avec succ�s",
                content = @Content(schema = @Schema(implementation = FactureResponse.class)))
    @ApiResponse(responseCode = "400", description = "Donn�es invalides")
    @ApiResponse(responseCode = "401", description = "Non autoris�")
    public FactureResponse createFacture(@RequestBody CreateFactureRequest createFactureRequest) {
        logger.info("Received request to create facture: {}", createFactureRequest);
        FactureResponse response = factureService.createFacture(createFactureRequest);
        logger.info("Created facture with ID: {}", response.getId());
        return response;
    }

    @GetMapping("/facture/getById/{id}")
    @Operation(summary = "Obtenir une facture par ID", 
               description = "R�cup�re les d�tails d'une facture sp�cifique")
    @ApiResponse(responseCode = "200", description = "Facture trouv�e")
    @ApiResponse(responseCode = "404", description = "Facture non trouv�e")
    public FactureResponse getById(@PathVariable Long id) {
        logger.info("Fetching facture with ID: {}", id);
        return factureService.getFactureById(id);
    }

    @GetMapping("/facture/getAll")
    @Operation(summary = "Lister toutes les factures", 
               description = "R�cup�re la liste compl�te des factures")
    @ApiResponse(responseCode = "200", description = "Liste des factures",
                content = @Content(schema = @Schema(implementation = FactureResponse.class)))
    public List<FactureResponse> getAll() {
        logger.info("Fetching all factures");
        return factureService.getAllFactures();
    }

    @PutMapping("/facture/update/{id}")
    @Operation(summary = "Mettre � jour une facture", 
               description = "Met � jour les informations d'une facture existante")
    @ApiResponse(responseCode = "200", description = "Facture mise � jour",
                content = @Content(schema = @Schema(implementation = FactureResponse.class)))
    @ApiResponse(responseCode = "404", description = "Facture non trouv�e")
    @ApiResponse(responseCode = "400", description = "Donn�es invalides")
    public FactureResponse updateFacture(@PathVariable Long id, @RequestBody UpdateFactureRequest updateFactureRequest) {
        logger.info("Updating facture with ID: {}", id);
        return factureService.updateFacture(id, updateFactureRequest);
    }

    @DeleteMapping("/facture/delete/{id}")
     @Operation(summary = "Supprimer une facture", 
               description = "Supprime une facture existante")
    @ApiResponse(responseCode = "200", description = "Facture supprim�e")
    @ApiResponse(responseCode = "404", description = "Facture non trouv�e")
    public void deleteFacture(@PathVariable Long id) {
        logger.info("Deleting facture with ID: {}", id);
        factureService.deleteFacture(id);
        logger.info("Facture with ID {} deleted successfully", id);
    }
    
    
   

    @PutMapping("/facture/{id}/statut")
    @Operation(summary = "Mettre � jour le statut d'une facture", 
               description = "Modifie le statut d'une facture existante")
    @ApiResponse(responseCode = "200", description = "Statut mis � jour",
                content = @Content(schema = @Schema(implementation = FactureResponse.class)))
    @ApiResponse(responseCode = "404", description = "Facture non trouv�e")
public FactureResponse updateStatut(@PathVariable Long id, 
                                  @RequestParam StatutFacture statut) {
    return factureService.updateStatut(id, statut);
}

@PutMapping("/facture/{id}/montant-restant")
 @Operation(summary = "Mettre � jour le montant restant", 
               description = "Modifie le montant restant � payer d'une facture")
    @ApiResponse(responseCode = "200", description = "Montant mis � jour",
                content = @Content(schema = @Schema(implementation = FactureResponse.class)))
    @ApiResponse(responseCode = "404", description = "Facture non trouv�e")
public FactureResponse updateMontantRestant(@PathVariable Long id, 
                                          @RequestParam Double montant) {
    return factureService.updateMontantRestant(id, montant);
}


 @GetMapping("/facture/by-company/{companyId}")
@Operation(summary = "Obtenir les factures par entreprise", 
           description = "R�cup�re les factures associ�es � une entreprise sp�cifique")
@ApiResponse(responseCode = "200", description = "Liste des factures trouv�es",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FactureResponse.class))))
@ApiResponse(responseCode = "404", description = "Aucune facture trouv�e")
public ResponseEntity<List<FactureResponse>> getFacturesByCompanyId(
        @PathVariable Long companyId) {
    
    List<FactureResponse> factures = factureService.getFacturesByCompanyId(companyId);
    
    if (factures.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(factures);
}
    }


