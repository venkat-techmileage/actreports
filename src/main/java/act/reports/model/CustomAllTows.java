package act.reports.model;

import java.util.List;

public class CustomAllTows {
	
	private String fromDate = "";
	private String toDate = "";
	List<CustomAllTowsDetails> customAllTowsDetails;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<CustomAllTowsDetails> getCustomAllTowsDetails() {
		return customAllTowsDetails;
	}
	public void setCustomAllTowsDetails(
			List<CustomAllTowsDetails> customAllTowsDetails) {
		this.customAllTowsDetails = customAllTowsDetails;
	}
	
	
}