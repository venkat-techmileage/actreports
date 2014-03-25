package act.reports.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.Audit;
import act.reports.model.AuditDetailsList;
import act.reports.model.SearchCriteria;
import act.reports.service.AuditService;

@Controller
//@RequestMapping(value="/audits")
public class AuditController {

	private Logger logger=Logger.getLogger(AuditController.class);
	
	@Autowired
	AuditService auditService;
	
	@Autowired
	private AuditControllerHelper auditControllerHelper;

	
	@RequestMapping(value="/audit.html")
	public String getHome(){
		logger.info("In AuditController-getHome()...");
		return "auditLog";
	}
	
	@RequestMapping(value="/displayAudits", method=RequestMethod.POST)
	public ModelAndView getAuditDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In AllInvoicesController-getAllInvoicesList()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			Audit auditlog = auditService.getAuditDetails(criteria);			
			Date fromDate = sdf.parse(auditlog.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(auditlog.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			logger.info("audit.getAuditDetails().size() : "+auditlog.getAuditDetails().size());
			model.addObject("auditDetails", auditlog.getAuditDetails());
			model.addObject("auditTotals", auditlog.getAuditTotals());
			model.setViewName("auditLog");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportAudits", method=RequestMethod.POST)
	public ModelAndView exportAuditDetails(AuditDetailsList auditDetailsList, ModelAndView model )
	{
		logger.info("In AuditController-exportAuditDetails(...) ...");
		try{
			logger.info("auditDetailsList.getUserId().size() = "+auditDetailsList.getUserId().size());			
			model.addObject("excelDetails",auditControllerHelper.convertAuditDetailsListToExcelFormat(auditDetailsList));
			model.addObject("excelHeaders",auditControllerHelper.getAuditDetailsListExcelHeaders());			
			model.addObject("fileName","AuditLog.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}
