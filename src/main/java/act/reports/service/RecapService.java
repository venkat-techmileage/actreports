package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.RecapDAO;
import act.reports.model.RecapDetails;
import act.reports.model.SearchCriteria;

@Service("recapService")
public class RecapService {

private Logger logger=Logger.getLogger(RecapService.class);
	
	@Autowired
	RecapDAO recapDAO;
	
	public RecapDetails getRecapDetails(SearchCriteria criteria){
		logger.info("In RecapService-getRecapDetails()...");
		RecapDetails recapDetails = null;
		try{			
			recapDetails = recapDAO.getRecapDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return recapDetails;
	}
}
