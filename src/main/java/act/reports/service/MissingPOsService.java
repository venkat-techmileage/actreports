package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.MissingPOsDAO;
import act.reports.model.MissingPODetailsList;
import act.reports.model.MissingPOs;

@Service("missingPOsService")
public class MissingPOsService {

private Logger logger=Logger.getLogger(MissingPOsService.class);
	
	@Autowired
	MissingPOsDAO missingPOsDAO;
	
	public MissingPOs getMissingPOs(){
		logger.info("In MissingPOsService-getMissingPOs()...");
		MissingPOs missingPOs = null;
		try{			
			missingPOs = missingPOsDAO.getMissingPOs();
		}catch(Exception e){
			logger.error(e);
		}
		return missingPOs;
	}
	
	public MissingPOs saveMissingPOs(MissingPODetailsList missingPODetailsList){
		logger.info("In MissingPOsService-saveMissingPOs(MissingPODetailsList missingPODetailsList)...");
		MissingPOs missingPOs = null;
		try{			
			missingPOs = missingPOsDAO.saveMissingPOs(missingPODetailsList);
		}catch(Exception e){
			logger.error(e);
		}
		return missingPOs;
	}
}
