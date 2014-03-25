package act.reports.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.BillingDetailsDAO;
import act.reports.model.BillingAddressDetails;
import act.reports.model.BillingDetails;
import act.reports.model.BillingInfoDetails;
import act.reports.util.DateUtility;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service("pdfService")
public class PdfService 
{
	private Logger logger=Logger.getLogger(PdfService.class);
	
	@Autowired
	BillingDetailsDAO billingDetailsDAO;
	
	public boolean generatePdfToViewStatement(String searchString, List<BillingDetails> billingDetailsList, String pdfPath, String logoPath)
	{
		boolean pdfGen=true;
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 9);
        Font tableFont = new Font(Font.FontFamily.HELVETICA, 8);
		try
		{
			if(searchString.equalsIgnoreCase("byAccount")){
				BillingDetails billingDetails = billingDetailsList.get(0);
				Document document=new Document();
				PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
				document.open();
				
				Paragraph para1 = new Paragraph();
	            para1.setAlignment(Element.ALIGN_CENTER);
	            para1.add(new Chunk("Statement Of Account ",headerFont));
	            para1.add(new Phrase("\n"));
	            para1.add(new Chunk("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"),headerFont));
	            PdfPTable para1Table = new PdfPTable(1);
	            para1Table.setWidthPercentage(20);
	            PdfPCell para1Cell1 = new PdfPCell(para1);
	            para1Cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            para1Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            para1Table.addCell(para1Cell1);            
	            
	            Image image = Image.getInstance(logoPath);
	            image.setAbsolutePosition(400f, 720f);
	            
	            Paragraph para2 = new Paragraph();
	            para2.add(new Chunk("ALL CITY TOWING",headerFont));
	            para2.add(new Phrase("\n"));
	            para2.add(new Chunk("2031 W. 1st St.",headerFont));
	            para2.add(new Phrase("\n"));
	            para2.add(new Chunk("Tempe, AZ 85281",headerFont));
	            para2.add(new Phrase("\n"));
	            para2.add(new Chunk("480.833.7278",headerFont));
	            PdfPTable para2Table = new PdfPTable(1);
	            para2Table.setWidthPercentage(20);
	            para2Table.setHorizontalAlignment(Element.ALIGN_LEFT);
	            PdfPCell para2Cell1 = new PdfPCell(para2);
	            para2Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            para2Table.addCell(para2Cell1);
	              
	            String accountName = billingDetails.getAccountName();
	            BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
	            
	            Paragraph para3 = new Paragraph();
	            para3.add(new Chunk("BILL TO:",headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk(accountName,headerFont));
	            para3.add(new Phrase("\n"));
	            /*para3.add(new Chunk("ATTN: Gene Weaver",headerFont));
	            para3.add(new Phrase("\n"));*/
	            para3.add(new Chunk(billingAddressDetails.getBillAddrLine1()+", "+billingAddressDetails.getBillAddrLine2(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk(billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("Email: "+billingAddressDetails.getBillEmailId(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("Phone #: "+billingAddressDetails.getBillAddrPhone(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("Fax #: "+billingAddressDetails.getBillAddrFax(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("Terms: Net 10 days",headerFont));
	            PdfPTable para3Table = new PdfPTable(1);
	            para3Table.setWidthPercentage(27);
	            para3Table.setHorizontalAlignment(Element.ALIGN_LEFT);
	            PdfPCell para3Cell1 = new PdfPCell(para3);
	            para3Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            para3Table.addCell(para3Cell1);
	            
	            PdfPTable dataTable = new PdfPTable(9);
	            dataTable.setWidthPercentage(100);
	            PdfPCell cell1 = new PdfPCell(new Paragraph("Date",tableFont));
	            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell2 = new PdfPCell(new Paragraph("Unit #",tableFont));
	            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell3 = new PdfPCell(new Paragraph("Year",tableFont));
	            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell4 = new PdfPCell(new Paragraph("Make",tableFont));
	            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell5 = new PdfPCell(new Paragraph("Model",tableFont));
	            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell6 = new PdfPCell(new Paragraph("Location",tableFont));
	            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell7 = new PdfPCell(new Paragraph("Invoice #",tableFont));
	            cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell8 = new PdfPCell(new Paragraph("VIN",tableFont));
	            cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell9 = new PdfPCell(new Paragraph("Amount Owed",tableFont));
	            cell9.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            dataTable.addCell(cell1);
	            dataTable.addCell(cell2);
	            dataTable.addCell(cell3);
	            dataTable.addCell(cell4);
	            dataTable.addCell(cell5);
	            dataTable.addCell(cell6);
	            dataTable.addCell(cell7);
	            dataTable.addCell(cell8);
	            dataTable.addCell(cell9);
	            //dataTable.spacingBefore();
	            
	            for(int i=0;i<billingDetails.getBillingInfoDetailsList().size();i++){
	            	BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(i);
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getDate(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getCommUnitNo(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleYear(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleMake(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleModel(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getLocation(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getInvoiceId(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleVIN(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getAmountOwed(),tableFont)));
	            }
	            
	            PdfPTable duesTable = new PdfPTable(6);
	            duesTable.setWidthPercentage(75);
	            duesTable.setHorizontalAlignment(Element.ALIGN_CENTER);
	            PdfPCell cell11 = new PdfPCell(new Paragraph("Current",tableFont));
	            cell11.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell12 = new PdfPCell(new Paragraph("1-30 Days",tableFont));
	            cell12.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell13 = new PdfPCell(new Paragraph("31-60 Days",tableFont));
	            cell13.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell14 = new PdfPCell(new Paragraph("61-90 Days",tableFont));
	            cell14.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell15 = new PdfPCell(new Paragraph("90+ Days",tableFont));
	            cell15.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell16 = new PdfPCell(new Paragraph("Total Due",tableFont));
	            cell16.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            duesTable.addCell(cell11);
	            duesTable.addCell(cell12);
	            duesTable.addCell(cell13);
	            duesTable.addCell(cell14);
	            duesTable.addCell(cell15);
	            duesTable.addCell(cell16);
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getCurrentTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getThirtyDaysTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getSixtyDaysTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getNintyDaysTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getMoreThanNintyDaysTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getGrandTotal(),tableFont)));
	            
	            document.add(para1Table);
	            document.add(image);
	            document.add(new Phrase("\n"));
	            document.add(para2Table);
	            document.add(new Phrase("\n"));
	            document.add(para3Table);
	            document.add(new Phrase("\n"));
	            document.add(dataTable);
	            document.add(new Phrase("\n"));
	            document.add(new Phrase("\n"));
	            document.add(duesTable);
	            
				document.close();
			}
			else if(searchString.equalsIgnoreCase("allBillable")){
				Document document=new Document();
				PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
				document.open();
				
				for(int i=0;i<billingDetailsList.size();i++){
					BillingDetails billingDetails = new BillingDetails();
					billingDetails = billingDetailsList.get(i);
					if(billingDetails.getBillingInfoDetailsList().size()>0){
						Paragraph para1 = new Paragraph();
			            para1.setAlignment(Element.ALIGN_CENTER);
			            para1.add(new Chunk("Statement Of Account ",headerFont));
			            para1.add(new Phrase("\n"));
			            para1.add(new Chunk("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"),headerFont));
			            PdfPTable para1Table = new PdfPTable(1);
			            para1Table.setWidthPercentage(20);
			            PdfPCell para1Cell1 = new PdfPCell(para1);
			            para1Cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			            para1Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            para1Table.addCell(para1Cell1);            
			            
			            Image image = Image.getInstance(logoPath);
			            image.setAbsolutePosition(400f, 720f);
			            
			            Paragraph para2 = new Paragraph();
			            para2.add(new Chunk("ALL CITY TOWING",headerFont));
			            para2.add(new Phrase("\n"));
			            para2.add(new Chunk("2031 W. 1st St.",headerFont));
			            para2.add(new Phrase("\n"));
			            para2.add(new Chunk("Tempe, AZ 85281",headerFont));
			            para2.add(new Phrase("\n"));
			            para2.add(new Chunk("480.833.7278",headerFont));
			            PdfPTable para2Table = new PdfPTable(1);
			            para2Table.setWidthPercentage(20);
			            para2Table.setHorizontalAlignment(Element.ALIGN_LEFT);
			            PdfPCell para2Cell1 = new PdfPCell(para2);
			            para2Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            para2Table.addCell(para2Cell1);
			              
			            String accountName = billingDetails.getAccountName();
			            BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
			            
			            Paragraph para3 = new Paragraph();
			            para3.add(new Chunk("BILL TO:",headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk(accountName,headerFont));
			            para3.add(new Phrase("\n"));
			            /*para3.add(new Chunk("ATTN: Gene Weaver",headerFont));
			            para3.add(new Phrase("\n"));*/
			            para3.add(new Chunk(billingAddressDetails.getBillAddrLine1()+", "+billingAddressDetails.getBillAddrLine2(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk(billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("Email: "+billingAddressDetails.getBillEmailId(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("Phone #: "+billingAddressDetails.getBillAddrPhone(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("Fax #: "+billingAddressDetails.getBillAddrFax(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("Terms: Net 10 days",headerFont));
			            PdfPTable para3Table = new PdfPTable(1);
			            para3Table.setWidthPercentage(27);
			            para3Table.setHorizontalAlignment(Element.ALIGN_LEFT);
			            PdfPCell para3Cell1 = new PdfPCell(para3);
			            para3Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            para3Table.addCell(para3Cell1);
			            
			            PdfPTable dataTable = new PdfPTable(9);
			            dataTable.setWidthPercentage(100);
			            PdfPCell cell1 = new PdfPCell(new Paragraph("Date",tableFont));
			            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell2 = new PdfPCell(new Paragraph("Unit #",tableFont));
			            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell3 = new PdfPCell(new Paragraph("Year",tableFont));
			            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell4 = new PdfPCell(new Paragraph("Make",tableFont));
			            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell5 = new PdfPCell(new Paragraph("Model",tableFont));
			            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell6 = new PdfPCell(new Paragraph("Location",tableFont));
			            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell7 = new PdfPCell(new Paragraph("Invoice #",tableFont));
			            cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell8 = new PdfPCell(new Paragraph("VIN",tableFont));
			            cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell9 = new PdfPCell(new Paragraph("Amount Owed",tableFont));
			            cell9.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            dataTable.addCell(cell1);
			            dataTable.addCell(cell2);
			            dataTable.addCell(cell3);
			            dataTable.addCell(cell4);
			            dataTable.addCell(cell5);
			            dataTable.addCell(cell6);
			            dataTable.addCell(cell7);
			            dataTable.addCell(cell8);
			            dataTable.addCell(cell9);
			            //dataTable.spacingBefore();
			            
			            for(int j=0;j<billingDetails.getBillingInfoDetailsList().size();j++){
			            	BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(j);
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getDate(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getCommUnitNo(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleYear(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleMake(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleModel(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getLocation(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getInvoiceId(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleVIN(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getAmountOwed(),tableFont)));
			            }
			            
			            PdfPTable duesTable = new PdfPTable(6);
			            duesTable.setWidthPercentage(75);
			            duesTable.setHorizontalAlignment(Element.ALIGN_CENTER);
			            PdfPCell cell11 = new PdfPCell(new Paragraph("Current",tableFont));
			            cell11.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell12 = new PdfPCell(new Paragraph("1-30 Days",tableFont));
			            cell12.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell13 = new PdfPCell(new Paragraph("31-60 Days",tableFont));
			            cell13.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell14 = new PdfPCell(new Paragraph("61-90 Days",tableFont));
			            cell14.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell15 = new PdfPCell(new Paragraph("90+ Days",tableFont));
			            cell15.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell16 = new PdfPCell(new Paragraph("Total Due",tableFont));
			            cell16.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            duesTable.addCell(cell11);
			            duesTable.addCell(cell12);
			            duesTable.addCell(cell13);
			            duesTable.addCell(cell14);
			            duesTable.addCell(cell15);
			            duesTable.addCell(cell16);
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getCurrentTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getThirtyDaysTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getSixtyDaysTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getNintyDaysTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getMoreThanNintyDaysTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getGrandTotal(),tableFont)));
			            
			            document.add(para1Table);
			            document.add(image);
			            document.add(new Phrase("\n"));
			            document.add(para2Table);
			            document.add(new Phrase("\n"));
			            document.add(para3Table);
			            document.add(new Phrase("\n"));
			            document.add(dataTable);
			            document.add(new Phrase("\n"));
			            document.add(new Phrase("\n"));
			            document.add(duesTable);
			            
			            document.newPage();
					}		            
				}
				
				document.close();
			}
			
		}
		catch (FileNotFoundException e)
		{
			logger.error(e);
			pdfGen=false;
		}
		catch (DocumentException e)
		{
			logger.error(e);
			pdfGen=false;
		}
		catch (Exception e)
		{
			logger.error(e);
			e.printStackTrace();
			pdfGen=false;
		}
		return pdfGen;
	}
	
	public boolean generatePdfToSendStatement(String searchString, List<BillingDetails> billingDetailsList, List<String> pdfPaths, String logoPath)
	{
		boolean pdfGen=true;
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 9);
        Font tableFont = new Font(Font.FontFamily.HELVETICA, 8);
		try
		{
			if(searchString.equalsIgnoreCase("byAccount")){
				BillingDetails billingDetails = billingDetailsList.get(0);
				Document document=new Document();
				String pdfPath = pdfPaths.get(0);
				PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
				document.open();
				
				Paragraph para1 = new Paragraph();
	            para1.setAlignment(Element.ALIGN_CENTER);
	            para1.add(new Chunk("Statement Of Account ",headerFont));
	            para1.add(new Phrase("\n"));
	            para1.add(new Chunk("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"),headerFont));
	            PdfPTable para1Table = new PdfPTable(1);
	            para1Table.setWidthPercentage(20);
	            PdfPCell para1Cell1 = new PdfPCell(para1);
	            para1Cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            para1Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            para1Table.addCell(para1Cell1);            
	            
	            Image image = Image.getInstance(logoPath);
	            image.setAbsolutePosition(400f, 720f);
	            
	            Paragraph para2 = new Paragraph();
	            para2.add(new Chunk("ALL CITY TOWING",headerFont));
	            para2.add(new Phrase("\n"));
	            para2.add(new Chunk("2031 W. 1st St.",headerFont));
	            para2.add(new Phrase("\n"));
	            para2.add(new Chunk("Tempe, AZ 85281",headerFont));
	            para2.add(new Phrase("\n"));
	            para2.add(new Chunk("480.833.7278",headerFont));
	            PdfPTable para2Table = new PdfPTable(1);
	            para2Table.setWidthPercentage(20);
	            para2Table.setHorizontalAlignment(Element.ALIGN_LEFT);
	            PdfPCell para2Cell1 = new PdfPCell(para2);
	            para2Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            para2Table.addCell(para2Cell1);
	              
	            String accountName = billingDetails.getAccountName();
	            BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
	            
	            Paragraph para3 = new Paragraph();
	            para3.add(new Chunk("BILL TO:",headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk(accountName,headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("ATTN: Gene Weaver",headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk(billingAddressDetails.getBillAddrLine1()+", "+billingAddressDetails.getBillAddrLine2(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk(billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("Email: "+billingAddressDetails.getBillEmailId(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("Phone #: "+billingAddressDetails.getBillAddrPhone(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("Fax #: "+billingAddressDetails.getBillAddrFax(),headerFont));
	            para3.add(new Phrase("\n"));
	            para3.add(new Chunk("Terms: Net 10 days",headerFont));
	            PdfPTable para3Table = new PdfPTable(1);
	            para3Table.setWidthPercentage(27);
	            para3Table.setHorizontalAlignment(Element.ALIGN_LEFT);
	            PdfPCell para3Cell1 = new PdfPCell(para3);
	            para3Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            para3Table.addCell(para3Cell1);
	            
	            PdfPTable dataTable = new PdfPTable(9);
	            dataTable.setWidthPercentage(100);
	            PdfPCell cell1 = new PdfPCell(new Paragraph("Date",tableFont));
	            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell2 = new PdfPCell(new Paragraph("Unit #",tableFont));
	            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell3 = new PdfPCell(new Paragraph("Year",tableFont));
	            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell4 = new PdfPCell(new Paragraph("Make",tableFont));
	            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell5 = new PdfPCell(new Paragraph("Model",tableFont));
	            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell6 = new PdfPCell(new Paragraph("Location",tableFont));
	            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell7 = new PdfPCell(new Paragraph("Invoice #",tableFont));
	            cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell8 = new PdfPCell(new Paragraph("VIN",tableFont));
	            cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell9 = new PdfPCell(new Paragraph("Amount Owed",tableFont));
	            cell9.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            dataTable.addCell(cell1);
	            dataTable.addCell(cell2);
	            dataTable.addCell(cell3);
	            dataTable.addCell(cell4);
	            dataTable.addCell(cell5);
	            dataTable.addCell(cell6);
	            dataTable.addCell(cell7);
	            dataTable.addCell(cell8);
	            dataTable.addCell(cell9);
	            //dataTable.spacingBefore();
	            
	            for(int i=0;i<billingDetails.getBillingInfoDetailsList().size();i++){
	            	BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(i);
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getDate(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getCommUnitNo(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleYear(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleMake(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleModel(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getLocation(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getInvoiceId(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleVIN(),tableFont)));
	            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getAmountOwed(),tableFont)));
	            }
	            
	            PdfPTable duesTable = new PdfPTable(6);
	            duesTable.setWidthPercentage(75);
	            duesTable.setHorizontalAlignment(Element.ALIGN_CENTER);
	            PdfPCell cell11 = new PdfPCell(new Paragraph("Current",tableFont));
	            cell11.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell12 = new PdfPCell(new Paragraph("1-30 Days",tableFont));
	            cell12.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell13 = new PdfPCell(new Paragraph("31-60 Days",tableFont));
	            cell13.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell14 = new PdfPCell(new Paragraph("61-90 Days",tableFont));
	            cell14.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell15 = new PdfPCell(new Paragraph("90+ Days",tableFont));
	            cell15.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            PdfPCell cell16 = new PdfPCell(new Paragraph("Total Due",tableFont));
	            cell16.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            duesTable.addCell(cell11);
	            duesTable.addCell(cell12);
	            duesTable.addCell(cell13);
	            duesTable.addCell(cell14);
	            duesTable.addCell(cell15);
	            duesTable.addCell(cell16);
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getCurrentTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getThirtyDaysTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getSixtyDaysTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getNintyDaysTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getMoreThanNintyDaysTotal(),tableFont)));
	            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getGrandTotal(),tableFont)));
	            
	            document.add(para1Table);
	            document.add(image);
	            document.add(new Phrase("\n"));
	            document.add(para2Table);
	            document.add(new Phrase("\n"));
	            document.add(para3Table);
	            document.add(new Phrase("\n"));
	            document.add(dataTable);
	            document.add(new Phrase("\n"));
	            document.add(new Phrase("\n"));
	            document.add(duesTable);
	            
				document.close();
			}
			else if(searchString.equalsIgnoreCase("allBillable")){					
				for(int i=0;i<billingDetailsList.size();i++){
					BillingDetails billingDetails = new BillingDetails();
					billingDetails = billingDetailsList.get(i);					
					if(billingDetails.getBillingInfoDetailsList().size()>0){
						String pdfPath = pdfPaths.get(i);
						logger.info("pdfPath = "+pdfPath);
						Document document=new Document();						
						PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
						document.open();
						
						Paragraph para1 = new Paragraph();
			            para1.setAlignment(Element.ALIGN_CENTER);
			            para1.add(new Chunk("Statement Of Account ",headerFont));
			            para1.add(new Phrase("\n"));
			            para1.add(new Chunk("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"),headerFont));
			            PdfPTable para1Table = new PdfPTable(1);
			            para1Table.setWidthPercentage(20);
			            PdfPCell para1Cell1 = new PdfPCell(para1);
			            para1Cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			            para1Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            para1Table.addCell(para1Cell1);            
			            
			            Image image = Image.getInstance(logoPath);
			            image.setAbsolutePosition(400f, 720f);
			            
			            Paragraph para2 = new Paragraph();
			            para2.add(new Chunk("ALL CITY TOWING",headerFont));
			            para2.add(new Phrase("\n"));
			            para2.add(new Chunk("2031 W. 1st St.",headerFont));
			            para2.add(new Phrase("\n"));
			            para2.add(new Chunk("Tempe, AZ 85281",headerFont));
			            para2.add(new Phrase("\n"));
			            para2.add(new Chunk("480.833.7278",headerFont));
			            PdfPTable para2Table = new PdfPTable(1);
			            para2Table.setWidthPercentage(20);
			            para2Table.setHorizontalAlignment(Element.ALIGN_LEFT);
			            PdfPCell para2Cell1 = new PdfPCell(para2);
			            para2Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            para2Table.addCell(para2Cell1);
			              
			            String accountName = billingDetails.getAccountName();
			            BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
			            
			            Paragraph para3 = new Paragraph();
			            para3.add(new Chunk("BILL TO:",headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk(accountName,headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("ATTN: Gene Weaver",headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk(billingAddressDetails.getBillAddrLine1()+", "+billingAddressDetails.getBillAddrLine2(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk(billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("Email: "+billingAddressDetails.getBillEmailId(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("Phone #: "+billingAddressDetails.getBillAddrPhone(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("Fax #: "+billingAddressDetails.getBillAddrFax(),headerFont));
			            para3.add(new Phrase("\n"));
			            para3.add(new Chunk("Terms: Net 10 days",headerFont));
			            PdfPTable para3Table = new PdfPTable(1);
			            para3Table.setWidthPercentage(27);
			            para3Table.setHorizontalAlignment(Element.ALIGN_LEFT);
			            PdfPCell para3Cell1 = new PdfPCell(para3);
			            para3Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            para3Table.addCell(para3Cell1);
			            
			            PdfPTable dataTable = new PdfPTable(9);
			            dataTable.setWidthPercentage(100);
			            PdfPCell cell1 = new PdfPCell(new Paragraph("Date",tableFont));
			            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell2 = new PdfPCell(new Paragraph("Unit #",tableFont));
			            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell3 = new PdfPCell(new Paragraph("Year",tableFont));
			            cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell4 = new PdfPCell(new Paragraph("Make",tableFont));
			            cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell5 = new PdfPCell(new Paragraph("Model",tableFont));
			            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell6 = new PdfPCell(new Paragraph("Location",tableFont));
			            cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell7 = new PdfPCell(new Paragraph("Invoice #",tableFont));
			            cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell8 = new PdfPCell(new Paragraph("VIN",tableFont));
			            cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell9 = new PdfPCell(new Paragraph("Amount Owed",tableFont));
			            cell9.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            dataTable.addCell(cell1);
			            dataTable.addCell(cell2);
			            dataTable.addCell(cell3);
			            dataTable.addCell(cell4);
			            dataTable.addCell(cell5);
			            dataTable.addCell(cell6);
			            dataTable.addCell(cell7);
			            dataTable.addCell(cell8);
			            dataTable.addCell(cell9);
			            //dataTable.spacingBefore();
			            
			            for(int j=0;j<billingDetails.getBillingInfoDetailsList().size();j++){
			            	BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(j);
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getDate(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getCommUnitNo(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleYear(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleMake(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleModel(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getLocation(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getInvoiceId(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleVIN(),tableFont)));
			            	dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getAmountOwed(),tableFont)));
			            }
			            
			            PdfPTable duesTable = new PdfPTable(6);
			            duesTable.setWidthPercentage(75);
			            duesTable.setHorizontalAlignment(Element.ALIGN_CENTER);
			            PdfPCell cell11 = new PdfPCell(new Paragraph("Current",tableFont));
			            cell11.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell12 = new PdfPCell(new Paragraph("1-30 Days",tableFont));
			            cell12.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell13 = new PdfPCell(new Paragraph("31-60 Days",tableFont));
			            cell13.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell14 = new PdfPCell(new Paragraph("61-90 Days",tableFont));
			            cell14.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell15 = new PdfPCell(new Paragraph("90+ Days",tableFont));
			            cell15.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            PdfPCell cell16 = new PdfPCell(new Paragraph("Total Due",tableFont));
			            cell16.setBackgroundColor(BaseColor.LIGHT_GRAY);
			            duesTable.addCell(cell11);
			            duesTable.addCell(cell12);
			            duesTable.addCell(cell13);
			            duesTable.addCell(cell14);
			            duesTable.addCell(cell15);
			            duesTable.addCell(cell16);
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getCurrentTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getThirtyDaysTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getSixtyDaysTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getNintyDaysTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getMoreThanNintyDaysTotal(),tableFont)));
			            duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getGrandTotal(),tableFont)));
			            
			            document.add(para1Table);
			            document.add(image);
			            document.add(new Phrase("\n"));
			            document.add(para2Table);
			            document.add(new Phrase("\n"));
			            document.add(para3Table);
			            document.add(new Phrase("\n"));
			            document.add(dataTable);
			            document.add(new Phrase("\n"));
			            document.add(new Phrase("\n"));
			            document.add(duesTable);
			            
			            document.close();
					}
				}				
			}
			
		}
		catch (FileNotFoundException e)
		{
			logger.error(e);
			pdfGen=false;
		}
		catch (DocumentException e)
		{
			logger.error(e);
			e.printStackTrace();
			pdfGen=false;
		}
		catch (Exception e)
		{
			logger.error(e);
			e.printStackTrace();
			pdfGen=false;
		}
		return pdfGen;
	}
	
	public boolean generatePdfToSendStatement(BillingDetails billingDetails, String pdfPath, String logoPath)
	{
		boolean pdfGen=true;
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 9);
        Font tableFont = new Font(Font.FontFamily.HELVETICA, 8);
        try
        {
        	Document document=new Document();
        	PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        	document.open();

        	Paragraph para1 = new Paragraph();
        	para1.setAlignment(Element.ALIGN_CENTER);
        	para1.add(new Chunk("Statement Of Account ",headerFont));
        	para1.add(new Phrase("\n"));
        	para1.add(new Chunk("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"),headerFont));
        	PdfPTable para1Table = new PdfPTable(1);
        	para1Table.setWidthPercentage(20);
        	PdfPCell para1Cell1 = new PdfPCell(para1);
        	para1Cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        	para1Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	para1Table.addCell(para1Cell1);            

        	Image image = Image.getInstance(logoPath);
        	image.setAbsolutePosition(400f, 720f);

        	Paragraph para2 = new Paragraph();
        	para2.add(new Chunk("ALL CITY TOWING",headerFont));
        	para2.add(new Phrase("\n"));
        	para2.add(new Chunk("2031 W. 1st St.",headerFont));
        	para2.add(new Phrase("\n"));
        	para2.add(new Chunk("Tempe, AZ 85281",headerFont));
        	para2.add(new Phrase("\n"));
        	para2.add(new Chunk("480.833.7278",headerFont));
        	PdfPTable para2Table = new PdfPTable(1);
        	para2Table.setWidthPercentage(20);
        	para2Table.setHorizontalAlignment(Element.ALIGN_LEFT);
        	PdfPCell para2Cell1 = new PdfPCell(para2);
        	para2Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	para2Table.addCell(para2Cell1);

        	String accountName = billingDetails.getAccountName();
        	BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);

        	Paragraph para3 = new Paragraph();
        	para3.add(new Chunk("BILL TO:",headerFont));
        	para3.add(new Phrase("\n"));
        	para3.add(new Chunk(accountName,headerFont));
        	para3.add(new Phrase("\n"));
        	/*para3.add(new Chunk("ATTN: Gene Weaver",headerFont));
	            para3.add(new Phrase("\n"));*/
        	para3.add(new Chunk(billingAddressDetails.getBillAddrLine1()+", "+billingAddressDetails.getBillAddrLine2(),headerFont));
        	para3.add(new Phrase("\n"));
        	para3.add(new Chunk(billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip(),headerFont));
        	para3.add(new Phrase("\n"));
        	para3.add(new Phrase("\n"));
        	para3.add(new Chunk("Email: "+billingAddressDetails.getBillEmailId(),headerFont));
        	para3.add(new Phrase("\n"));
        	para3.add(new Chunk("Phone #: "+billingAddressDetails.getBillAddrPhone(),headerFont));
        	para3.add(new Phrase("\n"));
        	para3.add(new Chunk("Fax #: "+billingAddressDetails.getBillAddrFax(),headerFont));
        	para3.add(new Phrase("\n"));
        	para3.add(new Chunk("Terms: Net 10 days",headerFont));
        	PdfPTable para3Table = new PdfPTable(1);
        	para3Table.setWidthPercentage(27);
        	para3Table.setHorizontalAlignment(Element.ALIGN_LEFT);
        	PdfPCell para3Cell1 = new PdfPCell(para3);
        	para3Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	para3Table.addCell(para3Cell1);

        	PdfPTable dataTable = new PdfPTable(9);
        	dataTable.setWidthPercentage(100);
        	PdfPCell cell1 = new PdfPCell(new Paragraph("Date",tableFont));
        	cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell2 = new PdfPCell(new Paragraph("Unit #",tableFont));
        	cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell3 = new PdfPCell(new Paragraph("Year",tableFont));
        	cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell4 = new PdfPCell(new Paragraph("Make",tableFont));
        	cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell5 = new PdfPCell(new Paragraph("Model",tableFont));
        	cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell6 = new PdfPCell(new Paragraph("Location",tableFont));
        	cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell7 = new PdfPCell(new Paragraph("Invoice #",tableFont));
        	cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell8 = new PdfPCell(new Paragraph("VIN",tableFont));
        	cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell9 = new PdfPCell(new Paragraph("Amount Owed",tableFont));
        	cell9.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	dataTable.addCell(cell1);
        	dataTable.addCell(cell2);
        	dataTable.addCell(cell3);
        	dataTable.addCell(cell4);
        	dataTable.addCell(cell5);
        	dataTable.addCell(cell6);
        	dataTable.addCell(cell7);
        	dataTable.addCell(cell8);
        	dataTable.addCell(cell9);
        	//dataTable.spacingBefore();

        	for(int i=0;i<billingDetails.getBillingInfoDetailsList().size();i++){
        		BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(i);
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getDate(),tableFont)));
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getCommUnitNo(),tableFont)));
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleYear(),tableFont)));
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleMake(),tableFont)));
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleModel(),tableFont)));
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getLocation(),tableFont)));
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getInvoiceId(),tableFont)));
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getVehicleVIN(),tableFont)));
        		dataTable.addCell(new PdfPCell(new Paragraph(billingInfoDetails.getAmountOwed(),tableFont)));
        	}

        	PdfPTable duesTable = new PdfPTable(6);
        	duesTable.setWidthPercentage(75);
        	duesTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        	PdfPCell cell11 = new PdfPCell(new Paragraph("Current",tableFont));
        	cell11.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell12 = new PdfPCell(new Paragraph("1-30 Days",tableFont));
        	cell12.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell13 = new PdfPCell(new Paragraph("31-60 Days",tableFont));
        	cell13.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell14 = new PdfPCell(new Paragraph("61-90 Days",tableFont));
        	cell14.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell15 = new PdfPCell(new Paragraph("90+ Days",tableFont));
        	cell15.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	PdfPCell cell16 = new PdfPCell(new Paragraph("Total Due",tableFont));
        	cell16.setBackgroundColor(BaseColor.LIGHT_GRAY);
        	duesTable.addCell(cell11);
        	duesTable.addCell(cell12);
        	duesTable.addCell(cell13);
        	duesTable.addCell(cell14);
        	duesTable.addCell(cell15);
        	duesTable.addCell(cell16);
        	duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getCurrentTotal(),tableFont)));
        	duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getThirtyDaysTotal(),tableFont)));
        	duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getSixtyDaysTotal(),tableFont)));
        	duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getNintyDaysTotal(),tableFont)));
        	duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getMoreThanNintyDaysTotal(),tableFont)));
        	duesTable.addCell(new PdfPCell(new Paragraph(billingDetails.getGrandTotal(),tableFont)));

        	document.add(para1Table);
        	document.add(image);
        	document.add(new Phrase("\n"));
        	document.add(para2Table);
        	document.add(new Phrase("\n"));
        	document.add(para3Table);
        	document.add(new Phrase("\n"));
        	document.add(dataTable);
        	document.add(new Phrase("\n"));
        	document.add(new Phrase("\n"));
        	document.add(duesTable);

        	document.close();
        }
		catch (FileNotFoundException e)
		{
			logger.error(e);
			pdfGen=false;
		}
		catch (DocumentException e)
		{
			logger.error(e);
			e.printStackTrace();
			pdfGen=false;
		}
		catch (Exception e)
		{
			logger.error(e);
			e.printStackTrace();
			pdfGen=false;
		}
		return pdfGen;
	}
	
	public boolean generateNoDataPDF(String accountName, String pdfPath, String logoPath)
	{
		boolean pdfGen=true;
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 9);
        Font tableFont = new Font(Font.FontFamily.HELVETICA, 11);
		try
		{
			Document document=new Document();
			PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
			document.open();
				
			Paragraph para1 = new Paragraph();
			para1.setAlignment(Element.ALIGN_CENTER);
			para1.add(new Chunk("Statement Of Account ",headerFont));
			para1.add(new Phrase("\n"));
			para1.add(new Chunk("Date: "+DateUtility.getCurrentDate("MM/dd/yyyy"),headerFont));
			PdfPTable para1Table = new PdfPTable(1);
			para1Table.setWidthPercentage(20);
			PdfPCell para1Cell1 = new PdfPCell(para1);
			para1Cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			para1Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			para1Table.addCell(para1Cell1);            
	            
			Image image = Image.getInstance(logoPath);
			image.setAbsolutePosition(400f, 720f);
	            
			Paragraph para2 = new Paragraph();
			para2.add(new Chunk("ALL CITY TOWING",headerFont));
			para2.add(new Phrase("\n"));
			para2.add(new Chunk("2031 W. 1st St.",headerFont));
			para2.add(new Phrase("\n"));
			para2.add(new Chunk("Tempe, AZ 85281",headerFont));
			para2.add(new Phrase("\n"));
			para2.add(new Chunk("480.833.7278",headerFont));
			PdfPTable para2Table = new PdfPTable(1);
			para2Table.setWidthPercentage(20);
			para2Table.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell para2Cell1 = new PdfPCell(para2);
			para2Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			para2Table.addCell(para2Cell1);
	              
			BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
	            
			Paragraph para3 = new Paragraph();
			para3.add(new Chunk("BILL TO:",headerFont));
			para3.add(new Phrase("\n"));
			para3.add(new Chunk(accountName,headerFont));
			para3.add(new Phrase("\n"));
			/*para3.add(new Chunk("ATTN: Gene Weaver",headerFont));
	        para3.add(new Phrase("\n"));*/
			para3.add(new Chunk(billingAddressDetails.getBillAddrLine1()+", "+billingAddressDetails.getBillAddrLine2(),headerFont));
			para3.add(new Phrase("\n"));
			para3.add(new Chunk(billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip(),headerFont));
			para3.add(new Phrase("\n"));
			para3.add(new Phrase("\n"));
			para3.add(new Chunk("Email: "+billingAddressDetails.getBillEmailId(),headerFont));
			para3.add(new Phrase("\n"));
			para3.add(new Chunk("Phone #: "+billingAddressDetails.getBillAddrPhone(),headerFont));
			para3.add(new Phrase("\n"));
			para3.add(new Chunk("Fax #: "+billingAddressDetails.getBillAddrFax(),headerFont));
			para3.add(new Phrase("\n"));
			para3.add(new Chunk("Terms: Net 10 days",headerFont));
			PdfPTable para3Table = new PdfPTable(1);
			para3Table.setWidthPercentage(27);
			para3Table.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell para3Cell1 = new PdfPCell(para3);
			para3Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			para3Table.addCell(para3Cell1);
	            
			PdfPTable dataTable = new PdfPTable(1);
			dataTable.setWidthPercentage(100);
			dataTable.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell1 = new PdfPCell(new Paragraph("No data found for this Account.",tableFont));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			dataTable.addCell(cell1);
	            
			document.add(para1Table);
			document.add(image);
			document.add(new Phrase("\n"));
			document.add(para2Table);
			document.add(new Phrase("\n"));
			document.add(para3Table);
			document.add(new Phrase("\n"));
			document.add(dataTable);
	        	            
			document.close();			
		}
		catch (FileNotFoundException e)
		{
			logger.error(e);
			pdfGen=false;
		}
		catch (DocumentException e)
		{
			logger.error(e);
			pdfGen=false;
		}
		catch (Exception e)
		{
			logger.error(e);
			e.printStackTrace();
			pdfGen=false;
		}
		return pdfGen;
	}	
}