package act.reports.model;

public class TruckDetails {

	private String truck = "";
	private String noOfTows = "";
	private String totalCharges = "";
	private String use = "";
	private String chargesPerTow = "";
	private String actualMileage = "";
	private String milesPerTow = "";
	
	public String getTruck() {
		return truck;
	}
	public void setTruck(String truck) {
		this.truck = truck;
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
	public String getActualMileage() {
		return actualMileage;
	}
	public void setActualMileage(String actualMileage) {
		this.actualMileage = actualMileage;
	}
	public String getMilesPerTow() {
		return milesPerTow;
	}
	public void setMilesPerTow(String milesPerTow) {
		this.milesPerTow = milesPerTow;
	}
}
