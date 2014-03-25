package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.AuditDAO;
import act.reports.model.Audit;
import act.reports.model.SearchCriteria;

@Service("auditService")
public class AuditService {

private Logger logger=Logger.getLogger(AuditService.class);
	
	@Autowired
	AuditDAO auditDAO;
	
	public Audit getAuditDetails(SearchCriteria criteria){
		logger.info("In ReceiptsService-getReceiptsDetails()...");
		Audit auditLog = null;
		try{			
			auditLog = auditDAO.getAuditDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return auditLog;
	}
}
