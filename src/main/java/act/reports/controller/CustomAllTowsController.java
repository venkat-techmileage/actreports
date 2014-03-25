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
import act.reports.model.CustomAllTows;
import act.reports.model.CustomAllTowsDetailsList;
import act.reports.model.SearchCriteria;
import act.reports.service.CustomAllTowsService;
import act.reports.util.DateUtility;

@Controller
//@RequestMapping(value="/commissions")
public class CustomAllTowsController {

private Logger logger=Logger.getLogger(CustomAllTowsController.class);
	
	@Autowired
	CustomAllTowsService customAllTowsService;
	
	@Autowired
	private CustomAllTowsControllerHelper customAllTowsControllerHelper;
	
	@RequestMapping(value="/customAllTows.html")
	public String getHome(){
		logger.info("In CustomAllTowsController-getHome()...");
		return "customAllTows";
	}
	

	@RequestMapping(value="/customDetails", method=RequestMethod.POST)
	public ModelAndView getCustomAllTows(SearchCriteria criteria, ModelAndView model) {
		logger.info("CustomAllTowsController-getCustomAllTows()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			CustomAllTows customAllTows = customAllTowsService.getCustomAllTows(criteria);
			logger.info("customAllTows.getCustomAllTowsDetails().size() : "+customAllTows.getCustomAllTowsDetails().size());
			model.addObject("customAllTowsDetailsList", customAllTows.getCustomAllTowsDetails());
			Date fromDate = sdf.parse(DateUtility.convertAsMySqlDateTime(criteria.getFromDate()));
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(DateUtility.convertAsMySqlDateTime(criteria.getToDate()));
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("accountName", criteria.getAccountName());
			model.setViewName("customAllTows");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportCustomAllTowsDetalis", method=RequestMethod.POST)
	public ModelAndView exportCustomAllTowsDetailslist(CustomAllTowsDetailsList customAllTowsDetailsList, ModelAndView model )
	{
		logger.info("In CustomAllTowsController-exportCustomAllTowsDetails(...) ...");
		try{
			logger.info("customAllTowsDetailsList.getId().size() : "+customAllTowsDetailsList.getId().size());
			model.addObject("excelDetails",customAllTowsControllerHelper.convertCustomAllTowsDetailsListAsExcelFormat(customAllTowsDetailsList));
			model.addObject("excelHeaders",customAllTowsControllerHelper.getCustomAllTowsDetailsListExcelHeaders());
			model.addObject("fileName","CustomAllTows.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value="/customAllTows/get/accountNames",method=RequestMethod.POST)
	public @ResponseBody List<AccountNames> getAccountNames()
	{
		logger.info("In CustomAllTowsController-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = customAllTowsService.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}
