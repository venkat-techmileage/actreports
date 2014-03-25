package act.reports.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.ReasonCodeDetailsList;
import act.reports.model.SummaryByReasonCode;
import act.reports.model.SearchCriteria;
import act.reports.service.SummaryByReasonService;

@Controller
//@RequestMapping(value="/summaries")
public class SummaryByReasonController {

private Logger logger=Logger.getLogger(SummaryByReasonController.class);
	
	@Autowired
	SummaryByReasonService summaryByReasonService;
	
	@Autowired
	private SummaryByReasonControllerHelper summaryByReasonControllerHelper;
	
	@RequestMapping(value="/summaryByReason.html")
	public String getHome(){
		logger.info("In SummaryByReasonController-getHome()...");
		return "summaryByReason";
	}
	
	@RequestMapping(value="/summaryByReasonDetails", method=RequestMethod.POST)
	public ModelAndView getSummaryByReason(SearchCriteria criteria, ModelAndView model) {
		logger.info("In SummaryByReasonController-getSummaryByReason()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			SummaryByReasonCode summaryByReason = summaryByReasonService.getSummaryByReason(criteria);
			logger.info("summaryByReason.getReasonCodeDetails().size() : "+summaryByReason.getReasonCodeDetails().size());
			model.addObject("summaryByReasonList", summaryByReason.getReasonCodeDetails());
			Date fromDate = sdf.parse(summaryByReason.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(summaryByReason.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("totalTows", summaryByReason.getTotalTows());
			model.addObject("sumOfTotalCharges", summaryByReason.getSumOfTotalCharges());
			model.addObject("avgChargePerTow", summaryByReason.getAvgChargePerTow());		
			model.setViewName("summaryByReason");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportSummaryByReason", method=RequestMethod.POST)
	public ModelAndView exportReasonDetails(ReasonCodeDetailsList reasonDetailsList, ModelAndView model )
	{
		logger.info("In SummaryByReasonController-exportReasonDetails(...) ...");
		try{
			logger.info("reasonDetailsList.getReason().size() : "+reasonDetailsList.getReason().size());
			model.addObject("excelDetails",summaryByReasonControllerHelper.convertInvoicesListAsExcelFormat(reasonDetailsList));
			model.addObject("excelHeaders",summaryByReasonControllerHelper.getReasonDetailsListExcelHeaders());
			model.addObject("fileName","SummaryByReason.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
}
