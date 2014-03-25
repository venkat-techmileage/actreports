package act.reports.model;

public class AuctionList
{
	private String auctionListId;
	private String auctionListName;
	private String invoiceId;
	private String auctionDate;
	private String userId;
	
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
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getAuctionDate() {
		return auctionDate;
	}
	public void setAuctionDate(String auctionDate) {
		this.auctionDate = auctionDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}	
}