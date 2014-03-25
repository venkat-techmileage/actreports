package act.reports.model;

import java.util.List;

public class MissingPOs {

	private String totalInvoices = "";
	List<MissingPODetails> missingPODetailsList;
	
	public String getTotalInvoices() {
		return totalInvoices;
	}
	public void setTotalInvoices(String totalInvoices) {
		this.totalInvoices = totalInvoices;
	}
	public List<MissingPODetails> getMissingPODetailsList() {
		return missingPODetailsList;
	}
	public void setMissingPODetailsList(List<MissingPODetails> missingPODetailsList) {
		this.missingPODetailsList = missingPODetailsList;
	}				
}