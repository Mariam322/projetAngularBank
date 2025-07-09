package tn.iit.response;

import java.time.LocalDate;

import tn.iit.entites.TypePieceCommerciale;

public class FactureResponse {
	private Long id;
    private String numeroFacture;
    private LocalDate dateFacture;
    private LocalDate datePaiement;
    private Double montantHt;
    private Double tva;
    private Double montantTtc;
    private Double remise;
 
    private String statut;
      private TypePieceCommerciale typePieceCommerciale;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
      
      
}
