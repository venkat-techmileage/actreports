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
import act.reports.model.AccountsReceivableDetail;
import act.reports.model.AccountsReceivableDetailList;
import act.reports.model.SearchCriteria;
import act.reports.service.AccountsReceivableDetailService;

@Controller
public class AccountsReceivableDetailController {

	private Logger logger=Logger.getLogger(AccountsReceivableDetailController.class);
	
	@Autowired
	AccountsReceivableDetailService accountsReceivableDetailService;
	
	@Autowired
	private AccountsReceivableDetailControllerHelper accountsReceivableDetailControllerHelper;

	
	@RequestMapping(value="/accountsReceivableDetail.html")
	public String getHome(){
		logger.info("In AccountsReceivabDetaillController-getHome()...");
		return "accountsReceivableDetail";
	}
	
	@RequestMapping(value="/showAccountsReceivableDetail", method=RequestMethod.POST)
	public ModelAndView getAccountsReceivableDetail(SearchCriteria criteria, ModelAndView model) {
		logger.info("In AccountsReceivableDetailController-getAccountsReceivableDetail()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			AccountsReceivableDetail accountsReceivableDetail = accountsReceivableDetailService.getAccountsReceivableDetail(criteria);			
			Date asOfDate = sdf.parse(accountsReceivableDetail.getAsOfDate());
			model.addObject("asOfDate", sdf1.format(asOfDate));
			logger.info("accountsReceivableDetail.getAccountReceivableDetailInfo().size() : "+accountsReceivableDetail.getAccountReceivableDetailInfo().size());
			model.addObject("accountsReceivableDetail", accountsReceivableDetail.getAccountReceivableDetailInfo());
			model.addObject("accName", accountsReceivableDetail.getAccountName());
			model.addObject("currentDue", accountsReceivableDetail.getCurrentDue());
			model.addObject("thirtyDaysDue", accountsReceivableDetail.getThirtyDaysDue());
			model.addObject("sixtyDaysDue", accountsReceivableDetail.getSixtyDaysDue());
			model.addObject("nintyDaysDue", accountsReceivableDetail.getNintyDaysDue());
			model.addObject("moreThanNintyDaysDue", accountsReceivableDetail.getMoreThanNintyDaysDue());
			model.addObject("total", accountsReceivableDetail.getTotal());
			model.setViewName("accountsReceivableDetail");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportAccountsReceivableDetail", method=RequestMethod.POST)
	public ModelAndView exportAccountReceivableDetail(AccountsReceivableDetailList accountsReceivableDetailList, ModelAndView model )
	{
		logger.info("In AccountsReceivableDetailController-exportAccountReceivableDetail(...) ...");
		try{
			logger.info("accountsReceivableDetailList.getInvoiceId().size() = "+accountsReceivableDetailList.getInvoiceId().size());			
			model.addObject("excelDetails",accountsReceivableDetailControllerHelper.convertAccountReceivableDetailToExcelFormat(accountsReceivableDetailList));
			model.addObject("excelHeaders",accountsReceivableDetailControllerHelper.getAccountsReceivableDetailExcelHeaders());			
			model.addObject("fileName","AccountsReceivableDetail.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
	@RequestMapping(value="/get/accountNames",method=RequestMethod.POST)
	public @ResponseBody List<AccountNames> getAccountNames()
	{
		logger.info("In AccountsReceivableDetailController-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = accountsReceivableDetailService.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}

