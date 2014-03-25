package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.CustomAllTowsDAO;
import act.reports.model.AccountNames;
import act.reports.model.CustomAllTows;
import act.reports.model.SearchCriteria;

@Service("customAllTowsService")
public class CustomAllTowsService {

private Logger logger=Logger.getLogger(CustomAllTowsService.class);
	
	@Autowired
	CustomAllTowsDAO customAllTowsDAO;
	
	public CustomAllTows getCustomAllTows(SearchCriteria criteria){
		logger.info("In CustomAllTowsService-getPhoenixAllTows()...");
		CustomAllTows customAllTows = null;
		try{			
			customAllTows = customAllTowsDAO.getCustomAllTows(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return customAllTows;

	}
	public List<AccountNames> getAccountNames()
	{
		logger.info("In CustomAllTowsService-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = customAllTowsDAO.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}