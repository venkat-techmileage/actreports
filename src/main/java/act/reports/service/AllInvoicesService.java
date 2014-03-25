package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.AllInvoicesDAO;
import act.reports.model.AccountNames;
import act.reports.model.AllInvoicesList;
import act.reports.model.DriverIDs;
import act.reports.model.InvoiceSearchCriteria;
import act.reports.model.Reasons;
import act.reports.model.SalesReps;
import act.reports.model.TowTypes;
import act.reports.model.TruckIDs;

@Service("allInvoicesService")
public class AllInvoicesService {

	private Logger logger=Logger.getLogger(AllInvoicesService.class);
	
	@Autowired
	AllInvoicesDAO allInvoicesDAO;
	
	public AllInvoicesList getAllInvoices(InvoiceSearchCriteria criteria){
		logger.info("In AllInvoicesService-getAllInvoices()...");
		AllInvoicesList allInvoicesList = null;
		try{			
			allInvoicesList = allInvoicesDAO.getAllInvoicesList(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return allInvoicesList;
	}
	
	public List<DriverIDs> getDriverIDs()
	{
		logger.info("In AllInvoicesService-getDriverIDs()...");
		List<DriverIDs> driverIDsList=null;
		try{
			driverIDsList = allInvoicesDAO.getDriverIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverIDsList;
	}
	
	public List<TruckIDs> getTruckIDs()
	{
		logger.info("In AllInvoicesService-getTruckIDs()...");
		List<TruckIDs> truckIDsList=null;
		try{
			truckIDsList = allInvoicesDAO.getTruckIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return truckIDsList;
	}
	
	public List<TowTypes> getTowTypes()
	{
		logger.info("In AllInvoicesService-getTowTypes()...");
		List<TowTypes> towTypesList=null;
		try{
			towTypesList = allInvoicesDAO.getTowTypes();
		}
		catch(Exception e){
			logger.error(e);
		}
		return towTypesList;
	}
	
	public List<Reasons> getReasons()
	{
		logger.info("In AllInvoicesService-getReasons()...");
		List<Reasons> reasonsList=null;
		try{
			reasonsList = allInvoicesDAO.getReasons();
		}
		catch(Exception e){
			logger.error(e);
		}
		return reasonsList;
	}
	
	public List<AccountNames> getAccountNames()
	{
		logger.info("In AllInvoicesService-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = allInvoicesDAO.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
	
	public List<SalesReps> getSalesReps()
	{
		logger.info("In AllInvoicesService-getSalesReps()...");
		List<SalesReps> salesRepsList=null;
		try{
			salesRepsList = allInvoicesDAO.getSalesReps();
		}
		catch(Exception e){
			logger.error(e);
		}
		return salesRepsList;
	}
}
