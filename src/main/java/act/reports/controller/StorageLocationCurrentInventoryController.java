package act.reports.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import act.reports.model.SearchCriteria;
import act.reports.model.SelectOption;
import act.reports.model.StorageLocationCurrentInventoryDetails;
import act.reports.model.StorageLocationCurrentInventoryDetailsList;
import act.reports.service.StorageLocationCurrentInventoryService;

@Controller
//@RequestMapping(value="/storage management")
public class StorageLocationCurrentInventoryController {

private static Logger logger=Logger.getLogger(StorageLocationCurrentInventoryController.class);
	
	@Autowired
	StorageLocationCurrentInventoryService storageLocationCurrentInventoryService;
	
	@Autowired
	private StorageLocationCurrentInventoryControllerHelper storageLocationCurrentInventoryControllerHelper;
	
	@RequestMapping(value="/storageInventory.html")
	public String getHome(){
		logger.info("In StoargeLocationCurrentInventoryController-getHome()...");
		return "storageInventory";
	}
	@RequestMapping(value="/select/getLocationList",method=RequestMethod.POST)
	 public @ResponseBody List<SelectOption> getLocationList()
	 {
	  logger.info("In StorageLocationCurrentInventoryController-getLocationList()...");
	  List<SelectOption> locationList = null;
	  try{
		  locationList = storageLocationCurrentInventoryService.getLocationList();
	  }
	  catch(Exception e){
		  logger.error(e);
	  }
	  return locationList;
	 }
	@RequestMapping(value="/displayStorageLocationCurrentInventory", method=RequestMethod.POST)
	public ModelAndView getStorageLocationCurrentInventoryDetails(SearchCriteria criteria, ModelAndView model) {
		logger.info("In StoargeLocationCurrentInventoryController-getStorageLocationCurrentInventoryDetails()...");
		try{
			List<StorageLocationCurrentInventoryDetails> storageLocationCurrentInventoryDetailsList  = storageLocationCurrentInventoryService.getStorageLocationCurrentInventoryDetails(criteria);
			logger.info("storageLocationCurrentInventoryDetailsList.size() : "+ storageLocationCurrentInventoryDetailsList.size());
			model.addObject("lotLocation", criteria.getLocation());
			model.addObject("ageRangeStart", criteria.getAgeRangeStart());
			model.addObject("ageRangeEnd", criteria.getAgeRangeEnd());
			model.addObject("storageLocationCurrentInventoryDetails", storageLocationCurrentInventoryDetailsList);
			model.addObject("totalVehicles", storageLocationCurrentInventoryDetailsList.size());
			model.setViewName("storageInventory");
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/exportStorageLocationCurrentInventory", method=RequestMethod.POST)
	public ModelAndView exportStorageLocationCurrentInventoryDetails(StorageLocationCurrentInventoryDetailsList storageDetailsList, ModelAndView model )
	{
		logger.info("In StorageLocationCurrentInventoryController-exportStorageLocationCurrentInventoryDetails(...) ...");
		try{
			logger.info("storageDetailsList.getServiceCallDate().size() : "+storageDetailsList.getServiceCallDate().size());
			model.addObject("excelDetails",storageLocationCurrentInventoryControllerHelper.convertStorageLocationCurrentInventoryDetailsListAsExcelFormat(storageDetailsList));
			model.addObject("excelHeaders",storageLocationCurrentInventoryControllerHelper.getStorageLocationCurrentInventoryDetailsListExcelHeaders());
			model.addObject("fileName","StorageLocationCurrentInventory.xls");
			model.setViewName("excelView");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
}
