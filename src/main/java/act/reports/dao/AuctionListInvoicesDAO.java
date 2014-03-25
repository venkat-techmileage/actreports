package act.reports.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.AuctionListInvoices;
import act.reports.model.AuctionListInvoicesDetails;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;

@Repository("auctionListInvoicesDAO")
public class AuctionListInvoicesDAO {

	private Logger logger=Logger.getLogger(AuctionListInvoicesDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<SelectOption> getAuctionList()
	{
		logger.info("In getAuctionList()...");
		String accNameQry = "select distinct auctionListId,auctionListName from AuctionList";		  
		List<SelectOption> auctionList=null;
		try
		{
			auctionList =jdbcTemplate.query(accNameQry, new Object[] {}, new RowMapper<SelectOption>() {			
		      public SelectOption mapRow(ResultSet rs, int rowNum) throws SQLException {		     
		    	  SelectOption auctionList = new SelectOption();		    	
		    	  auctionList.setOptionVal(rs.getString("auctionListId")!=null?rs.getString("auctionListId"):"");
		    	  auctionList.setOptionTxt(rs.getString("auctionListName")!=null?rs.getString("auctionListName"):"");		    	
		    	return auctionList;
		      }
		    });
	     }
		 catch (Exception e) {
		   logger.error(e);
		  }
		  logger.info("auctionList.size() : "+auctionList.size());
		  return auctionList;
	}
	
	public AuctionListInvoices getAuctionListInvoicesDetails(SearchCriteria criteria){
		logger.info("In AuctionListInvoicesDAO-getAuctionListInvoicesDetails()...");
		AuctionListInvoices auctionListInvoices = new AuctionListInvoices();
		//AuctionList auctionList = new AuctionList();
		List<AuctionListInvoicesDetails> auctionListInvoicesDetailsList = null;
		try {
			String auctionListId=criteria.getAuctionListId();
			String auctionListName=criteria.getAuctionListName();
			logger.info("auctionListId in AuctionListInvoicesDAO-getAuctionListInvoicesDetails() : "+auctionListId);
			logger.info("auctionListName in AuctionListInvoicesDAO-getAuctionListInvoicesDetails() : "+auctionListName);

			String auctionListInvoiceDetailsqQuery = "select ali.invoiceId,ali.auctionDate,i.dropOffLocation,iv.vehicle_Year,iv.vehicle_Make,iv.vehicle_Model,iv.vehicle_VIN,iv.vehicle_Country,vehicle_State,iv.vehicle_Plate_No,ir.releaseTo from AuctionList_Invoices ali LEFT OUTER JOIN Invoice i ON ali.invoiceId=i.invoiceId "+ 
						   							 "LEFT OUTER JOIN Invoice_Vehicle iv ON ali.invoiceId=iv.invoiceId LEFT OUTER JOIN Invoice_Release ir ON ali.invoiceId=ir.invoiceId where ali.auctionListId='"+auctionListId+"' and ali.auctionListName='"+auctionListName+"' order by ali.invoiceId";

			auctionListInvoicesDetailsList = jdbcTemplate.query(auctionListInvoiceDetailsqQuery, new Object[] {}, new RowMapper<AuctionListInvoicesDetails>() {

				public AuctionListInvoicesDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					AuctionListInvoicesDetails auctionListInvoicesDetails = new AuctionListInvoicesDetails();
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : "";
					auctionListInvoicesDetails.setInvoiceId(invoiceId);
					auctionListInvoicesDetails.setAuctionDate((rs.getString("auctionDate") != null) ? rs.getString("auctionDate") : "");
					auctionListInvoicesDetails.setLotLocation((rs.getString("dropOffLocation") != null) ? rs.getString("dropOffLocation") : "");
					auctionListInvoicesDetails.setVehicleYear((rs.getString("vehicle_Year") != null) ? rs.getString("vehicle_Year") : "");
					auctionListInvoicesDetails.setVehicleMake((rs.getString("vehicle_Make") != null) ? rs.getString("vehicle_Make") : "");
					auctionListInvoicesDetails.setVehicleModel((rs.getString("vehicle_Model") != null) ? rs.getString("vehicle_Model") : "");
					auctionListInvoicesDetails.setVehicleVIN((rs.getString("vehicle_VIN") != null) ? rs.getString("vehicle_VIN") : "");
					auctionListInvoicesDetails.setLicensePlateCountry((rs.getString("vehicle_Country") != null) ? rs.getString("vehicle_Country") : "");
					auctionListInvoicesDetails.setLicensePlateState((rs.getString("vehicle_State") != null) ? rs.getString("vehicle_State") : "");
					auctionListInvoicesDetails.setLicensePlateNo((rs.getString("vehicle_Plate_No") != null) ? rs.getString("vehicle_Plate_No") : "");
					
					if(!invoiceId.isEmpty()){
						if(getIsSalvageCall(invoiceId))
							auctionListInvoicesDetails.setMarkedAsSalvage("Y");
						else
							auctionListInvoicesDetails.setMarkedAsSalvage("N");
					}
					
					int releaseTo = rs.getInt("releaseTo");					
					auctionListInvoicesDetails.setReleasedTo(getAccountName(releaseTo)); //releaseTo
					
					return auctionListInvoicesDetails;
				}
			});

			auctionListInvoices.setAuctionListId(auctionListId);
			auctionListInvoices.setAuctionListName(auctionListName);
			if(auctionListInvoicesDetailsList.size() > 0)
				auctionListInvoices.setAuctionDate(auctionListInvoicesDetailsList.get(0).getAuctionDate());
			else
				auctionListInvoices.setAuctionDate(" ");
			auctionListInvoices.setAuctionListInvoicesDetailsList(auctionListInvoicesDetailsList);
			auctionListInvoices.setTotalVehicles(Integer.toString(auctionListInvoicesDetailsList.size()));
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return auctionListInvoices;
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
		catch(Exception e) 
		{
			logger.error(e);
		}

		return accountName;
	}
	
	public boolean getIsSalvageCall(String invoiceId)
	{
		logger.info("invoked Is Salvage Call");

		String query_salvage = "select count(*) from Invoice i inner join ServiceCallInfo si on si.serviceCallId = i.serviceCallId " +
				"where si.serviceCallType = 'salvage' and i.rehookInvoiceId=?";
		int i=0;
		try
		{
			i=jdbcTemplate.queryForInt(query_salvage,new Object[] {invoiceId});
		}
		catch(Exception e)	
		{
			logger.error(e);
		}
		if(i==0)
		{
			return false;
		}
		else return true;
	}
}
