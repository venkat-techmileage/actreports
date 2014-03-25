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
			for(int i=0;i<invoicesList.getRecCount();i++)
			{
				Map<String,String> excelExpireData=new LinkedHashMap<String,String>();
				if(invoicesList.getServiceCallDate()!=null && i<invoicesList.getServiceCallDate().size())
					excelExpireData.put("serviceCallDate", invoicesList.getServiceCallDate().get(i));
				if(invoicesList.getInvoice()!=null && i<invoicesList.getInvoice().size())
					excelExpireData.put("invoice", invoicesList.getInvoice().get(i));
				if(invoicesList.getRequestedBy()!=null && i<invoicesList.getRequestedBy().size())
					excelExpireData.put("requestedBy", invoicesList.getRequestedBy().get(i));
				if(invoicesList.getSalesRep()!=null && i<invoicesList.getSalesRep().size())
					excelExpireData.put("salesRep", invoicesList.getSalesRep().get(i));
				if(invoicesList.getRatePlan()!=null && i<invoicesList.getRatePlan().size())
					excelExpireData.put("ratePlan", invoicesList.getRatePlan().get(i));
				if(invoicesList.getPriority()!=null && i<invoicesList.getPriority().size())
					excelExpireData.put("priority", invoicesList.getPriority().get(i));
				if(invoicesList.getReason()!=null && i<invoicesList.getReason().size())
					excelExpireData.put("reason", invoicesList.getReason().get(i));
				/*if(invoicesList.getPriorityAndReason()!=null && i<invoicesList.getPriorityAndReason().size())
					excelExpireData.put("priorityAndReason", invoicesList.getPriorityAndReason().get(i));*/
				if(invoicesList.getTowType()!=null && i<invoicesList.getTowType().size())
					excelExpireData.put("towType", invoicesList.getTowType().get(i));
				if(invoicesList.getOrDr()!=null && i<invoicesList.getOrDr().size())
					excelExpireData.put("orDr", invoicesList.getOrDr().get(i));
				if(invoicesList.getDriverLicense()!=null && i<invoicesList.getDriverLicense().size())
					excelExpireData.put("driverLicense", invoicesList.getDriverLicense().get(i));
				if(invoicesList.getDriverIsOwner()!=null && i<invoicesList.getDriverIsOwner().size())
					excelExpireData.put("driverIsOwner", invoicesList.getDriverIsOwner().get(i));
				if(invoicesList.getNoOwnerInfo()!=null && i<invoicesList.getNoOwnerInfo().size())
					excelExpireData.put("noOwnerInfo", invoicesList.getNoOwnerInfo().get(i));
				if(invoicesList.getRegisteredOwner()!=null && i<invoicesList.getRegisteredOwner().size())
					excelExpireData.put("registeredOwner", invoicesList.getRegisteredOwner().get(i));
				if(invoicesList.getRegisteredOwnerName()!=null && i<invoicesList.getRegisteredOwnerName().size())
					excelExpireData.put("registeredOwnerName", invoicesList.getRegisteredOwnerName().get(i));
				if(invoicesList.getRegisteredOwnerAddress()!=null && i<invoicesList.getRegisteredOwnerAddress().size())
					excelExpireData.put("registeredOwnerAddress", invoicesList.getRegisteredOwnerAddress().get(i));
				if(invoicesList.getRegisteredOwnerCity()!=null && i<invoicesList.getRegisteredOwnerCity().size())
					excelExpireData.put("registeredOwnerCity", invoicesList.getRegisteredOwnerCity().get(i));
				if(invoicesList.getRegisteredOwnerState()!=null && i<invoicesList.getRegisteredOwnerState().size())
					excelExpireData.put("registeredOwnerState", invoicesList.getRegisteredOwnerState().get(i));
				if(invoicesList.getRegisteredOwnerZip()!=null && i<invoicesList.getRegisteredOwnerZip().size())
					excelExpireData.put("registeredOwnerZip", invoicesList.getRegisteredOwnerZip().get(i));
				if(invoicesList.getRegisteredOwnerPhone()!=null && i<invoicesList.getRegisteredOwnerPhone().size())
					excelExpireData.put("registeredOwnerPhone", invoicesList.getRegisteredOwnerPhone().get(i));
				if(invoicesList.getRegisteredOwnerEmail()!=null && i<invoicesList.getRegisteredOwnerEmail().size())
					excelExpireData.put("registeredOwnerEmail", invoicesList.getRegisteredOwnerEmail().get(i));
				if(invoicesList.getYear()!=null && i<invoicesList.getYear().size())
					excelExpireData.put("year", invoicesList.getYear().get(i));
				if(invoicesList.getMake()!=null && i<invoicesList.getMake().size())
					excelExpireData.put("make", invoicesList.getMake().get(i));
				if(invoicesList.getModel()!=null && i<invoicesList.getModel().size())
					excelExpireData.put("model", invoicesList.getModel().get(i));
				if(invoicesList.getColor()!=null && i<invoicesList.getColor().size())
					excelExpireData.put("color", invoicesList.getColor().get(i));
				if(invoicesList.getStyle()!=null && i<invoicesList.getStyle().size())
					excelExpireData.put("style", invoicesList.getStyle().get(i));
				if(invoicesList.getVin()!=null && i<invoicesList.getVin().size())
					excelExpireData.put("vin", invoicesList.getVin().get(i));
				if(invoicesList.getPlateCountry()!=null && i<invoicesList.getPlateCountry().size())
					excelExpireData.put("plateCountry", invoicesList.getPlateCountry().get(i));
				if(invoicesList.getPlateState()!=null && i<invoicesList.getPlateState().size())
					excelExpireData.put("plateState", invoicesList.getPlateState().get(i));
				if(invoicesList.getPlate()!=null && i<invoicesList.getPlate().size())
					excelExpireData.put("plate", invoicesList.getPlate().get(i));
				if(invoicesList.getKeys()!=null && i<invoicesList.getKeys().size())
					excelExpireData.put("keys", invoicesList.getKeys().get(i));
				if(invoicesList.getRadio()!=null && i<invoicesList.getRadio().size())
					excelExpireData.put("radio", invoicesList.getRadio().get(i));
				if(invoicesList.getVehicleStatus()!=null && i<invoicesList.getVehicleStatus().size())
					excelExpireData.put("vehicleStatus", invoicesList.getVehicleStatus().get(i));
				if(invoicesList.getCommercialUnit()!=null && i<invoicesList.getCommercialUnit().size())
					excelExpireData.put("commercialUnit", invoicesList.getCommercialUnit().get(i));
				if(invoicesList.getPoliceImpound()!=null && i<invoicesList.getPoliceImpound().size())
					excelExpireData.put("policeImpound", invoicesList.getPoliceImpound().get(i));
				if(invoicesList.getCallTime()!=null && i<invoicesList.getCallTime().size())
					excelExpireData.put("callTime", invoicesList.getCallTime().get(i));
				if(invoicesList.getDispatchTime()!=null && i<invoicesList.getDispatchTime().size())
					excelExpireData.put("dispatchTime", invoicesList.getDispatchTime().get(i));
				if(invoicesList.getAcceptedTime()!=null && i<invoicesList.getAcceptedTime().size())
					excelExpireData.put("acceptedTime", invoicesList.getAcceptedTime().get(i));
				if(invoicesList.getEnrouteTime()!=null && i<invoicesList.getEnrouteTime().size())
					excelExpireData.put("enrouteTime", invoicesList.getEnrouteTime().get(i));
				if(invoicesList.getArrivedTime()!=null && i<invoicesList.getArrivedTime().size())
					excelExpireData.put("arrivedTime", invoicesList.getArrivedTime().get(i));
				if(invoicesList.getHookedTime()!=null && i<invoicesList.getHookedTime().size())
					excelExpireData.put("hookedTime", invoicesList.getHookedTime().get(i));
				if(invoicesList.getDroppedTime()!=null && i<invoicesList.getDroppedTime().size())
					excelExpireData.put("droppedTime", invoicesList.getDroppedTime().get(i));
				if(invoicesList.getClearTime()!=null && i<invoicesList.getClearTime().size())
					excelExpireData.put("clearTime", invoicesList.getClearTime().get(i));
				if(invoicesList.getTotalTime()!=null && i<invoicesList.getTotalTime().size())
					excelExpireData.put("totalTime", invoicesList.getTotalTime().get(i));
				if(invoicesList.getPickupLocation()!=null && i<invoicesList.getPickupLocation().size())
					excelExpireData.put("pickupLocation", invoicesList.getPickupLocation().get(i));
				if(invoicesList.getPickupAddress()!=null && i<invoicesList.getPickupAddress().size())
					excelExpireData.put("pickupAddress", invoicesList.getPickupAddress().get(i));
				if(invoicesList.getPickupCity()!=null && i<invoicesList.getPickupCity().size())
					excelExpireData.put("pickupCity", invoicesList.getPickupCity().get(i));
				if(invoicesList.getPickupState()!=null && i<invoicesList.getPickupState().size())
					excelExpireData.put("pickupState", invoicesList.getPickupState().get(i));
				if(invoicesList.getPickupZipcode()!=null && i<invoicesList.getPickupZipcode().size())
					excelExpireData.put("pickupZipcode", invoicesList.getPickupZipcode().get(i));
				if(invoicesList.getDropOffLocation()!=null && i<invoicesList.getDropOffLocation().size())
					excelExpireData.put("dropOffLocation", invoicesList.getDropOffLocation().get(i));
				if(invoicesList.getDropOffAddress()!=null && i<invoicesList.getDropOffAddress().size())
					excelExpireData.put("dropOffAddress", invoicesList.getDropOffAddress().get(i));
				if(invoicesList.getDropOffCity()!=null && i<invoicesList.getDropOffCity().size())
					excelExpireData.put("dropOffCity", invoicesList.getDropOffCity().get(i));
				if(invoicesList.getDropOffState()!=null && i<invoicesList.getDropOffState().size())
					excelExpireData.put("dropOffState", invoicesList.getDropOffState().get(i));
				if(invoicesList.getDropOffZipcode()!=null && i<invoicesList.getDropOffZipcode().size())
					excelExpireData.put("dropOffZipcode", invoicesList.getDropOffZipcode().get(i));
				if(invoicesList.getTruck()!=null && i<invoicesList.getTruck().size())
					excelExpireData.put("truck", invoicesList.getTruck().get(i));
				if(invoicesList.getDriverId()!=null && i<invoicesList.getDriverId().size())
					excelExpireData.put("driverId", invoicesList.getDriverId().get(i));
				if(invoicesList.getDispatchId()!=null && i<invoicesList.getDispatchId().size())
					excelExpireData.put("dispatchId", invoicesList.getDispatchId().get(i));
				if(invoicesList.getCallReceiverId()!=null && i<invoicesList.getCallReceiverId().size())
					excelExpireData.put("callReceiverId", invoicesList.getCallReceiverId().get(i));
				if(invoicesList.getTowCharge()!=null && i<invoicesList.getTowCharge().size())
					excelExpireData.put("towCharge", invoicesList.getTowCharge().get(i));
				if(invoicesList.getStorageCharge()!=null && i<invoicesList.getStorageCharge().size())
					excelExpireData.put("storageCharge", invoicesList.getStorageCharge().get(i));
				if(invoicesList.getLaborCharge()!=null && i<invoicesList.getLaborCharge().size())
					excelExpireData.put("laborCharge", invoicesList.getLaborCharge().get(i));
				if(invoicesList.getMileageCharge()!=null && i<invoicesList.getMileageCharge().size())
					excelExpireData.put("mileageCharge", invoicesList.getMileageCharge().get(i));
				if(invoicesList.getWinchCharge()!=null && i<invoicesList.getWinchCharge().size())
					excelExpireData.put("winchCharge", invoicesList.getWinchCharge().get(i));
				if(invoicesList.getGateCharge()!=null && i<invoicesList.getGateCharge().size())
					excelExpireData.put("gateCharge", invoicesList.getGateCharge().get(i));
				if(invoicesList.getAdminCharge()!=null && i<invoicesList.getAdminCharge().size())
					excelExpireData.put("adminCharge", invoicesList.getAdminCharge().get(i));
				if(invoicesList.getMiscChargeDesc()!=null && i<invoicesList.getMiscChargeDesc().size())
					excelExpireData.put("miscChargeDesc", invoicesList.getMiscChargeDesc().get(i));
				if(invoicesList.getMiscCharge()!=null && i<invoicesList.getMiscCharge().size())
					excelExpireData.put("miscCharge", invoicesList.getMiscCharge().get(i));
				if(invoicesList.getDiscounts()!=null && i<invoicesList.getDiscounts().size())
					excelExpireData.put("discounts", invoicesList.getDiscounts().get(i));
				if(invoicesList.getTotalCharges()!=null && i<invoicesList.getTotalCharges().size())
					excelExpireData.put("totalCharges", invoicesList.getTotalCharges().get(i));
				if(invoicesList.getAmountPaid()!=null && i<invoicesList.getAmountPaid().size())
					excelExpireData.put("amountPaid", invoicesList.getAmountPaid().get(i));
				if(invoicesList.getInStorage()!=null && i<invoicesList.getInStorage().size())
					excelExpireData.put("inStorage", invoicesList.getInStorage().get(i));
				if(invoicesList.getBillTo()!=null && i<invoicesList.getBillTo().size())
					excelExpireData.put("billTo", invoicesList.getBillTo().get(i));
				if(invoicesList.getReleasedTo()!=null && i<invoicesList.getReleasedTo().size())
					excelExpireData.put("releasedTo", invoicesList.getReleasedTo().get(i));
				if(invoicesList.getReleaseTime()!=null && i<invoicesList.getReleaseTime().size())
					excelExpireData.put("releaseTime", invoicesList.getReleaseTime().get(i));
				if(invoicesList.getReleaseDate()!=null && i<invoicesList.getReleaseDate().size())
					excelExpireData.put("releaseDate", invoicesList.getReleaseDate().get(i));
				if(invoicesList.getAvrFiledDate()!=null && i<invoicesList.getAvrFiledDate().size())
					excelExpireData.put("avrFiledDate", invoicesList.getAvrFiledDate().get(i));
				if(invoicesList.getTrnsfrOfAuth()!=null && i<invoicesList.getTrnsfrOfAuth().size())
					excelExpireData.put("trnsfrOfAuth", invoicesList.getTrnsfrOfAuth().get(i));
				if(invoicesList.getTitleDate()!=null && i<invoicesList.getTitleDate().size())
					excelExpireData.put("titleDate", invoicesList.getTitleDate().get(i));
				if(invoicesList.getLocked()!=null && i<invoicesList.getLocked().size())
					excelExpireData.put("locked", invoicesList.getLocked().get(i));
				if(invoicesList.getClosed()!=null && i<invoicesList.getClosed().size())
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
				if(invoicesList.getPriority()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.priority"));
				if(invoicesList.getReason()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.reason"));
				/*if(invoicesList.getPriorityAndReason()!=null)
					excelHeaders.add(excelProps.getProperty("invoices.excel.header.priorityAndReason"));*/
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
			}
		}
		catch (Exception e) {
			logger.error(e);			
		}
		return excelHeaders;
	}
	
}
