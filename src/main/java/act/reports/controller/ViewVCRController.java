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

import act.reports.model.DriverIDs;
import act.reports.model.DriverNames;
import act.reports.model.SearchCriteria;
import act.reports.model.TruckIDs;
import act.reports.model.ViewVCRData;
import act.reports.model.ViewVCRDetailsList;
import act.reports.service.ViewVCRService;

@Controller
public class ViewVCRController {

	private Logger logger=Logger.getLogger(ViewVCRController.class);
	
	@Autowired
	ViewVCRService viewVCRService;
	
	@Autowired
	private ViewVCRControllerHelper viewVCRControllerHelper;

	
	@RequestMapping(value="/viewVCR.html")
	public String getHome(){
		logger.info("In ViewVCRController-getHome()...");
		return "viewVCR";
	}
	
	@RequestMapping(value="/viewVCRDetails", method=RequestMethod.POST)
	public @ResponseBody ViewVCRData getViewVCRDetailsList(@RequestBody SearchCriteria criteria) {
		logger.info("In ViewVCRController-viewVCRDetails()...");
		return  viewVCRService.viewVCRDetails(criteria);		
	}
	
	@RequestMapping(value="/exportVCRDetails", method=RequestMethod.POST)
	public ModelAndView exportVCRDetails(ViewVCRDetailsList viewVCRDetailsList, ModelAndView model )
	{
		logger.info("In ViewVCRController-exportVCRDetails(...) ...");
		try{
			logger.info("viewVCRDetailsList.getTruckId().size() = "+viewVCRDetailsList.getTruckId().size());			
			model.addObject("excelDetails",viewVCRControllerHelper.convertVCRDetailsToExcelFormat(viewVCRDetailsList));
			model.addObject("excelHeaders",viewVCRControllerHelper.getVCRDetailsExcelHeaders());			
			model.addObject("fileName","View_VCR.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/viewVCR/getDriverIDsList",method=RequestMethod.POST)
	public @ResponseBody List<DriverIDs> getDriverIDs()
	{
		logger.info("In ViewVCRController-getDriverIDs()...");
		List<DriverIDs> driverIDsList=null;
		try{
			driverIDsList = viewVCRService.getDriverIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverIDsList;
	}
	
	@RequestMapping(value="/viewVCR/getDriverNamesList",method=RequestMethod.POST)
	public @ResponseBody List<DriverNames> getDriverNames()
	{
		logger.info("In ViewVCRController-getDriverNames()...");
		List<DriverNames> driverNamesList=null;
		try{
			driverNamesList = viewVCRService.getDriverNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverNamesList;
	}
	
	@RequestMapping(value="/viewVCR/getTruckIDs",method=RequestMethod.POST)
	public @ResponseBody List<TruckIDs> getTruckIDs()
	{
		logger.info("In ViewVCRController-getTruckIDs()...");
		List<TruckIDs> truckIDsList=null;
		try{
			truckIDsList = viewVCRService.getTruckIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return truckIDsList;
	}
}