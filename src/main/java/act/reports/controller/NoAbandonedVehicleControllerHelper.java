package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.NoAbandonedVehicleDetailsList;

@Service("noAbandonedVehicleContollerHelper")
public class NoAbandonedVehicleControllerHelper {
	

	private Logger logger=Logger.getLogger(NoAbandonedVehicleControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertNoAbandonedVehicleDetailsListAsExcelFormat(NoAbandonedVehicleDetailsList noabandonedDetailsList)
	{
			logger.info("In convertNoAbandonedVehicleDetailsListAsExcelFormat(...) ...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<noabandonedDetailsList.getInvoice().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("invoice", (noabandonedDetailsList.getInvoice().get(i) != null) ? noabandonedDetailsList.getInvoice().get(i) : "");
				excelExpireData.put("serviceCalldate", (noabandonedDetailsList.getServiceCallDate().get(i)!= null) ? noabandonedDetailsList.getServiceCallDate().get(i) : "");
				excelExpireData.put("account", (noabandonedDetailsList.getAccount().get(i)!= null) ? noabandonedDetailsList.getAccount().get(i) : "");
				excelExpireData.put("reason", (noabandonedDetailsList.getReason().get(i)!= null) ? noabandonedDetailsList.getReason().get(i) : "");
				excelExpireData.put("year", (noabandonedDetailsList.getYear().get(i)!= null) ? noabandonedDetailsList.getYear().get(i) : "");
				excelExpireData.put("make", (noabandonedDetailsList.getMake().get(i)!= null) ? noabandonedDetailsList.getMake().get(i) : "");
				excelExpireData.put("model", (noabandonedDetailsList.getModel().get(i)!= null) ? noabandonedDetailsList.getModel().get(i) : "");
				excelExpireData.put("vin", (noabandonedDetailsList.getVin().get(i)!= null) ? noabandonedDetailsList.getVin().get(i) : "");
				excelExpireData.put("licensePlate", (noabandonedDetailsList.getLicensePlate().get(i)!= null) ? noabandonedDetailsList.getLicensePlate().get(i) : "");
				excelExpireData.put("daysInStorage", (noabandonedDetailsList.getDaysInStorage().get(i)!= null) ? noabandonedDetailsList.getDaysInStorage().get(i) : "");
				excelExpireData.put("lotlocation", (noabandonedDetailsList.getLotLocation().get(i)!= null) ? noabandonedDetailsList.getLotLocation().get(i) : "");

				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}


		public List<String> getNoAbandonedVehicleDetailsListExcelHeaders()
		{
			logger.info("In getNoAbandonedVehicleDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header7"));
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header8"));
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header9"));				
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header10"));
				excelHeaders.add(excelProps.getProperty("noAVRDetails.list.excel.header11"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
