package act.reports.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.AccountReceivableSummary;
import act.reports.model.AccountReceivableSummaryDetailsList;
import act.reports.model.SearchCriteria;
import act.reports.service.AccountReceivableSummaryService;

@Controller
public class AccountReceivableSummaryController {

	private Logger logger=Logger.getLogger(AccountReceivableSummaryController.class);
	
	@Autowired
	AccountReceivableSummaryService accountReceivableSummaryService;
	
	@Autowired
	private AccountReceivableSummaryControllerHelper accountReceivableSummaryControllerHelper;

	
	@RequestMapping(value="/accountReceivableSummary.html")
	public String getHome(){
		logger.info("In AccountReceivableSummaryController-getHome()...");
		return "accountReceivableSummary";
	}
	
	@RequestMapping(value="/showAccountReceivableSummary", method=RequestMethod.POST)
	public ModelAndView getAccountReceivableSummary(SearchCriteria criteria, ModelAndView model) {
		logger.info("In AccountReceivableSummaryController-getAccountReceivableSummary()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			AccountReceivableSummary accountReceivableSummary = accountReceivableSummaryService.getAccountReceivableSummary(criteria);			
			Date asOfDate = sdf.parse(accountReceivableSummary.getAsOfDate());
			model.addObject("asOfDate", sdf1.format(asOfDate));
			logger.info("accountReceivableSummary.getAccountReceivableSummaryDetails().size() : "+accountReceivableSummary.getAccountReceivableSummaryDetails().size());
			model.addObject("accountReceivableSummary", accountReceivableSummary.getAccountReceivableSummaryDetails());
			model.addObject("currentTotal", accountReceivableSummary.getCurrentTotal());
			model.addObject("thirtyDaysTotal", accountReceivableSummary.getThirtyDaysTotal());
			model.addObject("sixtyDaysTotal", accountReceivableSummary.getSixtyDaysTotal());
			model.addObject("nintyDaysTotal", accountReceivableSummary.getNintyDaysTotal());
			model.addObject("moreThanNintyDaysTotal", accountReceivableSummary.getMoreThanNintyDaysTotal());
			model.addObject("grandTotal", accountReceivableSummary.getGrandTotal());
			model.setViewName("accountReceivableSummary");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportAccountReceivableSummary", method=RequestMethod.POST)
	public ModelAndView exportAccountReceivableSummary(AccountReceivableSummaryDetailsList accountReceivableSummaryDetailsList, ModelAndView model )
	{
		logger.info("In AccountReceivableSummaryController-exportAccountReceivableSummary(...) ...");
		try{
			logger.info("accountReceivableSummaryDetailsList.getAccountName().size() = "+accountReceivableSummaryDetailsList.getAccountName().size());			
			model.addObject("excelDetails",accountReceivableSummaryControllerHelper.convertAccountReceivableSummaryToExcelFormat(accountReceivableSummaryDetailsList));
			model.addObject("excelHeaders",accountReceivableSummaryControllerHelper.getAccountReceivableSummaryExcelHeaders());			
			model.addObject("fileName","AccountReceivableSummary.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}