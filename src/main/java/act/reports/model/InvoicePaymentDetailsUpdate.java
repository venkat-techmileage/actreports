package act.reports.model;


public class InvoicePaymentDetailsUpdate {

	private String invoiceId="";
	private String paymentType="";
	private String paymentAmt="";
	private String chequeNo="";	
	private String ccTxNo="";
	private String userId="";
	private String paymentLoc="";
	private String paymentDate="";
		
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getCcTxNo() {
		return ccTxNo;
	}
	public void setCcTxNo(String ccTxNo) {
		this.ccTxNo = ccTxNo;
	}	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPaymentLoc() {
		return paymentLoc;
	}
	public void setPaymentLoc(String paymentLoc) {
		this.paymentLoc = paymentLoc;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}							
}