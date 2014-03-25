package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.SingleDriverCommissionDetailsList;

@Service("singleDriverCommissionControllerHelper")
public class SingleDriverCommissionControllerHelper {
	
	private Logger logger=Logger.getLogger(SingleDriverCommissionControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertSingleDriverCommissionToExcelFormat(SingleDriverCommissionDetailsList singleDriverCommissionDetailsList)
	{
			logger.info("In convertSingleDriverCommissionToExcelFormat(SingleDriverCommissionDetailsList singleDriverCommissionDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<singleDriverCommissionDetailsList.getInvoiceId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("date", (singleDriverCommissionDetailsList.getDate().get(i) != null) ? singleDriverCommissionDetailsList.getDate().get(i) : "");
				excelExpireData.put("invoiceId", (singleDriverCommissionDetailsList.getInvoiceId().get(i) != null) ? singleDriverCommissionDetailsList.getInvoiceId().get(i) : "");
				excelExpireData.put("towType", (singleDriverCommissionDetailsList.getTowType().get(i) != null) ? singleDriverCommissionDetailsList.getTowType().get(i) : "");
				excelExpireData.put("totalCharge", (singleDriverCommissionDetailsList.getTotalCharge().get(i) != null) ? singleDriverCommissionDetailsList.getTotalCharge().get(i) : "");
				excelExpireData.put("totalStorageCharge", (singleDriverCommissionDetailsList.getTotalStorageCharge().get(i) != null) ? singleDriverCommissionDetailsList.getTotalStorageCharge().get(i) : "");
				excelExpireData.put("commissionAmount", (singleDriverCommissionDetailsList.getCommissionAmount().get(i) != null) ? singleDriverCommissionDetailsList.getCommissionAmount().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getSingleDriverCommissionDetailsExcelHeaders()
		{
			logger.info("In getSingleDriverCommissionDetailsExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("singleDriverCommissionDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("singleDriverCommissionDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("singleDriverCommissionDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("singleDriverCommissionDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("singleDriverCommissionDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("singleDriverCommissionDetails.list.excel.header6"));				
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
