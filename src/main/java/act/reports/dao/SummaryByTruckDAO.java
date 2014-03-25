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

import act.reports.model.SummaryByTruck;
import act.reports.model.SearchCriteria;
import act.reports.model.TruckDetails;
import act.reports.util.DateUtility;

@Repository("summaryByTruckDAO")
public class SummaryByTruckDAO {

	private Logger logger=Logger.getLogger(SummaryByTruckDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public SummaryByTruck getSummaryByTruck(SearchCriteria criteria){
		logger.info("In SummaryByTruckDAO-getSummaryByTruck()...");
		List<TruckDetails> truckDetails = null;
		SummaryByTruck summaryByTruck = new SummaryByTruck();
		
		try {
			final int usePercent = 0;
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			
			String query = "select i.truckNo,count(truckNo) as noOfTows,ib.totalCharge,SUM(totalCharge) as totalCharges,ib.unloadedMiles,ib.loadedMiles from Invoice i,Invoice_Billing ib "+ 
						   "where i.invoiceCreatedDate>='"+fromDate+"' and i.invoiceCreatedDate<='"+toDate+"' and i.invoiceId = ib.invoiceId group by i.truckNo";
			
			truckDetails = jdbcTemplate.query(query, new Object[] {}, new RowMapper<TruckDetails>() {

				public TruckDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					TruckDetails truckDetails = new TruckDetails();
					float chargesPerTow = 0.0f;
					float loadedMiles = 0.0f;
					float unloadedMiles = 0.0f;
					float actualMileage = 0.0f;
					float milesPerTow = 0.0f;

					truckDetails.setTruck(rs.getString("truckNo")!=null ? rs.getString("truckNo") : " ");
					truckDetails.setNoOfTows(rs.getString("noOfTows")!=null ? rs.getString("noOfTows") : "0");
					truckDetails.setTotalCharges(rs.getString("totalCharges")!=null ? rs.getString("totalCharges") : "0.00");
					truckDetails.setUse(Integer.toString(usePercent));
					
					if((truckDetails.getTotalCharges()!= null && !truckDetails.getTotalCharges().isEmpty()) && (truckDetails.getNoOfTows()!= null && Integer.parseInt(truckDetails.getNoOfTows().trim())>0))
					{
						chargesPerTow = Float.parseFloat(truckDetails.getTotalCharges()) / Integer.parseInt(truckDetails.getNoOfTows()); 
						truckDetails.setChargesPerTow(roundUp(chargesPerTow, 2));
					}
					
					if(rs.getString("unloadedMiles")!=null)
						unloadedMiles = Float.parseFloat(rs.getString("unloadedMiles"));
					
					if(rs.getString("loadedMiles")!=null)
						loadedMiles = Float.parseFloat(rs.getString("loadedMiles"));					
					
					logger.info("unloadedMiles : "+unloadedMiles);
					logger.info("loadedMiles : "+loadedMiles);
					actualMileage = unloadedMiles + loadedMiles;
					logger.info("actualMileage : "+actualMileage);
					truckDetails.setActualMileage(roundUp(actualMileage, 2));
					
					if(actualMileage != 0.0f){
						milesPerTow = actualMileage / Integer.parseInt(truckDetails.getNoOfTows());
						logger.info("milesPerTow : "+milesPerTow);
						truckDetails.setMilesPerTow(roundUp(milesPerTow, 2));
					}
					else
						truckDetails.setMilesPerTow(roundUp(milesPerTow, 2));
					
					return truckDetails;
				}
			});

			int totalTows = 0;
			float sumOfTotalCharges = 0.0f;
			float avgChargePerTow = 0.0f;
			float usePercentage = 0.0f;		
			
			for(int i=0;i<truckDetails.size();i++){
				if(truckDetails.get(i).getTotalCharges()!=null && !truckDetails.get(i).getTotalCharges().isEmpty()){
					sumOfTotalCharges += Float.parseFloat(truckDetails.get(i).getTotalCharges());
				}
				if(truckDetails.get(i).getNoOfTows()!=null && !truckDetails.get(i).getNoOfTows().isEmpty()){
					totalTows += Integer.parseInt(truckDetails.get(i).getNoOfTows());
				}				
			}
			
			if((sumOfTotalCharges > 0) && (totalTows > 0))
				avgChargePerTow = sumOfTotalCharges / totalTows;
			
			for(int i=0;i<truckDetails.size();i++){
				if(truckDetails.get(i).getNoOfTows()!=null && !truckDetails.get(i).getNoOfTows().isEmpty()){
					usePercentage = (Float.parseFloat(truckDetails.get(i).getNoOfTows()) * 100.0f) / totalTows;
					truckDetails.get(i).setUse(roundUp(usePercentage, 2));
				}
			}
			
			summaryByTruck.setFromDate(fromDate);
			summaryByTruck.setToDate(toDate);
			summaryByTruck.setTotalTows(Integer.toString(totalTows));
			summaryByTruck.setSumOfTotalCharges(roundUp(sumOfTotalCharges, 2));
			summaryByTruck.setAvgChargePerTow(roundUp(avgChargePerTow, 2));
			summaryByTruck.setTruckDetails(truckDetails);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return summaryByTruck;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}