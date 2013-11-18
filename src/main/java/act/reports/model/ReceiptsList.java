package act.reports.model;

import java.util.List;

public class ReceiptsList {

	List<Receipts> receipts;
	private Totals totals;
	
	public List<Receipts> getReceipts() {
		return receipts;
	}
	public void setReceipts(List<Receipts> receipts) {
		this.receipts = receipts;
	}
	public Totals getTotals() {
		return totals;
	}
	public void setTotals(Totals totals) {
		this.totals = totals;
	}
}
