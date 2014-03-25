package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.AccountsReceivableDetailList;

@Service("accountsReceivableDetailControllerHelper")
public class AccountsReceivableDetailControllerHelper {
	
	private Logger logger=Logger.getLogger(AccountsReceivableDetailControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertAccountReceivableDetailToExcelFormat(AccountsReceivableDetailList accountReceivableDetailList)
	{
			logger.info("In convertAccountReceivableDetailToExcelFormat(AccountReceivableDetailList accountReceivableDetailList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<accountReceivableDetailList.getInvoiceId().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("invoiceId", (accountReceivableDetailList.getInvoiceId().get(i) != null) ? accountReceivableDetailList.getInvoiceId().get(i) : "");
				excelExpireData.put("serviceCallDate", (accountReceivableDetailList.getServiceCallDate().get(i) != null) ? accountReceivableDetailList.getServiceCallDate().get(i) : "");
				excelExpireData.put("dueDate", (accountReceivableDetailList.getDueDate().get(i) != null) ? accountReceivableDetailList.getDueDate().get(i) : "");
				excelExpireData.put("age", (accountReceivableDetailList.getAge().get(i) != null) ? accountReceivableDetailList.getAge().get(i) : "");
				excelExpireData.put("balance", (accountReceivableDetailList.getBalance().get(i) != null) ? accountReceivableDetailList.getBalance().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getAccountsReceivableDetailExcelHeaders()
		{
			logger.info("In getAccountsReceivableDetailExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("accountReceivableDetail.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("accountReceivableDetail.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("accountReceivableDetail.list.excel.header3"));
				excelHeaders.add(excelProps.getProperty("accountReceivableDetail.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("accountReceivableDetail.list.excel.header5"));			
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
