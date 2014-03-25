package act.reports.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.RecapDetails;
import act.reports.model.SearchCriteria;
import act.reports.service.RecapService;

@Controller
public class RecapController {

	private Logger logger=Logger.getLogger(RecapController.class);
	
	@Autowired
	RecapService recapService;
		
	@Autowired
	private RecapControllerHelper recapControllerHelper;
	
	@RequestMapping(value="/recap.html")
	public String getHome(){
		logger.info("In RecapController-getHome()...");
		return "recap";
	}
	
	@RequestMapping(value="/getRecapDetails", method=RequestMethod.POST)
	public ModelAndView getReceiptsDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In AllInvoicesController-getAllInvoicesList()...");
		try{
			RecapDetails recapDetails = recapService.getRecapDetails(criteria);			
			model.addObject("dateRangeFromDate", criteria.getFromDate());
			model.addObject("dateRangeToDate", criteria.getToDate());
			model.addObject("compareToFromDate", criteria.getCompareToFromDate());
			model.addObject("compareToToDate", criteria.getCompareToToDate());
			model.addObject("recapDetails", recapDetails);
			model.addObject("recapDateRangeDetails", recapDetails.getRecapDateRangeDetails());
			model.addObject("recapCompareToDetails", recapDetails.getRecapCompareToDetails());
			model.setViewName("recap");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
}
