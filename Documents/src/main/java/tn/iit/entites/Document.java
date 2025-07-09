package tn.iit.entites;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Document {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
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

		public Document(Long id, String nomfichier, byte[] contenuPdf, TypeDocument type, String filepath,
				Date dateAjout, Long factureId, Long depsneId, Long operationId,String driveFileId) {
			super();
			this.id = id;
			this.nomfichier = nomfichier;
			this.contenuPdf = contenuPdf;
			this.type = type;
			this.filepath = filepath;
			this.dateAjout = dateAjout;
			this.factureId = factureId;
			this.depsneId = depsneId;
			this.operationId = operationId;
      this.driveFileId=driveFileId;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
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