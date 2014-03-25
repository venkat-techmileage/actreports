package act.reports.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.MissingPODetailsList;
import act.reports.model.MissingPOs;
import act.reports.service.MissingPOsService;

@Controller
public class MissingPOsController {

	private Logger logger=Logger.getLogger(MissingPOsController.class);
	
	@Autowired
	MissingPOsService missingPOsService;
	
	@Autowired
	private MissingPOsControllerHelper missingPOsControllerHelper;

	
	@RequestMapping(value="/missingPOs.html")
	public String getHome(){
		logger.info("In MissingPOsController-getHome()...");
		return "missingPOs";
	}
	
	@RequestMapping(value="/showMissingPOs", method=RequestMethod.POST)
	public ModelAndView getMissingPOs(ModelAndView model) {
		logger.info("In MissingPOsController-getMissingPOs()...");
		try{
			MissingPOs missingPOs = missingPOsService.getMissingPOs();			
			logger.info("missingPOs.getMissingPODetailsList().size() : "+missingPOs.getMissingPODetailsList().size());
			model.addObject("missingPOs", missingPOs.getMissingPODetailsList());
			model.addObject("totalInvoices", missingPOs.getTotalInvoices());
			model.setViewName("missingPOs");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/saveMissingPOs", method=RequestMethod.POST)
	public ModelAndView saveMissingPOsList(MissingPODetailsList missingPODetailsList, ModelAndView model )
	{
		logger.info("In MissingPOsController-saveMissingPOsList(...) ...");
		try{
			logger.info("missingPODetailsList.getInvoiceId().size() : "+missingPODetailsList.getInvoiceId().size());
			MissingPOs missingPOs = missingPOsService.saveMissingPOs(missingPODetailsList);
			model.addObject("missingPOs", missingPOs.getMissingPODetailsList());
			model.addObject("totalInvoices", missingPOs.getTotalInvoices());
			model.setViewName("missingPOs");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
	
	@RequestMapping(value="/exportMissingPOs", method=RequestMethod.POST)
	public ModelAndView exportMissingPOsList(MissingPODetailsList missingPODetailsList, ModelAndView model )
	{
		logger.info("In MissingPOsController-exportMissingPOsList(...) ...");
		try{
			logger.info("missingPODetailsList.getInvoiceId().size() = "+missingPODetailsList.getInvoiceId().size());			
			model.addObject("excelDetails",missingPOsControllerHelper.convertMissingPOsToExcelFormat(missingPODetailsList));
			model.addObject("excelHeaders",missingPOsControllerHelper.getMissingPOsListExcelHeaders());			
			model.addObject("fileName","MissingPOs.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}