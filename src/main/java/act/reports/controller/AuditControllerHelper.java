package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.AuditDetailsList;

@Service("auditControllerHelper")
public class AuditControllerHelper {
	
	private Logger logger=Logger.getLogger(AuditControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertAuditDetailsListToExcelFormat(AuditDetailsList auditDetailsList)
	{
			logger.info("In convertAuditDetailsListToExcelFormat(AuditDetailsList auditDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<auditDetailsList.getUserId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("userId", (auditDetailsList.getUserId().get(i) != null) ? auditDetailsList.getUserId().get(i) : "");
				excelExpireData.put("changeDate", (auditDetailsList.getChangeDate().get(i) != null) ? auditDetailsList.getChangeDate().get(i) : "");
				excelExpireData.put("changeTime", (auditDetailsList.getChangeTime().get(i) != null) ? auditDetailsList.getChangeTime().get(i) : "");
				excelExpireData.put("invoice", (auditDetailsList.getInvoice().get(i) != null) ? auditDetailsList.getInvoice().get(i) : "");
				excelExpireData.put("originalTotalCharges", (auditDetailsList.getOriginalTotalCharges().get(i) != null) ? auditDetailsList.getOriginalTotalCharges().get(i) : "");
				excelExpireData.put("newTotalCharges", (auditDetailsList.getNewTotalCharges().get(i) != null) ? auditDetailsList.getNewTotalCharges().get(i) : "");
				excelExpireData.put("chargesDifference", (auditDetailsList.getChargesDifference().get(i) != null) ? auditDetailsList.getChargesDifference().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getAuditDetailsListExcelHeaders()
		{
			logger.info("In getAuditDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("auditDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("auditDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("auditDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("auditDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("auditDetails.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("auditDetails.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("auditDetails.list.excel.header7"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}


}
