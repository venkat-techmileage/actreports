package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.AbandonedVehicleDAO;
import act.reports.model.AbandonedVehicle;
import act.reports.model.AccountNames;
import act.reports.model.SearchCriteria;

@Service("abandonedVehicleReportService")
public class AbandonedVehicleReportService {

private Logger logger=Logger.getLogger(AbandonedVehicleReportService.class);
	
	@Autowired
	AbandonedVehicleDAO abandonedVehicleDAO;
	
	public AbandonedVehicle getAbandonedVehicle(SearchCriteria criteria){
		logger.info("In NoAbandonedVehicleService-getNoAbandonedVehicle()...");
		AbandonedVehicle abandonedVehicle = null;
		try{			
			abandonedVehicle = abandonedVehicleDAO.getAbandonedVehicle(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return abandonedVehicle;
	}
	public List<AccountNames> getAccountNames()
	{
		logger.info("In AbandonedVehicleReportService-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = abandonedVehicleDAO.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}
