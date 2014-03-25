package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.ReceiptDetailsList;

@Service("receiptsControllerHelper")
public class ReceiptsControllerHelper {
	
	private Logger logger=Logger.getLogger(ReceiptsControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertReceiptsListToExcelFormat(ReceiptDetailsList receiptDetailsList)
	{
			logger.info("In convertReceiptsListToExcelFormat(ReceiptDetailsList receiptDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<receiptDetailsList.getInvoiceId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("invoiceId", (receiptDetailsList.getInvoiceId().get(i) != null) ? receiptDetailsList.getInvoiceId().get(i) : "");
				excelExpireData.put("account", (receiptDetailsList.getAccount().get(i) != null) ? receiptDetailsList.getAccount().get(i) : "");
				excelExpireData.put("billTo", (receiptDetailsList.getBillTo().get(i) != null) ? receiptDetailsList.getBillTo().get(i) : "");
				excelExpireData.put("paymentMethod", (receiptDetailsList.getPaymentMethod().get(i) != null) ? receiptDetailsList.getPaymentMethod().get(i) : "");
				excelExpireData.put("amount", (receiptDetailsList.getAmount().get(i) != null) ? receiptDetailsList.getAmount().get(i) : "");
				excelExpireData.put("userId", (receiptDetailsList.getUserId().get(i) != null) ? receiptDetailsList.getUserId().get(i) : "");
				excelExpireData.put("location", (receiptDetailsList.getLocation().get(i) != null) ? receiptDetailsList.getLocation().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getReceiptDetailsListExcelHeaders()
		{
			logger.info("In getReceiptDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("receiptDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("receiptDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("receiptDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("receiptDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("receiptDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("receiptDetails.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("receiptDetails.list.excel.header7"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
