package act.reports.model;

import java.util.List;

public class TowTypDetailsList {

	private List<String> type;
	private List<String> noOfTows;
	private List<String> totalCharges;
	private List<String> use;
	private List<String> chargesPerTow;
	
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
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
