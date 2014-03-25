package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.CustomReleaseDAO;
import act.reports.model.AccountNames;
import act.reports.model.CustomRelease;
import act.reports.model.SearchCriteria;

@Service("customReleaseService")
public class CustomReleaseService {

private Logger logger=Logger.getLogger(CustomReleaseService.class);
	
	@Autowired
	CustomReleaseDAO customReleaseDAO;
	
	public CustomRelease getCustomRelease(SearchCriteria criteria){
		logger.info("In CustomReleaseService-getCustomRelease()...");
		CustomRelease customRelease = null;
		try{			
			customRelease = customReleaseDAO.getCustomRelease(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return customRelease;

	}
	public List<AccountNames> getAccountNames()
	{
		logger.info("In CustomReleaseService-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = customReleaseDAO.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}