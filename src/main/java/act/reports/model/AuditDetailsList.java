package act.reports.model;

import java.util.List;

public class AuditDetailsList {

	private List<String> userId;
	private List<String> changeDate;
	private List<String> changeTime;
	private List<String> invoice;
	private List<String> originalTotalCharges;
	private List<String> newTotalCharges;
	private List<String> chargesDifference;
	public List<String> getUserId() {
		return userId;
	}
	public void setUserId(List<String> userId) {
		this.userId = userId;
	}
	public List<String> getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(List<String> changeDate) {
		this.changeDate = changeDate;
	}
	public List<String> getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(List<String> changeTime) {
		this.changeTime = changeTime;
	}
	public List<String> getInvoice() {
		return invoice;
	}
	public void setInvoice(List<String> invoice) {
		this.invoice = invoice;
	}
	public List<String> getOriginalTotalCharges() {
		return originalTotalCharges;
	}
	public void setOriginalTotalCharges(List<String> originalTotalCharges) {
		this.originalTotalCharges = originalTotalCharges;
	}
	public List<String> getNewTotalCharges() {
		return newTotalCharges;
	}
	public void setNewTotalCharges(List<String> newTotalCharges) {
		this.newTotalCharges = newTotalCharges;
	}
	public List<String> getChargesDifference() {
		return chargesDifference;
	}
	public void setChargesDifference(List<String> chargesDifference) {
		this.chargesDifference = chargesDifference;
	}

	
}