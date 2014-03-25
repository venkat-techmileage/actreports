package act.reports.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.NoAbandonedVehicle;
import act.reports.model.NoAbandonedVehicleDetailsList;
import act.reports.service.NoAbandonedVehicleService;

@Controller
//@RequestMapping(value="/storage management")
public class NoAbandonedVehicleController {

private Logger logger=Logger.getLogger(NoAbandonedVehicleController.class);
	
	@Autowired
	NoAbandonedVehicleService noAbandonedVehicleService;
	
	@Autowired
	private NoAbandonedVehicleControllerHelper noAbandonedVehicleControllerHelper;
	
	@RequestMapping(value="/noAbandonedVehicle.html")
	public String getHome(){
		logger.info("In NoAbandonedVehicleController-getHome()...");
		return "noAbandonedVehicle";
	}
	@RequestMapping(value="/noAbandonedVehicleDetails", method=RequestMethod.POST)
	public ModelAndView getNoAbandonedVehicle( ModelAndView model) {
		logger.info("In NoAbandonedVehicleController-getNoAbandonedVehicle()...");
		try{
			NoAbandonedVehicle noAbandonedVehicle = noAbandonedVehicleService.getNoAbandonedVehicle();
			logger.info("noAbandonedVehicle.getNoAbandonedVehicleDetails().size() : "+noAbandonedVehicle.getNoAbandonedVehicleDetails().size());
			model.addObject("noAbandonedVehicleDetaillsList", noAbandonedVehicle.getNoAbandonedVehicleDetails());
			model.addObject("totalVehicles", noAbandonedVehicle.getTotalVehicles());
			model.setViewName("noAbandonedVehicle");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportNoAbandonedVehicle", method=RequestMethod.POST)
	public ModelAndView exportNoAbandonedVehileDetailsList(NoAbandonedVehicleDetailsList noabandonedDetailsList, ModelAndView model )
	{
		logger.info("In NoAbandonedVehicleController-exportNoAbandonedVehicleDetails(...) ...");
		try{
			logger.info("noabandonedDetailsList.getInvoice().size() : "+noabandonedDetailsList.getInvoice().size());
			model.addObject("excelDetails",noAbandonedVehicleControllerHelper.convertNoAbandonedVehicleDetailsListAsExcelFormat(noabandonedDetailsList));
			model.addObject("excelHeaders",noAbandonedVehicleControllerHelper.getNoAbandonedVehicleDetailsListExcelHeaders());
			model.addObject("fileName","NoAbandoned.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
}
