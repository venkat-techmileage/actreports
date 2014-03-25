package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.DriverCommissionSummaryDetailsList;

@Service("driverCommissionSummaryControllerHelper")
public class DriverCommissionSummaryControllerHelper {
	
	private Logger logger=Logger.getLogger(DriverCommissionSummaryControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertDriverCommissionSummaryToExcelFormat(DriverCommissionSummaryDetailsList driverCommissionSummaryDetailsList)
	{
			logger.info("In convertDriverCommissionSummaryToExcelFormat(DriverCommissionSummaryDetailsList driverCommissionSummaryDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<driverCommissionSummaryDetailsList.getUserId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("payrollId", (driverCommissionSummaryDetailsList.getPayrollId().get(i) != null) ? driverCommissionSummaryDetailsList.getPayrollId().get(i) : "");
				excelExpireData.put("userId", (driverCommissionSummaryDetailsList.getUserId().get(i) != null) ? driverCommissionSummaryDetailsList.getUserId().get(i) : "");
				excelExpireData.put("fullName", (driverCommissionSummaryDetailsList.getFullName().get(i) != null) ? driverCommissionSummaryDetailsList.getFullName().get(i) : "");
				excelExpireData.put("startDate", (driverCommissionSummaryDetailsList.getStartDate().get(i) != null) ? driverCommissionSummaryDetailsList.getStartDate().get(i) : "");
				excelExpireData.put("level", (driverCommissionSummaryDetailsList.getLevel().get(i) != null) ? driverCommissionSummaryDetailsList.getLevel().get(i) : "");
				excelExpireData.put("noOfTows", (driverCommissionSummaryDetailsList.getNoOfTows().get(i) != null) ? driverCommissionSummaryDetailsList.getNoOfTows().get(i) : "");
				excelExpireData.put("weeklyBase", (driverCommissionSummaryDetailsList.getWeeklyBase().get(i) != null) ? driverCommissionSummaryDetailsList.getWeeklyBase().get(i) : "");
				excelExpireData.put("commission", (driverCommissionSummaryDetailsList.getCommission().get(i) != null) ? driverCommissionSummaryDetailsList.getCommission().get(i) : "");
				excelExpireData.put("commissionAdj", (driverCommissionSummaryDetailsList.getCommissionAdj().get(i) != null) ? driverCommissionSummaryDetailsList.getCommissionAdj().get(i) : "");
				excelExpireData.put("totalPay", (driverCommissionSummaryDetailsList.getTotalPay().get(i) != null) ? driverCommissionSummaryDetailsList.getTotalPay().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getDriverCommissionSummaryDetailsExcelHeaders()
		{
			logger.info("In getDriverCommissionSummaryDetailsExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header6"));				
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header7"));
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header8"));
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header9"));				
				excelHeaders.add(excelProps.getProperty("driverCommissionSummaryDetails.list.excel.header10"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
