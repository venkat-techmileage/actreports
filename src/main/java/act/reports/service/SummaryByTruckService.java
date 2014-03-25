package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.SummaryByTruckDAO;
import act.reports.model.SummaryByTruck;
import act.reports.model.SearchCriteria;

@Service("summaryByTruckService")
public class SummaryByTruckService {

private Logger logger=Logger.getLogger(SummaryByTruckService.class);
	
	@Autowired
	SummaryByTruckDAO summaryByTruckDAO;
	
	public SummaryByTruck getSummaryByTruck(SearchCriteria criteria){
		logger.info("In SummaryByTruckService-getSummaryByTruck()...");
		SummaryByTruck summaryByTruck = null;
		try{			
			summaryByTruck = summaryByTruckDAO.getSummaryByTruck(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return summaryByTruck;
	}
}
