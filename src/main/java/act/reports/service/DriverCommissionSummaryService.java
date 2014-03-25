package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.DriverCommissionSummaryDAO;
import act.reports.model.DriverCommissionSummary;
import act.reports.model.SearchCriteria;

@Service("driverCommissionSummaryService")
public class DriverCommissionSummaryService {

private Logger logger=Logger.getLogger(DriverCommissionSummaryService.class);
	
	@Autowired
	DriverCommissionSummaryDAO driverCommissionSummaryDAO;
	
	public DriverCommissionSummary getDriverCommissionDetails(SearchCriteria criteria){
		logger.info("In DriverCommissionSummaryService-getDriverCommissionDetails()...");
		DriverCommissionSummary driverCommissionSummary = null;
		try{			
			driverCommissionSummary = driverCommissionSummaryDAO.getDriverCommissionDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return driverCommissionSummary;
	}
}
