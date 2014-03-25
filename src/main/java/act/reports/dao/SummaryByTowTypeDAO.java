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

import act.reports.model.SummaryByTowType;
import act.reports.model.SearchCriteria;
import act.reports.model.TowTypeDetails;
import act.reports.util.DateUtility;

@Repository("summaryByTowTypeDAO")
public class SummaryByTowTypeDAO {

	private Logger logger=Logger.getLogger(SummaryByTowTypeDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public SummaryByTowType getSummaryByTowType(SearchCriteria criteria){
		logger.info("In SummaryByTowTypeDAO-getSummaryByReason()...");
		List<TowTypeDetails> towTypeDetails = null;
		SummaryByTowType summaryByTowType = new SummaryByTowType();

		try {
			final int usePercent = 0;
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			
			String query = "select i.towType,count(towType) as noOfTows,ib.totalCharge,SUM(totalCharge) as totalCharges from Invoice i,Invoice_Billing ib "+ 
						   "where i.invoiceId = ib.invoiceId and i.invoiceCreatedDate>='"+fromDate+"' and i.invoiceCreatedDate<='"+toDate+"' group by i.towType";

			towTypeDetails = jdbcTemplate.query(query, new Object[] {}, new RowMapper<TowTypeDetails>() {

				public TowTypeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					TowTypeDetails towTypeDetails = new TowTypeDetails();
					float chargesPerTow = 0.0f;

					towTypeDetails.setType(rs.getString("towType")!=null ? rs.getString("towType") : " ");
					towTypeDetails.setNoOfTows(rs.getString("noOfTows")!=null ? rs.getString("noOfTows") : "0");
					String totalCharges = rs.getString("totalCharges")!=null ? rs.getString("totalCharges") : "0.00";
					towTypeDetails.setTotalCharges(roundUp(Float.parseFloat(totalCharges), 2));
					towTypeDetails.setUse(Integer.toString(usePercent));
					
					if((towTypeDetails.getTotalCharges()!= null && !towTypeDetails.getTotalCharges().isEmpty()) && (towTypeDetails.getNoOfTows()!= null && Integer.parseInt(towTypeDetails.getNoOfTows().trim())>0))
					{
						chargesPerTow = Float.parseFloat(towTypeDetails.getTotalCharges()) / Integer.parseInt(towTypeDetails.getNoOfTows()); 
						towTypeDetails.setChargesPerTow(roundUp(chargesPerTow, 2));
					}
					return towTypeDetails;
				}
			});

			int totalTows = 0;
			float sumOfTotalCharges = 0.0f;
			float avgChargePerTow = 0.0f;
			float usePercentage = 0.0f;		
			
			for(int i=0;i<towTypeDetails.size();i++){
				if(towTypeDetails.get(i).getTotalCharges()!=null && !towTypeDetails.get(i).getTotalCharges().isEmpty()){
					sumOfTotalCharges += Float.parseFloat(towTypeDetails.get(i).getTotalCharges());
				}
				if(towTypeDetails.get(i).getNoOfTows()!=null && !towTypeDetails.get(i).getNoOfTows().isEmpty()){
					totalTows += Float.parseFloat(towTypeDetails.get(i).getNoOfTows());
				}				
			}
			
			if((sumOfTotalCharges > 0) && (totalTows > 0))
				avgChargePerTow = sumOfTotalCharges / totalTows;
			
			for(int i=0;i<towTypeDetails.size();i++){
				if(towTypeDetails.get(i).getNoOfTows()!=null && !towTypeDetails.get(i).getNoOfTows().isEmpty()){
					usePercentage = (Float.parseFloat(towTypeDetails.get(i).getNoOfTows()) * 100.0f) / totalTows;
					towTypeDetails.get(i).setUse(roundUp(usePercentage, 2));
				}
			}
			
			summaryByTowType.setFromDate(fromDate);
			summaryByTowType.setToDate(toDate);
			summaryByTowType.setTotalTows(Integer.toString(totalTows));
			summaryByTowType.setSumOfTotalCharges(roundUp(sumOfTotalCharges, 2));
			summaryByTowType.setAvgChargePerTow(roundUp(avgChargePerTow, 2));
			summaryByTowType.setTowTypeDetails(towTypeDetails);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return summaryByTowType;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
