package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.CustomAllTowsDetailsList;

@Service("customAllTowsControllerHelper")
public class CustomAllTowsControllerHelper {
	

	private Logger logger=Logger.getLogger(CustomAllTowsControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertCustomAllTowsDetailsListAsExcelFormat(CustomAllTowsDetailsList customDetailsList)
	{
			logger.info("In convertCustomAllTowsDetailsListAsExcelFormat(...) ...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{				
				for(int i=0;i<customDetailsList.getId().size();i++)
				{
					Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
					excelExpireData.put("id", (customDetailsList.getId().get(i) != null) ? customDetailsList.getId().get(i) : "");
					excelExpireData.put("contract", (customDetailsList.getContract().get(i)!= null) ? customDetailsList.getContract().get(i) : "");
					excelExpireData.put("storageReport", (customDetailsList.getStorageReport().get(i)!= null) ? customDetailsList.getStorageReport().get(i) : "");
					excelExpireData.put("pdRequestDate", (customDetailsList.getPdRequestDate().get(i)!= null) ? customDetailsList.getPdRequestDate().get(i) : "");
					excelExpireData.put("pdRequestTime", (customDetailsList.getPdRequestTime().get(i)!= null) ? customDetailsList.getPdRequestTime().get(i) : "");
					excelExpireData.put("towArrivalDate", (customDetailsList.getTowArrivalDate().get(i)!= null) ? customDetailsList.getTowArrivalDate().get(i) : "");
					excelExpireData.put("towArrivalTime", (customDetailsList.getTowArrivalTime().get(i)!= null) ? customDetailsList.getTowArrivalTime().get(i) : "");
					excelExpireData.put("year", (customDetailsList.getYear().get(i) != null) ? customDetailsList.getYear().get(i) : "");
					excelExpireData.put("make", (customDetailsList.getMake().get(i)!= null) ? customDetailsList.getMake().get(i) : "");
					excelExpireData.put("model", (customDetailsList.getModel().get(i)!= null) ? customDetailsList.getModel().get(i) : "");
					excelExpireData.put("vin", (customDetailsList.getVin().get(i)!= null) ? customDetailsList.getVin().get(i) : "");
					excelExpireData.put("precinct", (customDetailsList.getPrecinct().get(i)!= null) ? customDetailsList.getPrecinct().get(i) : "");
					excelExpireData.put("towedTo", (customDetailsList.getTowedTo().get(i)!= null) ? customDetailsList.getTowedTo().get(i) : "");
					excelExpireData.put("towedType", (customDetailsList.getTowedType().get(i)!= null) ? customDetailsList.getTowedType().get(i) : "");
		
					excelDetails.add(excelExpireData);
				}
			}
			catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}


		public List<String> getCustomAllTowsDetailsListExcelHeaders()
		{
			logger.info("In getCustomAllTowsDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header7"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header8"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header9"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header10"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header11"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header12"));				
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header13"));
				excelHeaders.add(excelProps.getProperty("CustomDetails.list.excel.header14"));
				
		
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
