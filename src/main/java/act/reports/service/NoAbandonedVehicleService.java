package act.reports.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import act.reports.dao.NoAbandonedVehicleDAO;
import act.reports.model.NoAbandonedVehicle;

@Service("noAbandonedVehicleService")
public class NoAbandonedVehicleService {

private Logger logger=Logger.getLogger(NoAbandonedVehicleService.class);
	
	@Autowired
	NoAbandonedVehicleDAO noAbandonedVehicleDAO;
	
	public NoAbandonedVehicle getNoAbandonedVehicle(){
		logger.info("In NoAbandonedVehicleService-getNoAbandonedVehicle()...");
		NoAbandonedVehicle noAbandonedVehicle = null;
		try{			
			noAbandonedVehicle = noAbandonedVehicleDAO.getNoAbandonedVehicle();
		}catch(Exception e){
			logger.error(e);
		}
		return noAbandonedVehicle;
	}
}
