package act.reports.model;

public class RecapSalesSummary {

	private String accountType = "";
	private String noOfTows = "";
	private String totalCharges = "";
	private String chargePerTow = "";
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getNoOfTows() {
		return noOfTows;
	}
	public void setNoOfTows(String noOfTows) {
		this.noOfTows = noOfTows;
	}
	public String getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(String totalCharges) {
		this.totalCharges = totalCharges;
	}
	public String getChargePerTow() {
		return chargePerTow;
	}
	public void setChargePerTow(String chargePerTow) {
		this.chargePerTow = chargePerTow;
	}	
}