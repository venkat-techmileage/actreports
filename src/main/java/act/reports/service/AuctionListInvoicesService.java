package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.AuctionListInvoicesDAO;
import act.reports.model.AuctionListInvoices;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;

@Service("auctionListInvoicesService")
public class AuctionListInvoicesService {

private Logger logger=Logger.getLogger(AuctionListInvoicesService.class);
	
	@Autowired
	AuctionListInvoicesDAO auctionListInvoicesDAO;
	
	public List<SelectOption> getAuctionList()
	{
		logger.info("In AuctionListInvoicesService-getAuctionList()...");
		List<SelectOption> auctionList = null;
		try{
			auctionList = auctionListInvoicesDAO.getAuctionList();
		}
		catch(Exception e){
			logger.error(e);
		}
		return auctionList;
	}
	
	public AuctionListInvoices getAuctionListInvoicesDetails(SearchCriteria criteria){
		logger.info("In AuctionListInvoicesService-getAuctionListInvoicesDetails()...");
		AuctionListInvoices auctionListInvoices = null;
		try{			
			auctionListInvoices = auctionListInvoicesDAO.getAuctionListInvoicesDetails(criteria);
		}
		catch(Exception e){
			logger.error(e);
		}
		return auctionListInvoices;
	}
}