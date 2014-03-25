package act.reports.model;

public class DriverDetails {

	private String driverId = "";
	private String driverName = "";
	private String noOfTows = "";
	private String totalCharges = "";
	private String use = "";
	private String chargesPerTow = "";
	
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
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
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getChargesPerTow() {
		return chargesPerTow;
	}
	public void setChargesPerTow(String chargesPerTow) {
		this.chargesPerTow = chargesPerTow;
	}
}
