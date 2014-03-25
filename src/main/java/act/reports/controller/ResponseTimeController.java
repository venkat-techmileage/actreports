package act.reports.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.AccountNames;
import act.reports.model.ResponseTimeDetails;
import act.reports.model.ResponseTimeDetailsList;
import act.reports.model.SearchCriteria;
import act.reports.model.UserIds;
import act.reports.model.UserNames;
import act.reports.service.ResponseTimeService;

@Controller
public class ResponseTimeController {

	private Logger logger=Logger.getLogger(ResponseTimeController.class);
	
	@Autowired
	ResponseTimeService responseTimeService;
	
	@Autowired
	private ResponseTimeControllerHelper responseTimeControllerHelper;

	
	@RequestMapping(value="/responseTime.html")
	public String getHome(){
		logger.info("In ResponseTimeController-getHome()...");
		return "responseTime";
	}
	
	@RequestMapping(value="/getResponseTimeDetails", method=RequestMethod.POST)
	public ModelAndView getResponseTimeDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In ResponseTimeController-getResponseTimeDetails()...");
		try{
			List<ResponseTimeDetails> responseTimeDetailsList = responseTimeService.getResponseTimeDetails(criteria);			
			logger.info("responseTimeDetailsList.size() : "+responseTimeDetailsList.size());
			model.addObject("fromDate", criteria.getFromDate());
			model.addObject("toDate", criteria.getToDate());			
			int index = criteria.getSearchString().indexOf("-");
			model.addObject("lateType", criteria.getSearchString().substring(0, index));
			model.addObject("searchBy", criteria.getSearchString().substring(index+1));			
			model.addObject("userName", criteria.getUserName());
			model.addObject("userId", criteria.getUserId());
			model.addObject("accountName", criteria.getAccountName());
			model.addObject("responseTimeDetailsList", responseTimeDetailsList);
			model.addObject("totalRecords", responseTimeDetailsList.size());
			model.setViewName("responseTime");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportResponseTimeDetails", method=RequestMethod.POST)
	public ModelAndView exportResponseTimeDetails(ResponseTimeDetailsList responseTimeDetailsList, ModelAndView model )
	{
		logger.info("In ResponseTimeController-exportResponseTimeDetails(...) ...");
		try{
			logger.info("responseTimeDetailsList.getInvoiceId().size() = "+responseTimeDetailsList.getInvoiceId().size());			
			model.addObject("excelDetails",responseTimeControllerHelper.convertResponseTimeDetailsToExcelFormat(responseTimeDetailsList));
			model.addObject("excelHeaders",responseTimeControllerHelper.getResponseTimeDetailsExcelHeaders());			
			model.addObject("fileName","ResponseTime.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/responseTime/getUserNames",method=RequestMethod.POST)
	public @ResponseBody List<UserNames> getUserNames()
	{
		logger.info("In ResponseTimeController-getUserNames()...");
		List<UserNames> userNamesList=null;
		try{
			userNamesList = responseTimeService.getUserNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return userNamesList;
	}
	
	@RequestMapping(value="/responseTime/getUserIds",method=RequestMethod.POST)
	public @ResponseBody List<UserIds> getUserIds()
	{
		logger.info("In ResponseTimeController-getUserIds()...");
		List<UserIds> userIdsList=null;
		try{
			userIdsList = responseTimeService.getUserIds();
		}
		catch(Exception e){
			logger.error(e);
		}
		return userIdsList;
	}
	
	@RequestMapping(value="/responseTime/getAccountNames",method=RequestMethod.POST)
	public @ResponseBody List<AccountNames> getAccountNames()
	{
		logger.info("In ResponseTimeController-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = responseTimeService.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}