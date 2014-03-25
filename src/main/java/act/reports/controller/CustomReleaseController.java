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
import act.reports.model.CustomRelease;
import act.reports.model.CustomReleaseDetailsList;
import act.reports.model.SearchCriteria;
import act.reports.service.CustomReleaseService;
import act.reports.util.DateUtility;

@Controller
//@RequestMapping(value="/commissions")
public class CustomReleaseController {

private Logger logger=Logger.getLogger(CustomReleaseController.class);
	
	@Autowired
	CustomReleaseService customReleaseService;
	
	@Autowired
	private CustomReleaseControllerHelper customReleaseControllerHelper;
	
	@RequestMapping(value="/customRelease.html")
	public String getHome(){
		logger.info("In CustomReleaseController-getHome()...");
		return "customRelease";
	}
	

	@RequestMapping(value="/customReleaseDetails", method=RequestMethod.POST)
	public ModelAndView getCustomRelease(SearchCriteria criteria, ModelAndView model) {
		logger.info("CustomReleaseController-getCustomRelease()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			CustomRelease customRelease = customReleaseService.getCustomRelease(criteria);
			logger.info("customRelease.getCustomReleaseDetails().size() : "+customRelease.getCustomReleaseDetails().size());
			model.addObject("customReleaseDetailsList", customRelease.getCustomReleaseDetails());
			Date fromDate = sdf.parse(DateUtility.convertAsMySqlDateTime(criteria.getFromDate()));
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(DateUtility.convertAsMySqlDateTime(criteria.getToDate()));
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("accountName", criteria.getAccountName());
			model.setViewName("customRelease");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportCustomReleaseDetalis", method=RequestMethod.POST)
	public ModelAndView exportCustomReleaseDetailslist(CustomReleaseDetailsList customReleaseDetailsList, ModelAndView model )
	{
		logger.info("In CustomReleaseController-exportCustomReleaseDetails(...) ...");
		try{
			logger.info("customReleaseDetailsList.getId().size() : "+customReleaseDetailsList.getId().size());
			model.addObject("excelDetails",customReleaseControllerHelper.convertCustomReleaseDetailsListAsExcelFormat(customReleaseDetailsList));
			model.addObject("excelHeaders",customReleaseControllerHelper.getCustomAllTowsDetailsListExcelHeaders());
			model.addObject("fileName","CustomRelease.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value="/customPDRelease/get/accountNames",method=RequestMethod.POST)
	public @ResponseBody List<AccountNames> getAccountNames()
	{
		logger.info("In CustomReleaseController-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = customReleaseService.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}
