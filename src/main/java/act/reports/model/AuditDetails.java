package act.reports.model;

public class AuditDetails {
	
	private String userId = "";
	private String changeDate = "";
	private String changeTime = "";
	private String invoice = "";
	private String originalTotalCharges = "";
	private String newTotalCharges = "";
	private String chargesDifference = "";
	public String getUserId() {
		return userId;
	}
	public   void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getOriginalTotalCharges() {
		return originalTotalCharges;
	}
	public void setOriginalTotalCharges(String originalTotalCharges) {
		this.originalTotalCharges = originalTotalCharges;
	}
	public String getNewTotalCharges() {
		return newTotalCharges;
	}
	public void setNewTotalCharges(String newTotalCharges) {
		this.newTotalCharges = newTotalCharges;
	}
	public String getChargesDifference() {
		return chargesDifference;
	}
	public void setChargesDifference(String chargesDifference) {
		this.chargesDifference = chargesDifference;
	}

	
}