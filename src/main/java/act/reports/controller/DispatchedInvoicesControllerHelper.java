package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.DispatchedInvoiceDetailsList;

@Service("dispatchedInvoicesControllerHelper")
public class DispatchedInvoicesControllerHelper {
	
	private Logger logger=Logger.getLogger(DispatchedInvoicesControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertDispatchedInvoicesListToExcelFormat(DispatchedInvoiceDetailsList dispatchedInvoiceDetailsList)
	{
			logger.info("In convertDispatchedInvoicesListToExcelFormat(DispatchedInvoiceDetailsList dispatchedInvoiceDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<dispatchedInvoiceDetailsList.getCallTakerId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("callTakerId", (dispatchedInvoiceDetailsList.getCallTakerId().get(i) != null) ? dispatchedInvoiceDetailsList.getCallTakerId().get(i) : "");
				excelExpireData.put("dispatchId", (dispatchedInvoiceDetailsList.getDispatchId().get(i) != null) ? dispatchedInvoiceDetailsList.getDispatchId().get(i) : "");
				excelExpireData.put("driverId", (dispatchedInvoiceDetailsList.getDriverId().get(i) != null) ? dispatchedInvoiceDetailsList.getDriverId().get(i) : "");
				excelExpireData.put("noOwnerInfo", (dispatchedInvoiceDetailsList.getNoOwnerInfo().get(i) != null) ? dispatchedInvoiceDetailsList.getNoOwnerInfo().get(i) : "");
				excelExpireData.put("poRequired", (dispatchedInvoiceDetailsList.getPoRequired().get(i) != null) ? dispatchedInvoiceDetailsList.getPoRequired().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getDispatchedInvoiceListExcelHeaders()
		{
			logger.info("In getDispatchedInvoiceListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("dispatchedInvoices.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("dispatchedInvoices.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("dispatchedInvoices.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("dispatchedInvoices.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("dispatchedInvoices.list.excel.header5"));				
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
