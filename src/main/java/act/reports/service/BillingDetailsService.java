package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.BillingDetailsDAO;
import act.reports.model.AccountNames;
import act.reports.model.BillingDetails;
import act.reports.model.SearchCriteria;

@Service("billingDetailsService")
public class BillingDetailsService {

private Logger logger=Logger.getLogger(BillingDetailsService.class);
	
	@Autowired
	BillingDetailsDAO billingDetailsDAO;
	
	public List<BillingDetails> getBillingDetails(SearchCriteria criteria){
		logger.info("In BillingDetailsService-getBillingDetails()...");
		List<BillingDetails> billingDetailsList = null;
		try{			
			billingDetailsList = billingDetailsDAO.getBillingDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return billingDetailsList;
	}
	public List<AccountNames> getAccountNames()
	{
		logger.info("In BillingDetailsService-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = billingDetailsDAO.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}
