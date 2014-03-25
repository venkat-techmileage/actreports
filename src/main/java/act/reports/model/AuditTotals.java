package act.reports.model;

public class AuditTotals {

	private String userId = "";
	private String totalChanges = "";
	private String originalTotalCharges = "";
	private String newTotalCharges = "";
	private String chargesDifference = "";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTotalChanges() {
		return totalChanges;
	}
	public void setTotalChanges(String totalChanges) {
		this.totalChanges = totalChanges;
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