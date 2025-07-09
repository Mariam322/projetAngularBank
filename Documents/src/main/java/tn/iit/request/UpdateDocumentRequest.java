package tn.iit.request;

import java.util.Date;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import tn.iit.entites.TypeDocument;

public class UpdateDocumentRequest {
private String nomfichier;
	    
	    @Lob
	    @Column(columnDefinition = "LONGBLOB")
	    private byte[] contenuPdf;  
	    
	    private TypeDocument type;
	    private String filepath;
	    private Date dateAjout;
	    private String driveFileId;
	    private Long factureId; 
	    private Long depsneId;
	    private Long operationId;
		
	
	
		public String getNomfichier() {
			return nomfichier;
		}
		public void setNomfichier(String nomfichier) {
			this.nomfichier = nomfichier;
		}
		public byte[] getContenuPdf() {
			return contenuPdf;
		}
		public void setContenuPdf(byte[] contenuPdf) {
			this.contenuPdf = contenuPdf;
		}
		public TypeDocument getType() {
			return type;
		}
		public void setType(TypeDocument type) {
			this.type = type;
		}
		public String getFilepath() {
			return filepath;
		}
		public void setFilepath(String filepath) {
			this.filepath = filepath;
		}
		public Date getDateAjout() {
			return dateAjout;
		}
		public void setDateAjout(Date dateAjout) {
			this.dateAjout = dateAjout;
		}
		
		public Long getDepsneId() {
			return depsneId;
		}
		public void setDepsneId(Long depsneId) {
			this.depsneId = depsneId;
		}
		public Long getOperationId() {
			return operationId;
		}
		public void setOperationId(Long operationId) {
			this.operationId = operationId;
		}
		public Long getFactureId() {
			return factureId;
		}
		public void setFactureId(Long factureId) {
			this.factureId = factureId;
		}

		public String getDriveFileId() {
			return driveFileId;
		}
		public void setDriveFileId(String driveFileId) {
			this.driveFileId = driveFileId;
		}
    
    
}
