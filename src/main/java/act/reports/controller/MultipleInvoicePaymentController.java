package act.reports.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.AccountNames;
import act.reports.model.InvoicePaymentDetailsUpdate;
import act.reports.model.InvoicePaymentRequest;
import act.reports.model.MultipleInvoicePayment;
import act.reports.model.MultipleInvoicePaymentDetailsList;
import act.reports.model.MultipleInvoicePaymentDetailsUpdate;
import act.reports.model.PaymentClient;
import act.reports.model.PaymentResponse;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.model.ServiceStatus;
import act.reports.model.UserDetail;
import act.reports.service.MultipleInvoicePaymentService;
import act.reports.util.DateUtility;

@Controller
public class MultipleInvoicePaymentController {

	private Logger logger=Logger.getLogger(MultipleInvoicePaymentController.class);
	
	@Autowired
	MultipleInvoicePaymentService multipleInvoicePaymentService;
	
	@Autowired
	private MultipleInvoicePaymentControllerHelper multipleInvoicePaymentControllerHelper;

	
	@RequestMapping(value="/multipleInvoicePayment.html")
	public String getHome(){
		logger.info("In MultipleInvoicePaymentController-getHome()...");
		return "multipleInvoicePayment";
	}
	
	@RequestMapping(value="/multipleInvoicePayment/getAccountNamesList",method=RequestMethod.POST)
	public @ResponseBody List<AccountNames> getAccountNames()
	{
		logger.info("In MultipleInvoicePaymentController-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = multipleInvoicePaymentService.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
	
	@RequestMapping(value="/select/getLotLocations",method=RequestMethod.POST)
	public @ResponseBody List<SelectOption> getLotLocations()
	{
		logger.info("In MultipleInvoicePaymentController-getLotLocations()...");
		List<SelectOption> lotLocations = null;
		try{
			lotLocations = multipleInvoicePaymentService.getLotLocations();
		}
		catch(Exception e){
			logger.error(e);
		}
		return lotLocations;
	}
	
	/*@RequestMapping(value="/select/getPaymentTypes",method=RequestMethod.POST)
	public @ResponseBody List<SelectOption> getPaymentTypes()
	{
		logger.info("In MultipleInvoicePaymentController-getPaymentTypes()...");
		List<SelectOption> paymentTypes = null;
		try{
			paymentTypes = multipleInvoicePaymentService.getPaymentTypes();
		}
		catch(Exception e){
			logger.error(e);
		}
		return paymentTypes;
	}*/
	
	@RequestMapping(value="/showInvoicesDetails", method=RequestMethod.POST)
	public ModelAndView getInvoicesDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In MultipleInvoicePaymentController-getInvoicesDetails()...");
		try{
			MultipleInvoicePayment multipleInvoicePayment = multipleInvoicePaymentService.getInvoicesDetails(criteria);			
			model.addObject("accountName", criteria.getAccountName());
			model.addObject("multipleInvoicePaymentDetailsList", multipleInvoicePayment.getMultipleInvoicePaymentDetailsList());
			model.addObject("totalOpenInvoies", multipleInvoicePayment.getTotalOpenInvoices());
			model.setViewName("multipleInvoicePayment");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/updateInvoicePayment", method=RequestMethod.POST)
	public ModelAndView updateInvoicePayment(MultipleInvoicePaymentDetailsUpdate multipleInvoicePaymentDetailsUpdate, ModelAndView model) {
		logger.info("In MultipleInvoicePaymentController-updateInvoicePayment()...");
		try{
			MultipleInvoicePayment multipleInvoicePayment = multipleInvoicePaymentService.updateInvoicePayment(multipleInvoicePaymentDetailsUpdate);			
			model.addObject("accountName", multipleInvoicePayment.getAccountName());
			model.addObject("multipleInvoicePaymentDetailsList", multipleInvoicePayment.getMultipleInvoicePaymentDetailsList());
			model.addObject("totalOpenInvoies", multipleInvoicePayment.getTotalOpenInvoices());
			model.setViewName("multipleInvoicePayment");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;		
	}
	
	@RequestMapping(value="/get/paymentClient")
	 public @ResponseBody PaymentClient paymentClient(@RequestParam("accountName") String accountName,@RequestParam("amount") String amount)
	 {
		logger.info("get Payment client details ");
		//getBalanceDue(invoiceId);
		float amnt=Float.parseFloat(amount);
		PaymentClient payClient=multipleInvoicePaymentControllerHelper.getPaymentClient(accountName,amnt);
		logger.info("RelayUrl = "+payClient.getRelayUrl());
		logger.info("RelayRes = "+payClient.getRelayRes());
		return payClient;
	 }
	 
	 @RequestMapping(value="/payment/response.html")
	 public ModelAndView paymentResponseView(HttpServletRequest request,ModelAndView model)
	 {
		 logger.info("In paymentResponseView()... ");
		 PaymentResponse payResponse = null;
		 try 
		 {
			 logger.info("Payment Relay response from Authorize.net - x_trans_id : " + request.getParameter("x_trans_id"));
			 logger.info("Payment Relay response from Authorize.net - x_response_code : " + request.getParameter("x_response_code"));
			 logger.info("Payment Relay response from Authorize.net - x_response_reason_text : " + request.getParameter("x_response_reason_text"));
			 logger.info("Payment Relay response from Authorize.net - x_account_name : " + request.getParameter("x_account_name"));
			 logger.info("Payment Relay response from Authorize.net - x_payment_details : " + request.getParameter("x_payment_details"));
			 
			 payResponse = multipleInvoicePaymentControllerHelper.createPaymentResponse(request);
			 if(payResponse.getResponseCode().equalsIgnoreCase("1"))
			 {
				 HttpSession session = request.getSession();
				 UserDetail userDtl = null;
				 String userId = "";
				 if(session.getAttribute("userDetails")!=null){
				 	userDtl = (UserDetail)session.getAttribute("userDetails");	
				 	userId = userDtl.getUserId();				 	
				 }
				 else{
					 String actUserKeyCookieName = "actuserkey";
					 Cookie[] cookies = request.getCookies();
					 if (cookies != null){
						 for (Cookie ck : cookies){
							 if(actUserKeyCookieName.equals(ck.getName())){
								 userId = ck.getValue();
							 }
						 }
					 }
				 }
				 logger.info("userId in paymentResponseView() : "+userId);
				 
				 String paymentDetails = payResponse.getPaymentDetails();		 
				 List<InvoicePaymentDetailsUpdate> invoicePaymentUpdateList = new ArrayList<InvoicePaymentDetailsUpdate>();				 
				 ArrayList<String> paymentRecordsList = new ArrayList<String>();
				 StringTokenizer st1 = new StringTokenizer(paymentDetails, "|");
				 while (st1.hasMoreTokens()) {
					 String paymentRecord = st1.nextToken();
					 paymentRecordsList.add(paymentRecord);
				 }
				 for(int j=0;j<paymentRecordsList.size();j++){
					 InvoicePaymentDetailsUpdate invoicePaymentUpdate = new InvoicePaymentDetailsUpdate();
					 invoicePaymentUpdate.setCcTxNo(payResponse.getMD5Hash());			 
					 StringTokenizer st2 = new StringTokenizer(paymentRecordsList.get(j), ";");
					 while (st2.hasMoreTokens()) {
						 invoicePaymentUpdate.setInvoiceId(st2.nextToken());
						 invoicePaymentUpdate.setPaymentAmt(st2.nextToken());
						 invoicePaymentUpdate.setPaymentLoc(st2.nextToken());
					 }
					 invoicePaymentUpdate.setPaymentType("Credit");
					 invoicePaymentUpdate.setUserId(userId);
					 invoicePaymentUpdate.setPaymentDate(DateUtility.getCurrentMysqlDateTime());
					 invoicePaymentUpdateList.add(invoicePaymentUpdate);
				 }
		
				 ServiceStatus serviceStatus = multipleInvoicePaymentService.updateInvoicePayment(invoicePaymentUpdateList);
				 logger.info("Status Message = "+serviceStatus.getMessage());			 
			 
				 model.setViewName("paymentSuccess");
			 }
			 else
			 {
				 model.setViewName("paymentFailure");
			 }
			 
			 model.addObject("payResponse",payResponse);
		 } 
		 catch (Exception e)
		 {
			 logger.info("Error occured during saving payment made for the Account  : " + payResponse.getAccountName() + "with error :" + e.getMessage());
			 logger.error(e);
			 e.printStackTrace();
		 }		 
		 
		 return model;
	 }
	 
	 /*@RequestMapping(value="/payment/sendEmailReceipt",method=RequestMethod.POST)
	 public @ResponseBody ServiceStatus sendBillingPaymentReciept(@RequestParam("accountName") String accountName,HttpServletRequest request)
	 {	 	
		 HttpSession session=request.getSession();
		 String pdfPath=session.getServletContext().getRealPath("WEB-INF")+"/ACTPaymentReciept_"+accountName+".pdf";
		 return multipleInvoicePaymentControllerHelper.sendEmailPaymentReceipt(accountName,pdfPath);
	 }*/
	
	@RequestMapping(value="/exportInvoicesDetails", method=RequestMethod.POST)
	public ModelAndView exportInvoicesDetails(MultipleInvoicePaymentDetailsList multipleInvoicePaymentDetailsList, ModelAndView model )
	{
		logger.info("In MultipleInvoicePaymentController-exportInvoicesDetails(...) ...");
		try{
			logger.info("multipleInvoicePaymentDetailsList.getInvoiceId().size() = "+multipleInvoicePaymentDetailsList.getInvoiceId().size());			
			model.addObject("excelDetails",multipleInvoicePaymentControllerHelper.convertInvoicesDetailsToExcelFormat(multipleInvoicePaymentDetailsList));
			model.addObject("excelHeaders",multipleInvoicePaymentControllerHelper.getInvoicesDetailsExcelHeaders());			
			model.addObject("fileName","MultipleInvoicePayment.xls");
			model.setViewName("excelView");
		}catch(Exception e){
			logger.error(e);
		}
		return model;
	}
}