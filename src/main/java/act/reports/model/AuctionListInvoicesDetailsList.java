package act.reports.model;

import java.util.List;

public class AuctionListInvoicesDetailsList {

	private List<String> invoiceId;
	private List<String> vehicleYear;
	private List<String> vehicleMake;
	private List<String> vehicleModel;
	private List<String> vehicleVIN;
	private List<String> licensePlateCountry;
	private List<String> licensePlateState;
	private List<String> licensePlateNo;
	private List<String> markedAsSalvage;
	private List<String> lotLocation;
	private List<String> releasedTo;

	public List<String> getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(List<String> invoiceId) {
		this.invoiceId = invoiceId;
	}
	public List<String> getVehicleYear() {
		return vehicleYear;
	}
	public void setVehicleYear(List<String> vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
	public List<String> getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(List<String> vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public List<String> getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(List<String> vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public List<String> getVehicleVIN() {
		return vehicleVIN;
	}
	public void setVehicleVIN(List<String> vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	public List<String> getLicensePlateCountry() {
		return licensePlateCountry;
	}
	public void setLicensePlateCountry(List<String> licensePlateCountry) {
		this.licensePlateCountry = licensePlateCountry;
	}
	public List<String> getLicensePlateState() {
		return licensePlateState;
	}
	public void setLicensePlateState(List<String> licensePlateState) {
		this.licensePlateState = licensePlateState;
	}
	public List<String> getLicensePlateNo() {
		return licensePlateNo;
	}
	public void setLicensePlateNo(List<String> licensePlateNo) {
		this.licensePlateNo = licensePlateNo;
	}
	public List<String> getMarkedAsSalvage() {
		return markedAsSalvage;
	}
	public void setMarkedAsSalvage(List<String> markedAsSalvage) {
		this.markedAsSalvage = markedAsSalvage;
	}
	public List<String> getLotLocation() {
		return lotLocation;
	}
	public void setLotLocation(List<String> lotLocation) {
		this.lotLocation = lotLocation;
	}
	public List<String> getReleasedTo() {
		return releasedTo;
	}
	public void setReleasedTo(List<String> releasedTo) {
		this.releasedTo = releasedTo;
	}				
}