package act.reports.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.BillingDetailsDAO;
import act.reports.model.BillingDetails;
import act.reports.model.ServiceStatus;
import act.reports.service.ExcelStatementGenerator;
import act.reports.service.MailingDetails;
import act.reports.service.MailingService;
import act.reports.service.PdfService;

@Service("billingDetailsControllerHelper")
public class BillingDetailsControllerHelper {


	private Logger logger=Logger.getLogger(BillingDetailsControllerHelper.class);

	@Autowired
	private PdfService pdfService;
	
	@Autowired
	private ExcelStatementGenerator excelGenerator;

	@Autowired
	private MailingService mailService;
	
	@Autowired
	private BillingDetailsDAO billingDetailsDAO;

	public ServiceStatus viewBillingStatementPDF(String searchString, List<BillingDetails> billingDetailsList, String pdfPath, String logoPath)
	{
		logger.info("In BillingDetailsControllerHelper-viewBillingStatement()...");
		boolean pdfGen=pdfService.generatePdfToViewStatement(searchString, billingDetailsList, pdfPath, logoPath);
		ServiceStatus status=new ServiceStatus();
		if(pdfGen)
		{
			status.setMessage("PDF generated successfully");
			status.setStatus("OK");
		}
		else
		{
			status.setMessage("PDF not generated");
			status.setStatus("FAIL");
		}
		return status;
	}
	
	public ServiceStatus viewBillingStatementExcel(String searchString, List<BillingDetails> billingDetailsList, String filePath, String logoPath)
	{
		logger.info("In BillingDetailsControllerHelper-viewBillingStatementExcel()...");
		boolean fileGen=excelGenerator.viewBillingStatementExcel(searchString, billingDetailsList, filePath, logoPath);
		ServiceStatus status=new ServiceStatus();
		if(fileGen)
		{
			status.setMessage("Excel generated successfully");
			status.setStatus("OK");
		}
		else
		{
			status.setMessage("Excel not generated");
			status.setStatus("FAIL");
		}
		return status;
	}
	
	public ServiceStatus generateNoDataPDF(String accountName, String pdfPath, String logoPath)
	{
		logger.info("In BillingDetailsControllerHelper-generateNoDataPDF()...");
		boolean pdfGen=pdfService.generateNoDataPDF(accountName, pdfPath, logoPath);
		ServiceStatus status=new ServiceStatus();
		if(pdfGen)
		{
			status.setMessage("PDF generated successfully");
			status.setStatus("OK");
		}
		else
		{
			status.setMessage("PDF not generated");
			status.setStatus("FAIL");
		}
		return status;
	}
	
	public ServiceStatus generateNoDataExcel(String filePath, String logoPath)
	{
		logger.info("In BillingDetailsControllerHelper-generateNoDataExcel()...");
		boolean excelGen=excelGenerator.generateNoDataExcel(filePath, logoPath);
		ServiceStatus status=new ServiceStatus();
		if(excelGen)
		{
			status.setMessage("Excel generated successfully");
			status.setStatus("OK");
		}
		else
		{
			status.setMessage("Excel not generated");
			status.setStatus("FAIL");
		}
		return status;
	}
	
	public ServiceStatus sendEmailStatement(String statementType, String searchString, List<BillingDetails> billingDetailsList, List<String> filePaths, String logoPath)
	{
		logger.info("In BillingDetailsControllerHelper-sendEmailStatement()...");
		//String billToMailId=billingDetailsDAO.getBillToMailId(billingDetails.getAccountName());
		boolean pdfGen = false;
		boolean excelGen = false;
		MailingDetails mailDetails=new MailingDetails();
		mailDetails.setMailFrom("venkat@techmileage.com");
		mailDetails.setMailTo("venkat@techmileage.com");
		mailDetails.setMailSubject("ACT Billing Statement");
		mailDetails.setMailContent("Some testing data and a test file as an attachment from ACT");
		mailDetails.setAttachments(filePaths);
		ServiceStatus status=new ServiceStatus();
		
		if(statementType.equalsIgnoreCase("pdf"))
			pdfGen=pdfService.generatePdfToSendStatement(searchString, billingDetailsList, filePaths, logoPath);
		else if(statementType.equalsIgnoreCase("xls"))
			excelGen=excelGenerator.generateExcelToSendStatement(searchString, billingDetailsList, filePaths, logoPath);
		if((statementType.equalsIgnoreCase("pdf") && pdfGen) || (statementType.equalsIgnoreCase("xls") && excelGen))
		{
			boolean statementSent=mailService.sendReciptThroughMail(mailDetails);
			if(statementSent)
			{
				status.setMessage("Statement sent successfully.");
				status.setStatus("OK");
			}else
			{
				status.setMessage("Statement not sent successfully.");
				status.setStatus("FAIL");
			}
			
		}
		else
		{
			status.setMessage("Statement not generated");
			status.setStatus("FAIL");
		}
		return status;
	}
	
	public ServiceStatus sendEmailStatement(String statementType, BillingDetails billingDetails, String filePath, String logoPath)
	{
		logger.info("In BillingDetailsControllerHelper-sendEmailStatement()...");
		//String billToMailId=billingDetailsDAO.getBillToMailId(billingDetails.getAccountName());
		//boolean pdfGen=pdfService.generatePdfToSendStatement(searchString, billingDetailsList, pdfPaths, logoPath);
		boolean pdfGen = false;
		boolean excelGen = false;
		MailingDetails mailDetails=new MailingDetails();
		mailDetails.setMailFrom("venkat@techmileage.com");
		mailDetails.setMailTo("venkat@techmileage.com");
		mailDetails.setMailSubject("ACT Billing Statement - "+billingDetails.getAccountName());
		mailDetails.setMailContent("Some testing data and a test PDF file as an attachment from ACT");
		//mailDetails.setAttachments(pdfPaths);
		List<String> attachments = new ArrayList<String>();
		attachments.add(filePath);
		mailDetails.setAttachments(attachments);
		ServiceStatus status=new ServiceStatus();
		
		if(statementType.equalsIgnoreCase("pdf"))
			pdfGen=pdfService.generatePdfToSendStatement(billingDetails, filePath, logoPath);
		else if(statementType.equalsIgnoreCase("xls"))
			excelGen=excelGenerator.generateExcelToSendStatement(billingDetails, filePath, logoPath);
		
		if((statementType.equalsIgnoreCase("pdf") && pdfGen) || (statementType.equalsIgnoreCase("xls") && excelGen))
		{
			boolean pdfSent=mailService.sendReciptThroughMail(mailDetails);
			if(pdfSent)
			{
				status.setMessage("Statement sent successfully.");
				status.setStatus("OK");
			}else
			{
				status.setMessage("Statement not sent successfully.");
				status.setStatus("FAIL");
			}			
		}
		else
		{
			status.setMessage("Statement not generated");
			status.setStatus("FAIL");
		}
		return status;
	}
}