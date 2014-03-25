package act.reports.model;

import java.util.List;

public class TruckDetailsList {

	private List<String> truck;
	private List<String> noOfTows;
	private List<String> totalCharges;
	private List<String> use;
	private List<String> chargesPerTow;
	private List<String> actualMileage;
	private List<String> milesPerTow;
	
	public List<String> getTruck() {
		return truck;
	}
	public void setTruck(List<String> truck) {
		this.truck = truck;
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
	public List<String> getActualMileage() {
		return actualMileage;
	}
	public void setActualMileage(List<String> actualMileage) {
		this.actualMileage = actualMileage;
	}
	public List<String> getMilesPerTow() {
		return milesPerTow;
	}
	public void setMilesPerTow(List<String> milesPerTow) {
		this.milesPerTow = milesPerTow;
	}	
}
