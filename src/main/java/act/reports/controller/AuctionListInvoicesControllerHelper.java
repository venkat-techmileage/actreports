package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.AuctionListInvoicesDetailsList;

@Service("auctionListInvoicesControllerHelper")
public class AuctionListInvoicesControllerHelper {
	
	private Logger logger=Logger.getLogger(AuctionListInvoicesControllerHelper.class);

	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertAuctionListInvoicesToExcelFormat(AuctionListInvoicesDetailsList auctionListInvoicesDetailsList)
	{
			logger.info("In convertAuctionListInvoicesToExcelFormat(AuctionListInvoicesDetailsList auctionListInvoicesDetailsList)...");
			List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
			try 
			{
				for(int i=0;i<auctionListInvoicesDetailsList.getInvoiceId().size();i++)
				{
					logger.info("i = "+i);
					Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
					//excelExpireData.put("invoiceId", (auctionListInvoicesDetailsList.getInvoiceId().get(i) != null) ? auctionListInvoicesDetailsList.getInvoiceId().get(i) : "");
					excelExpireData.put("invoiceId", auctionListInvoicesDetailsList.getInvoiceId().get(i));
					excelExpireData.put("vehicleYear", (auctionListInvoicesDetailsList.getVehicleYear().get(i) != null) ? auctionListInvoicesDetailsList.getVehicleYear().get(i) : "");
					excelExpireData.put("vehicleMake", (auctionListInvoicesDetailsList.getVehicleMake().get(i) != null) ? auctionListInvoicesDetailsList.getVehicleMake().get(i) : "");
					excelExpireData.put("vehicleModel", (auctionListInvoicesDetailsList.getVehicleModel().get(i) != null) ? auctionListInvoicesDetailsList.getVehicleModel().get(i) : "");
					excelExpireData.put("vehicleVIN", (auctionListInvoicesDetailsList.getVehicleVIN().get(i) != null) ? auctionListInvoicesDetailsList.getVehicleVIN().get(i) : "");
					excelExpireData.put("licensePlateCountry", (auctionListInvoicesDetailsList.getLicensePlateCountry().get(i) != null) ? auctionListInvoicesDetailsList.getLicensePlateCountry().get(i) : "");
					excelExpireData.put("licensePlateState", (auctionListInvoicesDetailsList.getLicensePlateState().get(i) != null) ? auctionListInvoicesDetailsList.getLicensePlateState().get(i) : "");
					excelExpireData.put("licensePlateNo", (auctionListInvoicesDetailsList.getLicensePlateNo().get(i) != null) ? auctionListInvoicesDetailsList.getLicensePlateNo().get(i) : "");
					excelExpireData.put("markedAsSalvage", (auctionListInvoicesDetailsList.getMarkedAsSalvage().get(i) != null) ? auctionListInvoicesDetailsList.getMarkedAsSalvage().get(i) : "");
					excelExpireData.put("lotLocation", (auctionListInvoicesDetailsList.getLotLocation().get(i) != null) ? auctionListInvoicesDetailsList.getLotLocation().get(i) : "");
					excelExpireData.put("releasedTo", (auctionListInvoicesDetailsList.getReleasedTo().get(i) != null) ? auctionListInvoicesDetailsList.getReleasedTo().get(i) : "");					
					excelDetails.add(excelExpireData);
				}
			}
			catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}			
			return excelDetails;
		}
		
		public List<String> getAuctionListInvoicesDetailExcelHeaders()
		{
			logger.info("In getAuctionListInvoicesDetailExcelHeaders()...");
			List<String> excelHeaders=new ArrayList<String>();
			try
			{
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header1"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header2"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header3"));							
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header4"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header5"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header6"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header7"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header8"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header9"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header10"));
				excelHeaders.add(excelProps.getProperty("auctionListInvoices.list.excel.header11"));				
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return excelHeaders;
		}

}
