package act.reports.model;

import java.util.List;

public class AbandonedVehicle {
	
	private String fromDate = "";
	private String toDate = "";
	private String totalVehicles = "";
	List<AbandonedVehicleDetails> abandonedVehicleDetails;
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
	public String getTotalVehicles() {
		return totalVehicles;
	}
	public void setTotalVehicles(String totalVehicles) {
		this.totalVehicles = totalVehicles;
	}
	public List<AbandonedVehicleDetails> getAbandonedVehicleDetails() {
		return abandonedVehicleDetails;
	}
	public void setAbandonedVehicleDetails(
			List<AbandonedVehicleDetails> abandonedVehicleDetails) {
		this.abandonedVehicleDetails = abandonedVehicleDetails;
	}

	
		}
