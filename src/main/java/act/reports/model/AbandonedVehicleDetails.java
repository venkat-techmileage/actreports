package act.reports.model;

public class AbandonedVehicleDetails {

	private String serviceCallDate = "";
	private String orDr = "";
	private String vin = "";
	private String licensePlate = "";
	private String titleRequested = "";
	private String titleReceived = "";
	private String released = "";
	public String getServiceCallDate() {
		return serviceCallDate;
	}
	public void setServiceCallDate(String serviceCallDate) {
		this.serviceCallDate = serviceCallDate;
	}
	public String getOrDr() {
		return orDr;
	}
	public void setOrDr(String orDr) {
		this.orDr = orDr;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public String getTitleRequested() {
		return titleRequested;
	}
	public void setTitleRequested(String titleRequested) {
		this.titleRequested = titleRequested;
	}
	public String getTitleReceived() {
		return titleReceived;
	}
	public void setTitleReceived(String titleReceived) {
		this.titleReceived = titleReceived;
	}
	public String getReleased() {
		return released;
	}
	public void setReleased(String released) {
		this.released = released;
	}
	
	}
