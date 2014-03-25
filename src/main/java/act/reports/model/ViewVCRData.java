package act.reports.model;

import java.util.List;

public class ViewVCRData {
	
	private String fromDate = "";
	private String toDate = "";
	private String searchType = "";
	private String driverId = "";
	private String driverName = "";
	private String truckId = "";
	private String totalRecords = "";
	List<ViewVCRDetails> viewVCRDetails;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getTruckId() {
		return truckId;
	}
	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}	
	public String getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<ViewVCRDetails> getViewVCRDetails() {
		return viewVCRDetails;
	}
	public void setViewVCRDetails(List<ViewVCRDetails> viewVCRDetails) {
		this.viewVCRDetails = viewVCRDetails;
	}
}