package act.reports.model;

import java.util.List;

public class ResponseTimeDetailsList {

	private List<String> invoiceId;
	private List<String> account;
	private List<String> callerTakerId;
	private List<String> dispatchId;
	private List<String> driverId;
	private List<String> callTime;
	private List<String> dispatchedTime;
	private List<String> arrivalTime;
	private List<String> clearedTime;
	private List<String> callToDispatch;
	private List<String> callToArrival;
	private List<String> dispatchToClear;
	private List<String> late;
	
	public List<String> getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(List<String> invoiceId) {
		this.invoiceId = invoiceId;
	}
	public List<String> getAccount() {
		return account;
	}
	public void setAccount(List<String> account) {
		this.account = account;
	}	
	public List<String> getCallerTakerId() {
		return callerTakerId;
	}
	public void setCallerTakerId(List<String> callerTakerId) {
		this.callerTakerId = callerTakerId;
	}
	public List<String> getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(List<String> dispatchId) {
		this.dispatchId = dispatchId;
	}
	public List<String> getDriverId() {
		return driverId;
	}
	public void setDriverId(List<String> driverId) {
		this.driverId = driverId;
	}
	public List<String> getCallTime() {
		return callTime;
	}
	public void setCallTime(List<String> callTime) {
		this.callTime = callTime;
	}
	public List<String> getDispatchedTime() {
		return dispatchedTime;
	}
	public void setDispatchedTime(List<String> dispatchedTime) {
		this.dispatchedTime = dispatchedTime;
	}
	public List<String> getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(List<String> arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public List<String> getClearedTime() {
		return clearedTime;
	}
	public void setClearedTime(List<String> clearedTime) {
		this.clearedTime = clearedTime;
	}
	public List<String> getCallToDispatch() {
		return callToDispatch;
	}
	public void setCallToDispatch(List<String> callToDispatch) {
		this.callToDispatch = callToDispatch;
	}
	public List<String> getCallToArrival() {
		return callToArrival;
	}
	public void setCallToArrival(List<String> callToArrival) {
		this.callToArrival = callToArrival;
	}
	public List<String> getDispatchToClear() {
		return dispatchToClear;
	}
	public void setDispatchToClear(List<String> dispatchToClear) {
		this.dispatchToClear = dispatchToClear;
	}
	public List<String> getLate() {
		return late;
	}
	public void setLate(List<String> late) {
		this.late = late;
	}					
}