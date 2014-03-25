package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.CustomReleaseDetailsList;

@Service("customReleaseControllerHelper")
public class CustomReleaseControllerHelper {
	

	private Logger logger=Logger.getLogger(CustomReleaseControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertCustomReleaseDetailsListAsExcelFormat(CustomReleaseDetailsList customDetailsList)
	{
			logger.info("In convertCustomReleaseDetailsListAsExcelFormat(...) ...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=1;i<customDetailsList.getId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("id", (customDetailsList.getId().get(i) != null) ? customDetailsList.getId().get(i) : "");
				excelExpireData.put("contract", (customDetailsList.getContract().get(i)!= null) ? customDetailsList.getContract().get(i) : "");
				excelExpireData.put("storageReport", (customDetailsList.getStorageReport().get(i)!= null) ? customDetailsList.getStorageReport().get(i) : "");
				excelExpireData.put("year", (customDetailsList.getYear().get(i)!= null) ? customDetailsList.getYear().get(i) : "");
				excelExpireData.put("make", (customDetailsList.getMake().get(i)!= null) ? customDetailsList.getMake().get(i) : "");
				excelExpireData.put("model", (customDetailsList.getModel().get(i)!= null) ? customDetailsList.getModel().get(i) : "");
				excelExpireData.put("vin", (customDetailsList.getVin().get(i)!= null) ? customDetailsList.getVin().get(i) : "");
				excelExpireData.put("precinct", (customDetailsList.getPrecinct().get(i) != null) ? customDetailsList.getPrecinct().get(i) : "");
				excelExpireData.put("impoundDate", (customDetailsList.getImpoundDate().get(i)!= null) ? customDetailsList.getImpoundDate().get(i) : "");
				excelExpireData.put("releasedDate", (customDetailsList.getReleasedDate().get(i)!= null) ? customDetailsList.getReleasedDate().get(i) : "");
				excelExpireData.put("releasedTo", (customDetailsList.getReleasedTo().get(i)!= null) ? customDetailsList.getReleasedTo().get(i) : "");
				excelExpireData.put("towCharge", (customDetailsList.getTowCharge().get(i)!= null) ? customDetailsList.getTowCharge().get(i) : "");
				excelExpireData.put("ofStorageDays", (customDetailsList.getOfStorageDays().get(i)!= null) ? customDetailsList.getOfStorageDays().get(i) : "");
				excelExpireData.put("storageCharge", (customDetailsList.getStorageCharge().get(i)!= null) ? customDetailsList.getStorageCharge().get(i) : "");
				excelExpireData.put("afterHoursGateFee", (customDetailsList.getAfterHoursGateFee().get(i)!= null) ? customDetailsList.getAfterHoursGateFee().get(i) : "");
				excelExpireData.put("standByFee", (customDetailsList.getStandByFee().get(i)!= null) ? customDetailsList.getStandByFee().get(i) : "");
				excelExpireData.put("supervisorFee", (customDetailsList.getSupervisorFee().get(i)!= null) ? customDetailsList.getSupervisorFee().get(i) : "");
				excelExpireData.put("refrigFee", (customDetailsList.getRefrigFee().get(i)!= null) ? customDetailsList.getRefrigFee().get(i) : "");
				excelExpireData.put("outSideCity", (customDetailsList.getOutSideCity().get(i)!= null) ? customDetailsList.getOutSideCity().get(i) : "");
				excelExpireData.put("dryRunFee", (customDetailsList.getDryRunFee().get(i)!= null) ? customDetailsList.getDryRunFee().get(i) : "");
				excelExpireData.put("pccCharge", (customDetailsList.getPccCharge().get(i)!= null) ? customDetailsList.getPccCharge().get(i) : "");
				excelExpireData.put("level", (customDetailsList.getLevel().get(i)!= null) ? customDetailsList.getLevel().get(i) : "");

				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
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
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header7"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header8"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header9"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header10"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header11"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header12"));				
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header13"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header14"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header15"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header16"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header17"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header18"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header19"));				
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header20"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header21"));
				excelHeaders.add(excelProps.getProperty("CustomDetails1.list.excel.header22"));
		
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
