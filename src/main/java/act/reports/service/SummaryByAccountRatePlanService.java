package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.SummaryByAccountRatePlanDAO;
import act.reports.model.AccountNames;
import act.reports.model.SummaryByAccountRatePlan;
import act.reports.model.SearchCriteria;

@Service("summaryByAccountRatePlanService")
public class SummaryByAccountRatePlanService {

private Logger logger=Logger.getLogger(SummaryByAccountRatePlanService.class);
	
	@Autowired
	SummaryByAccountRatePlanDAO summaryByAccountRatePlanDAO;
	
	public List<AccountNames> getAccountNamesList(){
		logger.info("In SummaryByAccountRatePlanService-getAccountNamesList()...");
		List<AccountNames> accountNamesList = null;
		try{			
			accountNamesList = summaryByAccountRatePlanDAO.getAccountNamesList();
		}catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
	
	public SummaryByAccountRatePlan getSummaryByAccountRatePlan(SearchCriteria criteria){
		logger.info("In SummaryByAccountRatePlanService-getSummaryByAccountRatePlan()...");
		SummaryByAccountRatePlan summaryByAccountRatePlan = null;
		try{			
			summaryByAccountRatePlan = summaryByAccountRatePlanDAO.getSummaryByAccountRatePlan(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return summaryByAccountRatePlan;
	}
}
