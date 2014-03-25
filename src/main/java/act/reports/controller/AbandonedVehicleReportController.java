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

import act.reports.model.AbandonedVehicle;
import act.reports.model.AbandonedVehicleDetailsList;
import act.reports.model.AccountNames;
import act.reports.model.SearchCriteria;
import act.reports.service.AbandonedVehicleReportService;

@Controller
//@RequestMapping(value="/summaries")
public class AbandonedVehicleReportController {

private Logger logger=Logger.getLogger(AbandonedVehicleReportController.class);
	
	@Autowired
	AbandonedVehicleReportService abandonedVehicleReportService;
	
	@Autowired
	private AbandonedVehicleReportControllerHelper abandonedVehicleReportControllerHelper;
	
	@RequestMapping(value="/abandonedVehicle.html")
	public String getHome(){
		logger.info("In AbandonedVehicleReportController-getHome()...");
		return "abandonedVehicle";
	}
	

	@RequestMapping(value="/abandonedVehicleDetails", method=RequestMethod.POST)
	public ModelAndView getAbandonedVehicle(SearchCriteria criteria, ModelAndView model) {
		logger.info("AbandonedVehicleReportController-getAbandonedVehicle()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			AbandonedVehicle abandonedVehicle = abandonedVehicleReportService.getAbandonedVehicle(criteria);
			logger.info("abandonedVehicle.getAbandonedVehicleDetails().size() : "+abandonedVehicle.getAbandonedVehicleDetails().size());
			model.addObject("abandonedVehicleDetailsList", abandonedVehicle.getAbandonedVehicleDetails());
			Date fromDate = sdf.parse(abandonedVehicle.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(abandonedVehicle.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("accountName", criteria.getAccountName());
			model.addObject("totalVehicles", abandonedVehicle.getTotalVehicles());
			model.setViewName("abandonedVehicle");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportAbandonedVehicleDetail", method=RequestMethod.POST)
	public ModelAndView exportAbandonedVehicleDetailsList(AbandonedVehicleDetailsList abandonedVehicleDetailsList, ModelAndView model )
	{
		logger.info("In AbandonedVehicleController-exportAbandonedVehicleDetails(...) ...");
		try{
			logger.info("abandonedVehicleDetailsList.ServiceCallDate().size() : "+abandonedVehicleDetailsList.getServiceCallDate().size());
			model.addObject("excelDetails",abandonedVehicleReportControllerHelper.convertAbandonedVehicleDetailsListAsExcelFormat(abandonedVehicleDetailsList));
			model.addObject("excelHeaders",abandonedVehicleReportControllerHelper.getAbandonedVehicleDetailsListExcelHeaders());
			model.addObject("fileName","AbandonedVehicle.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	@RequestMapping(value="/abandoned/get/accountNames",method=RequestMethod.POST)
	public @ResponseBody List<AccountNames> getAccountNames()
	{
		logger.info("In AbandonedVehicleReportController-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = abandonedVehicleReportService.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}
