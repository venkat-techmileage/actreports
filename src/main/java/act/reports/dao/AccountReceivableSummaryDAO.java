package act.reports.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import act.reports.model.AccountReceivableSummary;
import act.reports.model.AccountReceivableSummaryDetails;
import act.reports.model.SearchCriteria;
import act.reports.util.DateUtility;

@Repository("accountReceivableSummaryDAO")
public class AccountReceivableSummaryDAO {

	private Logger logger=Logger.getLogger(AccountReceivableSummaryDAO.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public AccountReceivableSummary getAccountReceivableSummary(SearchCriteria criteria){
		logger.info("In AccountReceivableSummaryDAO-getDispatchedInvoices()...");
		List<AccountReceivableSummaryDetails> accountReceivableSummaryDetailsList = null;
		AccountReceivableSummary accountReceivableSummary = new AccountReceivableSummary();

		try {
			Float currentTotal = 0.0f;
			Float thirtyDaysTotal = 0.0f;
			Float sixtyDaysTotal = 0.0f;
			Float nintyDaysTotal = 0.0f;
			Float moreThanNintyDaysTotal = 0.0f;
			
			String asOfDate=criteria.getToDate();
			asOfDate = DateUtility.convertAsMySqlDateTime(asOfDate);
			asOfDate = asOfDate.replace("00:00:00", "23:59:59");
			logger.info("asOfDate in AccountReceivableSummaryDAO-getAccountReceivableSummary() : "+asOfDate);

			String query = "select distinct ib.invoiceDate,a.name from Invoice_Billing ib,Invoice_Payment ip,Account a where ib.invoiceDate<='"+asOfDate+"' "+ 
						   "and ip.paymentDate<='"+asOfDate+"' and ib.isPaid!=1 and a.idAccount=ib.accountId and ib.invoiceId=ip.invoiceId group by a.name";


			accountReceivableSummaryDetailsList = jdbcTemplate.query(query, new Object[] {}, new RowMapper<AccountReceivableSummaryDetails>() {

				public AccountReceivableSummaryDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

					AccountReceivableSummaryDetails accountReceivableSummaryDetails = new AccountReceivableSummaryDetails();				
					accountReceivableSummaryDetails.setInvoiceDate((rs.getString("invoiceDate") != null) ? rs.getString("invoiceDate") : " ");
					accountReceivableSummaryDetails.setAccountName((rs.getString("name") != null) ? rs.getString("name") : " ");
					accountReceivableSummaryDetails.setCurrent(" ");
					accountReceivableSummaryDetails.setThirtyDaysDue(" ");
					accountReceivableSummaryDetails.setSixtyDaysDue(" ");
					accountReceivableSummaryDetails.setNintyDaysDue(" ");
					accountReceivableSummaryDetails.setMoreThanNintyDaysDue(" ");
					accountReceivableSummaryDetails.setTotal(" ");
					return accountReceivableSummaryDetails;
				}
			});
				
			for(int i=0;i<accountReceivableSummaryDetailsList.size();i++){
				Float currentDue = 0.0f;
				Float thirtyDaysDue = 0.0f;
				Float sixtyDaysDue = 0.0f;
				Float nintyDaysDue = 0.0f;
				Float moreThanNintyDaysDue = 0.0f;
				Float accountTotal = 0.0f;
				
				String accountName = accountReceivableSummaryDetailsList.get(i).getAccountName();
				logger.info("accountName = "+accountName);
				String invoiceDate = accountReceivableSummaryDetailsList.get(i).getInvoiceDate();				
				logger.info("invoiceDate = "+invoiceDate);
				if((accountName!=null && !accountName.isEmpty()) && (invoiceDate!=null && !invoiceDate.isEmpty())){					
					String currentQry = "select distinct SUM(ib.totalCharge) as currentDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate"+
										" BETWEEN ib.invoiceDate and DATE_ADD(ib.invoiceDate, INTERVAL 10 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
					try{
						currentDue = jdbcTemplate.queryForObject(currentQry, Float.class);
					}catch(EmptyResultDataAccessException erde){
						logger.info(erde);
					}
					logger.info("currentDue = "+currentDue);
					accountReceivableSummaryDetailsList.get(i).setCurrent(Float.toString((currentDue != null) ? currentDue : 0.0f));
					
					DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateFormat.parse(invoiceDate));					
					calendar.add(Calendar.DATE,10);
					String billGraceDate = dateFormat.format(calendar.getTime());
					logger.info("billGraceDate = "+billGraceDate);
					String thirtyDaysQry = "select distinct SUM(ib.totalCharge) as thirtyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate "+
										   "BETWEEN '"+billGraceDate+"' and DATE_ADD('"+billGraceDate+"', INTERVAL 30 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
					try{
						thirtyDaysDue = jdbcTemplate.queryForObject(thirtyDaysQry, Float.class);
					}catch(EmptyResultDataAccessException erde){
						logger.info(erde);
					}
					logger.info("thirtyDaysDue = "+thirtyDaysDue);
					accountReceivableSummaryDetailsList.get(i).setThirtyDaysDue(Float.toString((thirtyDaysDue != null) ? thirtyDaysDue : 0.0f));
					
					calendar.setTime(dateFormat.parse(billGraceDate));					
					calendar.add(Calendar.DATE,30);
					String thirtyDaysDate = dateFormat.format(calendar.getTime());
					logger.info("thirtyDaysDate = "+thirtyDaysDate);
					String sixtyDaysQry = "select distinct SUM(ib.totalCharge) as sixtyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate "+
							   			  "BETWEEN '"+thirtyDaysDate+"' and DATE_ADD('"+thirtyDaysDate+"', INTERVAL 30 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
					try{
						sixtyDaysDue = jdbcTemplate.queryForObject(sixtyDaysQry, Float.class);
					}catch(EmptyResultDataAccessException erde){
						logger.info(erde);
					}
					logger.info("sixtyDaysDue = "+sixtyDaysDue);
					accountReceivableSummaryDetailsList.get(i).setSixtyDaysDue(Float.toString((sixtyDaysDue != null) ? sixtyDaysDue : 0.0f));
					
					calendar.setTime(dateFormat.parse(thirtyDaysDate));					
					calendar.add(Calendar.DATE,30);
					String sixtyDaysDate = dateFormat.format(calendar.getTime());
					logger.info("sixtyDaysDate = "+sixtyDaysDate);
					String nintyDaysQry = "select distinct SUM(ib.totalCharge) as nintyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"' and ib.invoiceDate "+
							   			  "BETWEEN '"+sixtyDaysDate+"' and DATE_ADD('"+sixtyDaysDate+"', INTERVAL 30 DAY) and a.name='"+accountName+"' and a.idAccount=ib.accountId";
					try{
						nintyDaysDue = jdbcTemplate.queryForObject(nintyDaysQry, Float.class);
					}catch(EmptyResultDataAccessException erde){
						logger.info(erde);
					}
					logger.info("nintyDaysDue = "+nintyDaysDue);
					accountReceivableSummaryDetailsList.get(i).setNintyDaysDue(Float.toString((nintyDaysDue != null) ? nintyDaysDue : 0.0f));
					
					calendar.setTime(dateFormat.parse(sixtyDaysDate));					
					calendar.add(Calendar.DATE,30);
					String nintyDaysDate = dateFormat.format(calendar.getTime());
					logger.info("nintyDaysDate = "+nintyDaysDate);
					String moreThanNintyDaysQry = "select distinct SUM(ib.totalCharge) as nintyDaysDue from Invoice_Billing ib,Account a where ib.invoiceDate<='"+asOfDate+"'"+
							   					  " and ib.invoiceDate>'"+nintyDaysDate+"' and a.name='"+accountName+"' and a.idAccount=ib.accountId";
					try{
						moreThanNintyDaysDue = jdbcTemplate.queryForObject(moreThanNintyDaysQry, Float.class);
					}catch(EmptyResultDataAccessException erde){
						logger.info(erde);
					}
					logger.info("moreThanNintyDaysDue = "+moreThanNintyDaysDue);
					accountReceivableSummaryDetailsList.get(i).setMoreThanNintyDaysDue(Float.toString((moreThanNintyDaysDue != null) ? moreThanNintyDaysDue : 0.0f));
						
					accountTotal += (currentDue != null ? currentDue : 0.0f) + (thirtyDaysDue != null ? thirtyDaysDue : 0.0f) + (sixtyDaysDue != null ? sixtyDaysDue : 0.0f) + (nintyDaysDue != null ? nintyDaysDue : 0.0f) + (moreThanNintyDaysDue != null ? moreThanNintyDaysDue : 0.0f);
					accountReceivableSummaryDetailsList.get(i).setTotal(Float.toString((accountTotal != null) ? accountTotal : 0.0f));
					logger.info("accountTotal = "+accountTotal);
					
					currentTotal += Float.parseFloat(accountReceivableSummaryDetailsList.get(i).getCurrent());
					thirtyDaysTotal += Float.parseFloat(accountReceivableSummaryDetailsList.get(i).getThirtyDaysDue());
					sixtyDaysTotal += Float.parseFloat(accountReceivableSummaryDetailsList.get(i).getSixtyDaysDue());
					nintyDaysTotal += Float.parseFloat(accountReceivableSummaryDetailsList.get(i).getNintyDaysDue());
					moreThanNintyDaysTotal += Float.parseFloat(accountReceivableSummaryDetailsList.get(i).getMoreThanNintyDaysDue());					
				}
			}
			logger.info("currentTotal = "+currentTotal);
			logger.info("thirtyDaysTotal = "+thirtyDaysTotal);
			logger.info("sixtyDaysTotal = "+sixtyDaysTotal);
			logger.info("nintyDaysTotal = "+nintyDaysTotal);
			logger.info("moreThanNintyDaysTotal = "+moreThanNintyDaysTotal);
			
			Float grandTotal = 0.0f;
			grandTotal += currentTotal + thirtyDaysTotal + sixtyDaysTotal + nintyDaysTotal + moreThanNintyDaysTotal;
			logger.info("grandTotal = "+grandTotal);
			
			accountReceivableSummary.setAsOfDate(asOfDate);
			accountReceivableSummary.setCurrentTotal(roundUp(currentTotal, 2));
			accountReceivableSummary.setThirtyDaysTotal(roundUp(thirtyDaysTotal, 2));
			accountReceivableSummary.setSixtyDaysTotal(roundUp(sixtyDaysTotal, 2));
			accountReceivableSummary.setNintyDaysTotal(roundUp(nintyDaysTotal, 2));
			accountReceivableSummary.setMoreThanNintyDaysTotal(roundUp(moreThanNintyDaysTotal, 2));
			accountReceivableSummary.setGrandTotal(roundUp(grandTotal, 2));
			accountReceivableSummary.setAccountReceivableSummaryDetails(accountReceivableSummaryDetailsList);						
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
		return accountReceivableSummary;
	}
	
	public String roundUp(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}