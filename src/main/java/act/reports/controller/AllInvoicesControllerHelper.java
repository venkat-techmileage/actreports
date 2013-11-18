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
			//for(int i=0;i<invoicesList.getInvoice().size();i++)
			for(int i=0;i<invoicesList.getRecCount();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				if(invoicesList.getServiceCallDate()!=null)
					excelExpireData.put("serviceCallDate", invoicesList.getServiceCallDate().get(i));
				if(invoicesList.getInvoice()!=null)
					excelExpireData.put("invoice", invoicesList.getInvoice().get(i));
				if(invoicesList.getRequestedBy()!=null)
					excelExpireData.put("requestedBy", invoicesList.getRequestedBy().get(i));
				if(invoicesList.getSalesRep()!=null)
					excelExpireData.put("salesRep", invoicesList.getSalesRep().get(i));
				if(invoicesList.getRatePlan()!=null)
					excelExpireData.put("ratePlan", invoicesList.getRatePlan().get(i));
				if(invoicesList.getPriorityAndReason()!=null)
					excelExpireData.put("priorityAndReason", invoicesList.getPriorityAndReason().get(i));
				if(invoicesList.getTowType()!=null)
					excelExpireData.put("towType", invoicesList.getTowType().get(i));
				if(invoicesList.getOrDr()!=null)
					excelExpireData.put("orDr", invoicesList.getOrDr().get(i));
				if(invoicesList.getDriverLicense()!=null)
					excelExpireData.put("driverLicense", invoicesList.getDriverLicense().get(i));
				if(invoicesList.getDriverIsOwner()!=null)
					excelExpireData.put("driverIsOwner", invoicesList.getDriverIsOwner().get(i));
				if(invoicesList.getNoOwnerInfo()!=null)
					excelExpireData.put("noOwnerInfo", invoicesList.getNoOwnerInfo().get(i));
				if(invoicesList.getRegisteredOwner()!=null)
					excelExpireData.put("registeredOwner", invoicesList.getRegisteredOwner().get(i));
				if(invoicesList.getRegisteredOwnerName()!=null)
					excelExpireData.put("registeredOwnerName", invoicesList.getRegisteredOwnerName().get(i));
				if(invoicesList.getRegisteredOwnerAddress()!=null)
					excelExpireData.put("registeredOwnerAddress", invoicesList.getRegisteredOwnerAddress().get(i));
				if(invoicesList.getRegisteredOwnerCity()!=null)
					excelExpireData.put("registeredOwnerCity", invoicesList.getRegisteredOwnerCity().get(i));
				if(invoicesList.getRegisteredOwnerState()!=null)
					excelExpireData.put("registeredOwnerState", invoicesList.getRegisteredOwnerState().get(i));
				if(invoicesList.getRegisteredOwnerZip()!=null)
					excelExpireData.put("registeredOwnerZip", invoicesList.getRegisteredOwnerZip().get(i));
				if(invoicesList.getRegisteredOwnerPhone()!=null)
					excelExpireData.put("registeredOwnerPhone", invoicesList.getRegisteredOwnerPhone().get(i));
				if(invoicesList.getRegisteredOwnerEmail()!=null)
					excelExpireData.put("registeredOwnerEmail", invoicesList.getRegisteredOwnerEmail().get(i));
				if(invoicesList.getYear()!=null)
					excelExpireData.put("year", invoicesList.getYear().get(i));
				if(invoicesList.getMake()!=null)
					excelExpireData.put("make", invoicesList.getMake().get(i));
				if(invoicesList.getModel()!=null)
					excelExpireData.put("model", invoicesList.getModel().get(i));
				if(invoicesList.getColor()!=null)
					excelExpireData.put("color", invoicesList.getColor().get(i));
				if(invoicesList.getStyle()!=null)
					excelExpireData.put("style", invoicesList.getStyle().get(i));
				if(invoicesList.getVin()!=null)
					excelExpireData.put("vin", invoicesList.getVin().get(i));
				if(invoicesList.getPlateCountry()!=null)
					excelExpireData.put("plateCountry", invoicesList.getPlateCountry().get(i));
				if(invoicesList.getPlateState()!=null)
					excelExpireData.put("plateState", invoicesList.getPlateState().get(i));
				if(invoicesList.getPlate()!=null)
					excelExpireData.put("plate", invoicesList.getPlate().get(i));
				if(invoicesList.getKeys()!=null)
					excelExpireData.put("keys", invoicesList.getKeys().get(i));
				if(invoicesList.getRadio()!=null)
					excelExpireData.put("radio", invoicesList.getRadio().get(i));
				if(invoicesList.getVehicleStatus()!=null)
					excelExpireData.put("vehicleStatus", invoicesList.getVehicleStatus().get(i));
				if(invoicesList.getCommercialUnit()!=null)
					excelExpireData.put("commercialUnit", invoicesList.getCommercialUnit().get(i));
				if(invoicesList.getPoliceImpound()!=null)
					excelExpireData.put("policeImpound", invoicesList.getPoliceImpound().get(i));
				if(invoicesList.getCallTime()!=null)
					excelExpireData.put("callTime", invoicesList.getCallTime().get(i));
				if(invoicesList.getDispatchTime()!=null)
					excelExpireData.put("dispatchTime", invoicesList.getDispatchTime().get(i));
				if(invoicesList.getAcceptedTime()!=null)
					excelExpireData.put("acceptedTime", invoicesList.getAcceptedTime().get(i));
				if(invoicesList.getEnrouteTime()!=null)
					excelExpireData.put("enrouteTime", invoicesList.getEnrouteTime().get(i));
				if(invoicesList.getArrivedTime()!=null)
					excelExpireData.put("arrivedTime", invoicesList.getArrivedTime().get(i));
				if(invoicesList.getHookedTime()!=null)
					excelExpireData.put("hookedTime", invoicesList.getHookedTime().get(i));
				if(invoicesList.getDroppedTime()!=null)
					excelExpireData.put("droppedTime", invoicesList.getDroppedTime().get(i));
				if(invoicesList.getClearTime()!=null)
					excelExpireData.put("clearTime", invoicesList.getClearTime().get(i));
				if(invoicesList.getTotalTime()!=null)
					excelExpireData.put("totalTime", invoicesList.getTotalTime().get(i));
				if(invoicesList.getPickupLocation()!=null)
					excelExpireData.put("pickupLocation", invoicesList.getPickupLocation().get(i));
				if(invoicesList.getPickupAddress()!=null)
					excelExpireData.put("pickupAddress", invoicesList.getPickupAddress().get(i));
				if(invoicesList.getPickupCity()!=null)
					excelExpireData.put("pickupCity", invoicesList.getPickupCity().get(i));
				if(invoicesList.getPickupState()!=null)
					excelExpireData.put("pickupState", invoicesList.getPickupState().get(i));
				if(invoicesList.getPickupZipcode()!=null)
					excelExpireData.put("pickupZipcode", invoicesList.getPickupZipcode().get(i));
				if(invoicesList.getDropOffLocation()!=null)
					excelExpireData.put("dropOffLocation", invoicesList.getDropOffLocation().get(i));
				if(invoicesList.getDropOffAddress()!=null)
					excelExpireData.put("dropOffAddress", invoicesList.getDropOffAddress().get(i));
				if(invoicesList.getDropOffCity()!=null)
					excelExpireData.put("dropOffCity", invoicesList.getDropOffCity().get(i));
				if(invoicesList.getDropOffState()!=null)
					excelExpireData.put("dropOffState", invoicesList.getDropOffState().get(i));
				if(invoicesList.getDropOffZipcode()!=null)
					excelExpireData.put("dropOffZipcode", invoicesList.getDropOffZipcode().get(i));
				if(invoicesList.getTruck()!=null)
					excelExpireData.put("truck", invoicesList.getTruck().get(i));
				if(invoicesList.getDriverId()!=null)
					excelExpireData.put("driverId", invoicesList.getDriverId().get(i));
				if(invoicesList.getDispatchId()!=null)
					excelExpireData.put("dispatchId", invoicesList.getDispatchId().get(i));
				if(invoicesList.getCallReceiverId()!=null)
					excelExpireData.put("callReceiverId", invoicesList.getCallReceiverId().get(i));
				if(invoicesList.getTowCharge()!=null)
					excelExpireData.put("towCharge", invoicesList.getTowCharge().get(i));
				if(invoicesList.getStorageCharge()!=null)
					excelExpireData.put("storageCharge", invoicesList.getStorageCharge().get(i));
				if(invoicesList.getLaborCharge()!=null)
					excelExpireData.put("laborCharge", invoicesList.getLaborCharge().get(i));
				if(invoicesList.getMileageCharge()!=null)
					excelExpireData.put("mileageCharge", invoicesList.getMileageCharge().get(i));
				if(invoicesList.getWinchCharge()!=null)
					excelExpireData.put("winchCharge", invoicesList.getWinchCharge().get(i));
				if(invoicesList.getGateCharge()!=null)
					excelExpireData.put("gateCharge", invoicesList.getGateCharge().get(i));
				if(invoicesList.getAdminCharge()!=null)
					excelExpireData.put("adminCharge", invoicesList.getAdminCharge().get(i));
				if(invoicesList.getMiscChargeDesc()!=null)
					excelExpireData.put("miscChargeDesc", invoicesList.getMiscChargeDesc().get(i));
				if(invoicesList.getMiscCharge()!=null)
					excelExpireData.put("miscCharge", invoicesList.getMiscCharge().get(i));
				if(invoicesList.getDiscounts()!=null)
					excelExpireData.put("discounts", invoicesList.getDiscounts().get(i));
				if(invoicesList.getTotalCharges()!=null)
					excelExpireData.put("totalCharges", invoicesList.getTotalCharges().get(i));
				if(invoicesList.getAmountPaid()!=null)
					excelExpireData.put("amountPaid", invoicesList.getAmountPaid().get(i));
				if(invoicesList.getInStorage()!=null)
					excelExpireData.put("inStorage", invoicesList.getInStorage().get(i));
				if(invoicesList.getBillTo()!=null)
					excelExpireData.put("billTo", invoicesList.getBillTo().get(i));
				if(invoicesList.getReleasedTo()!=null)
					excelExpireData.put("releasedTo", invoicesList.getReleasedTo().get(i));
				if(invoicesList.getReleaseTime()!=null)
					excelExpireData.put("releaseTime", invoicesList.getReleaseTime().get(i));
				if(invoicesList.getReleaseDate()!=null)
					excelExpireData.put("releaseDate", invoicesList.getReleaseDate().get(i));
				if(invoicesList.getAvrFiledDate()!=null)
					excelExpireData.put("avrFiledDate", invoicesList.getAvrFiledDate().get(i));
				if(invoicesList.getTrnsfrOfAuth()!=null)
					excelExpireData.put("trnsfrOfAuth", invoicesList.getTrnsfrOfAuth().get(i));
				if(invoicesList.getTitleDate()!=null)
					excelExpireData.put("titleDate", invoicesList.getTitleDate().get(i));
				if(invoicesList.getLocked()!=null)
					excelExpireData.put("locked", invoicesList.getLocked().get(i));
				if(invoicesList.getClosed()!=null)
					excelExpireData.put("closed", invoicesList.getClosed().get(i));
				excelDetails.add(excelExpireData);
			}
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return excelDetails;
	}
	
	public List<String> getinvoicesListExcelHeaders(ExportInvoicesList invoicesList)
	{
		List<String> excelHeaders=new ArrayList<String>();
		try
		{
			if(invoicesList.getRecCount()>0){
				if(invoicesList.getServiceCallDate()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.serviceCallDate"));
				if(invoicesList.getInvoice()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.invoice"));
				if(invoicesList.getRequestedBy()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.requestedBy"));
				if(invoicesList.getSalesRep()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.salesRep"));
				if(invoicesList.getRatePlan()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.ratePlan"));
				if(invoicesList.getPriorityAndReason()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.priorityAndReason"));
				if(invoicesList.getTowType()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.towType"));
				if(invoicesList.getOrDr()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.orDr"));
				if(invoicesList.getDriverLicense()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.driverLicense"));
				if(invoicesList.getDriverIsOwner()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.driverIsOwner"));
				if(invoicesList.getNoOwnerInfo()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.noOwnerInfo"));
				if(invoicesList.getRegisteredOwner()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.registeredOwner"));
				if(invoicesList.getRegisteredOwnerName()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.registeredOwnerName"));
				if(invoicesList.getRegisteredOwnerAddress()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.registeredOwnerAddress"));
				if(invoicesList.getRegisteredOwnerCity()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.registeredOwnerCity"));
				if(invoicesList.getRegisteredOwnerState()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.registeredOwnerState"));
				if(invoicesList.getRegisteredOwnerZip()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.registeredOwnerZip"));
				if(invoicesList.getRegisteredOwnerPhone()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.registeredOwnerPhone"));
				if(invoicesList.getRegisteredOwnerEmail()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.registeredOwnerEmail"));
				if(invoicesList.getYear()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.year"));
				if(invoicesList.getMake()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.make"));
				if(invoicesList.getModel()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.model"));
				if(invoicesList.getColor()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.color"));
				if(invoicesList.getStyle()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.style"));
				if(invoicesList.getVin()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.vin"));
				if(invoicesList.getPlateCountry()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.plateCountry"));
				if(invoicesList.getPlateState()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.plateState"));
				if(invoicesList.getPlate()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.plate"));
				if(invoicesList.getKeys()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.keys"));
				if(invoicesList.getRadio()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.radio"));
				if(invoicesList.getVehicleStatus()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.vehicleStatus"));
				if(invoicesList.getCommercialUnit()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.commercialUnit"));
				if(invoicesList.getPoliceImpound()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.policeImpound"));
				if(invoicesList.getCallTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.callTime"));
				if(invoicesList.getDispatchTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.dispatchTime"));
				if(invoicesList.getAcceptedTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.acceptedTime"));
				if(invoicesList.getEnrouteTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.enrouteTime"));
				if(invoicesList.getArrivedTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.arrivedTime"));
				if(invoicesList.getHookedTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.hookedTime"));
				if(invoicesList.getDroppedTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.droppedTime"));
				if(invoicesList.getClearTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.clearTime"));
				if(invoicesList.getTotalTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.totalTime"));
				if(invoicesList.getPickupLocation()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.pickupLocation"));
				if(invoicesList.getPickupAddress()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.pickupAddress"));
				if(invoicesList.getPickupCity()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.pickupCity"));
				if(invoicesList.getPickupState()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.pickupState"));
				if(invoicesList.getPickupZipcode()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.pickupZipcode"));
				if(invoicesList.getDropOffLocation()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.dropOffLocation"));
				if(invoicesList.getDropOffAddress()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.dropOffAddress"));
				if(invoicesList.getDropOffCity()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.dropOffCity"));
				if(invoicesList.getDropOffState()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.dropOffState"));
				if(invoicesList.getDropOffZipcode()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.dropOffZipcode"));
				if(invoicesList.getTruck()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.truck"));
				if(invoicesList.getDriverId()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.driverId"));
				if(invoicesList.getDispatchId()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.dispatchId"));
				if(invoicesList.getCallReceiverId()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.callReceiverId"));
				if(invoicesList.getTowCharge()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.towCharge"));
				if(invoicesList.getStorageCharge()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.storageCharge"));
				if(invoicesList.getLaborCharge()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.laborCharge"));
				if(invoicesList.getMileageCharge()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.mileageCharge"));
				if(invoicesList.getWinchCharge()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.winchCharge"));
				if(invoicesList.getGateCharge()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.gateCharge"));
				if(invoicesList.getAdminCharge()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.adminCharge"));
				if(invoicesList.getMiscChargeDesc()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.miscChargeDesc"));
				if(invoicesList.getMiscCharge()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.miscCharge"));
				if(invoicesList.getDiscounts()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.discounts"));
				if(invoicesList.getTotalCharges()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.totalCharges"));
				if(invoicesList.getAmountPaid()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.amountPaid"));
				if(invoicesList.getInStorage()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.inStorage"));
				if(invoicesList.getBillTo()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.billTo"));
				if(invoicesList.getReleasedTo()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.releasedTo"));
				if(invoicesList.getReleaseTime()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.releaseTime"));
				if(invoicesList.getReleaseDate()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.releaseDate"));
				if(invoicesList.getAvrFiledDate()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.avrFiledDate"));
				if(invoicesList.getTrnsfrOfAuth()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.trnsfrOfAuth"));
				if(invoicesList.getTitleDate()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.titleDate"));
				if(invoicesList.getLocked()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.locked"));
				if(invoicesList.getClosed()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.closed"));
				/*excelHeaders.add(excelProps.getProperty("invoices.list.excel.header1"));
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
				excelHeaders.add(excelProps.getProperty("invoices.list.excel.header78"));*/
			}
		}
		catch (Exception e) {
			logger.error(e);			
		}
		return excelHeaders;
	}
	
}
