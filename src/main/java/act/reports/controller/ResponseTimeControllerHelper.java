package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.ResponseTimeDetailsList;

@Service("responseTimeControllerHelper")
public class ResponseTimeControllerHelper {

	private Logger logger=Logger.getLogger(ResponseTimeControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertResponseTimeDetailsToExcelFormat(ResponseTimeDetailsList responseTimeDetailsList)
	{
		logger.info("In convertResponseTimeDetailsToExcelFormat(ResponseTimeDetailsList responseTimeDetailsList)...");
		List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
		try 
		{
			for(int i=0;i<responseTimeDetailsList.getInvoiceId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("invoiceId", (responseTimeDetailsList.getInvoiceId().get(i) != null) ? responseTimeDetailsList.getInvoiceId().get(i) : "");
				excelExpireData.put("account", (responseTimeDetailsList.getAccount().get(i) != null) ? responseTimeDetailsList.getAccount().get(i) : "");
				excelExpireData.put("callerTakerId", (responseTimeDetailsList.getCallerTakerId().get(i) != null) ? responseTimeDetailsList.getCallerTakerId().get(i) : "");
				excelExpireData.put("dispatchId", (responseTimeDetailsList.getDispatchId().get(i) != null) ? responseTimeDetailsList.getDispatchId().get(i) : "");
				excelExpireData.put("driverId", (responseTimeDetailsList.getDriverId().get(i) != null) ? responseTimeDetailsList.getDriverId().get(i) : "");
				excelExpireData.put("callTime", (responseTimeDetailsList.getCallTime().get(i) != null) ? responseTimeDetailsList.getCallTime().get(i) : "");
				excelExpireData.put("dispatchedTime", (responseTimeDetailsList.getDispatchedTime().get(i) != null) ? responseTimeDetailsList.getDispatchedTime().get(i) : "");
				excelExpireData.put("arrivalTime", (responseTimeDetailsList.getArrivalTime().get(i) != null) ? responseTimeDetailsList.getArrivalTime().get(i) : "");
				excelExpireData.put("clearedTime", (responseTimeDetailsList.getClearedTime().get(i) != null) ? responseTimeDetailsList.getClearedTime().get(i) : "");
				excelExpireData.put("callToDispatch", (responseTimeDetailsList.getCallToDispatch().get(i) != null) ? responseTimeDetailsList.getCallToDispatch().get(i) : "");
				excelExpireData.put("callToArrival", (responseTimeDetailsList.getCallToArrival().get(i) != null) ? responseTimeDetailsList.getCallToArrival().get(i) : "");
				excelExpireData.put("dispatchToClear", (responseTimeDetailsList.getDispatchToClear().get(i) != null) ? responseTimeDetailsList.getDispatchToClear().get(i) : "");
				excelExpireData.put("late", (responseTimeDetailsList.getLate().get(i) != null) ? responseTimeDetailsList.getLate().get(i) : "");
				excelDetails.add(excelExpireData);
			}
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return excelDetails;
	}

	public List<String> getResponseTimeDetailsExcelHeaders()
	{
		logger.info("In getResponseTimeDetailsExcelHeaders()...");
		List<String> excelHeaders=new ArrayList<String>();
		try
		{
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header1"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header2"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header3"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header4"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header5"));				
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header6"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header7"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header8"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header9"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header10"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header11"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header12"));
			excelHeaders.add(excelProps.getProperty("responseTimeDetails.list.excel.header13"));				
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return excelHeaders;
	}	
}
