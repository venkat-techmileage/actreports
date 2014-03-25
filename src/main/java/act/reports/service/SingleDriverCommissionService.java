package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.SingleDriverCommissionDAO;
import act.reports.model.SingleDriverCommission;
import act.reports.model.SearchCriteria;
import act.reports.model.UserIds;
import act.reports.model.UserNames;

@Service("singleDriverCommissionService")
public class SingleDriverCommissionService {

private Logger logger=Logger.getLogger(SingleDriverCommissionService.class);
	
	@Autowired
	SingleDriverCommissionDAO singleDriverCommissionDAO;
	
	public SingleDriverCommission getSingleDriverCommissionDetails(SearchCriteria criteria){
		logger.info("In DriverSingleCommissionService-getSingleDriverCommissionDetails()...");
		SingleDriverCommission singleDriverCommission = null;
		try{			
			singleDriverCommission = singleDriverCommissionDAO.getSingleDriverCommissionDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return singleDriverCommission;
	}

	public List<UserIds> getUserIds()
	{
		logger.info("In SingleDriverCommissionService-getDriverIDs()...");
		List<UserIds> userIdsList=null;
		try{
			userIdsList = singleDriverCommissionDAO.getUserIds();
		}
		catch(Exception e){
			logger.error(e);
		}
		return userIdsList;
	}
	public List<UserNames> getUserNames()
	{
		logger.info("In SingleDriverCommissionService-getUserNames()...");
		List<UserNames> userNamesList=null;
		try{
			userNamesList = singleDriverCommissionDAO.getUserNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return userNamesList;
	}
}