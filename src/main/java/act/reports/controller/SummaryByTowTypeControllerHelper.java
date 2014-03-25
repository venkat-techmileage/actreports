package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.TowTypDetailsList;

@Service("summaryByTowTypeHelper")
public class SummaryByTowTypeControllerHelper {
	
	private Logger logger=Logger.getLogger(SummaryByTowTypeControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertInvoicesListAsExcelFormat(TowTypDetailsList towTypDetailsList)
	{
			logger.info("In convertInvoicesListAsExcelFormat(TowTypDetailsList towTypDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<towTypDetailsList.getType().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("type", towTypDetailsList.getType().get(i));
				excelExpireData.put("noOfTows", towTypDetailsList.getNoOfTows().get(i));
				excelExpireData.put("totalCharges", towTypDetailsList.getTotalCharges().get(i));
				excelExpireData.put("use", towTypDetailsList.getUse().get(i));
				excelExpireData.put("chargesPerTow", towTypDetailsList.getChargesPerTow().get(i));
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getTowTypDetailsListExcelHeaders()
		{
			logger.info("In getTowTypDetailsListExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("towTypeDetails.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("towTypeDetails.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("towTypeDetails.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("towTypeDetails.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("towTypeDetails.list.excel.header5"));				
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
