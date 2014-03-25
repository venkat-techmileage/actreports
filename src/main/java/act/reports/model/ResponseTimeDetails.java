package act.reports.model;

public class ResponseTimeDetails {
	
	private String invoiceId = "";
	private String account = "";
	private String callerTakerId = "";
	private String dispatchId = "";
	private String driverId = "";
	private String callTime = "";
	private String dispatchedTime = "";
	private String arrivalTime = "";
	private String clearedTime = "";
	private String callToDispatch = "";
	private String callToArrival = "";
	private String dispatchToClear = "";
	private String late = "";
	
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}	
	public String getCallerTakerId() {
		return callerTakerId;
	}
	public void setCallerTakerId(String callerTakerId) {
		this.callerTakerId = callerTakerId;
	}
	public String getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(String dispatchId) {
		this.dispatchId = dispatchId;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public String getDispatchedTime() {
		return dispatchedTime;
	}
	public void setDispatchedTime(String dispatchedTime) {
		this.dispatchedTime = dispatchedTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getClearedTime() {
		return clearedTime;
	}
	public void setClearedTime(String clearedTime) {
		this.clearedTime = clearedTime;
	}
	public String getCallToDispatch() {
		return callToDispatch;
	}
	public void setCallToDispatch(String callToDispatch) {
		this.callToDispatch = callToDispatch;
	}
	public String getCallToArrival() {
		return callToArrival;
	}
	public void setCallToArrival(String callToArrival) {
		this.callToArrival = callToArrival;
	}
	public String getDispatchToClear() {
		return dispatchToClear;
	}
	public void setDispatchToClear(String dispatchToClear) {
		this.dispatchToClear = dispatchToClear;
	}
	public String getLate() {
		return late;
	}
	public void setLate(String late) {
		this.late = late;
	}				
}