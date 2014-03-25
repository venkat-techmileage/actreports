package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.DriverSalesVsCommissionDetailsList;

@Service("driverSalesVsCommissionControllerHelper")
public class DriverSalesVsCommissionControllerHelper {
	
	private Logger logger=Logger.getLogger(DriverSalesVsCommissionControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertDriverSalesVsCommissionDetailsListToExcelFormat(DriverSalesVsCommissionDetailsList driverSalesVsCommissionDetailsList)
	{
			logger.info("In convertDriverSalesVsCommissionDetailsListToExcelFormat(DriverSalesVsCommissionDetailsList driverSalesVsCommissionDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
				for(int i=0;i<driverSalesVsCommissionDetailsList.getSaleType().size();i++)
				{
					Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
					excelExpireData.put("saleType", (driverSalesVsCommissionDetailsList.getSaleType().get(i) != null) ? driverSalesVsCommissionDetailsList.getSaleType().get(i) : "");
					excelExpireData.put("salesAmount", (driverSalesVsCommissionDetailsList.getSalesAmount().get(i) != null) ? driverSalesVsCommissionDetailsList.getSalesAmount().get(i) : "");
					if(driverSalesVsCommissionDetailsList.getCommissionType().size()>i){
						excelExpireData.put("commissionType", (driverSalesVsCommissionDetailsList.getCommissionType().get(i) != null) ? driverSalesVsCommissionDetailsList.getCommissionType().get(i) : "");
						excelExpireData.put("commissionAmount", (driverSalesVsCommissionDetailsList.getCommissionAmount().get(i) != null) ? driverSalesVsCommissionDetailsList.getCommissionAmount().get(i) : "");
						excelExpireData.put("hoursOrInvoices", (driverSalesVsCommissionDetailsList.getHoursOrInvoices().get(i) != null) ? driverSalesVsCommissionDetailsList.getHoursOrInvoices().get(i) : "");
						excelExpireData.put("ratePerHourOrInvoice", (driverSalesVsCommissionDetailsList.getRatePerHourOrInvoice().get(i) != null) ? driverSalesVsCommissionDetailsList.getRatePerHourOrInvoice().get(i) : "");
					}
					excelDetails.add(excelExpireData);
				}
				/*for(int i=0;i<driverSalesVsCommissionDetailsList.getSaleType().size();i++)
				{
					Map<String,String> excelSalesData=new LinkedHashMap<String,String>();
					excelSalesData.put("saleType", (driverSalesVsCommissionDetailsList.getSaleType().get(i) != null) ? driverSalesVsCommissionDetailsList.getSaleType().get(i) : "");
					excelSalesData.put("salesAmount", (driverSalesVsCommissionDetailsList.getSalesAmount().get(i) != null) ? driverSalesVsCommissionDetailsList.getSalesAmount().get(i) : "");
					excelDetails.add(excelSalesData);
				}
				for(int j=0;j<driverSalesVsCommissionDetailsList.getCommissionType().size();j++)
				{
					Map<String,String> excelCommissionData=new LinkedHashMap<String,String>();
					excelCommissionData.put("commissionType", (driverSalesVsCommissionDetailsList.getCommissionType().get(j) != null) ? driverSalesVsCommissionDetailsList.getCommissionType().get(j) : "");
					excelCommissionData.put("commissionAmount", (driverSalesVsCommissionDetailsList.getCommissionAmount().get(j) != null) ? driverSalesVsCommissionDetailsList.getCommissionAmount().get(j) : "");
					excelCommissionData.put("hoursOrInvoices", (driverSalesVsCommissionDetailsList.getHoursOrInvoices().get(j) != null) ? driverSalesVsCommissionDetailsList.getHoursOrInvoices().get(j) : "");
					excelCommissionData.put("ratePerHourOrInvoice", (driverSalesVsCommissionDetailsList.getRatePerHourOrInvoice().get(j) != null) ? driverSalesVsCommissionDetailsList.getRatePerHourOrInvoice().get(j) : "");
					excelDetails.add(excelCommissionData);
				}*/
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getDriverSalesVsCommissionDetailsListExcelHeaders()
		{
			logger.info("In getDriverSalesVsCommissionDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("driverSalesVsCommissionDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("driverSalesVsCommissionDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("driverSalesVsCommissionDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("driverSalesVsCommissionDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("driverSalesVsCommissionDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("driverSalesVsCommissionDetails.list.excel.header6"));				
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
