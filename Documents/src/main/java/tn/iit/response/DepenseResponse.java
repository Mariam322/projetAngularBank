package tn.iit.response;

import java.time.LocalDate;

public class DepenseResponse {
private Long id;
    
    private String numDepense;
    private LocalDate dateDepense;
    private LocalDate datePaiementDepense;
    private LocalDate dateReceptionDepense;
    private Double montantHtDepense;
    private Double tvaDepense;
    private Double montantTtcDepense;
    private Double totalGeneralDepense;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
    
    
    
}
