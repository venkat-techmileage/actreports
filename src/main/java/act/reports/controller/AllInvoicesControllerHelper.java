package act.reports.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.model.ExportInvoicesList;

@Service("invoicesHelper")
public class AllInvoicesControllerHelper {
	
	private Logger logger=Logger.getLogger(AllInvoicesControllerHelper.class);
	
	@Autowired
	private Properties excelProps;

	public List<Map<String,String>> convertInvoicesListAsExcelFormat(ExportInvoicesList invoicesList)
	{		
		List<Map<String,String>> excelDetails=new ArrayList<Map<String,String>>();
		try 
		{
			for(int i=0;i<invoicesList.getInvoice().size();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				excelExpireData.put("serviceCallDate", invoicesList.getServiceCallDate().get(i));
				excelExpireData.put("invoice", invoicesList.getInvoice().get(i));
				excelExpireData.put("requestedBy", invoicesList.getRequestedBy().get(i));
				excelExpireData.put("salesRep", invoicesList.getSalesRep().get(i));
				excelExpireData.put("ratePlan", invoicesList.getRatePlan().get(i));
				excelExpireData.put("priorityAndReason", invoicesList.getPriorityAndReason().get(i));
				excelExpireData.put("towType", invoicesList.getTowType().get(i));
				excelExpireData.put("orDr", invoicesList.getOrDr().get(i));
				excelExpireData.put("driverLicense", invoicesList.getDriverLicense().get(i));
				excelExpireData.put("driverIsOwner", invoicesList.getDriverIsOwner().get(i));
				excelExpireData.put("noOwnerInfo", invoicesList.getNoOwnerInfo().get(i));
				excelExpireData.put("registeredOwner", invoicesList.getRegisteredOwner().get(i));
				excelExpireData.put("registeredOwnerName", invoicesList.getRegisteredOwnerName().get(i));
				excelExpireData.put("registeredOwnerAddress", invoicesList.getRegisteredOwnerAddress().get(i));
				excelExpireData.put("registeredOwnerCity", invoicesList.getRegisteredOwnerCity().get(i));
				excelExpireData.put("registeredOwnerState", invoicesList.getRegisteredOwnerState().get(i));
				excelExpireData.put("registeredOwnerZip", invoicesList.getRegisteredOwnerZip().get(i));
				excelExpireData.put("registeredOwnerPhone", invoicesList.getRegisteredOwnerPhone().get(i));
				excelExpireData.put("registeredOwnerEmail", invoicesList.getRegisteredOwnerEmail().get(i));
				excelExpireData.put("year", invoicesList.getYear().get(i));
				excelExpireData.put("make", invoicesList.getMake().get(i));
				excelExpireData.put("model", invoicesList.getModel().get(i));
				excelExpireData.put("color", invoicesList.getColor().get(i));
				excelExpireData.put("style", invoicesList.getStyle().get(i));
				excelExpireData.put("vin", invoicesList.getVin().get(i));
				excelExpireData.put("plateCountry", invoicesList.getPlateCountry().get(i));
				excelExpireData.put("plateState", invoicesList.getPlateState().get(i));
				excelExpireData.put("plate", invoicesList.getPlate().get(i));
				excelExpireData.put("keys", invoicesList.getKeys().get(i));
				excelExpireData.put("radio", invoicesList.getRadio().get(i));
				excelExpireData.put("vehicleStatus", invoicesList.getVehicleStatus().get(i));
				excelExpireData.put("commercialUnit", invoicesList.getCommercialUnit().get(i));
				excelExpireData.put("policeImpound", invoicesList.getPoliceImpound().get(i));
				excelExpireData.put("callTime", invoicesList.getCallTime().get(i));
				excelExpireData.put("dispatchTime", invoicesList.getDispatchTime().get(i));
				excelExpireData.put("acceptedTime", invoicesList.getAcceptedTime().get(i));
				excelExpireData.put("enrouteTime", invoicesList.getVin().get(i));
				excelExpireData.put("arrivedTime", invoicesList.getPlateCountry().get(i));
				excelExpireData.put("hookedTime", invoicesList.getPlateState().get(i));
				excelExpireData.put("droppedTime", invoicesList.getPlate().get(i));
				excelExpireData.put("clearTime", invoicesList.getKeys().get(i));
				excelExpireData.put("totalTime", invoicesList.getRadio().get(i));
				excelExpireData.put("pickupLocation", invoicesList.getVehicleStatus().get(i));
				excelExpireData.put("pickupAddress", invoicesList.getCommercialUnit().get(i));
				excelExpireData.put("pickupCity", invoicesList.getPoliceImpound().get(i));
				excelExpireData.put("pickupState", invoicesList.getCallTime().get(i));
				excelExpireData.put("pickupZipcode", invoicesList.getDispatchTime().get(i));
				excelExpireData.put("dropOffLocation", invoicesList.getAcceptedTime().get(i));
				excelExpireData.put("enrouteTime", invoicesList.getEnrouteTime().get(i));
				excelExpireData.put("arrivedTime", invoicesList.getArrivedTime().get(i));
				excelExpireData.put("hookedTime", invoicesList.getHookedTime().get(i));
				excelExpireData.put("droppedTime", invoicesList.getDroppedTime().get(i));
				excelExpireData.put("clearTime", invoicesList.getClearTime().get(i));
				excelExpireData.put("totalTime", invoicesList.getTotalTime().get(i));
				excelExpireData.put("pickupLocation", invoicesList.getPickupLocation().get(i));
				excelExpireData.put("pickupAddress", invoicesList.getPickupAddress().get(i));
				excelExpireData.put("pickupCity", invoicesList.getPickupCity().get(i));
				excelExpireData.put("pickupState", invoicesList.getPickupState().get(i));
				excelExpireData.put("pickupZipcode", invoicesList.getPickupZipcode().get(i));
				excelExpireData.put("dropOffLocation", invoicesList.getDropOffLocation().get(i));
				excelExpireData.put("dropOffAddress", invoicesList.getDropOffAddress().get(i));
				excelExpireData.put("dropOffCity", invoicesList.getDropOffCity().get(i));
				excelExpireData.put("dropOffState", invoicesList.getDropOffState().get(i));
				excelExpireData.put("dropOffZipcode", invoicesList.getDropOffZipcode().get(i));
				excelExpireData.put("truck", invoicesList.getTruck().get(i));
				excelExpireData.put("driverId", invoicesList.getDriverId().get(i));
				excelExpireData.put("dispatchId", invoicesList.getDispatchId().get(i));
				excelExpireData.put("callReceiverId", invoicesList.getCallReceiverId().get(i));
				excelExpireData.put("towCharge", invoicesList.getTowCharge().get(i));
				excelExpireData.put("storageCharge", invoicesList.getStorageCharge().get(i));
				excelExpireData.put("laborCharge", invoicesList.getLaborCharge().get(i));
				excelExpireData.put("mileageCharge", invoicesList.getMileageCharge().get(i));
				excelExpireData.put("winchCharge", invoicesList.getWinchCharge().get(i));
				excelExpireData.put("gateCharge", invoicesList.getGateCharge().get(i));
				excelExpireData.put("adminCharge", invoicesList.getAdminCharge().get(i));
				excelExpireData.put("miscChargeDesc", invoicesList.getMiscChargeDesc().get(i));
				excelExpireData.put("miscCharge", invoicesList.getMiscCharge().get(i));
				excelExpireData.put("discounts", invoicesList.getDiscounts().get(i));
				excelExpireData.put("totalCharges", invoicesList.getTotalCharges().get(i));
				excelExpireData.put("amountPaid", invoicesList.getAmountPaid().get(i));
				excelExpireData.put("inStorage", invoicesList.getInStorage().get(i));
				excelExpireData.put("billTo", invoicesList.getBillTo().get(i));
				excelExpireData.put("releasedTo", invoicesList.getReleasedTo().get(i));
				excelExpireData.put("releaseTime", invoicesList.getReleaseTime().get(i));
				excelExpireData.put("releaseDate", invoicesList.getReleaseDate().get(i));
				excelExpireData.put("avrFiledDate", invoicesList.getAvrFiledDate().get(i));
				excelExpireData.put("trnsfrOfAuth", invoicesList.getTrnsfrOfAuth().get(i));
				excelExpireData.put("titleDate", invoicesList.getTitleDate().get(i));
				excelExpireData.put("locked", invoicesList.getLocked().get(i));
				excelExpireData.put("closed", invoicesList.getClosed().get(i));
				excelDetails.add(excelExpireData);
			}
		}catch(Exception e) {
			logger.error(e);			
		}
		
		return excelDetails;
	}
	
	public List<String> getinvoicesListExcelHeaders()
	{
		List<String> excelHeaders=new ArrayList<String>();
		try
		{
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header1"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header2"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header3"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header4"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header5"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header6"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header7"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header8"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header9"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header10"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header11"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header12"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header13"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header14"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header15"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header16"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header17"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header18"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header19"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header20"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header21"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header22"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header23"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header24"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header25"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header26"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header27"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header28"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header29"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header30"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header31"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header32"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header33"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header34"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header35"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header36"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header37"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header38"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header39"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header40"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header41"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header42"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header43"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header44"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header45"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header46"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header47"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header48"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header49"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header50"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header51"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header52"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header53"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header54"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header55"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header56"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header57"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header58"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header59"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header60"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header61"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header62"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header63"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header64"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header65"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header66"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header67"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header68"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header69"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header70"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header71"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header72"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header73"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header74"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header75"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header76"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header77"));
			excelHeaders.add(excelProps.getProperty("invoices.list.excel.header78"));
		}
		catch (Exception e) {
			logger.error(e);			
		}
		return excelHeaders;
	}
	
}
