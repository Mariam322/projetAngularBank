package tn.iit.request;

import java.time.LocalDate;

public class UpdateDepenseRequest {
	private String numDepense;
    private LocalDate dateDepense;
    private LocalDate datePaiementDepense;
    private LocalDate dateReceptionDepense;
    private Double montantHtDepense;
    private Double tvaDepense;
    private Double montantTtcDepense;
    private Double totalGeneralDepense;
	public UpdateDepenseRequest(String numDepense, LocalDate dateDepense, LocalDate datePaiementDepense,
			LocalDate dateReceptionDepense, Double montantHtDepense, Double tvaDepense, Double montantTtcDepense,
			Double totalGeneralDepense) {
		super();
		this.numDepense = numDepense;
		this.dateDepense = dateDepense;
		this.datePaiementDepense = datePaiementDepense;
		this.dateReceptionDepense = dateReceptionDepense;
		this.montantHtDepense = montantHtDepense;
		this.tvaDepense = tvaDepense;
		this.montantTtcDepense = montantTtcDepense;
		this.totalGeneralDepense = totalGeneralDepense;
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
