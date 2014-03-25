package act.reports.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.BillingDetailsDAO;
import act.reports.model.BillingAddressDetails;
import act.reports.model.BillingDetails;
import act.reports.model.BillingInfoDetails;
import act.reports.util.DateUtility;

@Service("excelGenerator")
public class ExcelStatementGenerator
{	
	private Logger logger=Logger.getLogger(ExcelStatementGenerator.class);
	
	@Autowired
	BillingDetailsDAO billingDetailsDAO;
	
	public boolean viewBillingStatementExcel(String searchString, List<BillingDetails> billingDetailsList, String filePath, String logoPath)
	{
		boolean fileGen=true;
		
		try{
			if(searchString.equalsIgnoreCase("byAccount")){
				BillingDetails billingDetails = billingDetailsList.get(0);
				String accountName = billingDetails.getAccountName();
	            HSSFWorkbook workbook = new HSSFWorkbook();
	        	HSSFSheet sheet = workbook.createSheet(accountName);
	        	sheet.setDefaultColumnWidth(15);
	        	
	        	// create style for main header cells
			    CellStyle style = workbook.createCellStyle();
			    Font font = workbook.createFont();
			    font.setFontName("Arial");
			    style.setFillForegroundColor(HSSFColor.BLUE.index);
			    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    font.setColor(HSSFColor.WHITE.index);
			    style.setFont(font);
			    
			    // create style for header cells
			    CellStyle style1 = workbook.createCellStyle();
			    Font font1 = workbook.createFont();
			    font1.setFontName("Arial");
			    style1.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
			    style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
			    font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    font1.setColor(HSSFColor.WHITE.index);
			    style1.setFont(font1);
			    
			    // create header0 row
			    HSSFRow header0 = sheet.createRow(0);
			    header0.createCell(2).setCellValue("Statement Of Account");
			    header0.getCell(2).setCellStyle(style);
			    
			    // create header1 row
			    HSSFRow header1 = sheet.createRow(1);
			    header1.createCell(2).setCellValue("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"));
			    header1.getCell(2).setCellStyle(style);
			    
			    // create main address
			    Row addressRow1 = sheet.createRow(3);
			    addressRow1.createCell(0).setCellValue("ALL CITY TOWING");
			    Row addressRow2 = sheet.createRow(4);
			    addressRow2.createCell(0).setCellValue("2031 W. 1st St.");
			    Row addressRow3 = sheet.createRow(5);
			    addressRow3.createCell(0).setCellValue("Tempe, AZ 85281");
			    Row addressRow4 = sheet.createRow(6);
			    addressRow4.createCell(0).setCellValue("480.833.7278");
			    
			    BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
			    
			    // create account address
			    Row addressRow5 = sheet.createRow(8);
			    addressRow5.createCell(0).setCellValue("BILL TO: "+accountName);
			    Row addressRow6 = sheet.createRow(9);
			    addressRow6.createCell(0).setCellValue(billingAddressDetails.getBillAddrLine1()+" "+billingAddressDetails.getBillAddrLine2()+" "+billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip());
			    Row addressRow7 = sheet.createRow(10);
			    addressRow7.createCell(0).setCellValue("Email: "+billingAddressDetails.getBillEmailId());
			    Row addressRow8 = sheet.createRow(11);
			    addressRow8.createCell(0).setCellValue("Phone #: "+billingAddressDetails.getBillAddrPhone());
			    Row addressRow9 = sheet.createRow(12);
			    addressRow9.createCell(0).setCellValue("Fax #: "+billingAddressDetails.getBillAddrFax());
			    Row addressRow10 = sheet.createRow(13);
			    addressRow10.createCell(0).setCellValue("Terms: ");
			    
			    InputStream inputStream = new FileInputStream(logoPath);
			    byte[] bytes = IOUtils.toByteArray(inputStream);
			    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			    inputStream.close();
			    CreationHelper helper = workbook.getCreationHelper();
			    Drawing drawing = sheet.createDrawingPatriarch();
			    ClientAnchor anchor = helper.createClientAnchor();
			    anchor.setCol1(4);
			    anchor.setRow1(3);
			    Picture picture = drawing.createPicture(anchor, pictureIdx);
			    picture.resize();
			    //picture.setLineStyle(HSSFPicture.LINESTYLE_DASHDOTGEL);
	        	
			    // create data row headers
			    HSSFRow header2 = sheet.createRow(15);
			    header2.createCell(0).setCellValue("Date");
			    header2.getCell(0).setCellStyle(style);
			    header2.createCell(1).setCellValue("Unit #");
			    header2.getCell(1).setCellStyle(style);
			    header2.createCell(2).setCellValue("Year");
			    header2.getCell(2).setCellStyle(style);
			    header2.createCell(3).setCellValue("Make");
			    header2.getCell(3).setCellStyle(style);
			    header2.createCell(4).setCellValue("Model");
			    header2.getCell(4).setCellStyle(style);
			    header2.createCell(5).setCellValue("Location");
			    header2.getCell(5).setCellStyle(style);
			    header2.createCell(6).setCellValue("Invoice #");
			    header2.getCell(6).setCellStyle(style);
			    header2.createCell(7).setCellValue("VIN");
			    header2.getCell(7).setCellStyle(style);
			    		    
	        	Map<String, Object[]> data1 = new HashMap<String, Object[]>();
	        	for(int i=0;i<billingDetails.getBillingInfoDetailsList().size();i++){
	        		BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(i);
	        		data1.put(Integer.toString(i), new Object[] {billingInfoDetails.getDate(), billingInfoDetails.getCommUnitNo(), billingInfoDetails.getVehicleYear(), billingInfoDetails.getVehicleMake(), billingInfoDetails.getVehicleModel(), billingInfoDetails.getLocation(), billingInfoDetails.getInvoiceId(), billingInfoDetails.getVehicleVIN()});
	        	}
	        	 
	        	Set<String> keyset = data1.keySet();
	        	int rownum = 16;
	        	for (String key : keyset) {
	        	    Row row = sheet.createRow(rownum++);
	        	    Object [] objArr = data1.get(key);
	        	    int cellnum = 0;
	        	    for (Object obj : objArr) {
	        	        Cell cell = row.createCell(cellnum++);
	        	        cell.setCellValue((String)obj);        	        
	        	    }
	        	}
	        	
	        	rownum = rownum+2;
	        	// create data row headers
			    HSSFRow header3 = sheet.createRow(rownum);
			    header3.createCell(1).setCellValue("Current");
			    header3.getCell(1).setCellStyle(style);
			    header3.createCell(2).setCellValue("1-30 Days");
			    header3.getCell(2).setCellStyle(style);
			    header3.createCell(3).setCellValue("31-60 Days");
			    header3.getCell(3).setCellStyle(style);
			    header3.createCell(4).setCellValue("61-90 Days");
			    header3.getCell(4).setCellStyle(style);
			    header3.createCell(5).setCellValue("90+ Days");
			    header3.getCell(5).setCellStyle(style);
			    header3.createCell(6).setCellValue("Total Due");
			    header3.getCell(6).setCellStyle(style);
			    
			    Map<String, Object[]> data2 = new HashMap<String, Object[]>();
			    data2.put("1", new Object[] {billingDetails.getCurrentTotal(), billingDetails.getThirtyDaysTotal(), billingDetails.getSixtyDaysTotal(), billingDetails.getNintyDaysTotal(), billingDetails.getMoreThanNintyDaysTotal(), billingDetails.getGrandTotal()});
	        	 
			    Set<String> keyset1 = data2.keySet();
	        	int rownum1 = rownum+1;
	        	for (String key : keyset1) {
	        	    Row row = sheet.createRow(rownum1++);
	        	    Object [] objArr = data2.get(key);
	        	    int cellnum = 1;
	        	    for (Object obj : objArr) {
	        	        Cell cell = row.createCell(cellnum++);
	        	        cell.setCellValue((String)obj);        	        
	        	    }
	        	}
			    
	        	try{
	        	    FileOutputStream out = new FileOutputStream(new File(filePath));
	        	    workbook.write(out);
	        	    out.close();
	        	    logger.info("Excel written successfully.");
	        	     
	        	}catch(FileNotFoundException e){
	        		logger.error(e);
	        	    e.printStackTrace();
	        	    fileGen=false;
	        	}catch (IOException e){
	        		logger.error(e);
	        	    e.printStackTrace();
	        	    fileGen=false;
	        	}
			}
			else if(searchString.equalsIgnoreCase("allBillable")){
				HSSFWorkbook workbook = new HSSFWorkbook();
				for(int i=0;i<billingDetailsList.size();i++){
					BillingDetails billingDetails = billingDetailsList.get(i);
					String accountName = billingDetails.getAccountName();
					HSSFSheet sheet = workbook.createSheet(accountName);
		        	sheet.setDefaultColumnWidth(15);
		        	
		        	// create style for main header cells
				    CellStyle style = workbook.createCellStyle();
				    Font font = workbook.createFont();
				    font.setFontName("Arial");
				    style.setFillForegroundColor(HSSFColor.BLUE.index);
				    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				    font.setColor(HSSFColor.WHITE.index);
				    style.setFont(font);
				    
				    // create style for header cells
				    CellStyle style1 = workbook.createCellStyle();
				    Font font1 = workbook.createFont();
				    font1.setFontName("Arial");
				    style1.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
				    style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
				    font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				    font1.setColor(HSSFColor.WHITE.index);
				    style1.setFont(font1);
				    
				    // create header0 row
				    HSSFRow header0 = sheet.createRow(0);
				    header0.createCell(2).setCellValue("Statement Of Account");
				    header0.getCell(2).setCellStyle(style);
				    
				    // create header1 row
				    HSSFRow header1 = sheet.createRow(1);
				    header1.createCell(2).setCellValue("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"));
				    header1.getCell(2).setCellStyle(style);
				    
				    // create main address
				    Row addressRow1 = sheet.createRow(3);
				    addressRow1.createCell(0).setCellValue("ALL CITY TOWING");
				    Row addressRow2 = sheet.createRow(4);
				    addressRow2.createCell(0).setCellValue("2031 W. 1st St.");
				    Row addressRow3 = sheet.createRow(5);
				    addressRow3.createCell(0).setCellValue("Tempe, AZ 85281");
				    Row addressRow4 = sheet.createRow(6);
				    addressRow4.createCell(0).setCellValue("480.833.7278");
				    
				    BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
				    
				    // create account address
				    Row addressRow5 = sheet.createRow(8);
				    addressRow5.createCell(0).setCellValue("BILL TO: "+accountName);
				    Row addressRow6 = sheet.createRow(9);
				    addressRow6.createCell(0).setCellValue(billingAddressDetails.getBillAddrLine1()+" "+billingAddressDetails.getBillAddrLine2()+" "+billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip());
				    Row addressRow7 = sheet.createRow(10);
				    addressRow7.createCell(0).setCellValue("Email: "+billingAddressDetails.getBillEmailId());
				    Row addressRow8 = sheet.createRow(11);
				    addressRow8.createCell(0).setCellValue("Phone #: "+billingAddressDetails.getBillAddrPhone());
				    Row addressRow9 = sheet.createRow(12);
				    addressRow9.createCell(0).setCellValue("Fax #: "+billingAddressDetails.getBillAddrFax());
				    Row addressRow10 = sheet.createRow(13);
				    addressRow10.createCell(0).setCellValue("Terms: ");
				    
				    InputStream inputStream = new FileInputStream(logoPath);
				    byte[] bytes = IOUtils.toByteArray(inputStream);
				    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
				    inputStream.close();
				    CreationHelper helper = workbook.getCreationHelper();
				    Drawing drawing = sheet.createDrawingPatriarch();
				    ClientAnchor anchor = helper.createClientAnchor();
				    anchor.setCol1(4);
				    anchor.setRow1(3);
				    Picture picture = drawing.createPicture(anchor, pictureIdx);
				    picture.resize();
				    //picture.setLineStyle(HSSFPicture.LINESTYLE_DASHDOTGEL);
		        	
				    // create data row headers
				    HSSFRow header2 = sheet.createRow(15);
				    header2.createCell(0).setCellValue("Date");
				    header2.getCell(0).setCellStyle(style);
				    header2.createCell(1).setCellValue("Unit #");
				    header2.getCell(1).setCellStyle(style);
				    header2.createCell(2).setCellValue("Year");
				    header2.getCell(2).setCellStyle(style);
				    header2.createCell(3).setCellValue("Make");
				    header2.getCell(3).setCellStyle(style);
				    header2.createCell(4).setCellValue("Model");
				    header2.getCell(4).setCellStyle(style);
				    header2.createCell(5).setCellValue("Location");
				    header2.getCell(5).setCellStyle(style);
				    header2.createCell(6).setCellValue("Invoice #");
				    header2.getCell(6).setCellStyle(style);
				    header2.createCell(7).setCellValue("VIN");
				    header2.getCell(7).setCellStyle(style);
				    		    
		        	Map<String, Object[]> data1 = new HashMap<String, Object[]>();
		        	for(int j=0;j<billingDetails.getBillingInfoDetailsList().size();j++){
		        		BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(j);
		        		data1.put(Integer.toString(j), new Object[] {billingInfoDetails.getDate(), billingInfoDetails.getCommUnitNo(), billingInfoDetails.getVehicleYear(), billingInfoDetails.getVehicleMake(), billingInfoDetails.getVehicleModel(), billingInfoDetails.getLocation(), billingInfoDetails.getInvoiceId(), billingInfoDetails.getVehicleVIN()});
		        	}
		        	 
		        	Set<String> keyset = data1.keySet();
		        	int rownum = 16;
		        	for (String key : keyset) {
		        	    Row row = sheet.createRow(rownum++);
		        	    Object [] objArr = data1.get(key);
		        	    int cellnum = 0;
		        	    for (Object obj : objArr) {
		        	        Cell cell = row.createCell(cellnum++);
		        	        cell.setCellValue((String)obj);        	        
		        	    }
		        	}
		        	
		        	rownum = rownum+2;
		        	// create data row headers
				    HSSFRow header3 = sheet.createRow(rownum);
				    header3.createCell(1).setCellValue("Current");
				    header3.getCell(1).setCellStyle(style);
				    header3.createCell(2).setCellValue("1-30 Days");
				    header3.getCell(2).setCellStyle(style);
				    header3.createCell(3).setCellValue("31-60 Days");
				    header3.getCell(3).setCellStyle(style);
				    header3.createCell(4).setCellValue("61-90 Days");
				    header3.getCell(4).setCellStyle(style);
				    header3.createCell(5).setCellValue("90+ Days");
				    header3.getCell(5).setCellStyle(style);
				    header3.createCell(6).setCellValue("Total Due");
				    header3.getCell(6).setCellStyle(style);
				    
				    Map<String, Object[]> data2 = new HashMap<String, Object[]>();
				    data2.put("1", new Object[] {billingDetails.getCurrentTotal(), billingDetails.getThirtyDaysTotal(), billingDetails.getSixtyDaysTotal(), billingDetails.getNintyDaysTotal(), billingDetails.getMoreThanNintyDaysTotal(), billingDetails.getGrandTotal()});
				    
				    Set<String> keyset1 = data2.keySet();
		        	int rownum1 = rownum+1;
		        	for (String key : keyset1) {
		        	    Row row = sheet.createRow(rownum1++);
		        	    Object [] objArr = data2.get(key);
		        	    int cellnum = 1;
		        	    for (Object obj : objArr) {
		        	        Cell cell = row.createCell(cellnum++);
		        	        cell.setCellValue((String)obj);        	        
		        	    }
		        	}
				}				
				try{
	        	    FileOutputStream out = new FileOutputStream(new File(filePath));
	        	    workbook.write(out);
	        	    out.close();
	        	    logger.info("Excel written successfully.");
	        	     
	        	}catch(FileNotFoundException e){
	        		logger.error(e);
	        	    e.printStackTrace();
	        	    fileGen=false;
	        	}catch (IOException e){
	        		logger.error(e);
	        	    e.printStackTrace();
	        	    fileGen=false;
	        	}		        	
			}
				
        }catch (Exception e){
        	logger.error(e);
            e.printStackTrace();
            fileGen=false;
        }
		return fileGen;
    }
	
	public boolean generateNoDataExcel(String filePath, String logoPath)
	{
		boolean fileGen=true;		
		try{
			HSSFWorkbook workbook = new HSSFWorkbook();
        	HSSFSheet sheet = workbook.createSheet("Sheet1");
        	sheet.setDefaultColumnWidth(15);
        	
        	// create style for main header cells
		    CellStyle style = workbook.createCellStyle();
		    Font font = workbook.createFont();
		    font.setFontName("Arial");
		    style.setFillForegroundColor(HSSFColor.BLUE.index);
		    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    font.setColor(HSSFColor.WHITE.index);
		    style.setFont(font);
		    
		    // create style for header cells
		    CellStyle style1 = workbook.createCellStyle();
		    Font font1 = workbook.createFont();
		    font1.setFontName("Arial");
		    style1.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		    style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    font1.setColor(HSSFColor.WHITE.index);
		    style1.setFont(font1);
		    
		    // create header0 row
		    HSSFRow header0 = sheet.createRow(0);
		    header0.createCell(2).setCellValue("Statement Of Account");
		    header0.getCell(2).setCellStyle(style);
		    
		    // create header1 row
		    HSSFRow header1 = sheet.createRow(1);
		    header1.createCell(2).setCellValue("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"));
		    header1.getCell(2).setCellStyle(style);
		    
		    // create main address
		    Row addressRow1 = sheet.createRow(3);
		    addressRow1.createCell(0).setCellValue("ALL CITY TOWING");
		    Row addressRow2 = sheet.createRow(4);
		    addressRow2.createCell(0).setCellValue("2031 W. 1st St.");
		    Row addressRow3 = sheet.createRow(5);
		    addressRow3.createCell(0).setCellValue("Tempe, AZ 85281");
		    Row addressRow4 = sheet.createRow(6);
		    addressRow4.createCell(0).setCellValue("480.833.7278");
		    
		    // create account address
		    Row addressRow5 = sheet.createRow(8);
		    addressRow5.createCell(0).setCellValue("BILL TO: ");
		    Row addressRow6 = sheet.createRow(9);
		    addressRow6.createCell(0).setCellValue("Email: ");
		    Row addressRow7 = sheet.createRow(10);
		    addressRow7.createCell(0).setCellValue("Phone #: ");
		    Row addressRow8 = sheet.createRow(11);
		    addressRow8.createCell(0).setCellValue("Fax #: ");
		    Row addressRow9 = sheet.createRow(12);
		    addressRow9.createCell(0).setCellValue("Terms: ");
		    
		    InputStream inputStream = new FileInputStream(logoPath);
		    byte[] bytes = IOUtils.toByteArray(inputStream);
		    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		    inputStream.close();
		    CreationHelper helper = workbook.getCreationHelper();
		    Drawing drawing = sheet.createDrawingPatriarch();
		    ClientAnchor anchor = helper.createClientAnchor();
		    anchor.setCol1(4);
		    anchor.setRow1(3);
		    Picture picture = drawing.createPicture(anchor, pictureIdx);
		    picture.resize();
		    //picture.setLineStyle(HSSFPicture.LINESTYLE_DASHDOTGEL);
		    
		    // create data row headers
		    HSSFRow header2 = sheet.createRow(14);
		    header2.createCell(1).setCellValue("No records found.");
		    header2.getCell(1).setCellStyle(style);
		    
		    try{
        	    FileOutputStream out = new FileOutputStream(new File(filePath));
        	    workbook.write(out);
        	    out.close();
        	    logger.info("Excel written successfully.");
        	     
        	}catch(FileNotFoundException e){
        		logger.error(e);
        	    e.printStackTrace();
        	}catch (IOException e){
        		logger.error(e);
        	    e.printStackTrace();
        	    fileGen=false;
        	}
		}
		catch (Exception e){
        	logger.error(e);
            e.printStackTrace();
            fileGen=false;
        }
		return fileGen;
	}
	
	public boolean generateExcelToSendStatement(String searchString, List<BillingDetails> billingDetailsList, List<String> filePaths, String logoPath)
	{
		boolean fileGen=true;
		
		try{
			if(searchString.equalsIgnoreCase("byAccount")){
				BillingDetails billingDetails = billingDetailsList.get(0);
				String accountName = billingDetails.getAccountName();
	            HSSFWorkbook workbook = new HSSFWorkbook();
	        	HSSFSheet sheet = workbook.createSheet(accountName);
	        	sheet.setDefaultColumnWidth(15);
	        	
	        	// create style for main header cells
			    CellStyle style = workbook.createCellStyle();
			    Font font = workbook.createFont();
			    font.setFontName("Arial");
			    style.setFillForegroundColor(HSSFColor.BLUE.index);
			    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    font.setColor(HSSFColor.WHITE.index);
			    style.setFont(font);
			    
			    // create style for header cells
			    CellStyle style1 = workbook.createCellStyle();
			    Font font1 = workbook.createFont();
			    font1.setFontName("Arial");
			    style1.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
			    style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
			    font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    font1.setColor(HSSFColor.WHITE.index);
			    style1.setFont(font1);
			    
			    // create header0 row
			    HSSFRow header0 = sheet.createRow(0);
			    header0.createCell(2).setCellValue("Statement Of Account");
			    header0.getCell(2).setCellStyle(style);
			    
			    // create header1 row
			    HSSFRow header1 = sheet.createRow(1);
			    header1.createCell(2).setCellValue("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"));
			    header1.getCell(2).setCellStyle(style);
			    
			    // create main address
			    Row addressRow1 = sheet.createRow(3);
			    addressRow1.createCell(0).setCellValue("ALL CITY TOWING");
			    Row addressRow2 = sheet.createRow(4);
			    addressRow2.createCell(0).setCellValue("2031 W. 1st St.");
			    Row addressRow3 = sheet.createRow(5);
			    addressRow3.createCell(0).setCellValue("Tempe, AZ 85281");
			    Row addressRow4 = sheet.createRow(6);
			    addressRow4.createCell(0).setCellValue("480.833.7278");
			    
			    BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
			    
			    // create account address
			    Row addressRow5 = sheet.createRow(8);
			    addressRow5.createCell(0).setCellValue("BILL TO: "+accountName);
			    Row addressRow6 = sheet.createRow(9);
			    addressRow6.createCell(0).setCellValue(billingAddressDetails.getBillAddrLine1()+" "+billingAddressDetails.getBillAddrLine2()+" "+billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip());
			    Row addressRow7 = sheet.createRow(10);
			    addressRow7.createCell(0).setCellValue("Email: "+billingAddressDetails.getBillEmailId());
			    Row addressRow8 = sheet.createRow(11);
			    addressRow8.createCell(0).setCellValue("Phone #: "+billingAddressDetails.getBillAddrPhone());
			    Row addressRow9 = sheet.createRow(12);
			    addressRow9.createCell(0).setCellValue("Fax #: "+billingAddressDetails.getBillAddrFax());
			    Row addressRow10 = sheet.createRow(13);
			    addressRow10.createCell(0).setCellValue("Terms: ");
			    
			    InputStream inputStream = new FileInputStream(logoPath);
			    byte[] bytes = IOUtils.toByteArray(inputStream);
			    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			    inputStream.close();
			    CreationHelper helper = workbook.getCreationHelper();
			    Drawing drawing = sheet.createDrawingPatriarch();
			    ClientAnchor anchor = helper.createClientAnchor();
			    anchor.setCol1(4);
			    anchor.setRow1(3);
			    Picture picture = drawing.createPicture(anchor, pictureIdx);
			    picture.resize();
			    //picture.setLineStyle(HSSFPicture.LINESTYLE_DASHDOTGEL);
	        	
			    // create data row headers
			    HSSFRow header2 = sheet.createRow(15);
			    header2.createCell(0).setCellValue("Date");
			    header2.getCell(0).setCellStyle(style);
			    header2.createCell(1).setCellValue("Unit #");
			    header2.getCell(1).setCellStyle(style);
			    header2.createCell(2).setCellValue("Year");
			    header2.getCell(2).setCellStyle(style);
			    header2.createCell(3).setCellValue("Make");
			    header2.getCell(3).setCellStyle(style);
			    header2.createCell(4).setCellValue("Model");
			    header2.getCell(4).setCellStyle(style);
			    header2.createCell(5).setCellValue("Location");
			    header2.getCell(5).setCellStyle(style);
			    header2.createCell(6).setCellValue("Invoice #");
			    header2.getCell(6).setCellStyle(style);
			    header2.createCell(7).setCellValue("VIN");
			    header2.getCell(7).setCellStyle(style);
			    		    
	        	Map<String, Object[]> data1 = new HashMap<String, Object[]>();
	        	for(int i=0;i<billingDetails.getBillingInfoDetailsList().size();i++){
	        		BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(i);
	        		data1.put(Integer.toString(i), new Object[] {billingInfoDetails.getDate(), billingInfoDetails.getCommUnitNo(), billingInfoDetails.getVehicleYear(), billingInfoDetails.getVehicleMake(), billingInfoDetails.getVehicleModel(), billingInfoDetails.getLocation(), billingInfoDetails.getInvoiceId(), billingInfoDetails.getVehicleVIN()});
	        	}
	        	 
	        	Set<String> keyset = data1.keySet();
	        	int rownum = 16;
	        	for (String key : keyset) {
	        	    Row row = sheet.createRow(rownum++);
	        	    Object [] objArr = data1.get(key);
	        	    int cellnum = 0;
	        	    for (Object obj : objArr) {
	        	        Cell cell = row.createCell(cellnum++);
	        	        cell.setCellValue((String)obj);        	        
	        	    }
	        	}
	        	
	        	rownum = rownum+2;
	        	// create data row headers
			    HSSFRow header3 = sheet.createRow(rownum);
			    header3.createCell(1).setCellValue("Current");
			    header3.getCell(1).setCellStyle(style);
			    header3.createCell(2).setCellValue("1-30 Days");
			    header3.getCell(2).setCellStyle(style);
			    header3.createCell(3).setCellValue("31-60 Days");
			    header3.getCell(3).setCellStyle(style);
			    header3.createCell(4).setCellValue("61-90 Days");
			    header3.getCell(4).setCellStyle(style);
			    header3.createCell(5).setCellValue("90+ Days");
			    header3.getCell(5).setCellStyle(style);
			    header3.createCell(6).setCellValue("Total Due");
			    header3.getCell(6).setCellStyle(style);
			    
			    Map<String, Object[]> data2 = new HashMap<String, Object[]>();
			    data2.put("1", new Object[] {billingDetails.getCurrentTotal(), billingDetails.getThirtyDaysTotal(), billingDetails.getSixtyDaysTotal(), billingDetails.getNintyDaysTotal(), billingDetails.getMoreThanNintyDaysTotal(), billingDetails.getGrandTotal()});
	        	 
			    Set<String> keyset1 = data2.keySet();
	        	int rownum1 = rownum+1;
	        	for (String key : keyset1) {
	        	    Row row = sheet.createRow(rownum1++);
	        	    Object [] objArr = data2.get(key);
	        	    int cellnum = 1;
	        	    for (Object obj : objArr) {
	        	        Cell cell = row.createCell(cellnum++);
	        	        cell.setCellValue((String)obj);        	        
	        	    }
	        	}
			    
	        	try{
	        	    FileOutputStream out = new FileOutputStream(new File(filePaths.get(0)));
	        	    workbook.write(out);
	        	    out.close();
	        	    logger.info("Excel written successfully.");
	        	     
	        	}catch(FileNotFoundException e){
	        		logger.error(e);
	        	    e.printStackTrace();
	        	    fileGen=false;
	        	}catch (IOException e){
	        		logger.error(e);
	        	    e.printStackTrace();
	        	    fileGen=false;
	        	}
			}
			else if(searchString.equalsIgnoreCase("allBillable")){				
				for(int i=0;i<billingDetailsList.size();i++){
					HSSFWorkbook workbook = new HSSFWorkbook();
					BillingDetails billingDetails = billingDetailsList.get(i);
					String accountName = billingDetails.getAccountName();
					HSSFSheet sheet = workbook.createSheet(accountName);
		        	sheet.setDefaultColumnWidth(15);
		        	
		        	// create style for main header cells
				    CellStyle style = workbook.createCellStyle();
				    Font font = workbook.createFont();
				    font.setFontName("Arial");
				    style.setFillForegroundColor(HSSFColor.BLUE.index);
				    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				    font.setColor(HSSFColor.WHITE.index);
				    style.setFont(font);
				    
				    // create style for header cells
				    CellStyle style1 = workbook.createCellStyle();
				    Font font1 = workbook.createFont();
				    font1.setFontName("Arial");
				    style1.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
				    style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
				    font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				    font1.setColor(HSSFColor.WHITE.index);
				    style1.setFont(font1);
				    
				    // create header0 row
				    HSSFRow header0 = sheet.createRow(0);
				    header0.createCell(2).setCellValue("Statement Of Account");
				    header0.getCell(2).setCellStyle(style);
				    
				    // create header1 row
				    HSSFRow header1 = sheet.createRow(1);
				    header1.createCell(2).setCellValue("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"));
				    header1.getCell(2).setCellStyle(style);
				    
				    // create main address
				    Row addressRow1 = sheet.createRow(3);
				    addressRow1.createCell(0).setCellValue("ALL CITY TOWING");
				    Row addressRow2 = sheet.createRow(4);
				    addressRow2.createCell(0).setCellValue("2031 W. 1st St.");
				    Row addressRow3 = sheet.createRow(5);
				    addressRow3.createCell(0).setCellValue("Tempe, AZ 85281");
				    Row addressRow4 = sheet.createRow(6);
				    addressRow4.createCell(0).setCellValue("480.833.7278");
				    
				    BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);
				    
				    // create account address
				    Row addressRow5 = sheet.createRow(8);
				    addressRow5.createCell(0).setCellValue("BILL TO: "+accountName);
				    Row addressRow6 = sheet.createRow(9);
				    addressRow6.createCell(0).setCellValue(billingAddressDetails.getBillAddrLine1()+" "+billingAddressDetails.getBillAddrLine2()+" "+billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip());
				    Row addressRow7 = sheet.createRow(10);
				    addressRow7.createCell(0).setCellValue("Email: "+billingAddressDetails.getBillEmailId());
				    Row addressRow8 = sheet.createRow(11);
				    addressRow8.createCell(0).setCellValue("Phone #: "+billingAddressDetails.getBillAddrPhone());
				    Row addressRow9 = sheet.createRow(12);
				    addressRow9.createCell(0).setCellValue("Fax #: "+billingAddressDetails.getBillAddrFax());
				    Row addressRow10 = sheet.createRow(13);
				    addressRow10.createCell(0).setCellValue("Terms: ");
				    
				    InputStream inputStream = new FileInputStream(logoPath);
				    byte[] bytes = IOUtils.toByteArray(inputStream);
				    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
				    inputStream.close();
				    CreationHelper helper = workbook.getCreationHelper();
				    Drawing drawing = sheet.createDrawingPatriarch();
				    ClientAnchor anchor = helper.createClientAnchor();
				    anchor.setCol1(4);
				    anchor.setRow1(3);
				    Picture picture = drawing.createPicture(anchor, pictureIdx);
				    picture.resize();
				    //picture.setLineStyle(HSSFPicture.LINESTYLE_DASHDOTGEL);
		        	
				    // create data row headers
				    HSSFRow header2 = sheet.createRow(15);
				    header2.createCell(0).setCellValue("Date");
				    header2.getCell(0).setCellStyle(style);
				    header2.createCell(1).setCellValue("Unit #");
				    header2.getCell(1).setCellStyle(style);
				    header2.createCell(2).setCellValue("Year");
				    header2.getCell(2).setCellStyle(style);
				    header2.createCell(3).setCellValue("Make");
				    header2.getCell(3).setCellStyle(style);
				    header2.createCell(4).setCellValue("Model");
				    header2.getCell(4).setCellStyle(style);
				    header2.createCell(5).setCellValue("Location");
				    header2.getCell(5).setCellStyle(style);
				    header2.createCell(6).setCellValue("Invoice #");
				    header2.getCell(6).setCellStyle(style);
				    header2.createCell(7).setCellValue("VIN");
				    header2.getCell(7).setCellStyle(style);
				    		    
		        	Map<String, Object[]> data1 = new HashMap<String, Object[]>();
		        	for(int j=0;j<billingDetails.getBillingInfoDetailsList().size();j++){
		        		BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(j);
		        		data1.put(Integer.toString(j), new Object[] {billingInfoDetails.getDate(), billingInfoDetails.getCommUnitNo(), billingInfoDetails.getVehicleYear(), billingInfoDetails.getVehicleMake(), billingInfoDetails.getVehicleModel(), billingInfoDetails.getLocation(), billingInfoDetails.getInvoiceId(), billingInfoDetails.getVehicleVIN()});
		        	}
		        	 
		        	Set<String> keyset = data1.keySet();
		        	int rownum = 16;
		        	for (String key : keyset) {
		        	    Row row = sheet.createRow(rownum++);
		        	    Object [] objArr = data1.get(key);
		        	    int cellnum = 0;
		        	    for (Object obj : objArr) {
		        	        Cell cell = row.createCell(cellnum++);
		        	        cell.setCellValue((String)obj);        	        
		        	    }
		        	}
		        	
		        	rownum = rownum+2;
		        	// create data row headers
				    HSSFRow header3 = sheet.createRow(rownum);
				    header3.createCell(1).setCellValue("Current");
				    header3.getCell(1).setCellStyle(style);
				    header3.createCell(2).setCellValue("1-30 Days");
				    header3.getCell(2).setCellStyle(style);
				    header3.createCell(3).setCellValue("31-60 Days");
				    header3.getCell(3).setCellStyle(style);
				    header3.createCell(4).setCellValue("61-90 Days");
				    header3.getCell(4).setCellStyle(style);
				    header3.createCell(5).setCellValue("90+ Days");
				    header3.getCell(5).setCellStyle(style);
				    header3.createCell(6).setCellValue("Total Due");
				    header3.getCell(6).setCellStyle(style);
				    
				    Map<String, Object[]> data2 = new HashMap<String, Object[]>();
				    data2.put("1", new Object[] {billingDetails.getCurrentTotal(), billingDetails.getThirtyDaysTotal(), billingDetails.getSixtyDaysTotal(), billingDetails.getNintyDaysTotal(), billingDetails.getMoreThanNintyDaysTotal(), billingDetails.getGrandTotal()});
				    
				    Set<String> keyset1 = data2.keySet();
		        	int rownum1 = rownum+1;
		        	for (String key : keyset1) {
		        	    Row row = sheet.createRow(rownum1++);
		        	    Object [] objArr = data2.get(key);
		        	    int cellnum = 1;
		        	    for (Object obj : objArr) {
		        	        Cell cell = row.createCell(cellnum++);
		        	        cell.setCellValue((String)obj);        	        
		        	    }
		        	}
		        	
		        	try{
		        	    FileOutputStream out = new FileOutputStream(new File(filePaths.get(i)));
		        	    workbook.write(out);
		        	    out.close();
		        	    logger.info("Excel written successfully.");
		        	     
		        	}catch(FileNotFoundException e){
		        		logger.error(e);
		        	    e.printStackTrace();
		        	    fileGen=false;
		        	}catch (IOException e){
		        		logger.error(e);
		        	    e.printStackTrace();
		        	    fileGen=false;
		        	}	
				}				        	
			}				
        }catch (Exception e){
        	logger.error(e);
            e.printStackTrace();
            fileGen=false;
        }
		return fileGen;
    }
	
	public boolean generateExcelToSendStatement(BillingDetails billingDetails, String filePath, String logoPath)
	{
		boolean fileGen=true;
		
		try{
			String accountName = billingDetails.getAccountName();
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(accountName);
			sheet.setDefaultColumnWidth(15);

			// create style for main header cells
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setFontName("Arial");
			style.setFillForegroundColor(HSSFColor.BLUE.index);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.WHITE.index);
			style.setFont(font);

			// create style for header cells
			CellStyle style1 = workbook.createCellStyle();
			Font font1 = workbook.createFont();
			font1.setFontName("Arial");
			style1.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
			style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font1.setColor(HSSFColor.WHITE.index);
			style1.setFont(font1);

			// create header0 row
			HSSFRow header0 = sheet.createRow(0);
			header0.createCell(2).setCellValue("Statement Of Account");
			header0.getCell(2).setCellStyle(style);

			// create header1 row
			HSSFRow header1 = sheet.createRow(1);
			header1.createCell(2).setCellValue("Date: "+DateUtility.getCurrentDate("MM-dd-yyyy"));
			header1.getCell(2).setCellStyle(style);

			// create main address
			Row addressRow1 = sheet.createRow(3);
			addressRow1.createCell(0).setCellValue("ALL CITY TOWING");
			Row addressRow2 = sheet.createRow(4);
			addressRow2.createCell(0).setCellValue("2031 W. 1st St.");
			Row addressRow3 = sheet.createRow(5);
			addressRow3.createCell(0).setCellValue("Tempe, AZ 85281");
			Row addressRow4 = sheet.createRow(6);
			addressRow4.createCell(0).setCellValue("480.833.7278");

			BillingAddressDetails billingAddressDetails = billingDetailsDAO.getBillingAddress(accountName);

			// create account address
			Row addressRow5 = sheet.createRow(8);
			addressRow5.createCell(0).setCellValue("BILL TO: "+accountName);
			Row addressRow6 = sheet.createRow(9);
			addressRow6.createCell(0).setCellValue(billingAddressDetails.getBillAddrLine1()+" "+billingAddressDetails.getBillAddrLine2()+" "+billingAddressDetails.getBillAddrCity()+", "+billingAddressDetails.getBillAddrState()+" "+billingAddressDetails.getBillAddrZip());
			Row addressRow7 = sheet.createRow(10);
			addressRow7.createCell(0).setCellValue("Email: "+billingAddressDetails.getBillEmailId());
			Row addressRow8 = sheet.createRow(11);
			addressRow8.createCell(0).setCellValue("Phone #: "+billingAddressDetails.getBillAddrPhone());
			Row addressRow9 = sheet.createRow(12);
			addressRow9.createCell(0).setCellValue("Fax #: "+billingAddressDetails.getBillAddrFax());
			Row addressRow10 = sheet.createRow(13);
			addressRow10.createCell(0).setCellValue("Terms: ");

			InputStream inputStream = new FileInputStream(logoPath);
			byte[] bytes = IOUtils.toByteArray(inputStream);
			int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			inputStream.close();
			CreationHelper helper = workbook.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(4);
			anchor.setRow1(3);
			Picture picture = drawing.createPicture(anchor, pictureIdx);
			picture.resize();
			//picture.setLineStyle(HSSFPicture.LINESTYLE_DASHDOTGEL);

			// create data row headers
			HSSFRow header2 = sheet.createRow(15);
			header2.createCell(0).setCellValue("Date");
			header2.getCell(0).setCellStyle(style);
			header2.createCell(1).setCellValue("Unit #");
			header2.getCell(1).setCellStyle(style);
			header2.createCell(2).setCellValue("Year");
			header2.getCell(2).setCellStyle(style);
			header2.createCell(3).setCellValue("Make");
			header2.getCell(3).setCellStyle(style);
			header2.createCell(4).setCellValue("Model");
			header2.getCell(4).setCellStyle(style);
			header2.createCell(5).setCellValue("Location");
			header2.getCell(5).setCellStyle(style);
			header2.createCell(6).setCellValue("Invoice #");
			header2.getCell(6).setCellStyle(style);
			header2.createCell(7).setCellValue("VIN");
			header2.getCell(7).setCellStyle(style);

			Map<String, Object[]> data1 = new HashMap<String, Object[]>();
			for(int i=0;i<billingDetails.getBillingInfoDetailsList().size();i++){
				BillingInfoDetails billingInfoDetails = billingDetails.getBillingInfoDetailsList().get(i);
				data1.put(Integer.toString(i), new Object[] {billingInfoDetails.getDate(), billingInfoDetails.getCommUnitNo(), billingInfoDetails.getVehicleYear(), billingInfoDetails.getVehicleMake(), billingInfoDetails.getVehicleModel(), billingInfoDetails.getLocation(), billingInfoDetails.getInvoiceId(), billingInfoDetails.getVehicleVIN()});
			}

			Set<String> keyset = data1.keySet();
			int rownum = 16;
			for (String key : keyset) {
				Row row = sheet.createRow(rownum++);
				Object [] objArr = data1.get(key);
				int cellnum = 0;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					cell.setCellValue((String)obj);        	        
				}
			}

			rownum = rownum+2;
			// create data row headers
			HSSFRow header3 = sheet.createRow(rownum);
			header3.createCell(1).setCellValue("Current");
			header3.getCell(1).setCellStyle(style);
			header3.createCell(2).setCellValue("1-30 Days");
			header3.getCell(2).setCellStyle(style);
			header3.createCell(3).setCellValue("31-60 Days");
			header3.getCell(3).setCellStyle(style);
			header3.createCell(4).setCellValue("61-90 Days");
			header3.getCell(4).setCellStyle(style);
			header3.createCell(5).setCellValue("90+ Days");
			header3.getCell(5).setCellStyle(style);
			header3.createCell(6).setCellValue("Total Due");
			header3.getCell(6).setCellStyle(style);

			Map<String, Object[]> data2 = new HashMap<String, Object[]>();
			data2.put("1", new Object[] {billingDetails.getCurrentTotal(), billingDetails.getThirtyDaysTotal(), billingDetails.getSixtyDaysTotal(), billingDetails.getNintyDaysTotal(), billingDetails.getMoreThanNintyDaysTotal(), billingDetails.getGrandTotal()});

			Set<String> keyset1 = data2.keySet();
			int rownum1 = rownum+1;
			for (String key : keyset1) {
				Row row = sheet.createRow(rownum1++);
				Object [] objArr = data2.get(key);
				int cellnum = 1;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					cell.setCellValue((String)obj);        	        
				}
			}

			try{
				FileOutputStream out = new FileOutputStream(new File(filePath));
				workbook.write(out);
				out.close();
				logger.info("Excel written successfully.");

			}catch(FileNotFoundException e){
				logger.error(e);
				e.printStackTrace();
				fileGen=false;
			}catch (IOException e){
				logger.error(e);
				e.printStackTrace();
				fileGen=false;
			}
        }catch (Exception e){
        	logger.error(e);
            e.printStackTrace();
            fileGen=false;
        }
		return fileGen;
    }
}