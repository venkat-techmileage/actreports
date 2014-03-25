package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.ResponseTimeDAO;
import act.reports.model.AccountNames;
import act.reports.model.ResponseTimeDetails;
import act.reports.model.SearchCriteria;
import act.reports.model.UserIds;
import act.reports.model.UserNames;

@Service("responseTimeService")
public class ResponseTimeService {

private Logger logger=Logger.getLogger(ViewVCRService.class);
	
	@Autowired
	ResponseTimeDAO responseTimeDAO;
	
	public List<ResponseTimeDetails> getResponseTimeDetails(SearchCriteria criteria){
		logger.info("In ResponseTimeService-getResponseTimeDetails()...");
		List<ResponseTimeDetails> responseTimeDetailsList = null;
		try{			
			responseTimeDetailsList = responseTimeDAO.getResponseTimeDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return responseTimeDetailsList;
	}
	
	public List<UserNames> getUserNames()
	{
		logger.info("In ResponseTimeService-getUserNames()...");
		List<UserNames> userNamesList=null;
		try{
			userNamesList = responseTimeDAO.getUserNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return userNamesList;
	}
	
	public List<UserIds> getUserIds()
	{
		logger.info("In ResponseTimeService-getUserIds()...");
		List<UserIds> userIdsList=null;
		try{
			userIdsList = responseTimeDAO.getUserIds();
		}
		catch(Exception e){
			logger.error(e);
		}
		return userIdsList;
	}
	
	public List<AccountNames> getAccountNames()
	{
		logger.info("In ResponseTimeService-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = responseTimeDAO.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}
