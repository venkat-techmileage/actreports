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

import act.reports.model.DriverIDs;
import act.reports.model.DriverNames;
import act.reports.model.DriverSalesVsCommission;
import act.reports.model.DriverSalesVsCommissionDetailsList;
import act.reports.model.SearchCriteria;
import act.reports.service.DriverSalesVsCommissionService;

@Controller
public class DriverSalesVsCommissionController {

	private Logger logger=Logger.getLogger(DriverSalesVsCommissionController.class);
	
	@Autowired
	DriverSalesVsCommissionService driverSalesVsCommissionService;
	
	@Autowired
	private DriverSalesVsCommissionControllerHelper driverSalesVsCommissionControllerHelper;

	
	@RequestMapping(value="/driverSalesVsCommission.html")
	public String getHome(){
		logger.info("In DriverSalesVsCommissionController-getHome()...");
		return "driverSalesVsCommission";
	}
	
	@RequestMapping(value="/getDriverSalesVsCommission", method=RequestMethod.POST)
	public ModelAndView getDriverSalesCommissionDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In DriverSalesVsCommissionController-getDriverSalesCommissionDetails()...");
		try{
			DriverSalesVsCommission driverSalesVsCommission = driverSalesVsCommissionService.getDriverSalesCommissionDetails(criteria);			
			//DriverSalesDetails driverSalesDetails = driverSalesVsCommission.getDriverSalesDetails();
			//DriverCommissionDetails driverCommissionDetails = driverSalesVsCommission.getDriverCommissionDetails();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			Date fromDate = sdf.parse(driverSalesVsCommission.getFromDate());
			Date toDate = sdf.parse(driverSalesVsCommission.getToDate());			
			model.addObject("fromDate", sdf1.format(fromDate));
			model.addObject("toDate", sdf1.format(toDate));
			/*model.addObject("totalTowingCharges", driverSalesDetails.getTotalTowingCharges());
			model.addObject("totalMileageCharges", driverSalesDetails.getTotalTowingCharges());
			model.addObject("totalLaborCharges", driverSalesDetails.getTotalTowingCharges());
			model.addObject("totalGateCharges", driverSalesDetails.getTotalTowingCharges());
			model.addObject("totalAdminCharges", driverSalesDetails.getTotalTowingCharges());
			model.addObject("subTotal", driverSalesDetails.getTotalTowingCharges());
			model.addObject("totalDiscounts", driverSalesDetails.getTotalTowingCharges());			
			model.addObject("totalSales", driverSalesDetails.getTotalTowingCharges());*/
			model.addObject("driverId", driverSalesVsCommission.getDriverId());
			model.addObject("driverName", driverSalesVsCommission.getDriverName());
			model.addObject("driverSalesDetails", driverSalesVsCommission.getDriverSalesDetails());
			model.addObject("driverCommissionDetails", driverSalesVsCommission.getDriverCommissionDetails());
			model.addObject("totalInvoices", driverSalesVsCommission.getTotalInvoices());
			model.setViewName("driverSalesVsCommission");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportDriverSalesVsCommission", method=RequestMethod.POST)
	public ModelAndView exportDriverSalesVsCommission(DriverSalesVsCommissionDetailsList driverSalesVsCommissionDetailsList, ModelAndView model )
	{
		logger.info("In DriverSalesVsCommissionController-exportDriverSalesVsCommission(...) ...");
		try{
			logger.info("driverSalesVsCommissionDetailsList.getSaleType().size() = "+driverSalesVsCommissionDetailsList.getSaleType().size());			
			model.addObject("excelDetails",driverSalesVsCommissionControllerHelper.convertDriverSalesVsCommissionDetailsListToExcelFormat(driverSalesVsCommissionDetailsList));
			model.addObject("excelHeaders",driverSalesVsCommissionControllerHelper.getDriverSalesVsCommissionDetailsListExcelHeaders());			
			model.addObject("fileName","DriverSalesVsCommission.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}

	@RequestMapping(value="/driverSales/getDriverIDsList",method=RequestMethod.POST)
	public @ResponseBody List<DriverIDs> getDriverIDs()
	{
		logger.info("In DriverSalesVsCommissionController-getDriverIDs()...");
		List<DriverIDs> driverIDsList=null;
		try{
			driverIDsList = driverSalesVsCommissionService.getDriverIDs();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverIDsList;
	}

	@RequestMapping(value="/driverSales/getDriverNamesList",method=RequestMethod.POST)
	public @ResponseBody List<DriverNames> getDriverNames()
	{
		logger.info("In DriverSalesVsCommissionController-getDriverNames()...");
		 List<DriverNames> driverNamesList=null;
		try{
			driverNamesList = driverSalesVsCommissionService.getDriverNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return driverNamesList;
	}
}