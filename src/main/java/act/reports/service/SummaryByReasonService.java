package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.SummaryByReasonDAO;
import act.reports.model.SummaryByReasonCode;
import act.reports.model.SearchCriteria;

@Service("summaryByReasonService")
public class SummaryByReasonService {

private Logger logger=Logger.getLogger(SummaryByReasonService.class);
	
	@Autowired
	SummaryByReasonDAO summaryByReasonDAO;
	
	public SummaryByReasonCode getSummaryByReason(SearchCriteria criteria){
		logger.info("In SummaryByReasonService-getSummaryByReason()...");
		SummaryByReasonCode summaryByReason = null;
		try{			
			summaryByReason = summaryByReasonDAO.getSummaryByReason(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return summaryByReason;
	}
}
