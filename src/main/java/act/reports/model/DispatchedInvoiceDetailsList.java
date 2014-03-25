package act.reports.model;

import java.util.List;

public class DispatchedInvoiceDetailsList {

	private List<String> callTakerId;
	private List<String> dispatchId;
	private List<String> driverId;
	private List<String> noOwnerInfo;
	private List<String> poRequired;
	
	public List<String> getCallTakerId() {
		return callTakerId;
	}
	public void setCallTakerId(List<String> callTakerId) {
		this.callTakerId = callTakerId;
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
	public List<String> getNoOwnerInfo() {
		return noOwnerInfo;
	}
	public void setNoOwnerInfo(List<String> noOwnerInfo) {
		this.noOwnerInfo = noOwnerInfo;
	}
	public List<String> getPoRequired() {
		return poRequired;
	}
	public void setPoRequired(List<String> poRequired) {
		this.poRequired = poRequired;
	}			
}
