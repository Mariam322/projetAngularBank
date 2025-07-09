package tn.iit.response;


import java.util.ArrayList;

import java.util.List;
import java.time.LocalDate;
import java.util.Date;
import jakarta.persistence.Lob;
import tn.iit.entites.Document;
import tn.iit.entites.TypeDocument;
import tn.iit.entites.TypePieceCommerciale;
import jakarta.persistence.Column;
public class DocumentResponse {
	 private Long id;
	    private String nomfichier;
	    private TypeDocument type;
         
	    @Lob
	    @Column(columnDefinition = "LONGBLOB")
	    private byte[] contenuPdf;  
	    private String filepath;;
	    private Date dateAjout;
	    private String numDepense;
	    private LocalDate dateDepense;
	    private LocalDate datePaiementDepense;
	    private LocalDate dateReceptionDepense;
	    private Double montantHtDepense;
	    private Double tvaDepense;
	    private Double montantTtcDepense;
	    private Double totalGeneralDepense;
	    private String numeroFacture;
	    private LocalDate dateFacture;
	    private LocalDate datePaiement;
	    private Double montantHt;
	    private Double tva;
	    private Double montantTtc;
	    private Double remise;
	 
	    private String statut;
	      private TypePieceCommerciale typePieceCommerciale;
	      private Date dateOperation ;
	  	private String numCheque;
	  	private Long debit;
	  	private Long credit;
	  	private  Long Solde;
	  	
	  	public DocumentResponse(Document document) {
	  		this.id=document.getId();
	  		this.nomfichier=document.getNomfichier();
	  		this.type=document.getType();
	  		this.filepath=document.getFilepath();
        this.contenuPdf=document.getContenuPdf();                                    
	  				
	  	}
	  	
	  	public DocumentResponse(Document document,FactureResponse facture,OperationResponse operation,DepenseResponse depense) {
	  	 if (facture != null) {
 	        this.numeroFacture=facture.getNumeroFacture();
 	           
 	            this.dateFacture = facture.getDateFacture();
 	            this.datePaiement = facture.getDatePaiement();
 	            this.montantHt=facture.getMontantHt();
 	            this.tva = facture.getTva();
 	            this.montantTtc = facture.getMontantTtc();
 	            this.remise = facture.getRemise();
 	            this.typePieceCommerciale = facture.getTypePieceCommerciale();
 	 }
 	 if(operation!=null) {
 		 this.dateOperation=operation.getDateOperation();
 		 this.numCheque=operation.getNumCheque();
 		 this.debit=operation.getDebit();
 		 this.credit=operation.getCredit();
 		 
 	 }
 	 if(depense!=null) {
 		 this.numDepense=depense.getNumDepense();
 		 this.dateDepense=depense.getDateDepense();
 		 this.datePaiementDepense=depense.getDatePaiementDepense();
 		 this.dateReceptionDepense=depense.getDateReceptionDepense();
 		 this.montantHtDepense=depense.getMontantHtDepense();
 		 this.tvaDepense=depense.getTvaDepense();
 		 this.montantTtcDepense=depense.getMontantTtcDepense();
 		 this.totalGeneralDepense=depense.getTotalGeneralDepense();
 	 }
	  	}

		public Long getId() {
			return id;
		}
	public byte[] getContenuPdf() {
			return contenuPdf;
		}
		public void setContenuPdf(byte[] contenuPdf) {
			this.contenuPdf = contenuPdf;
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

		public String getNumDepense() {
			return numDepense;
		}

		public void setNumDepense(String numDepense) {
			this.numDepense = numDepense;
		}

		public LocalDate getDateDepense() {
			return dateDepense;
		}

		public void setDateDepense(LocalDate dateDepense) {
			this.dateDepense = dateDepense;
		}

		public LocalDate getDatePaiementDepense() {
			return datePaiementDepense;
		}

		public void setDatePaiementDepense(LocalDate datePaiementDepense) {
			this.datePaiementDepense = datePaiementDepense;
		}

		public LocalDate getDateReceptionDepense() {
			return dateReceptionDepense;
		}

		public void setDateReceptionDepense(LocalDate dateReceptionDepense) {
			this.dateReceptionDepense = dateReceptionDepense;
		}

		public Double getMontantHtDepense() {
			return montantHtDepense;
		}

		public void setMontantHtDepense(Double montantHtDepense) {
			this.montantHtDepense = montantHtDepense;
		}

		public Double getTvaDepense() {
			return tvaDepense;
		}

		public void setTvaDepense(Double tvaDepense) {
			this.tvaDepense = tvaDepense;
		}

		public Double getMontantTtcDepense() {
			return montantTtcDepense;
		}

		public void setMontantTtcDepense(Double montantTtcDepense) {
			this.montantTtcDepense = montantTtcDepense;
		}

		public Double getTotalGeneralDepense() {
			return totalGeneralDepense;
		}

		public void setTotalGeneralDepense(Double totalGeneralDepense) {
			this.totalGeneralDepense = totalGeneralDepense;
		}

		public String getNumeroFacture() {
			return numeroFacture;
		}

		public void setNumeroFacture(String numeroFacture) {
			this.numeroFacture = numeroFacture;
		}

		public LocalDate getDateFacture() {
			return dateFacture;
		}

		public void setDateFacture(LocalDate dateFacture) {
			this.dateFacture = dateFacture;
		}

		public LocalDate getDatePaiement() {
			return datePaiement;
		}

		public void setDatePaiement(LocalDate datePaiement) {
			this.datePaiement = datePaiement;
		}

		public Double getMontantHt() {
			return montantHt;
		}

		public void setMontantHt(Double montantHt) {
			this.montantHt = montantHt;
		}

		public Double getTva() {
			return tva;
		}

		public void setTva(Double tva) {
			this.tva = tva;
		}

		public Double getMontantTtc() {
			return montantTtc;
		}

		public void setMontantTtc(Double montantTtc) {
			this.montantTtc = montantTtc;
		}

		public Double getRemise() {
			return remise;
		}

		public void setRemise(Double remise) {
			this.remise = remise;
		}

		public String getStatut() {
			return statut;
		}

		public void setStatut(String statut) {
			this.statut = statut;
		}

		public TypePieceCommerciale getTypePieceCommerciale() {
			return typePieceCommerciale;
		}

		public void setTypePieceCommerciale(TypePieceCommerciale typePieceCommerciale) {
			this.typePieceCommerciale = typePieceCommerciale;
		}

		public Date getDateOperation() {
			return dateOperation;
		}

		public void setDateOperation(Date dateOperation) {
			this.dateOperation = dateOperation;
		}

		public String getNumCheque() {
			return numCheque;
		}

		public void setNumCheque(String numCheque) {
			this.numCheque = numCheque;
		}

		public Long getDebit() {
			return debit;
		}

		public void setDebit(Long debit) {
			this.debit = debit;
		}

		public Long getCredit() {
			return credit;
		}

		public void setCredit(Long credit) {
			this.credit = credit;
		}

		public Long getSolde() {
			return Solde;
		}

		public void setSolde(Long solde) {
			Solde = solde;
		}
	  	
	  	
	  // Méthode utilitaire pour convertir une liste de Document en liste de DocumentResponse
public static List<DocumentResponse> toArrayList(List<Document> documents) {
    List<DocumentResponse> responseList = new ArrayList<>();
    for (Document doc : documents) {
        responseList.add(new DocumentResponse(doc));
    }
    return responseList;
}
	  	
}
