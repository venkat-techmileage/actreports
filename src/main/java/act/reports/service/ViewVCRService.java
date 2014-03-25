package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.ViewVCRDAO;
import act.reports.model.DriverIDs;
import act.reports.model.DriverNames;
import act.reports.model.SearchCriteria;
import act.reports.model.TruckIDs;
import act.reports.model.ViewVCRData;

@Service("viewVCRService")
public class ViewVCRService {

private Logger logger=Logger.getLogger(ViewVCRService.class);
	
	@Autowired
	ViewVCRDAO viewVCRDAO;
	
	public ViewVCRData viewVCRDetails(SearchCriteria criteria){
		logger.info("In ViewVCRService-viewVCRDetails()...");
		ViewVCRData viewVCRData = null;
		try{			
			viewVCRData = viewVCRDAO.viewVCRDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return viewVCRData;
	}
	
	public List<DriverIDs> getDriverIDs()
	{
		logger.info("In ViewVCRService-getDriverIDs()...");
		List<DriverIDs> driverIDsList=null;
		try{
			driverIDsList = viewVCRDAO.getDriverIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverIDsList;
	}
	
	public List<DriverNames> getDriverNames()
	{
		logger.info("In ViewVCRService-getDriverNames()...");
		List<DriverNames> driverNamesList=null;
		try{
			driverNamesList = viewVCRDAO.getDriverNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverNamesList;
	}
	
	public List<TruckIDs> getTruckIDs()
	{
		logger.info("In ViewVCRService-getTruckIDs()...");
		List<TruckIDs> truckIDsList=null;
		try{
			truckIDsList = viewVCRDAO.getTruckIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return truckIDsList;
	}	
}
