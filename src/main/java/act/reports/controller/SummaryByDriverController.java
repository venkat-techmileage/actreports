package act.reports.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.DriverDetailsList;
import act.reports.model.SummaryByDriver;
import act.reports.model.SearchCriteria;
import act.reports.service.SummaryByDriverService;

@Controller
//@RequestMapping(value="/summaries")
public class SummaryByDriverController {

private Logger logger=Logger.getLogger(SummaryByDriverController.class);
	
	@Autowired
	SummaryByDriverService summaryByDriverService;
	
	@Autowired
	private SummaryByDriverControllerHelper summaryByDriverControllerHelper;
	
	@RequestMapping(value="/summaryByDriver.html")
	public String getHome(){
		logger.info("In SummaryByDriverController-getHome()...");
		return "summaryByDriver";
	}
	
	@RequestMapping(value="/summaryByDriverDetails", method=RequestMethod.POST)
	public ModelAndView getSummaryByDriver(SearchCriteria criteria, ModelAndView model) {
		logger.info("In SummaryByDriverController-getSummaryByDriver()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			SummaryByDriver summaryByDriver = summaryByDriverService.getSummaryByDriver(criteria);
			logger.info("summaryByDriver.getDriverDetails().size() : "+summaryByDriver.getDriverDetails().size());
			model.addObject("summaryByDriverList", summaryByDriver.getDriverDetails());
			Date fromDate = sdf.parse(summaryByDriver.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(summaryByDriver.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("totalTows", summaryByDriver.getTotalTows());
			model.addObject("sumOfTotalCharges", summaryByDriver.getSumOfTotalCharges());
			model.addObject("avgChargePerTow", summaryByDriver.getAvgChargePerTow());		
			model.setViewName("summaryByDriver");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportSummaryByDriver", method=RequestMethod.POST)
	public ModelAndView exportDriverDetails(DriverDetailsList driverDetailsList, ModelAndView model )
	{
		logger.info("In SummaryByDriverController-exportDriverDetails(...) ...");
		try{
			logger.info("driverDetailsList.getDriverId().size() : "+driverDetailsList.getDriverId().size());
			model.addObject("excelDetails",summaryByDriverControllerHelper.convertDriverDetailsListAsExcelFormat(driverDetailsList));
			model.addObject("excelHeaders",summaryByDriverControllerHelper.getDriverDetailsListExcelHeaders());
			model.addObject("fileName","SummaryByDriver.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
}
