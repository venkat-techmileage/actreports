package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.MultipleInvoicePaymentDetailsList;
import act.reports.model.PaymentClient;
import act.reports.model.PaymentResponse;
import act.reports.model.ServiceStatus;
import act.reports.service.MailingDetails;
import act.reports.service.MailingService;
import act.reports.service.PdfService;

@Service("multipleInvoicePaymentControllerHelper")
public class MultipleInvoicePaymentControllerHelper {
	
	private Logger logger=Logger.getLogger(MultipleInvoicePaymentControllerHelper.class);

	@Autowired
	private Properties excelProps;
	
	@Autowired
	private PdfService pdfService;
	
	@Autowired
	private PaymentClient payClient;
	
	@Autowired
	private MailingService mailService;

	public List<Map<String,String>> convertInvoicesDetailsToExcelFormat(MultipleInvoicePaymentDetailsList multipleInvoicePaymentDetailsList)
	{
		logger.info("In convertAuctionListInvoicesToExcelFormat(MultipleInvoicePaymentDetailsList multipleInvoicePaymentDetailsList)...");
		List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
		try 
		{
			for(int i=0;i<multipleInvoicePaymentDetailsList.getInvoiceId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("invoiceId", (multipleInvoicePaymentDetailsList.getInvoiceId().get(i) != null) ? multipleInvoicePaymentDetailsList.getInvoiceId().get(i) : "");
				excelExpireData.put("serviceCallDate", (multipleInvoicePaymentDetailsList.getServiceCallDate().get(i) != null) ? multipleInvoicePaymentDetailsList.getServiceCallDate().get(i) : "");
				excelExpireData.put("vehicleYear", (multipleInvoicePaymentDetailsList.getVehicleYear().get(i) != null) ? multipleInvoicePaymentDetailsList.getVehicleYear().get(i) : "");
				excelExpireData.put("vehicleMake", (multipleInvoicePaymentDetailsList.getVehicleMake().get(i) != null) ? multipleInvoicePaymentDetailsList.getVehicleMake().get(i) : "");
				excelExpireData.put("vehicleModel", (multipleInvoicePaymentDetailsList.getVehicleModel().get(i) != null) ? multipleInvoicePaymentDetailsList.getVehicleModel().get(i) : "");
				excelExpireData.put("vehicleVIN", (multipleInvoicePaymentDetailsList.getVehicleVIN().get(i) != null) ? multipleInvoicePaymentDetailsList.getVehicleVIN().get(i) : "");
				excelExpireData.put("balanceAmt", (multipleInvoicePaymentDetailsList.getBalanceAmt().get(i) != null) ? multipleInvoicePaymentDetailsList.getBalanceAmt().get(i) : "");
				excelExpireData.put("paymentAmt", (multipleInvoicePaymentDetailsList.getPaymentAmt().get(i) != null) ? multipleInvoicePaymentDetailsList.getPaymentAmt().get(i) : "");
				//excelExpireData.put("paidInFull", (multipleInvoicePaymentDetailsList.getPaidInFull().get(i) != null) ? multipleInvoicePaymentDetailsList.getPaidInFull().get(i) : "");
				excelDetails.add(excelExpireData);
			}
		}
		catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}			
		return excelDetails;
	}
		
	public List<String> getInvoicesDetailsExcelHeaders()
	{
		logger.info("In getInvoicesDetailsExcelHeaders()...");
		List<String> excelHeaders=new ArrayList<String>();
		try
		{
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header1"));
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header2"));
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header3"));							
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header4"));
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header5"));
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header6"));
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header7"));
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header8"));
			excelHeaders.add(excelProps.getProperty("multipleInvoicePaymentDetails.list.excel.header9"));								
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return excelHeaders;
	}
	
	public PaymentClient getPaymentClient(String accountName, float amnt)
	{
		
	    payClient.setAmount(amnt);
		payClient.setAccountName(accountName);

		Random generator = new Random();
		int sequence = generator.nextInt(1000);
		long timeStamp = System.currentTimeMillis()/1000;
		
		payClient.setSequence(sequence);
		payClient.setTimestamp(timeStamp);
		
		String fingerPrint=getPaymnetFingerprint(payClient);
		payClient.setHash(fingerPrint);
		payClient.setFormType("PAYMENT_FORM");
		payClient.setDescription("ACT Invoice Billing Payment");
		return payClient;
	}

	private String getPaymnetFingerprint(PaymentClient payClient)
	{
		String fingerprint ="";
		try
		{
		    /*
			 * This section uses Java Cryptography functions to generate a fingerprint
			 * First, the Transaction key is converted to a "SecretKey" object
			 */
			 
			KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
			SecretKey key = new SecretKeySpec(payClient.getTransactionKey().getBytes(), "HmacMD5");
			
			/* A MAC object is created to generate the hash using the HmacMD5 algorithm
			 * 
			 */
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(key);
			String inputstring = payClient.getLoginID() + "^" + payClient.getSequence() + "^" + payClient.getTimestamp() + "^" + payClient.getAmount() + "^";
			byte[] result = mac.doFinal(inputstring.getBytes());
			
			/* Convert the result from byte[] to hexadecimal format
			 * 
			 */
			StringBuffer strbuf = new StringBuffer(result.length * 2);
			
			for(int i=0; i< result.length; i++)
			{
				if(((int) result[i] & 0xff) < 0x10)
					strbuf.append("0");
				strbuf.append(Long.toString((int) result[i] & 0xff, 16));
			}
			 fingerprint = strbuf.toString();
			
		}
		catch (Exception e)
		{
			logger.error("Error occured at Paymnet client::::"+e);
		}
		
		
		return fingerprint;
	}

	public PaymentResponse createPaymentResponse(HttpServletRequest webRequest)
	{
		logger.error("In createPaymentResponse()...");
		PaymentResponse payResp=new PaymentResponse();
		payResp.setAccountNo(webRequest.getParameter("x_account_number"));
		payResp.setAddress(webRequest.getParameter("x_address"));
		payResp.setAmount(webRequest.getParameter("x_amount"));
		payResp.setAuthCode(webRequest.getParameter("x_auth_code"));
		payResp.setAvsCode(webRequest.getParameter("x_avs_code"));
		payResp.setCardType(webRequest.getParameter("x_card_type"));
		payResp.setCavvResponse(webRequest.getParameter("x_cavv_response"));
		payResp.setCity(webRequest.getParameter("x_city"));
		payResp.setCompany(webRequest.getParameter("x_company"));
		payResp.setCountry(webRequest.getParameter("x_country"));
		payResp.setCustomerId(webRequest.getParameter("x_cust_id"));
		payResp.setCvv2RespCode(webRequest.getParameter("x_cvv2_resp_code"));
		payResp.setDescription(webRequest.getParameter("x_description"));
		payResp.setDuty(webRequest.getParameter("x_duty"));
		payResp.setEmail(webRequest.getParameter("x_email"));
		payResp.setFax(webRequest.getParameter("x_fax"));
		payResp.setFirstName(webRequest.getParameter("x_first_name"));
		payResp.setFreight(webRequest.getParameter("x_freight"));
		payResp.setInvoiceNo(webRequest.getParameter("x_invoice_num"));
		payResp.setLastName(webRequest.getParameter("x_last_name"));
		payResp.setMD5Hash(webRequest.getParameter("x_MD5_Hash"));
		payResp.setMethod(webRequest.getParameter("x_method"));
		payResp.setPhone(webRequest.getParameter("x_phone"));
		payResp.setPoNo(webRequest.getParameter("x_po_num"));
		payResp.setPrepaidBalanceOnCard(webRequest.getParameter("x_prepaid_balance_on_card"));
		payResp.setPrepaidReqAmount(webRequest.getParameter("x_prepaid_requested_amount"));
		payResp.setResponseCode(webRequest.getParameter("x_response_code"));
		payResp.setResponseReasonCode(webRequest.getParameter("x_response_reason_code"));
		payResp.setResponseReasonText(webRequest.getParameter("x_response_reason_text"));
		payResp.setShipToAddress(webRequest.getParameter("x_ship_to_address"));
		payResp.setShipToCity(webRequest.getParameter("x_ship_to_city"));
		payResp.setShipToCompany(webRequest.getParameter("x_ship_to_company"));
		payResp.setShipToCountry(webRequest.getParameter("x_ship_to_country"));
		payResp.setShipToFirstName(webRequest.getParameter("x_ship_to_first_name"));
		payResp.setShipToLastName(webRequest.getParameter("x_ship_to_last_name"));
		payResp.setShipToState(webRequest.getParameter("x_ship_to_state"));
		payResp.setShipToZip(webRequest.getParameter("x_ship_to_zip"));
		payResp.setSplitTenderId(webRequest.getParameter("x_split_tender_id"));
		payResp.setState(webRequest.getParameter("x_state"));
		payResp.setTax(webRequest.getParameter("x_tax"));
		payResp.setTaxExempt(webRequest.getParameter("x_tax_exempt"));
		payResp.setTransactionId(webRequest.getParameter("x_trans_id"));
		payResp.setType(webRequest.getParameter("x_type"));
		payResp.setZip(webRequest.getParameter("x_zip"));
		payResp.setAccountName(webRequest.getParameter("x_account_name"));
		payResp.setPaymentDetails(webRequest.getParameter("x_payment_details"));
		
		return payResp;
	}
	
	/*public ServiceStatus sendEmailPaymentReceipt(String accountName, String pdfPath)
	{
			boolean pdfGen=pdfService.generatePdfReceipt(accountName,pdfPath);
		    MailingDetails mailDetails=new MailingDetails();
		    mailDetails.setMailFrom("venkat@techmileage.com");
		    mailDetails.setMailTo("srinivas@techmileage.com");
		    mailDetails.setMailSubject("ACT Billing Payment");
		    mailDetails.setMailContent("Some testing data and a test PDF file as an attachment from ACT");
		    List<String> paths=new ArrayList<String>();
		    paths.add(pdfPath);
		    mailDetails.setAttachments(paths);
		    mailService.sendReciptThroughMail(mailDetails);
			ServiceStatus status=new ServiceStatus();
			if(pdfGen)
			{
				status.setMessage("PDF generated successfully");
				status.setStatus("OK");
			}
			else
			{
				status.setMessage("PDF not generated");
				status.setStatus("FAIL");
			}
		return status;
	}*/
}
