package act.reports.model;

import java.util.List;

public class MissingPODetailsList {

	private List<String> invoiceId;
	private List<String> serviceCallDate;
	private List<String> year;
	private List<String> make;
	private List<String> model;
	private List<String> vin;
	private List<String> billedTo;
	private List<String> amount;
	private List<String> poNumber;
	
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
	public List<String> getBilledTo() {
		return billedTo;
	}
	public void setBilledTo(List<String> billedTo) {
		this.billedTo = billedTo;
	}
	public List<String> getAmount() {
		return amount;
	}
	public void setAmount(List<String> amount) {
		this.amount = amount;
	}
	public List<String> getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(List<String> poNumber) {
		this.poNumber = poNumber;
	}				
}