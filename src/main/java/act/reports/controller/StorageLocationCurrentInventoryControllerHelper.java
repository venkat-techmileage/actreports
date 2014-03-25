package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.StorageLocationCurrentInventoryDetailsList;

@Service("storageLocationCurrentInventoryContollerHelper")
public class StorageLocationCurrentInventoryControllerHelper {
	

	private Logger logger=Logger.getLogger(StorageLocationCurrentInventoryControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertStorageLocationCurrentInventoryDetailsListAsExcelFormat(StorageLocationCurrentInventoryDetailsList storageLocationCurrentInventoryDetailsList)
	{
			logger.info("In convertStorageLocationCurrentInventoryDetailsListAsExcelFormat(...) ...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<storageLocationCurrentInventoryDetailsList.getServiceCallDate().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("invoiceId", (storageLocationCurrentInventoryDetailsList.getInvoiceId().get(i) != null) ? storageLocationCurrentInventoryDetailsList.getInvoiceId().get(i) : "");
				excelExpireData.put("serviceCalldate", (storageLocationCurrentInventoryDetailsList.getServiceCallDate().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getServiceCallDate().get(i) : "");
				excelExpireData.put("account", (storageLocationCurrentInventoryDetailsList.getAccount().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getAccount().get(i) : "");
				excelExpireData.put("reason", (storageLocationCurrentInventoryDetailsList.getReason().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getReason().get(i) : "");
				excelExpireData.put("year", (storageLocationCurrentInventoryDetailsList.getYear().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getYear().get(i) : "");
				excelExpireData.put("make", (storageLocationCurrentInventoryDetailsList.getMake().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getMake().get(i) : "");
				excelExpireData.put("model", (storageLocationCurrentInventoryDetailsList.getModel().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getModel().get(i) : "");
				excelExpireData.put("vin", (storageLocationCurrentInventoryDetailsList.getVin().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getVin().get(i) : "");
				excelExpireData.put("licensePlateCountry", (storageLocationCurrentInventoryDetailsList.getLicensePlateCountry().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getLicensePlateCountry().get(i) : "");
				excelExpireData.put("licensePlateState", (storageLocationCurrentInventoryDetailsList.getLicensePlateState().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getLicensePlateState().get(i) : "");
				excelExpireData.put("licensePlate", (storageLocationCurrentInventoryDetailsList.getLicensePlate().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getLicensePlate().get(i) : "");
				excelExpireData.put("daysInStorage", (storageLocationCurrentInventoryDetailsList.getDaysInStorage().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getDaysInStorage().get(i) : "");
				excelExpireData.put("markedForSalvage", (storageLocationCurrentInventoryDetailsList.getMarkedForSalvage().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getMarkedForSalvage().get(i) : "");
				excelExpireData.put("lotLocation", (storageLocationCurrentInventoryDetailsList.getLotLocation().get(i)!= null) ? storageLocationCurrentInventoryDetailsList.getLotLocation().get(i) : "");

				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}


		public List<String> getStorageLocationCurrentInventoryDetailsListExcelHeaders()
		{
			logger.info("In getStorageLocationCurrentInventoryDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header7"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header8"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header9"));				
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header10"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header11"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header12"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header13"));
				excelHeaders.add(excelProps.getProperty("storageDetails.list.excel.header14"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
