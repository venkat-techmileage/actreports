package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.AccountTypes;
import act.reports.model.AccountsReceivableAccTypeDetail;
import act.reports.model.AccountsReceivableDetailByAccType;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("accountsReceivableDetailByAccTypeDAO")
public class AccountsReceivableDetailByAccTypeDAO {

	private Logger logger=Logger.getLogger(AccountsReceivableDetailByAccTypeDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<AccountTypes> getAccountTypesList(){
		logger.info("In AccountReceivableDetailDAO-getAccountNamesList()...");
		List<AccountTypes> accountTypesList = null;
		
		try{
			String query = " select DISTINCT AccountTypeName from AccountTypes order by AccountTypeName";
			accountTypesList = jdbcTemplate.query(query, new Object[] {}, new RowMapper<AccountTypes>() {
	
				public AccountTypes mapRow(ResultSet rs, int rowNum) throws SQLException {
	
					AccountTypes accountTypes = new AccountTypes();
					accountTypes.setAccountType(rs.getString("AccountTypeName"));
					
					return accountTypes;
				}
			});
			logger.info("accountTypesList.size() : "+accountTypesList.size());			
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return accountTypesList;
	}


	
	public AccountsReceivableDetailByAccType getAccountReceivableDetailByAccountType(SearchCriteria criteria){
		logger.info("In AccountsReceivableDetailByAccTypeDAO-getAccountReceivableDetailByAccountType()...");
		List<AccountsReceivableAccTypeDetail> accountsReceivableAccTypeDetailList = null;
		AccountsReceivableDetailByAccType accountsReceivableDetailByAccType = new AccountsReceivableDetailByAccType();

		try {
			String asOfDate=criteria.getAsOfDate();
			asOfDate = DateUtility.convertAsMySqlDateTime(asOfDate);
			asOfDate = asOfDate.replace("00:00:00", "23:59:59");
			final String asOfDate1 = asOfDate;
			logger.info("asOfDate in AccountsReceivableDetailByAccTypeDAO-getAccountReceivableDetailByAccountType() : "+asOfDate);
			final String accountType = criteria.getAccountType();
			logger.info("accountName in AccountsReceivableDetailByAccTypeDAO-getAccountReceivableDetailByAccountType() : "+accountType);
			
			Float currentDue = 0.0f;
			Float thirtyDaysDue = 0.0f;
			Float sixtyDaysDue = 0.0f;
			Float nintyDaysDue = 0.0f;
			Float moreThanNintyDaysDue = 0.0f;
			Float accountTotal = 0.0f;
				
			if((accountType!=null && !accountType.isEmpty()) && (asOfDate!=null && !asOfDate.isEmpty())){					
				String currentQry = "select distinct SUM(ib.totalCharge) as currentDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN ib.invoiceDate"+
									" and DATE_ADD(ib.invoiceDate, INTERVAL 10 DAY) and ib.accountId in (select distinct idAccount from Account where acct_type='"+accountType+"') group by a.name";
				try{
					currentDue = jdbcTemplate.queryForObject(currentQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("currentDue = "+currentDue);
				accountsReceivableDetailByAccType.setCurrentDue(Float.toString((currentDue != null) ? currentDue : 0.0f));
					
				String thirtyDaysQry = "select distinct SUM(ib.totalCharge) as thirtyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN DATE_ADD(ib.invoiceDate, INTERVAL 10 DAY) "+
									   "and DATE_ADD(ib.invoiceDate, INTERVAL 40 DAY) and ib.accountId in (select distinct idAccount from Account where acct_type='"+accountType+"') group by a.name";
				try{
					thirtyDaysDue = jdbcTemplate.queryForObject(thirtyDaysQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("thirtyDaysDue = "+thirtyDaysDue);
				accountsReceivableDetailByAccType.setThirtyDaysDue(Float.toString((thirtyDaysDue != null) ? thirtyDaysDue : 0.0f));
					
				String sixtyDaysQry = "select distinct SUM(ib.totalCharge) as sixtyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN DATE_ADD(ib.invoiceDate, INTERVAL 40 DAY)"+
									  " and DATE_ADD(ib.invoiceDate, INTERVAL 70 DAY) and ib.accountId in (select distinct idAccount from Account where acct_type='"+accountType+"') group by a.name";
				try{
					sixtyDaysDue = jdbcTemplate.queryForObject(sixtyDaysQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("sixtyDaysDue = "+sixtyDaysDue);
				accountsReceivableDetailByAccType.setSixtyDaysDue(Float.toString((sixtyDaysDue != null) ? sixtyDaysDue : 0.0f));
					
				String nintyDaysQry = "select distinct SUM(ib.totalCharge) as nintyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN DATE_ADD(ib.invoiceDate, INTERVAL 70 DAY)"+
									  " and DATE_ADD(ib.invoiceDate, INTERVAL 100 DAY) and ib.accountId in (select distinct idAccount from Account where acct_type='"+accountType+"') group by a.name";
				try{
					nintyDaysDue = jdbcTemplate.queryForObject(nintyDaysQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("nintyDaysDue = "+nintyDaysDue);
				accountsReceivableDetailByAccType.setNintyDaysDue(Float.toString((nintyDaysDue != null) ? nintyDaysDue : 0.0f));
					
				String moreThanNintyDaysQry = "select distinct SUM(ib.totalCharge) as nintyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate>"+
						   					  "DATE_ADD(ib.invoiceDate, INTERVAL 100 DAY) and ib.accountId in (select distinct idAccount from Account where acct_type='"+accountType+"') group by a.name";
				try{
					moreThanNintyDaysDue = jdbcTemplate.queryForObject(moreThanNintyDaysQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("moreThanNintyDaysDue = "+moreThanNintyDaysDue);
				accountsReceivableDetailByAccType.setMoreThanNintyDaysDue(Float.toString((moreThanNintyDaysDue != null) ? moreThanNintyDaysDue : 0.0f));
						
				accountTotal += (currentDue != null ? currentDue : 0.0f) + (thirtyDaysDue != null ? thirtyDaysDue : 0.0f) + (sixtyDaysDue != null ? sixtyDaysDue : 0.0f) + (nintyDaysDue != null ? nintyDaysDue : 0.0f) + (moreThanNintyDaysDue != null ? moreThanNintyDaysDue : 0.0f);
				accountsReceivableDetailByAccType.setTotal(Float.toString((accountTotal != null) ? accountTotal : 0.0f));
				logger.info("accountTotal = "+accountTotal);				
			}
			
			accountsReceivableDetailByAccType.setAsOfDate(asOfDate);
			accountsReceivableDetailByAccType.setAccountType(accountType);
			accountsReceivableDetailByAccType.setCurrentDue(Float.toString((currentDue != null) ? currentDue : 0.0f));
			accountsReceivableDetailByAccType.setThirtyDaysDue(Float.toString((thirtyDaysDue != null) ? thirtyDaysDue : 0.0f));
			accountsReceivableDetailByAccType.setSixtyDaysDue(Float.toString((sixtyDaysDue != null) ? sixtyDaysDue : 0.0f));
			accountsReceivableDetailByAccType.setNintyDaysDue(Float.toString((nintyDaysDue != null) ? nintyDaysDue : 0.0f));
			accountsReceivableDetailByAccType.setMoreThanNintyDaysDue(Float.toString((moreThanNintyDaysDue != null) ? moreThanNintyDaysDue : 0.0f));
			
			/*String query = "select a.name,count(ib.isPaid!=1) as unPaid,SUM(ib.totalCharge) as totalCharge,SUM(ip.paymentAmt) as paymentAmt from Invoice_Billing ib,Invoice_Payment ip,Account a where "+
							"a.acct_type='"+accountType+"' and a.idAccount=ib.accountId and ib.invoiceId = ip.invoiceId and ib.invoiceDate<='"+asOfDate+"' and ip.paymentDate<='"+asOfDate+"' and ib.isPaid!=1 group by a.name";*/
			
			String query = "select a.name,count(ib.isPaid!=1) as unPaid,SUM(ib.totalCharge) as totalCharge from Invoice_Billing ib,Account a where "+
						   "a.acct_type='"+accountType+"' and ib.invoiceDate<='"+asOfDate+"' and a.idAccount=ib.accountId and ib.isPaid!=1 group by a.name";

			accountsReceivableAccTypeDetailList = jdbcTemplate.query(query, new Object[] {}, new RowMapper<AccountsReceivableAccTypeDetail>() {

				public AccountsReceivableAccTypeDetail mapRow(ResultSet rs, int rowNum) throws SQLException {

					AccountsReceivableAccTypeDetail accountsReceivableAccTypeDetail = new AccountsReceivableAccTypeDetail();				
					accountsReceivableAccTypeDetail.setAccount((rs.getString("name") != null) ? rs.getString("name") : " ");
					accountsReceivableAccTypeDetail.setUnPaid((rs.getString("unPaid") != null) ? rs.getString("unPaid") : " ");
					
					Float balance = 0.0f;
					String totalCharge = (rs.getString("totalCharge") != null) ? rs.getString("totalCharge") : "0.00";
					logger.info("totalCharge = "+totalCharge);
					
					String paymentAmt = getInvoiceBillingPayment(accountType, asOfDate1);
					logger.info("paymentAmt = "+paymentAmt);
					
					balance = Float.parseFloat(totalCharge) - Float.parseFloat(paymentAmt);
					logger.info("balance = "+balance);
					accountsReceivableAccTypeDetail.setBalance(roundUp(balance, 2));					
					
					return accountsReceivableAccTypeDetail;
				}
			});
			
			accountsReceivableDetailByAccType.setAccountsReceivableAccTypeDetailList(accountsReceivableAccTypeDetailList);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		return accountsReceivableDetailByAccType;
	}
	
	public String getInvoiceBillingPayment(String accountType, String asOfDate)
	{
		//String paymentQry = "select sum(paymentAmt) as pay from Invoice_Payment where invoiceId=? and ip.paymentDate<='"+asOfDate+"' group by invoiceId";
		String paymentQry = "select SUM(ip.paymentAmt) as paymentAmt from Invoice_Billing ib,Invoice_Payment ip,Account a where a.acct_type='"+accountType+"' "+
					   		"and ip.paymentDate<='"+asOfDate+"' and a.idAccount=ib.accountId and ib.invoiceId = ip.invoiceId group by ip.invoiceId";
		float paymentAmt = 0.0f;
		try
		{					
			paymentAmt = jdbcTemplate.queryForObject(paymentQry,new Object[] {},new RowMapper<Float>() {

				public Float mapRow(ResultSet rs, int rowNum)throws SQLException {
					return rs.getFloat("paymentAmt");
				}
			});
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		
		return roundUp(paymentAmt, 2);
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
