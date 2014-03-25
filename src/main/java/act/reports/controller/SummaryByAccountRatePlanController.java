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

import act.reports.model.AccountNames;
import act.reports.model.AccountRatePlanDetailsList;
import act.reports.model.SummaryByAccountRatePlan;
import act.reports.model.SearchCriteria;
import act.reports.service.SummaryByAccountRatePlanService;

@Controller
//@RequestMapping(value="/summaries")
public class SummaryByAccountRatePlanController {

private Logger logger=Logger.getLogger(SummaryByAccountRatePlanController.class);
	
	@Autowired
	SummaryByAccountRatePlanService summaryByAccountRatePlanService;
	
	@Autowired
	private SummaryByAccountRatePlanControllerHelper summaryByAccountRatePlanControllerHelper;
	
	@RequestMapping(value="/summaryByAccountRatePlan.html")
	public String getHome(){
		logger.info("In SummaryByAccountRatePlanController-getHome()...");
		return "summaryByAccountRatePlan";
	}
	
	@RequestMapping(value="/summaryByAccountRatePlan/accountNames", method=RequestMethod.POST)
	@ResponseBody
	public List<AccountNames> getAccountNamesList() {
		logger.info("In SummaryByAccountRatePlanController-getAccountNamesList()...");
		List<AccountNames> accountNamesList = null;
		try{
			accountNamesList = summaryByAccountRatePlanService.getAccountNamesList();
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return accountNamesList;
	}
	
	@RequestMapping(value="/summaryByAccountDetails", method=RequestMethod.POST)
	public ModelAndView getSummaryByAccount(SearchCriteria criteria, ModelAndView model) {
		logger.info("In SummaryByAccountController-getSummaryByAccount()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			SummaryByAccountRatePlan summaryByAccountRatePlan = summaryByAccountRatePlanService.getSummaryByAccountRatePlan(criteria);
			logger.info("summaryByAccountRatePlan.getAccountRatePlanDetails().size() : "+summaryByAccountRatePlan.getAccountRatePlanDetails().size());
			model.addObject("summaryByAccountList", summaryByAccountRatePlan.getAccountRatePlanDetails());
			Date fromDate = sdf.parse(summaryByAccountRatePlan.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(summaryByAccountRatePlan.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("accountName", criteria.getAccountName());
			model.addObject("totalTows", summaryByAccountRatePlan.getTotalTows());
			model.addObject("sumOfTotalCharges", summaryByAccountRatePlan.getSumOfTotalCharges());
			model.addObject("avgChargePerTow", summaryByAccountRatePlan.getAvgChargePerTow());		
			model.setViewName("summaryByAccountRatePlan");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportSummaryByAccountRatePlan", method=RequestMethod.POST)
	public ModelAndView exportAccountRatePlanDetails(AccountRatePlanDetailsList accountRatePlanDetailsList, ModelAndView model )
	{
		logger.info("In SummaryByAccountRatePlanController-exportAccountRatePlanDetails(...) ...");
		try{
			logger.info("accountRatePlanDetailsList.getAccountRatePlan().size() : "+accountRatePlanDetailsList.getAccountRatePlan().size());
			model.addObject("excelDetails",summaryByAccountRatePlanControllerHelper.convertAccountRatePlanListAsExcelFormat(accountRatePlanDetailsList));
			model.addObject("excelHeaders",summaryByAccountRatePlanControllerHelper.getAccountRatePlanDetailsListExcelHeaders());
			model.addObject("fileName","SummaryByAccountRatePlan.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
}
