package act.reports.model;

import java.util.List;

public class CustomRelease {
	
	private String fromDate = "";
	private String toDate = "";
	List<CustomReleaseDetails> customReleaseDetails;
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
	public List<CustomReleaseDetails> getCustomReleaseDetails() {
		return customReleaseDetails;
	}
	public void setCustomReleaseDetails(
			List<CustomReleaseDetails> customReleaseDetails) {
		this.customReleaseDetails = customReleaseDetails;
	}
	
	
	
}