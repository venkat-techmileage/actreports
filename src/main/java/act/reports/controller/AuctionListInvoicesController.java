package act.reports.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.AuctionListInvoices;
import act.reports.model.AuctionListInvoicesDetailsList;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.service.AuctionListInvoicesService;

@Controller
public class AuctionListInvoicesController {

	private Logger logger=Logger.getLogger(AuctionListInvoicesController.class);
	
	@Autowired
	AuctionListInvoicesService auctionListInvoicesService;
	
	@Autowired
	private AuctionListInvoicesControllerHelper auctionListInvoicesControllerHelper;

	
	@RequestMapping(value="/auctionListInvoices.html")
	public String getHome(){
		logger.info("In AuctionListInvoicesController-getHome()...");
		return "auctionListInvoices";
	}
	
	@RequestMapping(value="/select/getAuctionList",method=RequestMethod.POST)
	public @ResponseBody List<SelectOption> getAuctionList()
	{
		logger.info("In AuctionListInvoicesController-getAuctionList()...");
		List<SelectOption> auctionList = null;
		try{
			auctionList = auctionListInvoicesService.getAuctionList();
		}
		catch(Exception e){
			logger.error(e);
		}
		return auctionList;
	}
	
	@RequestMapping(value="/showAuctionListInvoices", method=RequestMethod.POST)
	public ModelAndView getAuctionListInvoicesDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In AuctionListInvoicesController-getAuctionListInvoicesDetails()...");
		try{
			AuctionListInvoices auctionListInvoices = auctionListInvoicesService.getAuctionListInvoicesDetails(criteria);			
			model.addObject("auctionListId", auctionListInvoices.getAuctionListId());
			model.addObject("auctionListName", auctionListInvoices.getAuctionListName());
			model.addObject("auctionDate", auctionListInvoices.getAuctionDate());
			model.addObject("auctionListInvoicesDetailsList", auctionListInvoices.getAuctionListInvoicesDetailsList());
			model.addObject("totalVehicles", auctionListInvoices.getTotalVehicles());
			model.setViewName("auctionListInvoices");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportAuctionListInvoices", method=RequestMethod.POST)
	public ModelAndView exportAuctionListInvoices(AuctionListInvoicesDetailsList auctionListInvoicesDetailsList, ModelAndView model )
	{
		logger.info("In AuctionListInvoicesController-exportAuctionListInvoices(...) ...");
		try{
			logger.info("auctionListInvoicesDetailsList.getInvoiceId().size() = "+auctionListInvoicesDetailsList.getInvoiceId().size());			
			model.addObject("excelDetails",auctionListInvoicesControllerHelper.convertAuctionListInvoicesToExcelFormat(auctionListInvoicesDetailsList));
			model.addObject("excelHeaders",auctionListInvoicesControllerHelper.getAuctionListInvoicesDetailExcelHeaders());			
			model.addObject("fileName","AuctionList_Invoices.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}