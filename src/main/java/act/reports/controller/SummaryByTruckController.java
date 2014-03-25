package act.reports.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.SummaryByTruck;
import act.reports.model.SearchCriteria;
import act.reports.model.TruckDetailsList;
import act.reports.service.SummaryByTruckService;

@Controller
public class SummaryByTruckController {

private Logger logger=Logger.getLogger(SummaryByTruckController.class);
	
	@Autowired
	SummaryByTruckService summaryByTruckService;
	
	@Autowired
	private SummaryByTruckControllerHelper summaryByTruckControllerHelper;
	
	@RequestMapping(value="/summaryByTruck.html")
	public String getHome(){
		logger.info("In SummaryByTruckController-getHome()...");
		return "summaryByTruck";
	}
	
	@RequestMapping(value="/summaryByTruckDetails", method=RequestMethod.POST)
	public ModelAndView getSummaryByTruck(SearchCriteria criteria, ModelAndView model) {
		logger.info("In SummaryByTruckController-getSummaryByTruck()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			SummaryByTruck summaryByTruck = summaryByTruckService.getSummaryByTruck(criteria);
			logger.info("summaryByTruck.getTruckDetails().size() : "+summaryByTruck.getTruckDetails().size());
			model.addObject("summaryByTruckList", summaryByTruck.getTruckDetails());
			Date fromDate = sdf.parse(summaryByTruck.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(summaryByTruck.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("totalTows", summaryByTruck.getTotalTows());
			model.addObject("sumOfTotalCharges", summaryByTruck.getSumOfTotalCharges());
			model.addObject("avgChargePerTow", summaryByTruck.getAvgChargePerTow());		
			model.addObject("actualMileageTotal", summaryByTruck.getActualMileageTotal());
			model.addObject("avgMilesPerTow", summaryByTruck.getAvgMilesPerTow());
			model.setViewName("summaryByTruck");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportSummaryByTruck", method=RequestMethod.POST)
	public ModelAndView exportTruckDetails(TruckDetailsList truckDetailsList, ModelAndView model )
	{
		logger.info("In SummaryByTruckController-exportTruckDetails(...) ...");
		try{
			logger.info("truckDetailsList.getTruck().size() : "+truckDetailsList.getTruck().size());
			model.addObject("excelDetails",summaryByTruckControllerHelper.convertTruckDetailsListAsExcelFormat(truckDetailsList));
			model.addObject("excelHeaders",summaryByTruckControllerHelper.getTruckDetailsListExcelHeaders());
			model.addObject("fileName","SummaryByTruck.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
}
