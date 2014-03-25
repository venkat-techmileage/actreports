package act.reports.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.DriverCommissionSummary;
import act.reports.model.DriverCommissionSummaryDetailsList;
import act.reports.model.SearchCriteria;
import act.reports.service.DriverCommissionSummaryService;

@Controller
public class DriverCommissionSummaryController {

	private Logger logger=Logger.getLogger(DriverCommissionSummaryController.class);
	
	@Autowired
	DriverCommissionSummaryService driverCommissionSummaryService;
	
	@Autowired
	private DriverCommissionSummaryControllerHelper driverCommissionSummaryControllerHelper;

	
	@RequestMapping(value="/driverCommissionSummary.html")
	public String getHome(){
		logger.info("In DriverCommissionSummaryController-getHome()...");
		return "driverCommissionSummary";
	}
	
	@RequestMapping(value="/showDriverCommissionSummaryDetails", method=RequestMethod.POST)
	public ModelAndView getDriverCommissionSummaryDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In DriverCommissionSummaryController-getDriverCommissionSummaryDetails()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			DriverCommissionSummary driverCommissionSummary = driverCommissionSummaryService.getDriverCommissionDetails(criteria);			
			Date fromDate = sdf.parse(driverCommissionSummary.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(driverCommissionSummary.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			logger.info("driverCommissionSummary.getDriverCommissionSummaryDetailsList().size() : "+driverCommissionSummary.getDriverCommissionSummaryDetailsList().size());
			model.addObject("countOfTows", driverCommissionSummary.getCountOfTows());
			model.addObject("weeklyBaseTotal", driverCommissionSummary.getWeeklyBaseTotal());
			model.addObject("totalCommission", driverCommissionSummary.getTotalCommission());
			model.addObject("totalCommissionAdj", driverCommissionSummary.getTotalCommissionAdj());
			model.addObject("sumOfTotalPay", driverCommissionSummary.getSumOfTotalPay());
			model.addObject("driverCommissionSummaryDetails", driverCommissionSummary.getDriverCommissionSummaryDetailsList());
			model.setViewName("driverCommissionSummary");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportDriverCommissionSummaryDetails", method=RequestMethod.POST)
	public ModelAndView exportDriverCommissionSummaryDetails(DriverCommissionSummaryDetailsList driverCommissionSummaryDetailsList, ModelAndView model )
	{
		logger.info("In DriverCommissionSummaryController-exportDriverCommissionSummaryDetails(...) ...");
		try{
			logger.info("driverCommissionSummaryDetailsList.getUserId().size() = "+driverCommissionSummaryDetailsList.getUserId().size());			
			model.addObject("excelDetails",driverCommissionSummaryControllerHelper.convertDriverCommissionSummaryToExcelFormat(driverCommissionSummaryDetailsList));
			model.addObject("excelHeaders",driverCommissionSummaryControllerHelper.getDriverCommissionSummaryDetailsExcelHeaders());			
			model.addObject("fileName","DriverCommissionSummary.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}