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

import act.reports.model.AccountNames;
import act.reports.model.AccountRatePlanDetails;
import act.reports.model.SummaryByAccountRatePlan;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("summaryByAccountRatePlanDAO")
public class SummaryByAccountRatePlanDAO {

	private Logger logger=Logger.getLogger(SummaryByAccountRatePlanDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<AccountNames> getAccountNamesList(){
		logger.info("In SummaryByAccountRatePlanDAO-getAccountNamesList()...");
		List<AccountNames> accountNames = null;
		
		try{
			String query = " select DISTINCT name from Account order by name";
			accountNames = jdbcTemplate.query(query, new Object[] {}, new RowMapper<AccountNames>() {

				public AccountNames mapRow(ResultSet rs, int rowNum) throws SQLException {

					AccountNames accountNames = new AccountNames();
					accountNames.setAccountName(rs.getString("name"));
					
					return accountNames;
				}
			});
			logger.info("accountNames.size() : "+accountNames.size());			
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return accountNames;
	}
		
	public SummaryByAccountRatePlan getSummaryByAccountRatePlan(SearchCriteria criteria){
		logger.info("In SummaryByAccountRatePlanDAO-getSummaryByAccountRatePlan()...");
		List<AccountRatePlanDetails> accountRatePlanDetails = null;
		SummaryByAccountRatePlan summaryByAccountRatePlan = new SummaryByAccountRatePlan();

		try {
			final int usePercent = 0;
			String accountName = criteria.getAccountName();
			String fromDate=criteria.getFromDate();
			String toDate=criteria.getToDate();
			fromDate = DateUtility.convertAsMySqlDateTime(fromDate);
			logger.info("fromDate in SummaryByAccountRatePlanDAO-getSummaryByAccountRatePlan() : "+fromDate);
			toDate = DateUtility.convertAsMySqlDateTime(toDate);
			toDate = toDate.replace("00:00:00", "23:59:59");
			logger.info("toDate in SummaryByAccountRatePlanDAO-getSummaryByAccountRatePlan() : "+toDate);

			String query = "select ib.acountRatePlan,count(ib.acountRatePlan) as noOfTows,ib.totalCharge,SUM(ib.totalCharge) as totalCharges from Account a,Invoice_Billing ib " +
						   "where a.name='"+accountName+"' and ib.invoiceDate BETWEEN '"+fromDate+"' and '"+toDate+"' and a.idAccount=ib.accountId group by ib.acountRatePlan";


			accountRatePlanDetails = jdbcTemplate.query(query, new Object[] {}, new RowMapper<AccountRatePlanDetails>() {

				public AccountRatePlanDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					AccountRatePlanDetails accountRatePlanDetails = new AccountRatePlanDetails();
					float chargesPerTow = 0.0f;

					String acountRatePlan = rs.getString("acountRatePlan") !=null ? rs.getString("acountRatePlan") : " ";
					if(!acountRatePlan.trim().isEmpty())
						accountRatePlanDetails.setAccountRatePlan(getAccountRateplanName(acountRatePlan));
					else
						accountRatePlanDetails.setAccountRatePlan(acountRatePlan);
					
					accountRatePlanDetails.setNoOfTows(rs.getString("noOfTows") !=null ? rs.getString("noOfTows") : "0");
					accountRatePlanDetails.setTotalCharges(rs.getString("totalCharges") !=null ? rs.getString("totalCharges") : "0.00");
					accountRatePlanDetails.setUse(Integer.toString(usePercent));
					if((accountRatePlanDetails.getTotalCharges()!= null && !accountRatePlanDetails.getTotalCharges().isEmpty()) && (accountRatePlanDetails.getNoOfTows()!= null && Integer.parseInt(accountRatePlanDetails.getNoOfTows().trim())>0))
					{
						chargesPerTow = Float.parseFloat(accountRatePlanDetails.getTotalCharges()) / Integer.parseInt(accountRatePlanDetails.getNoOfTows()); 
						accountRatePlanDetails.setChargesPerTow(roundUp(chargesPerTow, 2));
					}					
					
					return accountRatePlanDetails;
				}
			});

			int totalTows = 0;
			float sumOfTotalCharges = 0.0f;
			float avgChargePerTow = 0.0f;
			float usePercentage = 0.0f;		
			
			for(int i=0;i<accountRatePlanDetails.size();i++){
				if(accountRatePlanDetails.get(i).getTotalCharges()!=null && !accountRatePlanDetails.get(i).getTotalCharges().isEmpty()){
					sumOfTotalCharges += Float.parseFloat(accountRatePlanDetails.get(i).getTotalCharges());
				}
				if(accountRatePlanDetails.get(i).getNoOfTows()!=null && !accountRatePlanDetails.get(i).getNoOfTows().isEmpty()){
					totalTows += Integer.parseInt(accountRatePlanDetails.get(i).getNoOfTows());
				}				
			}
			
			if((sumOfTotalCharges > 0.0f) && (totalTows > 0))
				avgChargePerTow = sumOfTotalCharges / totalTows;
			
			for(int i=0;i<accountRatePlanDetails.size();i++){
				if(accountRatePlanDetails.get(i).getNoOfTows()!=null && !accountRatePlanDetails.get(i).getNoOfTows().isEmpty() && Float.parseFloat(accountRatePlanDetails.get(i).getNoOfTows())>0){
					usePercentage = (Float.parseFloat(accountRatePlanDetails.get(i).getNoOfTows()) * 100.0f) / totalTows;
					accountRatePlanDetails.get(i).setUse(roundUp(usePercentage, 2));
				}
			}
			
			summaryByAccountRatePlan.setFromDate(fromDate);
			summaryByAccountRatePlan.setToDate(toDate);
			summaryByAccountRatePlan.setTotalTows(Integer.toString(totalTows));
			summaryByAccountRatePlan.setSumOfTotalCharges(roundUp(sumOfTotalCharges, 2));
			summaryByAccountRatePlan.setAvgChargePerTow(roundUp(avgChargePerTow, 2));
			summaryByAccountRatePlan.setAccountRatePlanDetails(accountRatePlanDetails);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return summaryByAccountRatePlan;
	}	
	
	public String getAccountRateplanName(String accountRateplanId)
	{
		String accountRateplanQry = "select AccountPlanRateName from AccountRatePlan where AccountRatePlanId=?";
		String accountRateplanName="";
		try
		{					
			accountRateplanName=jdbcTemplate.queryForObject(accountRateplanQry,new Object[] {accountRateplanId},String.class);
		}
		catch (NullPointerException npe)
		{
			logger.error(npe);
			accountRateplanName = "";
		}
		catch (Exception e)
		{
			logger.error(e);
		}

		return accountRateplanName;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
