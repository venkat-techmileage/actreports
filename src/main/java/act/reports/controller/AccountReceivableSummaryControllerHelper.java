package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.AccountReceivableSummaryDetailsList;

@Service("accountReceivableSummaryControllerHelper")
public class AccountReceivableSummaryControllerHelper {
	
	private Logger logger=Logger.getLogger(AccountReceivableSummaryControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertAccountReceivableSummaryToExcelFormat(AccountReceivableSummaryDetailsList accountReceivableSummaryDetailsList)
	{
			logger.info("In convertAccountReceivableSummaryToExcelFormat(AccountReceivableSummaryDetailsList accountReceivableSummaryDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<accountReceivableSummaryDetailsList.getAccountName().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("accountName", (accountReceivableSummaryDetailsList.getAccountName().get(i) != null) ? accountReceivableSummaryDetailsList.getAccountName().get(i) : "");
				excelExpireData.put("current", (accountReceivableSummaryDetailsList.getCurrent().get(i) != null) ? accountReceivableSummaryDetailsList.getCurrent().get(i) : "");
				excelExpireData.put("thirtyDaysDue", (accountReceivableSummaryDetailsList.getThirtyDaysDue().get(i) != null) ? accountReceivableSummaryDetailsList.getThirtyDaysDue().get(i) : "");
				excelExpireData.put("sixtyDaysDue", (accountReceivableSummaryDetailsList.getSixtyDaysDue().get(i) != null) ? accountReceivableSummaryDetailsList.getSixtyDaysDue().get(i) : "");
				excelExpireData.put("nintyDaysDue", (accountReceivableSummaryDetailsList.getNintyDaysDue().get(i) != null) ? accountReceivableSummaryDetailsList.getNintyDaysDue().get(i) : "");
				excelExpireData.put("moreThanNintyDaysDue", (accountReceivableSummaryDetailsList.getMoreThanNintyDaysDue().get(i) != null) ? accountReceivableSummaryDetailsList.getMoreThanNintyDaysDue().get(i) : "");
				excelExpireData.put("total", (accountReceivableSummaryDetailsList.getTotal().get(i) != null) ? accountReceivableSummaryDetailsList.getTotal().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getAccountReceivableSummaryExcelHeaders()
		{
			logger.info("In getAccountReceivableSummaryExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("accountReceivableSummary.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("accountReceivableSummary.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("accountReceivableSummary.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("accountReceivableSummary.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("accountReceivableSummary.list.excel.header5"));				
				excelHeaders.add(excelProps.getProperty("accountReceivableSummary.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("accountReceivableSummary.list.excel.header7"));
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
