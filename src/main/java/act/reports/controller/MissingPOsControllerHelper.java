package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.MissingPODetailsList;

@Service("missingPOsControllerHelper")
public class MissingPOsControllerHelper {
	
	private Logger logger=Logger.getLogger(MissingPOsControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertMissingPOsToExcelFormat(MissingPODetailsList missingPODetailsList)
	{
			logger.info("In convertMissingPOsToExcelFormat(MissingPODetailsList missingPODetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<missingPODetailsList.getInvoiceId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("invoiceId", (missingPODetailsList.getInvoiceId().get(i) != null) ? missingPODetailsList.getInvoiceId().get(i) : "");
				excelExpireData.put("serviceCallDate", (missingPODetailsList.getServiceCallDate().get(i) != null) ? missingPODetailsList.getServiceCallDate().get(i) : "");
				excelExpireData.put("year", (missingPODetailsList.getYear().get(i) != null) ? missingPODetailsList.getYear().get(i) : "");
				excelExpireData.put("make", (missingPODetailsList.getMake().get(i) != null) ? missingPODetailsList.getMake().get(i) : "");
				excelExpireData.put("model", (missingPODetailsList.getModel().get(i) != null) ? missingPODetailsList.getModel().get(i) : "");
				excelExpireData.put("vin", (missingPODetailsList.getVin().get(i) != null) ? missingPODetailsList.getVin().get(i) : "");
				excelExpireData.put("billedTo", (missingPODetailsList.getBilledTo().get(i) != null) ? missingPODetailsList.getBilledTo().get(i) : "");
				excelExpireData.put("amount", (missingPODetailsList.getAmount().get(i) != null) ? missingPODetailsList.getAmount().get(i) : "");
				excelExpireData.put("poNumber", (missingPODetailsList.getPoNumber().get(i) != null) ? missingPODetailsList.getPoNumber().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getMissingPOsListExcelHeaders()
		{
			logger.info("In getMissingPOsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header7"));
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header8"));
				excelHeaders.add(excelProps.getProperty("missingPOs.list.excel.header9"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
