package act.reports.model;

import java.util.List;

public class StorageLocationCurrentInventoryDetails {
	
	private String invoiceId = "";
	private String serviceCallDate = "";
	private String account = "";
	private String reason = "";
	private String year = "";
	private String make = "";
	private String model = "";
	private String vin = "";
	private String licensePlateCountry = "";
	private String licensePlateState = "";
	private String licensePlate = "";
	private String daysInStorage = "";
	private String markedForSalvage = "";
	private String lotLocation = "";
	
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getServiceCallDate() {
		return serviceCallDate;
	}
	public void setServiceCallDate(String serviceCallDate) {
		this.serviceCallDate = serviceCallDate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getLicensePlateCountry() {
		return licensePlateCountry;
	}
	public void setLicensePlateCountry(String licensePlateCountry) {
		this.licensePlateCountry = licensePlateCountry;
	}
	public String getLicensePlateState() {
		return licensePlateState;
	}
	public void setLicensePlateState(String licensePlateState) {
		this.licensePlateState = licensePlateState;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public String getDaysInStorage() {
		return daysInStorage;
	}
	public void setDaysInStorage(String daysInStorage) {
		this.daysInStorage = daysInStorage;
	}
	public String getMarkedForSalvage() {
		return markedForSalvage;
	}
	public void setMarkedForSalvage(String markedForSalvage) {
		this.markedForSalvage = markedForSalvage;
	}
	public String getLotLocation() {
		return lotLocation;
	}
	public void setLotLocation(String lotLocation) {
		this.lotLocation = lotLocation;
	}		
}