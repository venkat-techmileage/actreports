package act.reports.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;


public class ExcelCreater extends AbstractExcelView
{	
	/**
	 * This class builds an Excel spreadsheet document using Apache POI library.
	 */
	
	private Logger logger=Logger.getLogger(ExcelCreater.class);
	
	@SuppressWarnings("unchecked")
	@Override
	  protected void buildExcelDocument(Map<String, Object> model,
	      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
	      throws Exception {
		  
		logger.info("Invoked Excel Creater========");
		 
		try{
		    // get data model which is passed by the Spring container
		   
			List<Map<String,String>> excelDetails = (List<Map<String,String>>) model.get("excelDetails");
		   
			List<String> excelHeaders=(List<String>)model.get("excelHeaders");
			String fileName = (String)model.get("fileName");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		    
		    // create a new Excel sheet
		    HSSFSheet sheet = workbook.createSheet("All Invoices");
		    sheet.setDefaultColumnWidth(20);
		    
		    // create style for header cells
		    CellStyle style = workbook.createCellStyle();
		    Font font = workbook.createFont();
		    font.setFontName("Arial");
		    style.setFillForegroundColor(HSSFColor.BLUE.index);
		    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    font.setColor(HSSFColor.WHITE.index);
		    style.setFont(font);
		    
		    // create header row
		    HSSFRow header = sheet.createRow(0);
		    int i=0;
		    for(String head:excelHeaders)
		    {
		    header.createCell(i).setCellValue(head);
		    header.getCell(i).setCellStyle(style);
		    i++;
		    }
		   	    // create data rows
		    int rowCount = 1;
		    //logger.info("excelDetails.size() = "+excelDetails.size());
		    for (Map<String,String> excelRow : excelDetails) {
		      HSSFRow aRow = sheet.createRow(rowCount++);
		      int colCount=0;
		      for(Entry<String, String> entry:excelRow.entrySet())
		      {
		    	  String val = entry.getValue();
		    	  //logger.info("val = "+val);
		    	  aRow.createCell(colCount).setCellValue(val);
		    	  colCount++;
		      }
		      
		    }
		}catch(Exception e){
			logger.error(e);
		}
	    
	  }
         
	}

