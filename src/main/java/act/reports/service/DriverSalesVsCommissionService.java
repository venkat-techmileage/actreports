package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.DriverSalesVsCommissionDAO;
import act.reports.model.DriverIDs;
import act.reports.model.DriverNames;
import act.reports.model.DriverSalesVsCommission;
import act.reports.model.SearchCriteria;

@Service("driverSalesVsCommissionService")
public class DriverSalesVsCommissionService {

private Logger logger=Logger.getLogger(DriverSalesVsCommissionService.class);
	
	@Autowired
	DriverSalesVsCommissionDAO driverSalesVsCommissionDAO;
	
	public DriverSalesVsCommission getDriverSalesCommissionDetails(SearchCriteria criteria){
		logger.info("In DriverSalesVsCommissionService-getDriverSalesCommissionDetails()...");
		DriverSalesVsCommission driverSalesVsCommission = null;
		try{			
			driverSalesVsCommission = driverSalesVsCommissionDAO.getDriverSalesCommissionDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return driverSalesVsCommission;
	}
	public List<DriverIDs> getDriverIDs()
	{
		logger.info("In DriverSalesVsCommissionService-getDriverIDs()...");
		List<DriverIDs> driverIDsList=null;
		try{
			driverIDsList = driverSalesVsCommissionDAO.getDriverIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverIDsList;
	}
	public List<DriverNames> getDriverNames()
	{
		logger.info("In DriverSalesVsCommissionService-getDriverIDs()...");
		 List<DriverNames> driverNamesList=null;
		try{
			driverNamesList = driverSalesVsCommissionDAO.getDriverNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverNamesList;
	}
	
}
