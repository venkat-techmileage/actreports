package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.DriverDetails;
import act.reports.model.SummaryByDriver;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("summaryByDriverDAO")
public class SummaryByDriverDAO {

	private Logger logger=Logger.getLogger(SummaryByDriverDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public SummaryByDriver getSummaryByDriver(SearchCriteria criteria){
		logger.info("In SummaryByDriverDAO-getSummaryByDriver()...");
		List<DriverDetails> driverDetails = null;
		SummaryByDriver summaryByDriver = new SummaryByDriver();

		try{
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			
			String query = "select i.driverId,count(i.driverId) as noOfTows,itd.DriverName,ib.totalCharge,SUM(totalCharge) as totalCharges from Invoice i,Invoice_Billing ib LEFT OUTER JOIN "+ 
						   "Invoice_Truck_Driver itd ON ib.invoiceId=itd.invoiceId where ib.invoiceDate BETWEEN '"+fromDate+"' and '"+toDate+"' and i.invoiceId=ib.invoiceId group by i.driverId";
			 

			driverDetails = jdbcTemplate.query(query, new Object[] {}, new RowMapper<DriverDetails>() {

				public DriverDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					DriverDetails driverDetails = new DriverDetails();
					float chargesPerTow = 0.0f;

					String driverId = rs.getString("driverId")!=null ? rs.getString("driverId") : " ";
					if(!driverId.trim().isEmpty())
						driverDetails.setDriverId(getUserId(driverId));
					else
						driverDetails.setDriverId(driverId);
						
					driverDetails.setNoOfTows(rs.getString("noOfTows")!=null ? rs.getString("noOfTows") : "0");
					driverDetails.setDriverName(rs.getString("DriverName")!=null ? rs.getString("DriverName") : " ");
					driverDetails.setTotalCharges(rs.getString("totalCharges")!=null ? rs.getString("totalCharges") : "0.00");
					driverDetails.setUse(" ");
					if((driverDetails.getTotalCharges()!= null && !driverDetails.getTotalCharges().trim().isEmpty()) && (driverDetails.getNoOfTows()!= null && Integer.parseInt(driverDetails.getNoOfTows().trim())>0))
					{
						chargesPerTow = Float.parseFloat(driverDetails.getTotalCharges()) / Integer.parseInt(driverDetails.getNoOfTows()); 
						logger.info("chargesPerTow = "+chargesPerTow);
						driverDetails.setChargesPerTow(roundUp(chargesPerTow, 2));
					}					
					
					return driverDetails;
				}
			});

			int totalTows = 0;
			float sumOfTotalCharges = 0.0f;
			float avgChargePerTow = 0.0f;
			float usePercentage = 0.0f;		
			
			for(int i=0;i<driverDetails.size();i++){
				if(driverDetails.get(i).getTotalCharges()!=null && !driverDetails.get(i).getTotalCharges().isEmpty()){
					sumOfTotalCharges += Float.parseFloat(driverDetails.get(i).getTotalCharges());
				}
				if(driverDetails.get(i).getNoOfTows()!=null && !driverDetails.get(i).getNoOfTows().isEmpty()){
					totalTows += Integer.parseInt(driverDetails.get(i).getNoOfTows());
				}				
			}
			
			if((sumOfTotalCharges > 0.0f) && (totalTows > 0))
				avgChargePerTow = sumOfTotalCharges / totalTows;
			
			for(int i=0;i<driverDetails.size();i++){
				if(driverDetails.get(i).getNoOfTows()!=null && !driverDetails.get(i).getNoOfTows().isEmpty() && Float.parseFloat(driverDetails.get(i).getNoOfTows())>0){
					usePercentage = (Float.parseFloat(driverDetails.get(i).getNoOfTows()) * 100.0f) / totalTows;					
					driverDetails.get(i).setUse(roundUp(usePercentage, 2));
				}
			}
			
			summaryByDriver.setFromDate(fromDate);
			summaryByDriver.setToDate(toDate);
			summaryByDriver.setTotalTows(Integer.toString(totalTows));
			summaryByDriver.setSumOfTotalCharges(roundUp(sumOfTotalCharges, 2));
			summaryByDriver.setAvgChargePerTow(roundUp(avgChargePerTow, 2));
			summaryByDriver.setDriverDetails(driverDetails);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return summaryByDriver;
	}
	
	public String getUserId(String driverId)
	{
		String userIdQry = "select userId from UserAuth where Employee_idEmployee=?";
		String userId="";
		try
		{					
			userId=jdbcTemplate.queryForObject(userIdQry,new Object[] {driverId},String.class);
		}
		catch (NullPointerException npe)
		{
			logger.error(npe);
			userId = "";
		}
		catch (Exception e)
		{
			logger.error(e);
		}

		return userId;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}