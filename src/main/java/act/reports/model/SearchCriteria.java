package act.reports.model;

public class SearchCriteria {

	private String searchString;
	private String accountName = "";
	private String accountType = "";
	private String asOfDate = "";
	private String fromDate = "";
	private String toDate = "";
	private String compareToFromDate = "";
	private String compareToToDate = "";
	private String location = "";
	private String userId = "";
	private String userName = "";
	private String driverId = "";
	private String driverName = "";
	private String truckId = "";
	private String auctionListId = "";
	private String auctionListName = "";
	private String ageRangeStart="";
	private String ageRangeEnd="";
	private String statementType="";

	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(String asOfDate) {
		this.asOfDate = asOfDate;
	}
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
	public String getCompareToFromDate() {
		return compareToFromDate;
	}
	public void setCompareToFromDate(String compareToFromDate) {
		this.compareToFromDate = compareToFromDate;
	}
	public String getCompareToToDate() {
		return compareToToDate;
	}
	public void setCompareToToDate(String compareToToDate) {
		this.compareToToDate = compareToToDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getAuctionListId() {
		return auctionListId;
	}
	public void setAuctionListId(String auctionListId) {
		this.auctionListId = auctionListId;
	}
	public String getAuctionListName() {
		return auctionListName;
	}
	public void setAuctionListName(String auctionListName) {
		this.auctionListName = auctionListName;
	}
	public String getAgeRangeStart() {
		return ageRangeStart;
	}
	public void setAgeRangeStart(String ageRangeStart) {
		this.ageRangeStart = ageRangeStart;
	}
	public String getAgeRangeEnd() {
		return ageRangeEnd;
	}
	public void setAgeRangeEnd(String ageRangeEnd) {
		this.ageRangeEnd = ageRangeEnd;
	}
	public String getStatementType() {
		return statementType;
	}
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}	
}