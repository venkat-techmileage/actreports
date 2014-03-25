package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.MultipleInvoicePaymentDAO;
import act.reports.model.AccountNames;
import act.reports.model.InvoicePaymentDetailsUpdate;
import act.reports.model.MultipleInvoicePayment;
import act.reports.model.MultipleInvoicePaymentDetailsUpdate;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.model.ServiceStatus;

@Service("multipleInvoicePaymentService")
public class MultipleInvoicePaymentService {

private Logger logger=Logger.getLogger(MultipleInvoicePaymentService.class);
	
	@Autowired
	MultipleInvoicePaymentDAO multipleInvoicePaymentDAO;
	
	public List<AccountNames> getAccountNames()
	{
		logger.info("In MultipleInvoicePaymentService-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = multipleInvoicePaymentDAO.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
	
	public List<SelectOption> getLotLocations()
	{
		logger.info("In MultipleInvoicePaymentService-getLotLocations()...");
		List<SelectOption> lotLocations = null;
		try{
			lotLocations = multipleInvoicePaymentDAO.getLotLocations();
		}
		catch(Exception e){
			logger.error(e);
		}
		return lotLocations;
	}
	
	/*public List<SelectOption> getPaymentTypes()
	{
		logger.info("In MultipleInvoicePaymentService-getPaymentTypes()...");
		List<SelectOption> paymentTypes = null;
		try{
			paymentTypes = multipleInvoicePaymentDAO.getPaymentTypes();
		}
		catch(Exception e){
			logger.error(e);
		}
		return paymentTypes;
	}*/
	
	public MultipleInvoicePayment getInvoicesDetails(SearchCriteria criteria){
		logger.info("In MultipleInvoicePaymentService-getInvoicesDetails()...");
		MultipleInvoicePayment multipleInvoicePayment = null;
		try{			
			multipleInvoicePayment = multipleInvoicePaymentDAO.getInvoicesDetails(criteria);
		}
		catch(Exception e){
			logger.error(e);
		}
		return multipleInvoicePayment;
	}
	
	public MultipleInvoicePayment updateInvoicePayment(MultipleInvoicePaymentDetailsUpdate multipleInvoicePaymentDetailsUpdate){
		logger.info("In MultipleInvoicePaymentService-updateInvoicePayment()...");
		MultipleInvoicePayment multipleInvoicePayment = null;
		try{			
			multipleInvoicePayment = multipleInvoicePaymentDAO.updateInvoicePayment(multipleInvoicePaymentDetailsUpdate);
		}
		catch(Exception e){
			logger.error(e);
		}
		return multipleInvoicePayment;
	}
	
	public ServiceStatus updateInvoicePayment(List<InvoicePaymentDetailsUpdate> invoicePaymentUpdateList){
		logger.info("In MultipleInvoicePaymentService-updateInvoicePayment()...");
		ServiceStatus serviceStatus = null;
		try{			
			serviceStatus = multipleInvoicePaymentDAO.updateInvoicePayment(invoicePaymentUpdateList);
		}
		catch(Exception e){
			logger.error(e);
		}
		return serviceStatus;
	}
}