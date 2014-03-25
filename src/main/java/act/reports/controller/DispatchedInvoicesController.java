package act.reports.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.DispatchedInvoiceDetailsList;
import act.reports.model.DispatchedInvoices;
import act.reports.model.SearchCriteria;
import act.reports.service.DispatchedInvoicesService;

@Controller
public class DispatchedInvoicesController {

	private Logger logger=Logger.getLogger(DispatchedInvoicesController.class);
	
	@Autowired
	DispatchedInvoicesService dispatchedInvoicesService;
	
	@Autowired
	private DispatchedInvoicesControllerHelper dispatchedInvoicesControllerHelper;

	
	@RequestMapping(value="/dispatchClear.html")
	public String getHome(){
		logger.info("In DispatchedInvoicesController-getHome()...");
		return "dispatchClear";
	}
	
	@RequestMapping(value="/dispatchedInvoices", method=RequestMethod.POST)
	public ModelAndView getDispatchedInvoices(SearchCriteria criteria, ModelAndView model) {
		logger.info("In DispatchedInvoicesController-getDispatchedInvoices()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			DispatchedInvoices dispatchedInvoices = dispatchedInvoicesService.getDispatchedInvoices(criteria);			
			Date fromDate = sdf.parse(dispatchedInvoices.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(dispatchedInvoices.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			logger.info("dispatchedInvoices.getDispatchedInvoiceDetails().size() : "+dispatchedInvoices.getDispatchedInvoiceDetails().size());
			model.addObject("dispatchedInvoices", dispatchedInvoices.getDispatchedInvoiceDetails());
			model.addObject("totalInvoices", dispatchedInvoices.getTotalInvoices());
			model.setViewName("dispatchClear");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportDispatchedInvoices", method=RequestMethod.POST)
	public ModelAndView exportDispatchedInvoicesList(DispatchedInvoiceDetailsList dispatchedInvoiceDetailsList, ModelAndView model )
	{
		logger.info("In DispatchedInvoicesController-exportDispatchedInvoicesList(...) ...");
		try{
			logger.info("dispatchedInvoiceDetailsList.getCallTakerId().size() = "+dispatchedInvoiceDetailsList.getCallTakerId().size());			
			model.addObject("excelDetails",dispatchedInvoicesControllerHelper.convertDispatchedInvoicesListToExcelFormat(dispatchedInvoiceDetailsList));
			model.addObject("excelHeaders",dispatchedInvoicesControllerHelper.getDispatchedInvoiceListExcelHeaders());			
			model.addObject("fileName","Dispatch_Clear.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}