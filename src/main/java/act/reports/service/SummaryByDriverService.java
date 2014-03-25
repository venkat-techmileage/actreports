package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.SummaryByDriverDAO;
import act.reports.model.SummaryByDriver;
import act.reports.model.SearchCriteria;

@Service("summaryByDriverService")
public class SummaryByDriverService {

private Logger logger=Logger.getLogger(SummaryByDriverService.class);
	
	@Autowired
	SummaryByDriverDAO summaryByDriverDAO;
	
	public SummaryByDriver getSummaryByDriver(SearchCriteria criteria){
		logger.info("In SummaryByDriverService-getSummaryByDriver()...");
		SummaryByDriver summaryByDriver = null;
		try{			
			summaryByDriver = summaryByDriverDAO.getSummaryByDriver(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return summaryByDriver;
	}
}
