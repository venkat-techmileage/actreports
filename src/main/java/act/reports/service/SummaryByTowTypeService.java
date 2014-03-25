package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.SummaryByTowTypeDAO;
import act.reports.model.SummaryByTowType;
import act.reports.model.SearchCriteria;

@Service("summaryByTowTypeService")
public class SummaryByTowTypeService {

private Logger logger=Logger.getLogger(SummaryByTowTypeService.class);
	
	@Autowired
	SummaryByTowTypeDAO summaryByTowTypeDAO;
	
	public SummaryByTowType getSummaryByTowType(SearchCriteria criteria){
		logger.info("In SummaryByTowTypeService-getSummaryByTowType()...");
		SummaryByTowType summaryByTowType = null;
		try{			
			summaryByTowType = summaryByTowTypeDAO.getSummaryByTowType(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return summaryByTowType;
	}
}
