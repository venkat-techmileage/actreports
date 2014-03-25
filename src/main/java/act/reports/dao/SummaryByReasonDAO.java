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

import act.reports.model.ReasonCodeDetails;
import act.reports.model.SummaryByReasonCode;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("summaryByReasonDAO")
public class SummaryByReasonDAO {

	private Logger logger=Logger.getLogger(SummaryByReasonDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public SummaryByReasonCode getSummaryByReason(SearchCriteria criteria){
		logger.info("In SummaryByReasonDAO-getSummaryByReason()...");
		List<ReasonCodeDetails> reasonCodeDetails = null;
		SummaryByReasonCode summaryByReason = new SummaryByReasonCode();

		try {
			final int usePercent = 0;
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in SummaryByReasonDAO-getSummaryByReason() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in SummaryByReasonDAO-getSummaryByReason() : "+toDate);

			String query = "select i.reason,count(reason) as noOfTows,ib.totalCharge,SUM(totalCharge) as totalCharges from Invoice i,Invoice_Billing ib "+ 
						   "where i.invoiceId = ib.invoiceId and i.invoiceCreatedDate>='"+fromDate+"' and i.invoiceCreatedDate<='"+toDate+"' group by i.reason";


			reasonCodeDetails = jdbcTemplate.query(query, new Object[] {}, new RowMapper<ReasonCodeDetails>() {

				public ReasonCodeDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					ReasonCodeDetails reasonCodeDetails = new ReasonCodeDetails();
					float chargesPerTow = 0.0f;

					reasonCodeDetails.setReason(rs.getString("reason")!=null ? rs.getString("reason") : " ");
					reasonCodeDetails.setNoOfTows(rs.getString("noOfTows")!=null ? rs.getString("noOfTows") : "0");
					String totalCharges = rs.getString("totalCharges")!=null ? rs.getString("totalCharges") : "0.00";
					reasonCodeDetails.setTotalCharges(roundUp(Float.parseFloat(totalCharges), 2));
					reasonCodeDetails.setUse(Integer.toString(usePercent));
					if((reasonCodeDetails.getTotalCharges()!= null && !reasonCodeDetails.getTotalCharges().isEmpty()) && (reasonCodeDetails.getNoOfTows()!= null && Integer.parseInt(reasonCodeDetails.getNoOfTows().trim())>0))
					{
						chargesPerTow = Float.parseFloat(reasonCodeDetails.getTotalCharges()) / Integer.parseInt(reasonCodeDetails.getNoOfTows()); 
						reasonCodeDetails.setChargesPerTow(roundUp(chargesPerTow, 2));
					}					
					
					return reasonCodeDetails;
				}
			});

			int totalTows = 0;
			float sumOfTotalCharges = 0.0f;
			float avgChargePerTow = 0.0f;
			float usePercentage = 0.0f;		
			
			for(int i=0;i<reasonCodeDetails.size();i++){
				if(reasonCodeDetails.get(i).getTotalCharges()!=null && !reasonCodeDetails.get(i).getTotalCharges().isEmpty()){
					sumOfTotalCharges += Float.parseFloat(reasonCodeDetails.get(i).getTotalCharges());
				}
				if(reasonCodeDetails.get(i).getNoOfTows()!=null && !reasonCodeDetails.get(i).getNoOfTows().isEmpty()){
					totalTows += Integer.parseInt(reasonCodeDetails.get(i).getNoOfTows());
				}				
			}
			
			if((sumOfTotalCharges > 0.0f) && (totalTows > 0))
				avgChargePerTow = sumOfTotalCharges / totalTows;
			
			for(int i=0;i<reasonCodeDetails.size();i++){
				if(reasonCodeDetails.get(i).getNoOfTows()!=null && !reasonCodeDetails.get(i).getNoOfTows().isEmpty()){
					usePercentage = (Float.parseFloat(reasonCodeDetails.get(i).getNoOfTows()) * 100.0f) / totalTows;
					reasonCodeDetails.get(i).setUse(roundUp(usePercentage, 2));
				}
			}
			
			summaryByReason.setFromDate(fromDate);
			summaryByReason.setToDate(toDate);
			summaryByReason.setTotalTows(Integer.toString(totalTows));
			summaryByReason.setSumOfTotalCharges(roundUp(sumOfTotalCharges, 2));
			summaryByReason.setAvgChargePerTow(roundUp(avgChargePerTow, 2));
			summaryByReason.setReasonCodeDetails(reasonCodeDetails);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return summaryByReason;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}