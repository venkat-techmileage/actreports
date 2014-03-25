package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.TruckDetailsList;

@Service("summaryByTruckHelper")
public class SummaryByTruckControllerHelper {
	
	private Logger logger=Logger.getLogger(SummaryByTruckControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertTruckDetailsListAsExcelFormat(TruckDetailsList truckDetailsList)
	{
			logger.info("In convertTruckDetailsListAsExcelFormat(TruckDetailsList truckDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<truckDetailsList.getTruck().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("truck", truckDetailsList.getTruck().get(i));
				excelExpireData.put("noOfTows", truckDetailsList.getNoOfTows().get(i));
				excelExpireData.put("totalCharges", truckDetailsList.getTotalCharges().get(i));
				excelExpireData.put("use", truckDetailsList.getUse().get(i));
				excelExpireData.put("chargesPerTow", truckDetailsList.getChargesPerTow().get(i));
				excelExpireData.put("actualMileage", truckDetailsList.getActualMileage().get(i));
				excelExpireData.put("milesPerTow", truckDetailsList.getMilesPerTow().get(i));
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getTruckDetailsListExcelHeaders()
		{
			logger.info("In getTruckDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("truckDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("truckDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("truckDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("truckDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("truckDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("truckDetails.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("truckDetails.list.excel.header7"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
