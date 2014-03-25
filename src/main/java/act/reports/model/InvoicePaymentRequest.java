package act.reports.model;

public class InvoicePaymentRequest 
{
	private Integer paymentId;
	private String invoiceId;
	private String paymentType;
	private float paymentAmt;
	private String chequeNo;
	private String cctxNo;
	private String userId;
	private String paymentLocation;
	
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
	public float getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(float paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getCctxNo() {
		return cctxNo;
	}
	public void setCctxNo(String cctxNo) {
		this.cctxNo = cctxNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentLocation() {
		return paymentLocation;
	}
	public void setPaymentLocation(String paymentLocation) {
		this.paymentLocation = paymentLocation;
	}
	
}
