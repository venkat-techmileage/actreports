package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.DriverDetailsList;

@Service("summaryByDriverHelper")
public class SummaryByDriverControllerHelper {
	
	private Logger logger=Logger.getLogger(SummaryByDriverControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertDriverDetailsListAsExcelFormat(DriverDetailsList driverDetailsList)
	{
			logger.info("In convertDriverDetailsListAsExcelFormat(...) ...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<driverDetailsList.getDriverId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("driverId", driverDetailsList.getDriverId().get(i));
				excelExpireData.put("driverName", driverDetailsList.getDriverName().get(i));
				excelExpireData.put("noOfTows", driverDetailsList.getNoOfTows().get(i));
				excelExpireData.put("totalCharges", driverDetailsList.getTotalCharges().get(i));
				excelExpireData.put("use", driverDetailsList.getUse().get(i));
				excelExpireData.put("chargesPerTow", driverDetailsList.getChargesPerTow().get(i));
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getDriverDetailsListExcelHeaders()
		{
			logger.info("In getDriverDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("driverDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("driverDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("driverDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("driverDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("driverDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("driverDetails.list.excel.header6"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}
}
