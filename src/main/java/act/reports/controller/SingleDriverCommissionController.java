package act.reports.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.SearchCriteria;
import act.reports.model.SingleDriverCommission;
import act.reports.model.SingleDriverCommissionDetailsList;
import act.reports.model.UserIds;
import act.reports.model.UserNames;
import act.reports.service.SingleDriverCommissionService;

@Controller
public class SingleDriverCommissionController {

	private Logger logger=Logger.getLogger(SingleDriverCommissionController.class);
	
	@Autowired
	SingleDriverCommissionService singleDriverCommissionService;
	
	@Autowired
	private SingleDriverCommissionControllerHelper singleDriverCommissionControllerHelper;

	
	@RequestMapping(value="/singleDriverCommission.html")
	public String getHome(){
		logger.info("In SingleDriverCommissionController-getHome()...");
		return "singleDriverCommission";
	}
	
	@RequestMapping(value="/showSingleDriverCommissionDetails", method=RequestMethod.POST)
	public ModelAndView getSingleDriverCommissionDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In SingleDriverCommissionController-getSingleDriverCommissionDetails()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			SingleDriverCommission singleDriverCommission = singleDriverCommissionService.getSingleDriverCommissionDetails(criteria);			
			Date fromDate = sdf.parse(singleDriverCommission.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(singleDriverCommission.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			logger.info("singleDriverCommission.getSingleDriverCommissionDetails().size() : "+singleDriverCommission.getSingleDriverCommissionDetails().size());
			model.addObject("userId", criteria.getUserId());
			model.addObject("userName", criteria.getUserName());
			model.addObject("driverId", singleDriverCommission.getDriverId());
			model.addObject("driverName", singleDriverCommission.getDriverName());
			model.addObject("totalInvoices", singleDriverCommission.getTotalInvoices());
			model.addObject("totalCharges", singleDriverCommission.getTotalCharges());
			model.addObject("totalCommission", singleDriverCommission.getTotalCommission());
			model.addObject("singleDriverCommissionDetails", singleDriverCommission.getSingleDriverCommissionDetails());
			model.addObject("towTypeSummaryDetails", singleDriverCommission.getTowTypeSummaryDetails());
			model.setViewName("singleDriverCommission");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportSingleDriverCommissionDetails", method=RequestMethod.POST)
	public ModelAndView exportSingleDriverCommissionDetails(SingleDriverCommissionDetailsList singleDriverCommissionDetailsList, ModelAndView model )
	{
		logger.info("In SingleDriverCommissionController-exportSingleDriverCommissionDetails(...) ...");
		try{
			logger.info("singleDriverCommissionDetailsList.getInvoiceId().size() = "+singleDriverCommissionDetailsList.getInvoiceId().size());			
			model.addObject("excelDetails",singleDriverCommissionControllerHelper.convertSingleDriverCommissionToExcelFormat(singleDriverCommissionDetailsList));
			model.addObject("excelHeaders",singleDriverCommissionControllerHelper.getSingleDriverCommissionDetailsExcelHeaders());			
			model.addObject("fileName","SingleDriverCommission.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
	@RequestMapping(value="/singleDriverCommission/getUserIds",method=RequestMethod.POST)
	public @ResponseBody List<UserIds> getUserIds()
	{
		logger.info("In SingleDriverCommissionController-getUserIds()...");
		 List<UserIds> userIdsList=null;
		try{
			userIdsList = singleDriverCommissionService.getUserIds();
		}
		catch(Exception e){
			logger.error(e);
		}
		return userIdsList;
	}

	@RequestMapping(value="/singleDriverCommission/getUserNames",method=RequestMethod.POST)
	public @ResponseBody List<UserNames> getUserNames()
	{
		logger.info("In SingleDriverCommissionController-getUserNames()...");
		 List<UserNames> userNamesList=null;
		try{
			userNamesList = singleDriverCommissionService.getUserNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return userNamesList;
	}
}