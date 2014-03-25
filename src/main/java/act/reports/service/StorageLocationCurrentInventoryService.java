package act.reports.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.StorageLocationCurrentInventoryDAO;
import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.model.StorageLocationCurrentInventoryDetails;

@Service("storageLocationCurrentInventoryService")
public class StorageLocationCurrentInventoryService {

private Logger logger=Logger.getLogger(StorageLocationCurrentInventoryService.class);
	
	@Autowired
	StorageLocationCurrentInventoryDAO storageLocationCurrentInventoryDAO;
	public List<SelectOption> getLocationList()
	{
		logger.info("In StorageLocationCurrentInventoryService-getLocationList()...");
		List<SelectOption> locationList = null;
		try{
			locationList = storageLocationCurrentInventoryDAO.getLocationList();
		}
		catch(Exception e){
			logger.error(e);
		}
		return locationList;
	}
	
	public List<StorageLocationCurrentInventoryDetails> getStorageLocationCurrentInventoryDetails(SearchCriteria criteria){
		logger.info("In StorageLocationCurrentInventoryService-getStorageLocationCurrentInventoryDetails()...");
		List<StorageLocationCurrentInventoryDetails> storageLocationCurrentInventoryDetailsList = null;
		try{			
			storageLocationCurrentInventoryDetailsList = storageLocationCurrentInventoryDAO.getStorageLocationCurrentInventoryDetails(criteria);
		}catch(Exception e){
			logger.error(e);
		}
		return storageLocationCurrentInventoryDetailsList;
	}
}
