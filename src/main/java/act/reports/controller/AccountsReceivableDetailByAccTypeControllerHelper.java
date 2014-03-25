package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.AccountsReceivableAccTypeDetailList;

@Service("accountsReceivableDetailByAccTypeControllerHelper")
public class AccountsReceivableDetailByAccTypeControllerHelper {
	
	private Logger logger=Logger.getLogger(AccountsReceivableDetailByAccTypeControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertAccountReceivableDetailByAccTypeToExcelFormat(AccountsReceivableAccTypeDetailList accountsReceivableAccTypeDetailList)
	{
			logger.info("In convertAccountReceivableDetailByAccTypeToExcelFormat(AccountsReceivableAccTypeDetailList accountsReceivableAccTypeDetailList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
			for(int i=0;i<accountsReceivableAccTypeDetailList.getAccount().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("account", (accountsReceivableAccTypeDetailList.getAccount().get(i) != null) ? accountsReceivableAccTypeDetailList.getAccount().get(i) : "");
				excelExpireData.put("unPaid", (accountsReceivableAccTypeDetailList.getUnPaid().get(i) != null) ? accountsReceivableAccTypeDetailList.getUnPaid().get(i) : "");
				excelExpireData.put("balance", (accountsReceivableAccTypeDetailList.getBalance().get(i) != null) ? accountsReceivableAccTypeDetailList.getBalance().get(i) : "");
				excelDetails.add(excelExpireData);
			}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			return excelDetails;
		}
		
		public List<String> getAccountsReceivableDetailByAccTypeExcelHeaders()
		{
			logger.info("In getAccountsReceivableDetailByAccTypeExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("accountReceivableDetailByAccType.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("accountReceivableDetailByAccType.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("accountReceivableDetailByAccType.list.excel.header3"));							
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
