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

import act.reports.model.AccountTypes;
import act.reports.model.AccountsReceivableAccTypeDetailList;
import act.reports.model.AccountsReceivableDetailByAccType;
import act.reports.model.SearchCriteria;
import act.reports.service.AccountsReceivableDetailByAccTypeService;

@Controller
public class AccountsReceivableDetailByAccTypeController {

	private Logger logger=Logger.getLogger(AccountsReceivableDetailByAccTypeController.class);
	
	@Autowired
	AccountsReceivableDetailByAccTypeService accountsReceivableDetailByAccTypeService;
	
	@Autowired
	private AccountsReceivableDetailByAccTypeControllerHelper accountsReceivableDetailByAccTypeControllerHelper;

	
	@RequestMapping(value="/accountsReceivableDetailByAccType.html")
	public String getHome(){
		logger.info("In AccountsReceivableDetailByAccTypeController-getHome()...");
		return "accountsReceivableDetailByAccType";
	}
	
	@RequestMapping(value="/accountsReceivableDetailByAccType/accountTypes", method=RequestMethod.POST)
	@ResponseBody
	public List<AccountTypes> getAccountTypesList() {
		logger.info("In AccountsReceivableDetailByAccTypeController-getAccountTypesList()...");
		List<AccountTypes> accountTypesList = null;
		try{
			accountTypesList = accountsReceivableDetailByAccTypeService.getAccountTypesList();
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return accountTypesList;
	}
	
	@RequestMapping(value="/showAccountsReceivableDetailByAccType", method=RequestMethod.POST)
	public ModelAndView getAccountsReceivableDetailByAccType(SearchCriteria criteria, ModelAndView model) {
		logger.info("In AccountsReceivableDetailByAccTypeController-getAccountsReceivableDetailByAccType()...");
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			AccountsReceivableDetailByAccType accountsReceivableDetailByAccType = accountsReceivableDetailByAccTypeService.getAccountsReceivableDetailByAccountType(criteria);			
			Date asOfDate = sdf.parse(accountsReceivableDetailByAccType.getAsOfDate());
			model.addObject("asOfDate", sdf1.format(asOfDate));
			logger.info("accountsReceivableDetailByAccType.getAccountsReceivableAccTypeDetailList().size() : "+accountsReceivableDetailByAccType.getAccountsReceivableAccTypeDetailList().size());
			model.addObject("accountsReceivableDetailByAccType", accountsReceivableDetailByAccType.getAccountsReceivableAccTypeDetailList());
			model.addObject("accType", accountsReceivableDetailByAccType.getAccountType());
			model.addObject("currentDue", accountsReceivableDetailByAccType.getCurrentDue());
			model.addObject("thirtyDaysDue", accountsReceivableDetailByAccType.getThirtyDaysDue());
			model.addObject("sixtyDaysDue", accountsReceivableDetailByAccType.getSixtyDaysDue());
			model.addObject("nintyDaysDue", accountsReceivableDetailByAccType.getNintyDaysDue());
			model.addObject("moreThanNintyDaysDue", accountsReceivableDetailByAccType.getMoreThanNintyDaysDue());
			model.addObject("total", accountsReceivableDetailByAccType.getTotal());
			model.setViewName("accountsReceivableDetailByAccType");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/exportAccountsReceivableDetailByAccType", method=RequestMethod.POST)
	public ModelAndView exportAccountReceivableDetailByAccType(AccountsReceivableAccTypeDetailList accountsReceivableAccTypeDetailList, ModelAndView model )
	{
		logger.info("In AccountsReceivableDetailByAccTypeController-exportAccountReceivableDetailByAccType(...) ...");
		try{
			logger.info("accountsReceivableAccTypeDetailList.getAccount().size() = "+accountsReceivableAccTypeDetailList.getAccount().size());			
			model.addObject("excelDetails",accountsReceivableDetailByAccTypeControllerHelper.convertAccountReceivableDetailByAccTypeToExcelFormat(accountsReceivableAccTypeDetailList));
			model.addObject("excelHeaders",accountsReceivableDetailByAccTypeControllerHelper.getAccountsReceivableDetailByAccTypeExcelHeaders());			
			model.addObject("fileName","AccountsReceivableDetailByAccountType.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}