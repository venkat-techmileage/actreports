package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.AccountsReceivableDetailDAO;
import act.reports.model.AccountNames;
import act.reports.model.AccountsReceivableDetail;
import act.reports.model.SearchCriteria;

@Service("accountsReceivableDetailService")
public class AccountsReceivableDetailService {

private Logger logger=Logger.getLogger(AccountsReceivableDetailService.class);
	
	@Autowired
	AccountsReceivableDetailDAO accountsReceivableDetailDAO;
	
	public AccountsReceivableDetail getAccountsReceivableDetail(SearchCriteria criteria){
		logger.info("In AccountsReceivableDetailService-getAccountsReceivableDetail()...");
		AccountsReceivableDetail accountsReceivableDetail = null;
		try{			
			accountsReceivableDetail = accountsReceivableDetailDAO.getAccountReceivableDetail(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return accountsReceivableDetail;
	}
	public List<AccountNames> getAccountNames()
	{
		logger.info("In AccountsReceivableDetailService-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = accountsReceivableDetailDAO.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}
