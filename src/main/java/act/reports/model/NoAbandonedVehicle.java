package act.reports.model;

import java.util.List;

public class NoAbandonedVehicle {
	
	private String totalVehicles = "";
	List<NoAbandonedVehicleDetails> noAbandonedVehicleDetails;
	
	public String getTotalVehicles() {
		return totalVehicles;
	}
	public void setTotalVehicles(String totalVehicles) {
		this.totalVehicles = totalVehicles;
	}
	public List<NoAbandonedVehicleDetails> getNoAbandonedVehicleDetails() {
		return noAbandonedVehicleDetails;
	}
	public void setNoAbandonedVehicleDetails(
			List<NoAbandonedVehicleDetails> noAbandonedVehicleDetails) {
		this.noAbandonedVehicleDetails = noAbandonedVehicleDetails;
	}
}