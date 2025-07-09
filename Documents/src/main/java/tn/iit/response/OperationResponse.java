package tn.iit.response;

import java.util.Date;

public class OperationResponse {

	private Long idOperation;
	private Date dateOperation ;
	private String numCheque;
	private Long debit;
	private Long credit;
	private  Long Solde;
	public Long getIdOperation() {
		return idOperation;
	}
	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
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
	
	
}
