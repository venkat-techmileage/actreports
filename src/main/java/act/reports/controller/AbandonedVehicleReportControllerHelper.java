package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.AbandonedVehicleDetailsList;

@Service("abandonedVehicleReportControllerHelper")
public class AbandonedVehicleReportControllerHelper {
	

	private Logger logger=Logger.getLogger(AbandonedVehicleReportControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertAbandonedVehicleDetailsListAsExcelFormat(AbandonedVehicleDetailsList abandonedDetailsList)
	{
			logger.info("In convertAbandonedVehicleDetailsListAsExcelFormat(...) ...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<abandonedDetailsList.getServiceCallDate().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("invoice", (abandonedDetailsList.getServiceCallDate().get(i) != null) ? abandonedDetailsList.getServiceCallDate().get(i) : "");
				excelExpireData.put("serviceCalldate", (abandonedDetailsList.getOrDr().get(i)!= null) ? abandonedDetailsList.getOrDr().get(i) : "");
				excelExpireData.put("account", (abandonedDetailsList.getVin().get(i)!= null) ? abandonedDetailsList.getVin().get(i) : "");
				excelExpireData.put("reason", (abandonedDetailsList.getLicensePlate().get(i)!= null) ? abandonedDetailsList.getLicensePlate().get(i) : "");
				excelExpireData.put("year", (abandonedDetailsList.getTitleRequested().get(i)!= null) ? abandonedDetailsList.getTitleRequested().get(i) : "");
				excelExpireData.put("make", (abandonedDetailsList.getTitleReceived().get(i)!= null) ? abandonedDetailsList.getTitleReceived().get(i) : "");
				excelExpireData.put("model", (abandonedDetailsList.getReleased().get(i)!= null) ? abandonedDetailsList.getReleased().get(i) : "");
	
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}


		public List<String> getAbandonedVehicleDetailsListExcelHeaders()
		{
			logger.info("In getAbandonedVehicleDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("AVRDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("AVRDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("AVRDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("AVRDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("AVRDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("AVRDetails.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("AVRDetails.list.excel.header7"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
