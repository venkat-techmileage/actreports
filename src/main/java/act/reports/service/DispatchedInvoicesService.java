package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.DispatchedInvoicesDAO;
import act.reports.model.DispatchedInvoices;
import act.reports.model.SearchCriteria;

@Service("dispatchedInvoicesService")
public class DispatchedInvoicesService {

private Logger logger=Logger.getLogger(DispatchedInvoicesService.class);
	
	@Autowired
	DispatchedInvoicesDAO dispatchedInvoicesDAO;
	
	public DispatchedInvoices getDispatchedInvoices(SearchCriteria criteria){
		logger.info("In DispatchedInvoicesService-getDispatchedInvoices()...");
		DispatchedInvoices dispatchedInvoices = null;
		try{			
			dispatchedInvoices = dispatchedInvoicesDAO.getDispatchedInvoices(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return dispatchedInvoices;
	}
}
