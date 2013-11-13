package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.controller.DashboardController;
import act.reports.model.AllInvoices;
import act.reports.model.AllInvoicesList;
import act.reports.model.InvoiceSearchCriteria;
import act.reports.util.DateUtility;

@Repository("allInvoicesDAO")
public class AllInvoicesDAO {
	
	private Logger logger=Logger.getLogger(AllInvoicesDAO.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public AllInvoicesList getAllInvoicesList(InvoiceSearchCriteria criteria){
		logger.info("In AllInvoicesDAO-getAllInvoicesList()...");
		
		String query = "select sc.callCreatedTime,i.invoiceId,sc.callRecevierFullName,ib.acountRatePlan,i.priority,i.reason,i.towType,iv.vehicle_Info_DLNo," +
						"iv.isDriverIsOwner,iv.isDriverIsOwner,iv.vehicle_Owner_FirstName,iv.vehicle_Owner_LastName,iv.vehicle_Owner_Addr_Line1,iv.vehicle_Owner_Addr_Line2," +
						"iv.vehicle_Owner_City,iv.vehicle_Owner_State,iv.vehicle_Owner_Zip,iv.vehicle_Owner_PhoneNo,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model," +
						"iv.vehicle_Color,iv.vehicle_Style,iv.vehicle_VIN,iv.vehicle_Country,iv.vehicle_State,iv.vehicle_Plate_No,iv.vehicle_Key_Location,iv.vehicle_Status," +
						"iv.vehicle_Comm_Unit_No,i.requestTime,i.dispatchTime,i.acceptedTime,i.enrouteTime,i.arrivedTime,i.hookedTime,i.dropOffTime,i.elapsedTime," +
						"i.pickUpAddr_Address_Landmark,i.pickUpAddr_Address_Line1,i.pickUpAddr_Address_Line2,i.pickUpAddr_Address_City,i.pickUpAddr_Address_State," +
						"i.pickUpAddr_Address_Zip,i.dropOffAddr_Address_Landmark,i.dropOffAddr_Address_Line1,i.dropOffAddr_Address_Line2,i.dropOffAddr_Address_City," +
						"i.dropOffAddr_Address_State,i.dropOffAddr_Address_Zip,i.truckNo,i.driverId,sc.callRecevierId,ib.towCharge,ib.storageCharge,ib.laborCharge," +
						"ib.mileageCharge,ib.winch_Charge,ib.miscCharge,ib.totalCharge,ib.isPaid from servicecallinfo sc,invoice i,invoice_billing ib," +
						"invoice_vehicle iv where sc.serviceCallId = i.serviceCallId and i.invoiceId = ib.invoiceId and i.invoiceId=iv.invoiceId";
		
		final Float gateCharge = 0f;
		final Float adminCharge = 0f;
		String qryString="";
		String fromDate=criteria.getFromDate();
		String toDate=criteria.getToDate();
		if(criteria.getSearchQuery().equalsIgnoreCase("byServiceCallDate")){
			   
				   fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
				   qryString+= " and sc.callCreatedTime>='"+fromDate+"'";
				   toDate = DateUtility.convertAsMySqlDateTime(toDate);
				   qryString+= " and sc.callCreatedTime<='"+toDate+"'";
				
		}
		else if(criteria.getSearchQuery().equalsIgnoreCase("byReleaseDate")){
				   fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
				   qryString+= " and sc.callCreatedTime >='"+fromDate+"'";
				   toDate = DateUtility.convertAsMySqlDateTime(toDate);
				   qryString+= " and sc.callCreatedTime <='"+toDate+"'";
		}
		
		query+=qryString;
		
		List<AllInvoices> allInvoices = null;
		AllInvoicesList allInvoicesList = new AllInvoicesList();
		
		try {
				allInvoices = jdbcTemplate.query(query, new Object[] {}, new RowMapper<AllInvoices>() {
				    
				    public AllInvoices mapRow(ResultSet rs, int rowNum) throws SQLException {
				    	
				    	AllInvoices allInvoices = new AllInvoices();
				    
				    	allInvoices.setServiceCallDate(rs.getString("callCreatedTime"));
				    	allInvoices.setInvoice(rs.getString("invoiceId"));
				    	allInvoices.setRequestedBy(rs.getString("callRecevierFullName"));
				    	allInvoices.setSalesRep("");
				    	allInvoices.setRatePlan(rs.getInt("acountRatePlan"));
				    	
				    	int priority = rs.getInt("priority");
				    	//allInvoices.setPriority(priority);
				    	String reason = rs.getString("reason");
				    	//allInvoices.setReason(reason);
				    	allInvoices.setPriorityAndReason((Integer.toString(priority)+ ", "+ reason));
				    	
				    	allInvoices.setTowType(rs.getString("towType"));
				    	allInvoices.setOrDr("");
				    	allInvoices.setDriverLicense(rs.getString("vehicle_Info_DLNo"));
				    	allInvoices.setDriverIsOwner(rs.getInt("isDriverIsOwner"));
				    	allInvoices.setNoOwnerInfo("");
				    	allInvoices.setDriverIsOwner(rs.getInt("isDriverIsOwner"));
				    	allInvoices.setRegisteredOwnerName(rs.getString("vehicle_Owner_FirstName")+" "+rs.getString("vehicle_Owner_LastName"));
				    	allInvoices.setRegisteredOwnerAddress(rs.getString("vehicle_Owner_Addr_Line1")+" "+rs.getString("vehicle_Owner_Addr_Line2"));
				    	allInvoices.setRegisteredOwnerCity(rs.getString("vehicle_Owner_City"));
				    	allInvoices.setRegisteredOwnerState(rs.getString("vehicle_Owner_State"));
				    	allInvoices.setRegisteredOwnerZip(rs.getInt("vehicle_Owner_Zip"));
				    	allInvoices.setRegisteredOwnerPhone(rs.getString("vehicle_Owner_PhoneNo"));
				    	allInvoices.setRegisteredOwnerEmail("");
				    	allInvoices.setYear(rs.getInt("vehicle_Year"));
				    	allInvoices.setModel(rs.getString("vehicle_Model"));
				    	allInvoices.setColor(rs.getString("vehicle_Color"));
				    	allInvoices.setStyle(rs.getString("vehicle_Style"));
				    	allInvoices.setVin(rs.getString("vehicle_VIN"));
				    	allInvoices.setPlateCountry(rs.getString("vehicle_Country"));
				    	allInvoices.setPlateState(rs.getString("vehicle_State"));
				    	allInvoices.setPlate(rs.getString("vehicle_Plate_No"));
				    	allInvoices.setKeys(rs.getString("vehicle_Key_Location")); //yes (or) no
				    	allInvoices.setRodio("");
				    	allInvoices.setVehicleStatus(rs.getString("vehicle_Status"));
				    	allInvoices.setCommercialUnit(rs.getString("vehicle_Comm_Unit_No"));
				    	allInvoices.setPoliceImpound("");
				    	allInvoices.setCallTime(rs.getString("requestTime"));
				    	allInvoices.setDispatchTime(rs.getString("dispatchTime"));
				    	allInvoices.setAcceptedTime(rs.getString("acceptedTime"));
				    	allInvoices.setEnrouteTime(rs.getString("enrouteTime"));
				    	allInvoices.setArrivedTime(rs.getString("arrivedTime"));
				    	allInvoices.setHookedTime(rs.getString("hookedTime"));
				    	allInvoices.setDroppedTime(rs.getString("dropOffTime"));
				    	allInvoices.setClearTime("");
				    	allInvoices.setTotalTime(rs.getString("elapsedTime"));
				    	allInvoices.setPickupLocation(rs.getString("pickUpAddr_Address_Landmark"));
				    	allInvoices.setPickupAddress(rs.getString("pickUpAddr_Address_Line1")+" "+rs.getString("pickUpAddr_Address_Line2"));
				    	allInvoices.setPickupCity(rs.getString("pickUpAddr_Address_City"));
				    	allInvoices.setPickupState(rs.getString("pickUpAddr_Address_State"));
				    	allInvoices.setPickupZipcode(rs.getInt("pickUpAddr_Address_Zip"));
				    	allInvoices.setDropOffLocation(rs.getString("dropOffAddr_Address_Landmark"));
				    	allInvoices.setDropOffAddress(rs.getString("dropOffAddr_Address_Line1")+", "+rs.getString("dropOffAddr_Address_Line2"));
				    	allInvoices.setDropOffCity(rs.getString("dropOffAddr_Address_City"));
				    	allInvoices.setDropOffState(rs.getString("dropOffAddr_Address_State"));
				    	allInvoices.setDropOffZipcode(rs.getInt("dropOffAddr_Address_Zip"));
				    	allInvoices.setTruck(rs.getInt("truckNo"));
				    	allInvoices.setDriverId(rs.getInt("driverId"));
				    	allInvoices.setDispatchId("");
				    	allInvoices.setCallReceiverId(rs.getString("callRecevierId"));
				    	allInvoices.setTowCharge(rs.getFloat("towCharge"));
				    	allInvoices.setStorageCharge(rs.getFloat("storageCharge"));
				    	allInvoices.setLaborCharge(rs.getFloat("laborCharge"));
				    	allInvoices.setMileageCharge(rs.getFloat("mileageCharge"));
				    	allInvoices.setWinchCharge(rs.getFloat("winch_Charge"));
				    	allInvoices.setGateCharge(gateCharge);
				    	allInvoices.setAdminCharge(adminCharge);
				    	allInvoices.setMiscChargeDesc("");
				    	allInvoices.setMiscCharge(rs.getFloat("miscCharge"));
				    	
				    	Float totalDiscount = 0f;
				    	allInvoices.setDiscounts(totalDiscount);
				    	
				    	allInvoices.setTotalCharges(rs.getFloat("totalCharge"));
				    	allInvoices.setAmountPaid(rs.getInt("isPaid"));
				    	allInvoices.setInStorage("");
				    	allInvoices.setBillTo("");
				    	allInvoices.setReleasedTo("");
				    	allInvoices.setReleaseTime("");
				    	allInvoices.setReleaseDate("");
				    	allInvoices.setAvrSentDate("");
				    	allInvoices.setAvrReceivedDate("");
				    	allInvoices.setTitleDate("");
				    	allInvoices.setLocked("");
				    	allInvoices.setClosed("");
				    	
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
}
