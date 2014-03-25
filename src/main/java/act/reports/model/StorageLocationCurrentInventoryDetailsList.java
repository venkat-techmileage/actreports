package act.reports.model;

import java.util.List;

public class StorageLocationCurrentInventoryDetailsList {

	private List<String> invoiceId;
	private List<String> serviceCallDate;
	private List<String> account;
	private List<String> reason;
	private List<String> year;
	private List<String> make;
	private List<String> model;
	private List<String> vin;
	private List<String> licensePlateCountry;
	private List<String> licensePlateState;
	private List<String> licensePlate;
	private List<String> daysInStorage;
	private List<String> markedForSalvage;
	private List<String> lotLocation;

	public List<String> getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(List<String> invoiceId) {
		this.invoiceId = invoiceId;
	}
	public List<String> getServiceCallDate() {
		return serviceCallDate;
	}
	public void setServiceCallDate(List<String> serviceCallDate) {
		this.serviceCallDate = serviceCallDate;
	}
	public List<String> getAccount() {
		return account;
	}
	public void setAccount(List<String> account) {
		this.account = account;
	}
	public List<String> getReason() {
		return reason;
	}
	public void setReason(List<String> reason) {
		this.reason = reason;
	}
	public List<String> getYear() {
		return year;
	}
	public void setYear(List<String> year) {
		this.year = year;
	}
	public List<String> getMake() {
		return make;
	}
	public void setMake(List<String> make) {
		this.make = make;
	}
	public List<String> getModel() {
		return model;
	}
	public void setModel(List<String> model) {
		this.model = model;
	}
	public List<String> getVin() {
		return vin;
	}
	public void setVin(List<String> vin) {
		this.vin = vin;
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
	public List<String> getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(List<String> licensePlate) {
		this.licensePlate = licensePlate;
	}
	public List<String> getDaysInStorage() {
		return daysInStorage;
	}
	public void setDaysInStorage(List<String> daysInStorage) {
		this.daysInStorage = daysInStorage;
	}
	public List<String> getMarkedForSalvage() {
		return markedForSalvage;
	}
	public void setMarkedForSalvage(List<String> markedForSalvage) {
		this.markedForSalvage = markedForSalvage;
	}
	public List<String> getLotLocation() {
		return lotLocation;
	}
	public void setLotLocation(List<String> lotLocation) {
		this.lotLocation = lotLocation;
	}				
}