package act.reports.model;

import java.util.List;

public class DriverDetailsList {

	private List<String> driverId;
	private List<String> driverName;
	private List<String> noOfTows;
	private List<String> totalCharges;
	private List<String> use;
	private List<String> chargesPerTow;
	
	public List<String> getDriverId() {
		return driverId;
	}
	public void setDriverId(List<String> driverId) {
		this.driverId = driverId;
	}
	public List<String> getDriverName() {
		return driverName;
	}
	public void setDriverName(List<String> driverName) {
		this.driverName = driverName;
	}
	public List<String> getNoOfTows() {
		return noOfTows;
	}
	public void setNoOfTows(List<String> noOfTows) {
		this.noOfTows = noOfTows;
	}
	public List<String> getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(List<String> totalCharges) {
		this.totalCharges = totalCharges;
	}
	public List<String> getUse() {
		return use;
	}
	public void setUse(List<String> use) {
		this.use = use;
	}
	public List<String> getChargesPerTow() {
		return chargesPerTow;
	}
	public void setChargesPerTow(List<String> chargesPerTow) {
		this.chargesPerTow = chargesPerTow;
	}	
}
