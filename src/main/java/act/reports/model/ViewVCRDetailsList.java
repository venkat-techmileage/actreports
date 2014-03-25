package act.reports.model;

import java.util.List;

public class ViewVCRDetailsList {

	private List<String> truckId;
	private List<String> date;
	private List<String> preOrPost;
	private List<String> defects;
	private List<String> driverId;
	private List<String> mileage;
	
	public List<String> getTruckId() {
		return truckId;
	}
	public void setTruckId(List<String> truckId) {
		this.truckId = truckId;
	}
	public List<String> getDate() {
		return date;
	}
	public void setDate(List<String> date) {
		this.date = date;
	}
	public List<String> getPreOrPost() {
		return preOrPost;
	}
	public void setPreOrPost(List<String> preOrPost) {
		this.preOrPost = preOrPost;
	}
	public List<String> getDefects() {
		return defects;
	}
	public void setDefects(List<String> defects) {
		this.defects = defects;
	}
	public List<String> getDriverId() {
		return driverId;
	}
	public void setDriverId(List<String> driverId) {
		this.driverId = driverId;
	}
	public List<String> getMileage() {
		return mileage;
	}
	public void setMileage(List<String> mileage) {
		this.mileage = mileage;
	}					
}