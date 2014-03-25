package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.AccountsReceivableDetailByAccTypeDAO;
import act.reports.model.AccountTypes;
import act.reports.model.AccountsReceivableDetailByAccType;
import act.reports.model.SearchCriteria;

@Service("accountsReceivableDetailByAccTypeService")
public class AccountsReceivableDetailByAccTypeService {

private Logger logger=Logger.getLogger(AccountsReceivableDetailByAccTypeService.class);
	
	@Autowired
	AccountsReceivableDetailByAccTypeDAO accountsReceivableDetailByAccTypeDAO;
	
	public List<AccountTypes> getAccountTypesList(){
		logger.info("In AccountsReceivableDetailByAccTypeService-getAccountTypesList()...");
		List<AccountTypes> accountTypesList = null;
		try{			
			accountTypesList = accountsReceivableDetailByAccTypeDAO.getAccountTypesList();
		}catch(Exception e){
			logger.error(e);
		}
		return accountTypesList;
	}
	
	public AccountsReceivableDetailByAccType getAccountsReceivableDetailByAccountType(SearchCriteria criteria){
		logger.info("In AccountsReceivableDetailByAccTypeService-getAccountsReceivableDetailByAccountType()...");
		AccountsReceivableDetailByAccType accountsReceivableDetailByAccType = null;
		try{			
			accountsReceivableDetailByAccType = accountsReceivableDetailByAccTypeDAO.getAccountReceivableDetailByAccountType(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return accountsReceivableDetailByAccType;
	}
}
