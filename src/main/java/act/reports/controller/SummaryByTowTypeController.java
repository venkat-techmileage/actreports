package act.reports.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.SummaryByTowType;
import act.reports.model.SearchCriteria;
import act.reports.model.TowTypDetailsList;
import act.reports.service.SummaryByTowTypeService;

@Controller
public class SummaryByTowTypeController {

private Logger logger=Logger.getLogger(SummaryByTowTypeController.class);
	
	@Autowired
	SummaryByTowTypeService summaryByTowTypeService;
	
	@Autowired
	private SummaryByTowTypeControllerHelper summaryByTowTypeControllerHelper;
	
	@RequestMapping(value="/summaryByTowType.html")
	public String getHome(){
		logger.info("In SummaryByTowTypeController-getHome()...");
		return "summaryByTowType";
	}
	
	@RequestMapping(value="/summaryByTowTypeDetails", method=RequestMethod.POST)
	public ModelAndView getSummaryByTowType(SearchCriteria criteria, ModelAndView model) {
		logger.info("In SummaryByTowTypeController-getSummaryByTowType()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			SummaryByTowType summaryByTowType = summaryByTowTypeService.getSummaryByTowType(criteria);
			logger.info("summaryByTowType.getTowTypeDetails().size() : "+summaryByTowType.getTowTypeDetails().size());
			model.addObject("summaryByTowTypeList", summaryByTowType.getTowTypeDetails());
			Date fromDate = sdf.parse(summaryByTowType.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(summaryByTowType.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("totalTows", summaryByTowType.getTotalTows());
			model.addObject("sumOfTotalCharges", summaryByTowType.getSumOfTotalCharges());
			model.addObject("avgChargePerTow", summaryByTowType.getAvgChargePerTow());		
			model.setViewName("summaryByTowType");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportSummaryByTowType", method=RequestMethod.POST)
	public ModelAndView exportTowTypeDetails(TowTypDetailsList towTypDetailsList, ModelAndView model )
	{
		logger.info("In SummaryByTowTypeController-exportTowTypeDetails(...) ...");
		try{
			logger.info("reasonDetailsList.getReason().size() : "+towTypDetailsList.getType().size());
			model.addObject("excelDetails",summaryByTowTypeControllerHelper.convertInvoicesListAsExcelFormat(towTypDetailsList));
			model.addObject("excelHeaders",summaryByTowTypeControllerHelper.getTowTypDetailsListExcelHeaders());
			model.addObject("fileName","SummaryByTowType.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
}
