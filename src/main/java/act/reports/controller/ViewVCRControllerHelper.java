package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.ViewVCRDetailsList;

@Service("viewVCRControllerHelper")
public class ViewVCRControllerHelper {
	
	private Logger logger=Logger.getLogger(ViewVCRControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertVCRDetailsToExcelFormat(ViewVCRDetailsList viewVCRDetailsList)
	{
			logger.info("In convertVCRDetailsToExcelFormat(ViewVCRDetailsList viewVCRDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<viewVCRDetailsList.getTruckId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("truckId", (viewVCRDetailsList.getTruckId().get(i) != null) ? viewVCRDetailsList.getTruckId().get(i) : "");
				excelExpireData.put("date", (viewVCRDetailsList.getDate().get(i) != null) ? viewVCRDetailsList.getDate().get(i) : "");
				excelExpireData.put("preOrPost", (viewVCRDetailsList.getPreOrPost().get(i) != null) ? viewVCRDetailsList.getPreOrPost().get(i) : "");
				excelExpireData.put("defects", (viewVCRDetailsList.getDefects().get(i) != null) ? viewVCRDetailsList.getDefects().get(i) : "");
				excelExpireData.put("driverId", (viewVCRDetailsList.getDriverId().get(i) != null) ? viewVCRDetailsList.getDriverId().get(i) : "");
				excelExpireData.put("mileage", (viewVCRDetailsList.getMileage().get(i) != null) ? viewVCRDetailsList.getMileage().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getVCRDetailsExcelHeaders()
		{
			logger.info("In getVCRDetailsExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("viewVCRDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("viewVCRDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("viewVCRDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("viewVCRDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("viewVCRDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("viewVCRDetails.list.excel.header6"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
