package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.ReasonCodeDetailsList;

@Service("summaryByReasonHelper")
public class SummaryByReasonControllerHelper {
	
	private Logger logger=Logger.getLogger(SummaryByReasonControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertInvoicesListAsExcelFormat(ReasonCodeDetailsList reasonDetailsList)
	{
			logger.info("In convertInvoicesListAsExcelFormat(ReasonDetailsList reasonDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<reasonDetailsList.getReason().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("reason", reasonDetailsList.getReason().get(i));
				excelExpireData.put("noOfTows", reasonDetailsList.getNoOfTows().get(i));
				excelExpireData.put("totalCharges", reasonDetailsList.getTotalCharges().get(i));
				excelExpireData.put("use", reasonDetailsList.getUse().get(i));
				excelExpireData.put("chargesPerTow", reasonDetailsList.getChargesPerTow().get(i));
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getReasonDetailsListExcelHeaders()
		{
			logger.info("In getReasonDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("reasonDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("reasonDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("reasonDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("reasonDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("reasonDetails.list.excel.header5"));				
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
