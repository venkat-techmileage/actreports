package act.reports.model;

import java.util.List;

public class AbandonedVehicleDetailsList {

	private List<String> serviceCallDate;
	private List<String> orDr;
	private List<String> vin;
	private List<String> licensePlate;
	private List<String> titleRequested;
	private List<String> titleReceived;
	private List<String> released;
	public List<String> getServiceCallDate() {
		return serviceCallDate;
	}
	public void setServiceCallDate(List<String> serviceCallDate) {
		this.serviceCallDate = serviceCallDate;
	}
	public List<String> getOrDr() {
		return orDr;
	}
	public void setOrDr(List<String> orDr) {
		this.orDr = orDr;
	}
	public List<String> getVin() {
		return vin;
	}
	public void setVin(List<String> vin) {
		this.vin = vin;
	}
	public List<String> getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(List<String> licensePlate) {
		this.licensePlate = licensePlate;
	}
	public List<String> getTitleRequested() {
		return titleRequested;
	}
	public void setTitleRequested(List<String> titleRequested) {
		this.titleRequested = titleRequested;
	}
	public List<String> getTitleReceived() {
		return titleReceived;
	}
	public void setTitleReceived(List<String> titleReceived) {
		this.titleReceived = titleReceived;
	}
	public List<String> getReleased() {
		return released;
	}
	public void setReleased(List<String> released) {
		this.released = released;
	}
	}
