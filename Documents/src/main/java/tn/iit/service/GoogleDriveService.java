package tn.iit.service;
import com.google.api.services.drive.model.Permission;
import java.util.Collections;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
public class GoogleDriveService {
private static final Logger logger = LoggerFactory.getLogger(GoogleDriveService.class);
    private final Drive driveService;

   /* public String uploadFile(String filePath, String fileName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        java.io.File file = new java.io.File(filePath);
        FileContent mediaContent = new FileContent("application/pdf", file);

        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        return uploadedFile.getId();
    }
  /*  public byte[] downloadFile(String fileId) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    driveService.files().get(fileId)
            .executeMediaAndDownloadTo(outputStream);
    return outputStream.toByteArray();
}*/
/*public void shareFileWithUser(String fileId, String userEmail) throws IOException {
    Permission permission = new Permission()
            .setType("user")
            .setRole("reader") // "writer" pour les permissions d'édition
            .setEmailAddress(userEmail);
    
    driveService.permissions().create(fileId, permission).execute();
}
public byte[] downloadFile(String fileId) throws IOException {
    try {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        driveService.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
        return outputStream.toByteArray();
    } catch (IOException e) {
        logger.error("Error downloading file from Google Drive with id: " + fileId, e);
        throw e;
    }
}*/
public String uploadFile(String filePath, String fileName) throws IOException {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(fileName);
            
            // Option: Définir un dossier parent spécifique
            // fileMetadata.setParents(Collections.singletonList("1ABCxyz..."));

            java.io.File file = new java.io.File(filePath);
            FileContent mediaContent = new FileContent("application/octet-stream", file);

            File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                    .setFields("id,name,webViewLink")
                    .execute();

            logger.info("Fichier uploadé vers Google Drive - ID: {}, Nom: {}", 
                uploadedFile.getId(), uploadedFile.getName());
            
            return uploadedFile.getId();
        } catch (Exception e) {
            logger.error("Échec de l'upload vers Google Drive", e);
            throw new IOException("Erreur lors de l'upload vers Google Drive", e);
        }
    }

    public byte[] downloadFile(String fileId) throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            driveService.files().get(fileId)
                    .executeMediaAndDownloadTo(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Erreur lors du téléchargement du fichier depuis Google Drive. ID: " + fileId, e);
            throw new IOException("Impossible de télécharger le fichier depuis Google Drive", e);
        }
    }

    public void shareFileWithUser(String fileId, String userEmail) throws IOException {
        try {
            Permission permission = new Permission()
                    .setType("user")
                    .setRole("reader")
                    .setEmailAddress(userEmail);
            
            driveService.permissions().create(fileId, permission)
                    .setFields("id")
                    .execute();
            
            logger.info("Fichier partagé avec {} - ID: {}", userEmail, fileId);
        } catch (Exception e) {
            logger.error("Échec du partage du fichier", e);
            throw new IOException("Erreur lors du partage du fichier", e);
        }
    }

}
