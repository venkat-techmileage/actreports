package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.AccountReceivableSummaryDAO;
import act.reports.model.AccountReceivableSummary;
import act.reports.model.SearchCriteria;

@Service("accountReceivableSummaryService")
public class AccountReceivableSummaryService {

private Logger logger=Logger.getLogger(AccountReceivableSummaryService.class);
	
	@Autowired
	AccountReceivableSummaryDAO accountReceivableSummaryDAO;
	
	public AccountReceivableSummary getAccountReceivableSummary(SearchCriteria criteria){
		logger.info("In AccountReceivableSummaryService-getAccountReceivableSummary()...");
		AccountReceivableSummary accountReceivableSummary = null;
		try{			
			accountReceivableSummary = accountReceivableSummaryDAO.getAccountReceivableSummary(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return accountReceivableSummary;
	}
}
