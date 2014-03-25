package act.reports.model;

public class DispatchedInvoiceDetails {
	
	private String callTakerId = "";
	private String dispatchId = "";
	private String driverId = "";
	private String noOwnerInfo = "";
	private String poRequired = "";
	
	public String getCallTakerId() {
		return callTakerId;
	}
	public void setCallTakerId(String callTakerId) {
		this.callTakerId = callTakerId;
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
	public String getNoOwnerInfo() {
		return noOwnerInfo;
	}
	public void setNoOwnerInfo(String noOwnerInfo) {
		this.noOwnerInfo = noOwnerInfo;
	}
	public String getPoRequired() {
		return poRequired;
	}
	public void setPoRequired(String poRequired) {
		this.poRequired = poRequired;
	}		
}
