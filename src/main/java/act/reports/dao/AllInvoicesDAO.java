package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.AccountNames;
import act.reports.model.AllInvoices;
import act.reports.model.AllInvoicesList;
import act.reports.model.DriverIDs;
import act.reports.model.InvoiceSearchCriteria;
import act.reports.model.Reasons;
import act.reports.model.SalesReps;
import act.reports.model.TowTypes;
import act.reports.model.TruckIDs;
import act.reports.util.DateUtility;

@Repository("allInvoicesDAO")
public class AllInvoicesDAO {

	private Logger logger=Logger.getLogger(AllInvoicesDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public AllInvoicesList getAllInvoicesList(InvoiceSearchCriteria criteria){
		logger.info("In AllInvoicesDAO-getAllInvoicesList()...");		
		String allInvoicesQuery = "";
		List<AllInvoices> allInvoices = null;
		AllInvoicesList allInvoicesList = new AllInvoicesList();		
		try{
			String fromDate=criteria.getFromDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in AllInvoicesDAO-getAllInvoicesList() : "+fromDate);
			String toDate=criteria.getToDate();
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in AllInvoicesDAO-getAllInvoicesList() : "+toDate);
			String searchString = criteria.getSearchQuery();
			logger.info("searchString in AllInvoicesDAO-getAllInvoicesList() : "+searchString);
			int index = searchString.indexOf("-");
			logger.info("index = "+index);			
			final String salesRep = criteria.getSalesRep();
			
			if(index != -1){
				if(criteria.getSearchQuery().substring(0, index).equalsIgnoreCase("byServiceCallDate")){
					if(searchString.substring(index+1).equalsIgnoreCase("driver")){
						logger.info("driver in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getDriver());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
										   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
										   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
										   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
										   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
										   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
										   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
										   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
										   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice_Billing ib,Invoice_Vehicle iv,UserAuth u,Invoice i " +
										   "LEFT OUTER JOIN Invoice_Release ir ON i.invoiceId=ir.invoiceId LEFT OUTER JOIN Invoice_TitleProcess itp ON i.invoiceId=itp.invoiceId where u.userId='"+criteria.getDriver()+"' and sc.callCreatedTime " +
										   "BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and i.driverId=u.Employee_idEmployee and sc.serviceCallId = i.serviceCallId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
					else if(searchString.substring(index+1).equalsIgnoreCase("truck")){
						logger.info("truck in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getTruck());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
								   		   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
								   		   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
								   		   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
								   		   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
								   		   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
								   		   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
								   		   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
								   		   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice_Billing ib,Invoice_Vehicle iv,Invoice i " +
								   		   "LEFT OUTER JOIN Invoice_Release ir ON i.invoiceId=ir.invoiceId LEFT OUTER JOIN Invoice_TitleProcess itp ON i.invoiceId=itp.invoiceId where i.truckNo='"+criteria.getTruck()+"' and " +
								   		   "sc.callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId = i.serviceCallId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
					} 
					else if(searchString.substring(index+1).equalsIgnoreCase("towType")){
						logger.info("towType in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getTowType());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
								   		   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
								   		   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
								   		   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
								   		   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
								   		   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
								   		   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
								   		   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
								   		   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice_Billing ib,Invoice_Vehicle iv,Invoice i " +
								   		   "LEFT OUTER JOIN Invoice_Release ir ON i.invoiceId=ir.invoiceId LEFT OUTER JOIN Invoice_TitleProcess itp ON i.invoiceId=itp.invoiceId where i.towType='"+criteria.getTowType()+"' and " +
								   		   "sc.callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId = i.serviceCallId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
					else if(searchString.substring(index+1).equalsIgnoreCase("reason")){
						logger.info("reason in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getReason());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
								   		   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
								   		   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
								   		   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
								   		   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
								   		   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
								   		   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
								   		   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
								   		   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice_Billing ib,Invoice_Vehicle iv,Invoice i " +
								   		   "LEFT OUTER JOIN Invoice_Release ir ON i.invoiceId=ir.invoiceId LEFT OUTER JOIN Invoice_TitleProcess itp ON i.invoiceId=itp.invoiceId where i.reason='"+criteria.getReason()+"' and " +
								   		   "sc.callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId = i.serviceCallId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
					else if(searchString.substring(index+1).equalsIgnoreCase("account")){
						logger.info("account in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getAccount());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
										   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
										   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
										   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
										   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
										   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
										   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
										   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
										   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice_Billing ib,Invoice_Vehicle iv,Invoice i " +
										   "LEFT OUTER JOIN Invoice_Release ir ON i.invoiceId=ir.invoiceId LEFT OUTER JOIN Invoice_TitleProcess itp ON i.invoiceId=itp.invoiceId where a.name='"+criteria.getAccount()+"' and sc.callCreatedTime " +
										   "BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId = i.serviceCallId and a.idAccount=i.accountId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
					else if(searchString.substring(index+1).equalsIgnoreCase("salesRep")){
						logger.info("salesRep in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getSalesRep());
						int index1 = salesRep.indexOf(" ");
						String fname = salesRep.substring(0, index1);
						String lname = salesRep.substring(index1+1);
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
										   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
										   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
										   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
										   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
										   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
										   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
										   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
										   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice_Billing ib,Invoice_Vehicle iv,Employee e,Invoice i " +
										   "LEFT OUTER JOIN Invoice_Release ir ON i.invoiceId=ir.invoiceId LEFT OUTER JOIN Invoice_TitleProcess itp ON i.invoiceId=itp.invoiceId where e.firstName='"+fname+"' and e.lastName='"+lname+"' and sc.callCreatedTime BETWEEN " +
										   "'"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and a.salesRepId=e.idEmployee and sc.serviceCallId = i.serviceCallId and a.idAccount=i.accountId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
				}
				else if(criteria.getSearchQuery().substring(0, index).equalsIgnoreCase("byReleaseDate")){
					if(searchString.substring(index+1).equalsIgnoreCase("driver")){
						logger.info("driver in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getDriver());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
										   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
										   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
										   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
										   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
										   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
										   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
										   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
										   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice i,Invoice_Billing ib,Invoice_Vehicle iv,UserAuth u,Invoice_Release ir " +
										   "LEFT OUTER JOIN Invoice_TitleProcess itp ON ir.invoiceId=itp.invoiceId where u.userId='"+criteria.getDriver()+"' and ir.releaseDate" +
										   " BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and i.driverId=u.Employee_idEmployee and sc.serviceCallId=i.serviceCallId and ir.invoiceId=i.invoiceId and i.invoiceId=ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
					else if(searchString.substring(index+1).equalsIgnoreCase("truck")){
						logger.info("truck in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getTruck());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
								   		   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
								   		   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
								   		   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
								   		   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
								   		   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
								   		   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
								   		   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
								   		   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice i,Invoice_Billing ib,Invoice_Vehicle iv,Invoice_Release ir " +
								   		   "LEFT OUTER JOIN Invoice_TitleProcess itp ON ir.invoiceId=itp.invoiceId where i.truckNo='"+criteria.getTruck()+"' and " +
								   		   "ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId=i.serviceCallId and ir.invoiceId=i.invoiceId and i.invoiceId=ib.invoiceId and i.invoiceId=iv.invoiceId";
					} 
					else if(searchString.substring(index+1).equalsIgnoreCase("towType")){
						logger.info("towType in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getTowType());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
								   		   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
								   		   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
								   		   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
								   		   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
								   		   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
								   		   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
								   		   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
								   		   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice i,Invoice_Billing ib,Invoice_Vehicle iv,Invoice_Release ir " +
								   		   "LEFT OUTER JOIN Invoice_TitleProcess itp ON ir.invoiceId=itp.invoiceId where i.towType='"+criteria.getTowType()+"' and " +
								   		   "ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId=i.serviceCallId and ir.invoiceId=i.invoiceId and i.invoiceId=ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
					else if(searchString.substring(index+1).equalsIgnoreCase("reason")){
						logger.info("reason in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getReason());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
								   		   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
								   		   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
								   		   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
								   		   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
								   		   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
								   		   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
								   		   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
								   		   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice i,Invoice_Billing ib,Invoice_Vehicle iv,Invoice_Release ir " +
								   		   "LEFT OUTER JOIN Invoice_TitleProcess itp ON ir.invoiceId=itp.invoiceId where i.reason='"+criteria.getReason()+"' and " +
								   		   "ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId=i.serviceCallId and ir.invoiceId=i.invoiceId and i.invoiceId=ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
					else if(searchString.substring(index+1).equalsIgnoreCase("account")){
						logger.info("account in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getAccount());
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
										   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
										   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
										   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
										   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
										   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
										   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
										   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status," +
										   "iv.vehicle_Comm_Unit_No,iv.isImpound,ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice i,Invoice_Billing ib,Invoice_Vehicle iv,Invoice_Release ir " +
										   "LEFT OUTER JOIN Invoice_TitleProcess itp ON ir.invoiceId=itp.invoiceId where a.name='"+criteria.getAccount()+"' and ir.releaseDate " +
										   "BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId=i.serviceCallId and ir.invoiceId=i.invoiceId and a.idAccount=i.accountId and i.invoiceId=ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
					else if(searchString.substring(index+1).equalsIgnoreCase("salesRep")){						
						logger.info("salesRep in AllInvoicesDAO-getAllInvoicesList() : "+criteria.getSalesRep());
						int index1 = salesRep.indexOf(" ");
						String fname = salesRep.substring(0, index1);
						String lname = salesRep.substring(index1+1);
						allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime," +
										   "i.arrivedTime,i.hookedTime,i.dropOffTime,i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City," +
										   "i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
										   "i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
										   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner," +
										   "iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip," +
										   "iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN," +
										   "iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status,iv.vehicle_Comm_Unit_No,iv.isImpound," +
										   "ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice i,Invoice_Billing ib,Invoice_Vehicle iv,Employee e,Invoice_Release ir " +
										   "LEFT OUTER JOIN Invoice_TitleProcess itp ON ir.invoiceId=itp.invoiceId where e.firstName='"+fname+"' and e.lastName='"+lname+"' and ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' " +
										   "and a.idAccount=i.accountId and a.salesRepId=e.idEmployee and sc.serviceCallId=i.serviceCallId and ir.invoiceId=i.invoiceId and a.idAccount=i.accountId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
					}
				}
			}
			else{
				if(criteria.getSearchQuery().equalsIgnoreCase("byServiceCallDate")){
					allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime,i.arrivedTime,i.hookedTime,i.dropOffTime," +
							   		   "i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City,i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1," +
							   		   "i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City,i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
							   		   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner,iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1," +
							   		   "iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip,iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color," +
							   		   "iv.vehicle_Style,iv.vehicle_VIN,iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status,iv.vehicle_Comm_Unit_No,iv.isImpound," +
							   		   "ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice_Billing ib,Invoice_Vehicle iv,UserAuth u,Invoice i LEFT OUTER JOIN Invoice_Release ir ON i.invoiceId=ir.invoiceId LEFT OUTER JOIN Invoice_TitleProcess itp " +
							   		   "ON i.invoiceId=itp.invoiceId where sc.callCreatedTime BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and i.driverId=u.Employee_idEmployee and sc.serviceCallId=i.serviceCallId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
				}
				else if(criteria.getSearchQuery().equalsIgnoreCase("byReleaseDate")){
					allInvoicesQuery = "select a.name,a.salesRepId,sc.serviceCallId,sc.callRecevierId,sc.callRecevierFullName,sc.callCreatedTime,i.invoiceId,i.priority,i.reason,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime,i.arrivedTime,i.hookedTime,i.dropOffTime," +
							   		   "i.clearedTime,i.elapsedTime,i.pickUpLocation,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City,i.pickUpAddr_Address_State,i.towType,i.pickUpAddr_Address_Zip,i.dropOffLocation,i.dropOffAddr_Address_Line1," +
							   		   "i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City,i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,i.assignedBy,ib.acountRatePlan,ib.towCharge,ib.storageCharge,ib.laborCharge,ib.mileageCharge," +
							   		   "ib.winch_Charge,ib.miscCharge,ib.gateCharge,ib.totalCharge,ib.PONo,ib.totalDiscount,iv.isDriverIsOwner,iv.noInfo,iv.vehicle_OR_DR_No,iv.isDriverIsOwner,iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1," +
							   		   "iv.vehicle_Owner_Addr_Line2,iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip,iv.vehicle_Owner_Email,iv.vehicle_Owner_PhoneNo,iv.vehicle_Info_DLNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_Color,iv.vehicle_Style," +
							   		   "iv.vehicle_VIN,iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_keys,iv.vehicle_Key_Location,iv.vehicle_ODM_Reading,iv.police_impound,iv.vehicle_Radio,iv.vehicle_Status,iv.vehicle_Comm_Unit_No,iv.isImpound," +
							   		   "ir.releaseTo,ir.releaseDate,itp.avrDate,itp.titleDate from Account a,ServiceCallInfo sc,Invoice i,Invoice_Billing ib,Invoice_Vehicle iv,Invoice_Release ir LEFT OUTER JOIN Invoice_TitleProcess itp ON ir.invoiceId=itp.invoiceId " +
							   		   "where ir.releaseDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=i.accountId and sc.serviceCallId=i.serviceCallId and ir.invoiceId=i.invoiceId and a.idAccount=i.accountId and i.invoiceId=ib.invoiceId and i.invoiceId=iv.invoiceId";
				}
			}
				
			allInvoices = jdbcTemplate.query(allInvoicesQuery, new Object[] {}, new RowMapper<AllInvoices>() {

				public AllInvoices mapRow(ResultSet rs, int rowNum) throws SQLException {

					AllInvoices allInvoices = new AllInvoices();

					allInvoices.setRequestedBy(rs.getString("name") != null ? rs.getString("name") : " "); //,a.
					
					int salesRepId = rs.getInt("salesRepId");
					if(salesRepId!=0){
						List<SalesReps> salesRepsList = getSalesRepName(salesRepId);
						allInvoices.setSalesRep(salesRepsList.get(0).getSalesRep());
					}
					else
						allInvoices.setSalesRep(" ");

					
					allInvoices.setServiceCallId(rs.getString("serviceCallId") != null ? rs.getString("serviceCallId") : " ");

					String callCreatedDate = rs.getString("callCreatedTime") != null ? rs.getString("callCreatedTime") : " ";					
					if(!callCreatedDate.trim().isEmpty()){
						callCreatedDate=DateUtility.convertToDateTimeFormat(callCreatedDate);
						callCreatedDate = callCreatedDate.substring(0, 10);
					}
					allInvoices.setServiceCallDate(callCreatedDate);
					
					String invoiceId = rs.getString("invoiceId") != null ? rs.getString("invoiceId") : " ";
					allInvoices.setInvoice(invoiceId);					
										
					int ratePlan = rs.getInt("acountRatePlan");	
					String ratePlanName = " ";
					if(ratePlan != 0){
						String ratePlanNameQry = "select AccountPlanRateName from AccountRatePlan where AccountRatePlanId='"+ratePlan+"'";
						ratePlanName = jdbcTemplate.queryForObject(ratePlanNameQry, String.class)!=null ? jdbcTemplate.queryForObject(ratePlanNameQry, String.class) : " ";						
					}
					logger.info("ratePlanName in AllInvoicesDAO-getAllInvoicesList() : "+ratePlanName);
					allInvoices.setRatePlan(ratePlanName);
					
					int priority = rs.getInt("priority");
					allInvoices.setPriority(priority);
					
					String reason = rs.getString("reason")!= null ? rs.getString("reason") : " ";
					allInvoices.setReason(reason);
					//allInvoices.setPriorityAndReason((Integer.toString(priority)+ ", "+ reason));				    	
					
					allInvoices.setTowType(rs.getString("towType") != null ? rs.getString("towType") : " ");
					allInvoices.setOrDr((rs.getString("vehicle_OR_DR_No") != null) ? rs.getString("vehicle_OR_DR_No") : " ");	
					allInvoices.setDriverLicense((rs.getString("vehicle_Info_DLNo") != null) ? rs.getString("vehicle_Info_DLNo") : " ");	

					String isDriverIsOwner=((rs.getString("isDriverIsOwner") != null) ? rs.getString("isDriverIsOwner") : " ");
					if(!isDriverIsOwner.trim().equalsIgnoreCase("")){
						if(isDriverIsOwner.equalsIgnoreCase("1"))
							isDriverIsOwner = "Y";
						else
							isDriverIsOwner = "N";
					}
					allInvoices.setDriverIsOwner(isDriverIsOwner);
					
					String noOwnerInfo = rs.getString("noInfo") != null ? rs.getString("noInfo") : " ";
					logger.info("noOwnerInfo in AllInvoicesDAO-getAllInvoicesList() : "+noOwnerInfo);
					if(!noOwnerInfo.trim().isEmpty() && noOwnerInfo.equalsIgnoreCase("1"))
						allInvoices.setNoOwnerInfo("Y");					
					else if(!noOwnerInfo.trim().isEmpty() && noOwnerInfo.equalsIgnoreCase("0"))
						allInvoices.setNoOwnerInfo("N");
					else
						allInvoices.setNoOwnerInfo(noOwnerInfo);
					
					//allInvoices.setRegisteredOwnerName(rs.getString("vehicle_Owner_FirstName")+" "+rs.getString("vehicle_Owner_LastName"));
					String firstName = rs.getString("vehicle_Owner_FirstName")!=null?rs.getString("vehicle_Owner_FirstName"):" ";
					String lastName = rs.getString("vehicle_Owner_LastName")!=null?rs.getString("vehicle_Owner_LastName"):" ";
					allInvoices.setRegisteredOwnerName(firstName+" "+lastName); 

					String firstAdd = rs.getString("vehicle_Owner_Addr_Line1")!=null?rs.getString("vehicle_Owner_Addr_Line1"):" ";
					String lastAdd = rs.getString("vehicle_Owner_Addr_Line2")!=null?rs.getString("vehicle_Owner_Addr_Line2"):" ";
					allInvoices.setRegisteredOwnerAddress(firstAdd+" "+lastAdd); 

					allInvoices.setRegisteredOwnerCity((rs.getString("vehicle_Owner_City") != null) ? rs.getString("vehicle_Owner_City") : " ");	
					allInvoices.setRegisteredOwnerState((rs.getString("vehicle_Owner_State") != null) ? rs.getString("vehicle_Owner_State") : " ");
					
					int registeredOwnerZip = rs.getInt("vehicle_Owner_Zip");
					if(Integer.toString(registeredOwnerZip).length() > 1)
						allInvoices.setRegisteredOwnerZip(String.format("%05d", registeredOwnerZip));
					else
						allInvoices.setRegisteredOwnerZip(Integer.toString(registeredOwnerZip));
					
					allInvoices.setRegisteredOwnerPhone((rs.getString("vehicle_Owner_PhoneNo") != null) ? rs.getString("vehicle_Owner_PhoneNo") : " ");
					allInvoices.setRegisteredOwnerEmail((rs.getString("vehicle_Owner_Email") != null) ? rs.getString("vehicle_Owner_Email") : " ");
					allInvoices.setYear(rs.getInt("vehicle_Year"));
					allInvoices.setMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : " ");
					allInvoices.setModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : " ");	
					allInvoices.setColor((rs.getString("vehicle_Color") != null) ? rs.getString("vehicle_Color") : " ");	
					allInvoices.setStyle((rs.getString("vehicle_Style") != null) ? rs.getString("vehicle_Style") : " ");	
					allInvoices.setVin((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : " ");	
					allInvoices.setPlateCountry((rs.getString("vehicle_Country") != null) ? rs.getString("vehicle_Country") : " ");
					allInvoices.setPlateState((rs.getString("vehicle_State") != null) ? rs.getString("vehicle_State") : " ");
					allInvoices.setPlate((rs.getString("vehicle_Plate_No") != null) ? rs.getString("vehicle_Plate_No") : " ");
					
					//allInvoices.setKeys((rs.getString("vehicle_keys") != null) ? rs.getString("vehicle_keys") : ""); //yes (or) no
					String keys=((rs.getString("vehicle_keys") != null) ? rs.getString("vehicle_keys") : " ");
					if(!keys.trim().equalsIgnoreCase("")){
						if(keys.equalsIgnoreCase("1"))
							allInvoices.setKeys("Y");
						else
							allInvoices.setKeys("N");
					}
					
					allInvoices.setKeysLocation((rs.getString("vehicle_Key_Location") != null) ? rs.getString("vehicle_Key_Location") : " ");
					allInvoices.setVehicleMileage((rs.getString("vehicle_ODM_Reading") != null) ? rs.getString("vehicle_ODM_Reading") : " ");
					
					//allInvoices.setRadio((rs.getString("vehicle_Radio") != null) ? rs.getString("vehicle_Radio") : "");
					String radio=((rs.getString("vehicle_Radio") != null) ? rs.getString("vehicle_Radio") : " ");
					if(!radio.trim().equalsIgnoreCase("")){
						if(radio.equalsIgnoreCase("1"))
							allInvoices.setRadio("Y");
						else
							allInvoices.setRadio("N");
					}
					
					allInvoices.setVehicleStatus((rs.getString("vehicle_Status") != null) ? rs.getString("vehicle_Status") : " ");
					allInvoices.setCommercialUnit((rs.getString("vehicle_Comm_Unit_No") != null) ? rs.getString("vehicle_Comm_Unit_No") : " ");
					allInvoices.setPoliceImpound((rs.getString("police_impound") != null) ? rs.getString("police_impound") : " ");
					
					String requestTime = rs.getString("requestTime") != null ? rs.getString("requestTime") : " ";
					if(!requestTime.trim().isEmpty())
					      allInvoices.setCallTime(DateUtility.convertToDateTimeFormat(requestTime));
					     else
					      allInvoices.setCallTime(requestTime);
					
					String dispatchTime = rs.getString("dispatchTime") != null ? rs.getString("dispatchTime") : " ";
					if(!dispatchTime.trim().isEmpty())
					      allInvoices.setDispatchTime(DateUtility.convertToDateTimeFormat(dispatchTime));
					   else
					allInvoices.setDispatchTime(dispatchTime);
				
					String acceptedTime = rs.getString("acceptedTime") != null ? rs.getString("acceptedTime") : " ";
					if(!acceptedTime.trim().isEmpty())
					      allInvoices.setAcceptedTime(DateUtility.convertToDateTimeFormat(acceptedTime));
					   else
					allInvoices.setAcceptedTime(acceptedTime);
					
					String enrouteTime = rs.getString("enrouteTime") != null ? rs.getString("enrouteTime") : " ";
					if(!enrouteTime.trim().isEmpty())
					      allInvoices.setEnrouteTime(DateUtility.convertToDateTimeFormat(enrouteTime));
					   else
					     allInvoices.setEnrouteTime(enrouteTime);
					
					String arrivedTime = rs.getString("arrivedTime") != null ? rs.getString("arrivedTime") : " ";
					if(!arrivedTime.trim().isEmpty())
					      allInvoices.setArrivedTime(DateUtility.convertToDateTimeFormat(arrivedTime));
					      else
					      allInvoices.setArrivedTime(arrivedTime);
					
					String hookedTime = rs.getString("hookedTime") != null ? rs.getString("hookedTime") : " ";
					if(!hookedTime.trim().isEmpty())
					      allInvoices.setHookedTime(DateUtility.convertToDateTimeFormat(hookedTime));
					      else
					      allInvoices.setHookedTime(hookedTime);
					
					String dropOffTime = rs.getString("dropOffTime") != null ? rs.getString("dropOffTime") : " ";
					if(!dropOffTime.trim().isEmpty())
					      allInvoices.setDroppedTime(DateUtility.convertToDateTimeFormat(dropOffTime));
					      else
					      allInvoices.setDroppedTime(dropOffTime);
					
					String clearedTime = rs.getString("clearedTime") != null ? rs.getString("clearedTime") : " ";
					if(!clearedTime.trim().isEmpty())
					      allInvoices.setClearTime(DateUtility.convertToDateTimeFormat(clearedTime));
					     else
					      allInvoices.setClearTime(clearedTime);
					
					String totalTime = " ";
					if(!requestTime.trim().isEmpty() && !clearedTime.trim().isEmpty()){
						String callToClearedQry = "SELECT TIMESTAMPDIFF(MINUTE,'"+requestTime+"','"+clearedTime+"')";
						totalTime = jdbcTemplate.queryForObject(callToClearedQry, String.class);
					}
					allInvoices.setTotalTime(totalTime);
					
					allInvoices.setPickupLocation(rs.getString("pickUpLocation") != null ? rs.getString("pickUpLocation") : " ");
					allInvoices.setPickupAddress(rs.getString("pickUpAddr_Address_Line1") != null ? rs.getString("pickUpAddr_Address_Line1") : " ");
					allInvoices.setPickupCity(rs.getString("pickUpAddr_Address_City") != null ? rs.getString("pickUpAddr_Address_City") : " ");
					allInvoices.setPickupState(rs.getString("pickUpAddr_Address_State") != null ? rs.getString("pickUpAddr_Address_State") : " ");
					allInvoices.setPickupZipcode(rs.getInt("pickUpAddr_Address_Zip"));
					allInvoices.setDropOffLocation(rs.getString("dropOffLocation") != null ? rs.getString("dropOffLocation") : " ");
					allInvoices.setDropOffAddress(rs.getString("dropOffAddr_Address_Line1") != null ? rs.getString("dropOffAddr_Address_Line1") : " ");
					allInvoices.setDropOffCity(rs.getString("dropOffAddr_Address_City") != null ? rs.getString("dropOffAddr_Address_City") : " ");
					allInvoices.setDropOffState(rs.getString("dropOffAddr_Address_State") != null ? rs.getString("dropOffAddr_Address_State") : " ");
					allInvoices.setDropOffZipcode(rs.getInt("dropOffAddr_Address_Zip"));
					allInvoices.setTruck(rs.getInt("truckNo"));
					
					String driverId = Integer.toString(rs.getInt("driverId"));
					if(!driverId.equalsIgnoreCase("0")){
						String driverIdQry = "select u.userId from UserAuth u,Employee e where e.idEmployee='"+driverId+"' and e.idEmployee=u.Employee_idEmployee";
						driverId = jdbcTemplate.queryForObject(driverIdQry, String.class)!=null ? jdbcTemplate.queryForObject(driverIdQry, String.class) : " ";						
					}
					logger.info("driverId in AllInvoicesDAO-getAllInvoicesList() : "+driverId);
					allInvoices.setDriverId(driverId);
					
					allInvoices.setDispatchId(rs.getString("assignedBy") != null ? rs.getString("assignedBy") : " ");
					allInvoices.setCallReceiverId(rs.getString("callRecevierId") != null ? rs.getString("callRecevierId") : " ");
					allInvoices.setTowCharge(rs.getString("towCharge") != null ? rs.getString("towCharge") : " ");
					allInvoices.setStorageCharge(rs.getString("storageCharge") != null ? rs.getString("storageCharge") : " ");
					allInvoices.setLaborCharge(rs.getString("laborCharge") != null ? rs.getString("laborCharge") : " ");
					allInvoices.setMileageCharge(rs.getString("mileageCharge") != null ? rs.getString("mileageCharge") : " ");
					allInvoices.setWinchCharge(rs.getString("winch_Charge") != null ? rs.getString("winch_Charge") : " ");
					allInvoices.setGateCharge(rs.getString("gateCharge") != null ? rs.getString("gateCharge") : " ");
					allInvoices.setAdminCharge("0.00");
					allInvoices.setMiscChargeDesc(" ");
					allInvoices.setMiscCharge(rs.getString("miscCharge") != null ? rs.getString("miscCharge") : " ");
					allInvoices.setDiscounts(rs.getString("totalDiscount") != null ? rs.getString("totalDiscount") : " ");
					allInvoices.setTotalCharges(rs.getString("totalCharge") != null ? rs.getString("totalCharge") : " ");
					allInvoices.setPoNo(rs.getString("PONo") != null ? rs.getString("PONo") : " ");
					
					float amountPaid = 0.0f;
					if(!invoiceId.trim().isEmpty()){
						amountPaid = getInvoiceBillingPayment(invoiceId);
						allInvoices.setAmountPaid(roundUp(amountPaid, 2));
					}
					else
						allInvoices.setAmountPaid("0.00");
						
					String vehicleIsImpound = rs.getString("isImpound") != null ? rs.getString("isImpound") : " ";
					String inStorage = "";
					if(!vehicleIsImpound.trim().isEmpty()){
						if(vehicleIsImpound.equalsIgnoreCase("1"))
							inStorage = "Y";
						else
							inStorage = "N";
					}
					allInvoices.setInStorage(inStorage);
					
					allInvoices.setBillTo(" ");
					
					int releaseTo = rs.getInt("releaseTo");
					allInvoices.setReleasedTo(getAccountName(releaseTo));
					
					String releaseDate = rs.getString("releaseDate")!=null ? rs.getString("releaseDate") : " ";
					logger.info("releaseDate = "+releaseDate);
					String releaseTime = " ";
					if(!releaseDate.trim().isEmpty() && releaseDate.trim().length()>10){
						releaseDate = DateUtility.convertToDateTimeFormat(releaseDate);
						allInvoices.setReleaseDate(releaseDate.substring(0, 10));
						allInvoices.setReleaseTime(releaseDate.substring(10));
					}
					else{
						allInvoices.setReleaseDate(releaseDate);
						allInvoices.setReleaseTime(releaseTime);						
					}
					
					String avrFiledDate = rs.getString("avrDate")!=null ? rs.getString("avrDate") : " ";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date avrDate = null;
					try{
						if(!avrFiledDate.trim().isEmpty()){
							avrDate = sdf.parse(avrFiledDate);
							avrFiledDate = DateUtility.getDisplayDate(avrDate, "MM-dd-yyyy");
						}
					}catch(ParseException pe){
						logger.error(pe);
					}
											
					logger.info("avrFiledDate = "+avrFiledDate);
					allInvoices.setAvrFiledDate(avrFiledDate);
					
					allInvoices.setTrnsfrOfAuth(" ");
					allInvoices.setAvrSentDate(" "); 
					allInvoices.setAvrReceivedDate(" ");
					
					String titleDate = rs.getString("titleDate")!=null ? rs.getString("titleDate") : " ";
					/*if(!titleDate.trim().isEmpty())
						titleDate = DateUtility.convertToDateTimeFormat(titleDate);*/
					Date titleDate1 = null;
					try{
						if(!titleDate.trim().isEmpty()){
							titleDate1 = sdf.parse(titleDate);
							titleDate = DateUtility.getDisplayDate(titleDate1, "MM-dd-yyyy");
						}
					}catch(ParseException pe){
						logger.error(pe);
					}
					logger.info("titleDate = "+titleDate);
					allInvoices.setTitleDate(titleDate);
					
					allInvoices.setLocked(" ");
					allInvoices.setClosed(" ");

					return allInvoices;
				}
			});

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		allInvoicesList.setAllInvoices(allInvoices);
		return allInvoicesList;
	}
	
	public String getAccountName(int releaseTo)
	{
		String accountName="";		
		logger.info("In getAccountName()...");

		String query_email = "select name from Account where idAccount=?";

		try
		{
			accountName=jdbcTemplate.queryForObject(query_email,new Object[] {releaseTo},String.class);
		}
		catch (NullPointerException npe)
		{
			logger.error(npe);
			accountName = " ";
		}
		catch(Exception e) 
		{
			logger.error(e);
		}

		return accountName;
	}
	
	public Float getInvoiceBillingPayment(String invoiceId)
	{
		String paymentQry = "select sum(paymentAmt) from Invoice_Payment where invoiceId=?";
		float payment=0.0f;
		try
		{					
			payment=jdbcTemplate.queryForObject(paymentQry,new Object[] {invoiceId},Float.class);
		}
		catch (NullPointerException npe)
		{
			logger.error(npe);
			payment = 0.0f;
		}
		catch (Exception e)
		{
			logger.error(e);
		}

		return payment;
	}

	public List<DriverIDs> getDriverIDs()
	{
		logger.info("In getDriverIDs()...");
		//String driverIDsQry = "select idEmployee from Employee where isDriver='1'";	
		String driverIDsQry = "select u.userId from UserAuth u,Employee e where e.isDriver='1'and e.idEmployee=u.Employee_idEmployee";
		List<DriverIDs> driverIDsList=null;
		try
		{
			driverIDsList =jdbcTemplate.query(driverIDsQry, new Object[] {}, new RowMapper<DriverIDs>() {			
				public DriverIDs mapRow(ResultSet rs, int rowNum) throws SQLException {		     
					DriverIDs driverIDs = new DriverIDs();		    	
					driverIDs.setDriverID(rs.getString("userId")!=null?rs.getString("userId"):"");		    	  		    	
					return driverIDs;
				}
			});
		}
		catch (Exception e) {
			logger.error(e);
		}
		logger.info("driverIDsList.size() : "+driverIDsList.size());
		return driverIDsList;
	}

	public List<TruckIDs> getTruckIDs()
	{
		logger.info("In getDriverIDs()...");
		String truckIDsQry = "select TruckId from Truck";		  
		List<TruckIDs> truckIDsList=null;
		try
		{
			truckIDsList =jdbcTemplate.query(truckIDsQry, new Object[] {}, new RowMapper<TruckIDs>() {			
				public TruckIDs mapRow(ResultSet rs, int rowNum) throws SQLException {		     
					TruckIDs truckIDs = new TruckIDs();		    	
					truckIDs.setTruckID(rs.getString("TruckId")!=null?rs.getString("TruckId"):"");		    	  		    	
					return truckIDs;
				}
			});
		}
		catch (Exception e) {
			logger.error(e);
		}
		logger.info("truckIDsList.size() : "+truckIDsList.size());
		return truckIDsList;
	}

	public List<TowTypes> getTowTypes()
	{
		logger.info("In getTowTypes()...");
		String towTypesQry = "select TowType from TowType";		  
		List<TowTypes> towTypesList=null;
		try
		{
			towTypesList =jdbcTemplate.query(towTypesQry, new Object[] {}, new RowMapper<TowTypes>() {			
				public TowTypes mapRow(ResultSet rs, int rowNum) throws SQLException {		     
					TowTypes towTypes = new TowTypes();		    	
					towTypes.setTowType(rs.getString("TowType")!=null?rs.getString("TowType"):"");		    	  		    	
					return towTypes;
				}
			});
		}
		catch (Exception e) {
			logger.error(e);
		}
		logger.info("towTypesList.size() : "+towTypesList.size());
		return towTypesList;
	}

	public List<Reasons> getReasons()
	{
		logger.info("In getReasons()...");
		String reasonsQry = "select reason from Call_Reasons";		  
		List<Reasons> reasonsList=null;
		try
		{
			reasonsList =jdbcTemplate.query(reasonsQry, new Object[] {}, new RowMapper<Reasons>() {			
				public Reasons mapRow(ResultSet rs, int rowNum) throws SQLException {		     
					Reasons reasons = new Reasons();		    	
					reasons.setReason(rs.getString("reason")!=null?rs.getString("reason"):"");		    	  		    	
					return reasons;
				}
			});
		}
		catch (Exception e) {
			logger.error(e);
		}
		logger.info("reasonsList.size() : "+reasonsList.size());
		return reasonsList;
	}

	public List<AccountNames> getAccountNames()
	{
		logger.info("In getAccountNames()...");
		String accountNamesQry = "select name from Account";		  
		List<AccountNames> accountNamesList=null;
		try
		{
			accountNamesList =jdbcTemplate.query(accountNamesQry, new Object[] {}, new RowMapper<AccountNames>() {			
				public AccountNames mapRow(ResultSet rs, int rowNum) throws SQLException {		     
					AccountNames accountNames = new AccountNames();		    	
					accountNames.setAccountName(rs.getString("name")!=null?rs.getString("name"):"");		    	  		    	
					return accountNames;
				}
			});
		}
		catch (Exception e) {
			logger.error(e);
		}
		logger.info("accountNamesList.size() : "+accountNamesList.size());
		return accountNamesList;
	}

	public List<SalesReps> getSalesReps()
	{
		logger.info("In getSalesReps()...");
		String salesRepsQry = "select distinct firstName,lastName from Employee where jobTitle='SALES REP' order by firstName";		  
		List<SalesReps> salesRepsList=null;
		try
		{
			salesRepsList =jdbcTemplate.query(salesRepsQry, new Object[] {}, new RowMapper<SalesReps>() {			
				public SalesReps mapRow(ResultSet rs, int rowNum) throws SQLException {		     
					SalesReps salesReps = new SalesReps();
					String firstName = rs.getString("firstName")!=null?rs.getString("firstName"):"";
					String lastName = rs.getString("lastName")!=null?rs.getString("lastName"):"";
					salesReps.setSalesRep(firstName+" "+lastName);		    	  		    	  		    	
					return salesReps;
				}
			});
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("salesRepsList.size() : "+salesRepsList.size());
		  return salesRepsList;
	}
	
	public List<SalesReps> getSalesRepName(int salesRepId)
	{
		logger.info("In getSalesRepName()...");
		String salesRepsQry = "select firstName,lastName from Employee where idEmployee=?";		  
		List<SalesReps> salesRepsList=null;
		try
		{
			salesRepsList =jdbcTemplate.query(salesRepsQry, new Object[] {salesRepId}, new RowMapper<SalesReps>() {			
				public SalesReps mapRow(ResultSet rs, int rowNum) throws SQLException {		     
					SalesReps salesReps = new SalesReps();
					String firstName = rs.getString("firstName")!=null?rs.getString("firstName"):"";
					String lastName = rs.getString("lastName")!=null?rs.getString("lastName"):"";
					salesReps.setSalesRep(firstName+" "+lastName);		    	  		    	  		    	
					return salesReps;
				}
			});
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("salesRepsList.size() : "+salesRepsList.size());
		  return salesRepsList;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
