package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.AccountNames;
import act.reports.model.AccountsReceivableDetail;
import act.reports.model.AccountsReceivableDetailInfo;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("accountsReceivableDetailDAO")
public class AccountsReceivableDetailDAO {

	private Logger logger=Logger.getLogger(AccountsReceivableDetailDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public AccountsReceivableDetail getAccountReceivableDetail(SearchCriteria criteria){
		logger.info("In AccountReceivableDetailDAO-getAccountReceivableDetail()...");
		List<AccountsReceivableDetailInfo> accountReceivableDetailInfoList = null;
		List<AccountsReceivableDetailInfo> accountReceivableDetailInfoListFinal = new ArrayList<AccountsReceivableDetailInfo>();
		AccountsReceivableDetail accountsReceivableDetail = new AccountsReceivableDetail();

		try{
			String asOfDate=criteria.getToDate();
			asOfDate = DateUtility.convertAsMySqlDateTime(asOfDate);
			asOfDate = asOfDate.replace("00:00:00", "23:59:59");
			final String asOfDate1 = asOfDate;
			logger.info("asOfDate in AccountReceivableDetailDAO-getAccountReceivableDetail() : "+asOfDate);
			String accountName = criteria.getAccountName();
			logger.info("accountName in AccountReceivableDetailDAO-getAccountReceivableDetail() : "+accountName);

			Float currentDue = 0.0f;
			Float thirtyDaysDue = 0.0f;
			Float sixtyDaysDue = 0.0f;
			Float nintyDaysDue = 0.0f;
			Float moreThanNintyDaysDue = 0.0f;
			Float accountTotal = 0.0f;

			if((accountName!=null && !accountName.isEmpty()) && (asOfDate!=null && !asOfDate.isEmpty())){					
				String currentQry = "select distinct SUM(ib.totalCharge) as currentDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and " +
						 			"ib.invoiceDate BETWEEN ib.invoiceDate and DATE_ADD(ib.invoiceDate, INTERVAL 10 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
				try{
					currentDue = jdbcTemplate.queryForObject(currentQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("currentDue = "+currentDue);
				accountsReceivableDetail.setCurrentDue(Float.toString((currentDue != null) ? currentDue : 0.0f));

				String thirtyDaysQry = "select distinct SUM(ib.totalCharge) as thirtyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN "+
						 			   "DATE_ADD(ib.invoiceDate, INTERVAL 10 DAY) and DATE_ADD(ib.invoiceDate, INTERVAL 40 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
				try{
					thirtyDaysDue = jdbcTemplate.queryForObject(thirtyDaysQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("thirtyDaysDue = "+thirtyDaysDue);
				accountsReceivableDetail.setThirtyDaysDue(Float.toString((thirtyDaysDue != null) ? thirtyDaysDue : 0.0f));

				String sixtyDaysQry = "select distinct SUM(ib.totalCharge) as sixtyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN "+
						 			  "DATE_ADD(ib.invoiceDate, INTERVAL 40 DAY) and DATE_ADD(ib.invoiceDate, INTERVAL 70 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
				try{
					sixtyDaysDue = jdbcTemplate.queryForObject(sixtyDaysQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("sixtyDaysDue = "+sixtyDaysDue);
				accountsReceivableDetail.setSixtyDaysDue(Float.toString((sixtyDaysDue != null) ? sixtyDaysDue : 0.0f));

				String nintyDaysQry = "select distinct SUM(ib.totalCharge) as nintyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate BETWEEN "+
						 			  "DATE_ADD(ib.invoiceDate, INTERVAL 70 DAY) and DATE_ADD(ib.invoiceDate, INTERVAL 100 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
				try{
					nintyDaysDue = jdbcTemplate.queryForObject(nintyDaysQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("nintyDaysDue = "+nintyDaysDue);
				accountsReceivableDetail.setNintyDaysDue(Float.toString((nintyDaysDue != null) ? nintyDaysDue : 0.0f));

				String moreThanNintyDaysQry = "select distinct SUM(ib.totalCharge) as nintyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' "+
						 					  "and ib.invoiceDate>DATE_ADD(ib.invoiceDate, INTERVAL 100 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
				try{
					moreThanNintyDaysDue = jdbcTemplate.queryForObject(moreThanNintyDaysQry, Float.class);
				}catch(EmptyResultDataAccessException erde){
					logger.info(erde);
				}
				logger.info("moreThanNintyDaysDue = "+moreThanNintyDaysDue);
				accountsReceivableDetail.setMoreThanNintyDaysDue(Float.toString((moreThanNintyDaysDue != null) ? moreThanNintyDaysDue : 0.0f));

				accountTotal += (currentDue != null ? currentDue : 0.0f) + (thirtyDaysDue != null ? thirtyDaysDue : 0.0f) + (sixtyDaysDue != null ? sixtyDaysDue : 0.0f) + (nintyDaysDue != null ? nintyDaysDue : 0f) + (moreThanNintyDaysDue != null ? moreThanNintyDaysDue : 0f);
				accountsReceivableDetail.setTotal(Float.toString((accountTotal != null) ? accountTotal : 0.0f));
				logger.info("accountTotal = "+accountTotal);				
			}

			accountsReceivableDetail.setAsOfDate(asOfDate);
			accountsReceivableDetail.setAccountName(accountName);
			accountsReceivableDetail.setCurrentDue(Float.toString((currentDue != null) ? currentDue : 0.0f));
			accountsReceivableDetail.setThirtyDaysDue(Float.toString((thirtyDaysDue != null) ? thirtyDaysDue : 0.0f));
			accountsReceivableDetail.setSixtyDaysDue(Float.toString((sixtyDaysDue != null) ? sixtyDaysDue : 0.0f));
			accountsReceivableDetail.setNintyDaysDue(Float.toString((nintyDaysDue != null) ? nintyDaysDue : 0.0f));
			accountsReceivableDetail.setMoreThanNintyDaysDue(Float.toString((moreThanNintyDaysDue != null) ? moreThanNintyDaysDue : 0.0f));

			/*String query = "select i.invoiceId,sc.callCreatedTime,ib.invoiceDate,DATE_ADD(invoiceDate, INTERVAL 10 DAY) as dueDate,DATEDIFF('"+asOfDate+"',invoiceDate) as age,SUM(ib.totalCharge) as totalCharge,"+
						   "SUM(ip.paymentAmt) as paymentAmt from Invoice i,Invoice_Billing ib,ServiceCallInfo sc,Invoice_Payment ip where sc.serviceCallId = i.serviceCallId and i.invoiceId = ib.invoiceId and "+
						   "ib.invoiceId = ip.invoiceId and ib.accountName='"+accountName+"' and ib.invoiceDate<='"+asOfDate+"' and ip.paymentDate<='"+asOfDate+"' and ib.isPaid!=1 group by i.invoiceId";*/
			
			String query = "select i.invoiceId,sc.callCreatedTime,ib.invoiceDate,DATE_ADD(invoiceDate, INTERVAL 10 DAY) as dueDate,DATEDIFF('"+asOfDate+"',invoiceDate) as age,SUM(ib.totalCharge) as totalCharge from Invoice i,Invoice_Billing ib,"+
					   	   "ServiceCallInfo sc,Account a where a.name='"+accountName+"' and ib.invoiceDate<='"+asOfDate+"' and ib.isPaid!=1 and a.idAccount=ib.accountId and sc.serviceCallId = i.serviceCallId and i.invoiceId = ib.invoiceId group by i.invoiceId";

			accountReceivableDetailInfoList = jdbcTemplate.query(query, new Object[] {}, new RowMapper<AccountsReceivableDetailInfo>() {

				public AccountsReceivableDetailInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

					AccountsReceivableDetailInfo accountReceivableDetailInfo = new AccountsReceivableDetailInfo();	
					String invoiceId = (rs.getString("invoiceId") != null) ? rs.getString("invoiceId") : " ";
					accountReceivableDetailInfo.setInvoiceId(invoiceId);

					String serviceCallDate = (rs.getString("callCreatedTime") != null) ? rs.getString("callCreatedTime") : " ";
					if(!serviceCallDate.isEmpty() && serviceCallDate.length()>10){
						serviceCallDate=DateUtility.convertToDateTimeFormat(serviceCallDate);
					    serviceCallDate=serviceCallDate.substring(0, 10);
					}
					accountReceivableDetailInfo.setServiceCallDate(serviceCallDate);
					
					//accountReceivableDetailInfo.setDueDate((rs.getString("dueDate") != null) ? rs.getString("dueDate") : " ");
					String dueDate = (rs.getString("dueDate") != null) ? rs.getString("dueDate") : " ";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date dueDate1 = null;
					try{
						if(!dueDate.trim().isEmpty()){
							dueDate1 = sdf.parse(dueDate);
							dueDate = DateUtility.getDisplayDate(dueDate1, "MM-dd-yyyy");
						}
					}catch(ParseException pe){
						logger.error(pe);
					}
					logger.info("dueDate = "+dueDate);
					accountReceivableDetailInfo.setDueDate(dueDate);

					float balance = 0.0f;
					String totalCharge = (rs.getString("totalCharge") != null) ? rs.getString("totalCharge") : "0.00";
					logger.info("totalCharge = "+totalCharge);
					
					String paymentAmt = getInvoiceBillingPayment(invoiceId, asOfDate1);					
					logger.info("paymentAmt = "+paymentAmt);
					
					balance = Float.parseFloat(totalCharge) - Float.parseFloat(paymentAmt);
					logger.info("balance = "+balance);

					accountReceivableDetailInfo.setAge((rs.getString("age") != null) ? rs.getString("age") : " ");
					accountReceivableDetailInfo.setBalance(roundUp(balance, 2));					
					return accountReceivableDetailInfo;
				}
			});

			for(int i=0;i<accountReceivableDetailInfoList.size();i++){
				AccountsReceivableDetailInfo accountReceivableDetailInfo = accountReceivableDetailInfoList.get(i);
				if(Float.parseFloat(accountReceivableDetailInfo.getBalance()) > 0){
					accountReceivableDetailInfoListFinal.add(accountReceivableDetailInfo);
				}
			}

			accountsReceivableDetail.setAccountReceivableDetailInfo(accountReceivableDetailInfoListFinal);						
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		return accountsReceivableDetail;
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
	
	public String getInvoiceBillingPayment(String invoiceId, String asOfDate)
	{
		String paymentQry = "select sum(paymentAmt) as pay from Invoice_Payment where invoiceId='"+invoiceId+"' and ip.paymentDate<='"+asOfDate+"' group by invoiceId";
		float payment=(float)0;
		try
		{					
			payment=jdbcTemplate.queryForObject(paymentQry,new Object[] {},new RowMapper<Float>() {

				public Float mapRow(ResultSet rs, int rowNum)throws SQLException {
					return rs.getFloat("pay");
				}
			});
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		
		return roundUp(payment, 2);
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}