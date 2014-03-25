package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.ReceiptsDAO;
import act.reports.model.Receipts;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;

@Service("receiptsService")
public class ReceiptsService {

private Logger logger=Logger.getLogger(ReceiptsService.class);
	
	@Autowired
	ReceiptsDAO receiptsDAO;
	
	public List<SelectOption> getLotLocations()
	{
		logger.info("In ReceiptsService-getLotLocations()...");
		List<SelectOption> lotLocations = null;
		try{
			lotLocations = receiptsDAO.getLotLocations();
		}
		catch(Exception e){
			logger.error(e);
		}
		return lotLocations;
	}
	
	public Receipts getReceiptsDetails(SearchCriteria criteria){
		logger.info("In ReceiptsService-getReceiptsDetails()...");
		Receipts receipts = null;
		try{			
			receipts = receiptsDAO.getReceiptsDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return receipts;
	}
}
