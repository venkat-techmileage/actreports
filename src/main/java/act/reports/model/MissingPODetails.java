package act.reports.model;

public class MissingPODetails {
	
	private String invoiceId = "";
	private String serviceCallDate = "";
	private String year = "";
	private String make = "";
	private String model = "";
	private String vin = "";
	private String billedTo = "";
	private String amount = "";
	private String poNumber = "";
	
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
	public String getBilledTo() {
		return billedTo;
	}
	public void setBilledTo(String billedTo) {
		this.billedTo = billedTo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}			
}