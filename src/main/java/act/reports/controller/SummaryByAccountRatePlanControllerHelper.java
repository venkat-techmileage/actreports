package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.AccountRatePlanDetailsList;

@Service("summaryByAccountRatePlanControllerHelper")
public class SummaryByAccountRatePlanControllerHelper {
	
	private Logger logger=Logger.getLogger(SummaryByAccountRatePlanControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertAccountRatePlanListAsExcelFormat(AccountRatePlanDetailsList accountRatePlanDetailsList)
	{
			logger.info("In convertAccountRatePlanListAsExcelFormat(AccountRatePlanDetailsList accountRatePlanDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<accountRatePlanDetailsList.getAccountRatePlan().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("accountRatePlan", accountRatePlanDetailsList.getAccountRatePlan().get(i));
				excelExpireData.put("noOfTows", accountRatePlanDetailsList.getNoOfTows().get(i));
				excelExpireData.put("totalCharges", accountRatePlanDetailsList.getTotalCharges().get(i));
				excelExpireData.put("use", accountRatePlanDetailsList.getUse().get(i));
				excelExpireData.put("chargesPerTow", accountRatePlanDetailsList.getChargesPerTow().get(i));
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getAccountRatePlanDetailsListExcelHeaders()
		{
			logger.info("In getAccountRatePlanDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("accountRatePlanDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("accountRatePlanDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("accountRatePlanDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("accountRatePlanDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("accountRatePlanDetails.list.excel.header5"));				
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
