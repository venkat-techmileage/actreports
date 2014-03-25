package act.reports.model;

public class ViewVCRDetails {
	
	private String truckId = "";
	private String date = "";
	private String preOrPost = "";
	private String defects = "";
	private String driverId = "";
	private String mileage = "";
	private String vehicleInspectionNotes = "";
	private String inspectionNotes = "";
	
	public String getTruckId() {
		return truckId;
	}
	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPreOrPost() {
		return preOrPost;
	}
	public void setPreOrPost(String preOrPost) {
		this.preOrPost = preOrPost;
	}
	public String getDefects() {
		return defects;
	}
	public void setDefects(String defects) {
		this.defects = defects;
	}
	public String getDriverId() {
		return driverId;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getVehicleInspectionNotes() {
		return vehicleInspectionNotes;
	}
	public void setVehicleInspectionNotes(String vehicleInspectionNotes) {
		this.vehicleInspectionNotes = vehicleInspectionNotes;
	}
	public String getInspectionNotes() {
		return inspectionNotes;
	}
	public void setInspectionNotes(String inspectionNotes) {
		this.inspectionNotes = inspectionNotes;
	}					
}