package act.reports.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

/**

 */
public class SampleExcel {

    public static void main(String[] args) 
    {
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();
        	HSSFSheet sheet = workbook.createSheet("Sample");
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
		    
		    InputStream inputStream = new FileInputStream("D:/TechMileage/logo.gif");
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
        	data1.put("1", new Object[] {"2014-02-24", "439758", "2007", "Kia", "Sportage", "A-Lot", "14055-0011", "198.47"});
        	data1.put("2", new Object[] {"2014-02-24", "439758", "2007", "Kia", "Sportage", "A-Lot", "14055-0011", "198.47"});
        	data1.put("3", new Object[] {"2014-02-24", "439758", "2007", "Kia", "Sportage", "A-Lot", "14055-0011", "198.47"});
        	 
        	Set<String> keyset = data1.keySet();
        	int rownum = 15;
        	for (String key : keyset) {
        	    Row row = sheet.createRow(rownum++);
        	    Object [] objArr = data1.get(key);
        	    int cellnum = 0;
        	    for (Object obj : objArr) {
        	        Cell cell = row.createCell(cellnum++);
        	        cell.setCellValue((String)obj);        	        
        	    }
        	}
        	 
        	try{
        	    FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Sri\\Desktop\\Sample.xls"));
        	    workbook.write(out);
        	    out.close();
        	    System.out.println("Excel written successfully..");
        	     
        	}catch(FileNotFoundException e){
        	    e.printStackTrace();
        	}catch (IOException e){
        	    e.printStackTrace();
        	}
        }catch (Exception e){
            e.printStackTrace();
        }        
    }
}