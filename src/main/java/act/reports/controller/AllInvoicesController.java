package act.reports.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.AccountNames;
import act.reports.model.AllInvoicesList;
import act.reports.model.DriverIDs;
import act.reports.model.ExportInvoicesList;
import act.reports.model.InvoiceSearchCriteria;
import act.reports.model.Reasons;
import act.reports.model.SalesReps;
import act.reports.model.TowTypes;
import act.reports.model.TruckIDs;
import act.reports.service.AllInvoicesService;

@Controller
public class AllInvoicesController {

	private Logger logger=Logger.getLogger(AllInvoicesController.class);
	
	@Autowired
	AllInvoicesService allInvoicesService;
	
	@Autowired
	private AllInvoicesControllerHelper invoicesHelper;

	
	@RequestMapping(value="/allInvoices.html")
	public String getHome(){
		logger.info("In AllInvoicesController-getHome()...");
		return "allInvoices";
	}
	
	@RequestMapping(value="/allInvoices/details", method=RequestMethod.POST)
	public @ResponseBody AllInvoicesList getAllInvoicesList(@RequestBody InvoiceSearchCriteria criteria) {
		logger.info("In AllInvoicesController-getAllInvoicesList()...");
		return allInvoicesService.getAllInvoices(criteria);
	}
	
	@RequestMapping(value="/allInvoices/exportInvoicesList", method=RequestMethod.POST)
	public ModelAndView exportInvoicesList(ExportInvoicesList invoicesList, ModelAndView model )
	{
		logger.info("In AllInvoicesController-exportInvoicesList(...) ...");
		try{
			logger.info("invoicesList.getRecCount() = "+invoicesList.getRecCount());			
			model.addObject("excelDetails",invoicesHelper.convertInvoicesListAsExcelFormat(invoicesList));
			model.addObject("excelHeaders",invoicesHelper.getinvoicesListExcelHeaders(invoicesList));			
			model.addObject("fileName","Invoices_List.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
	
	@RequestMapping(value="/get/driverIDsList",method=RequestMethod.POST)
	public @ResponseBody List<DriverIDs> getDriverIDs()
	{
		logger.info("In AllInvoicesController-getDriverIDs()...");
		List<DriverIDs> driverIDsList=null;
		try{
			driverIDsList = allInvoicesService.getDriverIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverIDsList;
	}
	
	@RequestMapping(value="/get/truckIDsList",method=RequestMethod.POST)
	public @ResponseBody List<TruckIDs> getTruckIDs()
	{
		logger.info("In AllInvoicesController-getDriverIDs()...");
		List<TruckIDs> truckIDsList=null;
		try{
			truckIDsList = allInvoicesService.getTruckIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return truckIDsList;
	}
	
	@RequestMapping(value="/get/towTypesList",method=RequestMethod.POST)
	public @ResponseBody List<TowTypes> getTowTypes()
	{
		logger.info("In AllInvoicesController-getTowTypes()...");
		List<TowTypes> towTypesList=null;
		try{
			towTypesList = allInvoicesService.getTowTypes();
		}
		catch(Exception e){
			logger.error(e);
		}
		return towTypesList;
	}
	
	@RequestMapping(value="/get/reasonsList",method=RequestMethod.POST)
	public @ResponseBody List<Reasons> getReasons()
	{
		logger.info("In AllInvoicesController-getReasons()...");
		List<Reasons> reasonsList=null;
		try{
			reasonsList = allInvoicesService.getReasons();
		}
		catch(Exception e){
			logger.error(e);
		}
		return reasonsList;
	}
	
	@RequestMapping(value="/get/accountNamesList",method=RequestMethod.POST)
	public @ResponseBody List<AccountNames> getAccountNames()
	{
		logger.info("In AllInvoicesController-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = allInvoicesService.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
	
	@RequestMapping(value="/get/salesRepsList",method=RequestMethod.POST)
	public @ResponseBody List<SalesReps> getSalesReps()
	{
		logger.info("In AllInvoicesController-getSalesReps()...");
		List<SalesReps> salesRepsList=null;
		try{
			salesRepsList = allInvoicesService.getSalesReps();
		}
		catch(Exception e){
			logger.error(e);
		}
		return salesRepsList;
	}
}
