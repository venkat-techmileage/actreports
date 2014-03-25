package act.reports.model;

import java.util.List;

public class AuctionListInvoices {

	private String auctionListId = "";
	private String auctionListName = "";
	private String auctionDate = "";
	private String totalVehicles = "";
	List<AuctionListInvoicesDetails> auctionListInvoicesDetailsList;
	
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
	public String getAuctionDate() {
		return auctionDate;
	}
	public void setAuctionDate(String auctionDate) {
		this.auctionDate = auctionDate;
	}
	public String getTotalVehicles() {
		return totalVehicles;
	}
	public void setTotalVehicles(String totalVehicles) {
		this.totalVehicles = totalVehicles;
	}
	public List<AuctionListInvoicesDetails> getAuctionListInvoicesDetailsList() {
		return auctionListInvoicesDetailsList;
	}
	public void setAuctionListInvoicesDetailsList(
			List<AuctionListInvoicesDetails> auctionListInvoicesDetailsList) {
		this.auctionListInvoicesDetailsList = auctionListInvoicesDetailsList;
	}				
}