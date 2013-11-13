package act.reports.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.AllInvoicesList;
import act.reports.model.ExportInvoicesList;
import act.reports.model.InvoiceSearchCriteria;
import act.reports.service.AllInvoicesService;

@Controller
public class AllInvoicesController {

	private Logger logger=Logger.getLogger(AllInvoicesController.class);
	
	@Autowired
	AllInvoicesService allInvoicesService;
	
	@Autowired
	private AllInvoicesControllerHelper invoicesHelper;

	
	@RequestMapping("/allInvoices.html")
	public String getHome(){
		logger.info("In AllInvoicesController-getHome()...");
		return "allInvoices";
	}
	
	@RequestMapping(value="/allInvoices/details", method=RequestMethod.POST)
	public @ResponseBody AllInvoicesList getAllInvoicesList(@RequestBody InvoiceSearchCriteria criteria) {
		logger.info("In AllInvoicesController-getAllInvoicesList()...");
		return allInvoicesService.getAllInvoices(criteria);
	}
	
	@RequestMapping(value="/allInvoices/exportInvoicesList")
	public ModelAndView exportInvoicesList(ExportInvoicesList invoicesList, ModelAndView model )
	{
		logger.info("In AllInvoicesController-exportInvoicesList(...) ...");
		try{
			logger.info("invoicesList.getInvoice().size() = "+invoicesList.getInvoice().size());
			model.addObject("excelDetails",invoicesHelper.convertInvoicesListAsExcelFormat(invoicesList));
			model.addObject("excelHeaders",invoicesHelper.getinvoicesListExcelHeaders());			
			model.addObject("fileName","Invoices_List.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}
