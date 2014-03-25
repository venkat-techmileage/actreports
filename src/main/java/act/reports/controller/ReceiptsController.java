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

import act.reports.model.ReceiptDetailsList;
import act.reports.model.Receipts;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.service.ReceiptsService;

@Controller
//@RequestMapping(value="/receipts")
public class ReceiptsController {

	private Logger logger=Logger.getLogger(ReceiptsController.class);
	
	@Autowired
	ReceiptsService receiptsService;
	
	@Autowired
	private ReceiptsControllerHelper receiptsControllerHelper;

	
	@RequestMapping(value="/receipts.html")
	public String getHome(){
		logger.info("In ReceiptsController-getHome()...");
		return "receipts";
	}
	
	@RequestMapping(value="/receipts/getLotLocations",method=RequestMethod.POST)
	public @ResponseBody List<SelectOption> getLotLocations()
	{
		logger.info("In ReceiptsController-getLotLocations()...");
		List<SelectOption> lotLocations = null;
		try{
			lotLocations = receiptsService.getLotLocations();
		}
		catch(Exception e){
			logger.error(e);
		}
		return lotLocations;
	}
	
	@RequestMapping(value="/displayReceipts", method=RequestMethod.POST)
	public ModelAndView getReceiptsDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In AllInvoicesController-getAllInvoicesList()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			Receipts receipts = receiptsService.getReceiptsDetails(criteria);			
			Date fromDate = sdf.parse(receipts.getFromDate());
			model.addObject("fromDate", sdf1.format(fromDate));
			Date toDate = sdf.parse(receipts.getToDate());
			model.addObject("toDate", sdf1.format(toDate));
			model.addObject("location", criteria.getLocation());
			logger.info("receipts.getReceiptDetails().size() : "+receipts.getReceiptDetails().size());
			model.addObject("receiptDetails", receipts.getReceiptDetails());
			model.addObject("receiptTotals", receipts.getReceiptsTotals());
			model.addObject("paymentTypeTotal", receipts.getPaymentTypeTotal());
			model.setViewName("receipts");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportReceipts", method=RequestMethod.POST)
	public ModelAndView exportReceiptsList(ReceiptDetailsList receiptDetailsList, ModelAndView model )
	{
		logger.info("In ReceiptsController-exportReceiptsList(...) ...");
		try{
			logger.info("receiptDetailsList.getInvoiceId().size() = "+receiptDetailsList.getInvoiceId().size());			
			model.addObject("excelDetails",receiptsControllerHelper.convertReceiptsListToExcelFormat(receiptDetailsList));
			model.addObject("excelHeaders",receiptsControllerHelper.getReceiptDetailsListExcelHeaders());			
			model.addObject("fileName","Receipts.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}
