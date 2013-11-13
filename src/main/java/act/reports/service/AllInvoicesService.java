package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.AllInvoicesDAO;
import act.reports.model.AllInvoicesList;
import act.reports.model.InvoiceSearchCriteria;

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
}
