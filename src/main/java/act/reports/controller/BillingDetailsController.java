package act.reports.controller;


import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import act.reports.dao.BillingDetailsDAO;
import act.reports.model.AccountNames;
import act.reports.model.BillingDetails;
import act.reports.model.SearchCriteria;
import act.reports.model.ServiceStatus;
import act.reports.service.BillingDetailsService;
import act.reports.util.DateUtility;

@Controller
public class BillingDetailsController {

	private Logger logger=Logger.getLogger(BillingDetailsController.class);

	@Autowired
	BillingDetailsService billingDetailsService;

	@Autowired
	BillingDetailsDAO billingDetailsDAO;

	@Autowired
	private BillingDetailsControllerHelper billingDetailsControllerHelper;

	@RequestMapping(value="/billing.html")
	public String getHome(){
		logger.info("In BillingDetailsController-getHome()...");
		return "billing";
	}

	@RequestMapping(value="/billing/viewStatement",method=RequestMethod.POST)
	public void viewBillingStatement(SearchCriteria criteria, HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("In viewBillingStatement()...");
		List<BillingDetails> billingDetailsList = null;
		ServiceStatus status=new ServiceStatus();
		String filePath="";
		try{
			HttpSession session=request.getSession();
			String currDateTime = DateUtility.getDisplayDate(new java.util.Date(),"dd-MM-yy_HH-mm");
			String accountName = criteria.getAccountName();
			String statementType = criteria.getStatementType();
			logger.info("statementType in viewBillingStatement() : "+statementType);
			String logoPath=session.getServletContext().getRealPath("resources")+"/images/logo.gif";
			billingDetailsList = billingDetailsService.getBillingDetails(criteria);
			logger.info("billingDetailsList.size() : "+billingDetailsList.size());
			if(billingDetailsList.size()>0){
				if(criteria.getSearchString().equalsIgnoreCase("allBillable")){
					if(statementType.equalsIgnoreCase("pdf"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+currDateTime+".pdf";
					else if(statementType.equalsIgnoreCase("xls"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+currDateTime+".xls";
				}
				else if(criteria.getSearchString().equalsIgnoreCase("byAccount")){
					if(statementType.equalsIgnoreCase("pdf"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".pdf";					
					else if(statementType.equalsIgnoreCase("xls"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".xls";
				}
				
				if(statementType.equalsIgnoreCase("pdf")){
					status = billingDetailsControllerHelper.viewBillingStatementPDF(criteria.getSearchString(), billingDetailsList, filePath, logoPath);
					if(status.getStatus().equalsIgnoreCase("OK")){
						Path path = Paths.get(filePath);
						byte[] pdfFileContentInBytes = Files.readAllBytes(path);
						response.setContentType("application/octet-stream");
						response.setHeader("Content-Disposition", "attachment; filename=\"ACT_Statement_"+accountName+"_"+currDateTime+".pdf");
						OutputStream out = response.getOutputStream();
						out.write(pdfFileContentInBytes);
						out.flush();                   
					}
				}
				else if(statementType.equalsIgnoreCase("xls")){
					status = billingDetailsControllerHelper.viewBillingStatementExcel(criteria.getSearchString(), billingDetailsList, filePath, logoPath);
					if(status.getStatus().equalsIgnoreCase("OK")){
						Path path = Paths.get(filePath);
						byte[] pdfFileContentInBytes = Files.readAllBytes(path);
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "attachment; filename=\"ACT_Statement_"+accountName+"_"+currDateTime+".xls");
						OutputStream out = response.getOutputStream();
						out.write(pdfFileContentInBytes);
						out.flush();                   
					}
				}
			}
			else
			{
				//pdfPath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".pdf";
				if(criteria.getSearchString().equalsIgnoreCase("allBillable")){
					if(statementType.equalsIgnoreCase("pdf"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+currDateTime+".pdf";
					else if(statementType.equalsIgnoreCase("xls"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+currDateTime+".xls";
				}
				else if(criteria.getSearchString().equalsIgnoreCase("byAccount")){
					if(statementType.equalsIgnoreCase("pdf"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".pdf";
					else if(statementType.equalsIgnoreCase("xls"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".xls";
				}
				
				if(statementType.equalsIgnoreCase("pdf")){
					status = billingDetailsControllerHelper.generateNoDataPDF(accountName, filePath, logoPath);
					if(status.getStatus().equalsIgnoreCase("OK")){
						Path path = Paths.get(filePath);
						byte[] pdfFileContentInBytes = Files.readAllBytes(path);
						response.setContentType("application/octet-stream");
						response.setHeader("Content-Disposition", "attachment; filename=\"ACT_Statement_"+accountName+"_"+currDateTime+".pdf");
						OutputStream out = response.getOutputStream();
						out.write(pdfFileContentInBytes);
						out.flush();
					}
				}
				else if(statementType.equalsIgnoreCase("xls")){
					status = billingDetailsControllerHelper.generateNoDataExcel(filePath, logoPath);
					if(status.getStatus().equalsIgnoreCase("OK")){
						Path path = Paths.get(filePath);
						byte[] pdfFileContentInBytes = Files.readAllBytes(path);
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition", "attachment; filename=\"ACT_Statement_"+accountName+"_"+currDateTime+".xls");
						OutputStream out = response.getOutputStream();
						out.write(pdfFileContentInBytes);
						out.flush();                   
					}
				}
			}
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();			
		}		
	}

	@RequestMapping(value="/billing/sendStatementsEmail",method=RequestMethod.POST)
	public @ResponseBody ServiceStatus sendBillingStatementEmail(@RequestBody SearchCriteria criteria, HttpServletRequest request)
	{	 	
		logger.info("In sendBillingStatementEmail()...");
		ServiceStatus status=new ServiceStatus();
		String filePath="";
		List<String> filePaths=new ArrayList<String>();
		try{
			HttpSession session=request.getSession();
			String currDateTime = DateUtility.getDisplayDate(new java.util.Date(),"dd-MM-yy_HH-mm");
			String accountName = criteria.getAccountName();
			String statementType = criteria.getStatementType();
			logger.info("statementType in viewBillingStatement() : "+statementType);
			String logoPath=session.getServletContext().getRealPath("resources")+"/images/logo.gif";			
			List<BillingDetails> billingDetailsList = billingDetailsService.getBillingDetails(criteria);
			logger.info("billingDetailsList.size() : "+billingDetailsList.size());
			if(billingDetailsList.size()>0){
				if(criteria.getSearchString().equalsIgnoreCase("byAccount")){
					if(statementType.equalsIgnoreCase("pdf"))
						filePath=session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".pdf";
					else if(statementType.equalsIgnoreCase("xls"))
						filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".xls";
					//pdfPaths.add(pdfPath);
					//status = billingDetailsControllerHelper.sendEmailStatement(criteria.getSearchString(), billingDetailsList, pdfPaths, logoPath);
					status = billingDetailsControllerHelper.sendEmailStatement(statementType, billingDetailsList.get(0), filePath, logoPath);
				}
				else if(criteria.getSearchString().equalsIgnoreCase("allBillable")){					
					for(int i=0;i<billingDetailsList.size();i++){
						BillingDetails billingDetails = new BillingDetails();
						billingDetails = billingDetailsList.get(i);
						if(billingDetails.getBillingInfoDetailsList().size()>0){
							accountName = billingDetails.getAccountName();
							if(statementType.equalsIgnoreCase("pdf"))
								filePath=session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".pdf";
							else if(statementType.equalsIgnoreCase("xls"))
								filePath = session.getServletContext().getRealPath("WEB-INF")+"/ACT_Statement_"+accountName+"_"+currDateTime+".xls";
							filePaths.add(filePath);
							/*status = billingDetailsControllerHelper.sendEmailStatement(billingDetails, pdfPath, logoPath);
							logger.info("Mail sent status for "+billingDetails.getAccountName()+" : "+status);*/
						}						
					}
					logger.info("filePaths.size() : "+filePaths.size());
					status = billingDetailsControllerHelper.sendEmailStatement(statementType, criteria.getSearchString(), billingDetailsList, filePaths, logoPath);
				}
			}
			else{
				status.setMessage("No data found for this Account.");
				status.setStatus("FAIL");
			}
		}
		catch(Exception e){
			logger.error(e);
			status.setMessage("Error occured.");
			status.setStatus("FAIL");
		}
		return status;
	}

	@RequestMapping(value="/billing/get/accountNames",method=RequestMethod.POST)
	public @ResponseBody List<AccountNames> getAccountNames()
	{
		logger.info("In BillingDetailsController-getAccountNames()...");
		List<AccountNames> accountNamesList=null;
		try{
			accountNamesList = billingDetailsService.getAccountNames();
		}
		catch(Exception e){
			logger.error(e);
		}
		return accountNamesList;
	}
}
