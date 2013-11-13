package act.reports.model;


public class AllInvoices {

	private String serviceCallDate = "";
	private String invoice = "";
	private String requestedBy = "";
	private String salesRep = "";
	private Integer ratePlan;
	/*private Integer priority;
	private String reason = "";*/
	private String priorityAndReason = "";
	private String towType = "";
	private String orDr = "";
	private String driverLicense = "";
	private Integer driverIsOwner;
	private String noOwnerInfo = "";
	private String registeredOwner = "";
	private String registeredOwnerName = "";
	private String registeredOwnerAddress = "";
	private String registeredOwnerCity = "";
	private String registeredOwnerState = "";
	private Integer registeredOwnerZip;
	private String registeredOwnerEmail = "";
	private String registeredOwnerPhone = "";
	private Integer year;
	private String make = "";
	private String model = "";
	private String color = "";
	private String style = "";
	private String vin = "";
	private String plateCountry = "";
	private String plateState = "";
	private String plate = "";
	private String keys = "";
	private String rodio = "";
	private String vehicleStatus = "";
	private String commercialUnit = "";
	private String policeImpound = "";
	private String callTime = "";
	private String dispatchTime = "";
	private String acceptedTime = "";
	private String enrouteTime = "";
	private String arrivedTime = "";
	private String hookedTime = "";
	private String droppedTime = "";
	private String clearTime = "";
	private String totalTime = "";
	private String pickupLocation = "";
	private String pickupAddress = "";
	private String pickupCity = "";
	private String pickupState = "";
	private Integer pickupZipcode;
	private String dropOffLocation = "";
	private String dropOffAddress = "";
	private String dropOffCity = "";
	private String dropOffState = "";
	private Integer dropOffZipcode;
	private Integer truck;
	private Integer driverId;
	private String dispatchId = "";
	private String callReceiverId = "";
	private Float towCharge = 0f;
	private Float storageCharge = 0f;
	private Float laborCharge = 0f;
	private Float mileageCharge = 0f;
	private Float winchCharge = 0f;
	private Float gateCharge = 0f;
	private Float adminCharge = 0f;
	private String miscChargeDesc = "";
	private Float miscCharge = 0f;
	private Float discounts = 0f;
	private Float totalCharges = 0f;
	private Integer amountPaid = 0;
	private String inStorage = "";
	private String billTo = "";
	private String releasedTo = "";
	private String releaseTime = "";
	private String releaseDate = "";
	private String avrSentDate = "";
	private String avrReceivedDate = "";
	private String titleDate = "";
	private String locked = "";
	private String closed = "";
	
	public String getPriorityAndReason() {
		return priorityAndReason;
	}
	public void setPriorityAndReason(String priorityAndReason) {
		this.priorityAndReason = priorityAndReason;
	}	
	public String getServiceCallDate() {
		return serviceCallDate;
	}
	public void setServiceCallDate(String serviceCallDate) {
		this.serviceCallDate = serviceCallDate;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getSalesRep() {
		return salesRep;
	}
	public void setSalesRep(String salesRep) {
		this.salesRep = salesRep;
	}
	public Integer getRatePlan() {
		return ratePlan;
	}
	public void setRatePlan(Integer ratePlan) {
		this.ratePlan = ratePlan;
	}
	public String getTowType() {
		return towType;
	}
	public void setTowType(String towType) {
		this.towType = towType;
	}
	public String getOrDr() {
		return orDr;
	}
	public void setOrDr(String orDr) {
		this.orDr = orDr;
	}
	public String getDriverLicense() {
		return driverLicense;
	}
	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}
	public Integer getDriverIsOwner() {
		return driverIsOwner;
	}
	public void setDriverIsOwner(Integer driverIsOwner) {
		this.driverIsOwner = driverIsOwner;
	}
	public String getNoOwnerInfo() {
		return noOwnerInfo;
	}
	public void setNoOwnerInfo(String noOwnerInfo) {
		this.noOwnerInfo = noOwnerInfo;
	}
	public String getRegisteredOwner() {
		return registeredOwner;
	}
	public void setRegisteredOwner(String registeredOwner) {
		this.registeredOwner = registeredOwner;
	}
	public String getRegisteredOwnerName() {
		return registeredOwnerName;
	}
	public void setRegisteredOwnerName(String registeredOwnerName) {
		this.registeredOwnerName = registeredOwnerName;
	}
	public String getRegisteredOwnerAddress() {
		return registeredOwnerAddress;
	}
	public void setRegisteredOwnerAddress(String registeredOwnerAddress) {
		this.registeredOwnerAddress = registeredOwnerAddress;
	}
	public String getRegisteredOwnerCity() {
		return registeredOwnerCity;
	}
	public void setRegisteredOwnerCity(String registeredOwnerCity) {
		this.registeredOwnerCity = registeredOwnerCity;
	}
	public String getRegisteredOwnerState() {
		return registeredOwnerState;
	}
	public void setRegisteredOwnerState(String registeredOwnerState) {
		this.registeredOwnerState = registeredOwnerState;
	}
	public Integer getRegisteredOwnerZip() {
		return registeredOwnerZip;
	}
	public void setRegisteredOwnerZip(Integer registeredOwnerZip) {
		this.registeredOwnerZip = registeredOwnerZip;
	}
	public String getRegisteredOwnerEmail() {
		return registeredOwnerEmail;
	}
	public void setRegisteredOwnerEmail(String registeredOwnerEmail) {
		this.registeredOwnerEmail = registeredOwnerEmail;
	}
	public String getRegisteredOwnerPhone() {
		return registeredOwnerPhone;
	}
	public void setRegisteredOwnerPhone(String registeredOwnerPhone) {
		this.registeredOwnerPhone = registeredOwnerPhone;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getPlateCountry() {
		return plateCountry;
	}
	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public String getRodio() {
		return rodio;
	}
	public void setRodio(String rodio) {
		this.rodio = rodio;
	}
	public String getVehicleStatus() {
		return vehicleStatus;
	}
	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}
	public String getCommercialUnit() {
		return commercialUnit;
	}
	public void setCommercialUnit(String commercialUnit) {
		this.commercialUnit = commercialUnit;
	}
	public String getPoliceImpound() {
		return policeImpound;
	}
	public void setPoliceImpound(String policeImpound) {
		this.policeImpound = policeImpound;
	}
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public String getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public String getAcceptedTime() {
		return acceptedTime;
	}
	public void setAcceptedTime(String acceptedTime) {
		this.acceptedTime = acceptedTime;
	}
	public String getEnrouteTime() {
		return enrouteTime;
	}
	public void setEnrouteTime(String enrouteTime) {
		this.enrouteTime = enrouteTime;
	}
	public String getArrivedTime() {
		return arrivedTime;
	}
	public void setArrivedTime(String arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	public String getHookedTime() {
		return hookedTime;
	}
	public void setHookedTime(String hookedTime) {
		this.hookedTime = hookedTime;
	}
	public String getDroppedTime() {
		return droppedTime;
	}
	public void setDroppedTime(String droppedTime) {
		this.droppedTime = droppedTime;
	}
	public String getClearTime() {
		return clearTime;
	}
	public void setClearTime(String clearTime) {
		this.clearTime = clearTime;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	public String getPickupCity() {
		return pickupCity;
	}
	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}
	public String getPickupState() {
		return pickupState;
	}
	public void setPickupState(String pickupState) {
		this.pickupState = pickupState;
	}
	public Integer getPickupZipcode() {
		return pickupZipcode;
	}
	public void setPickupZipcode(Integer pickupZipcode) {
		this.pickupZipcode = pickupZipcode;
	}
	public String getDropOffLocation() {
		return dropOffLocation;
	}
	public void setDropOffLocation(String dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}
	public String getDropOffAddress() {
		return dropOffAddress;
	}
	public void setDropOffAddress(String dropOffAddress) {
		this.dropOffAddress = dropOffAddress;
	}
	public String getDropOffCity() {
		return dropOffCity;
	}
	public void setDropOffCity(String dropOffCity) {
		this.dropOffCity = dropOffCity;
	}
	public String getDropOffState() {
		return dropOffState;
	}
	public void setDropOffState(String dropOffState) {
		this.dropOffState = dropOffState;
	}
	public Integer getDropOffZipcode() {
		return dropOffZipcode;
	}
	public void setDropOffZipcode(Integer dropOffZipcode) {
		this.dropOffZipcode = dropOffZipcode;
	}
	public Integer getTruck() {
		return truck;
	}
	public void setTruck(Integer truck) {
		this.truck = truck;
	}
	public Integer getDriverId() {
		return driverId;
	}
	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	public String getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(String dispatchId) {
		this.dispatchId = dispatchId;
	}
	public String getCallReceiverId() {
		return callReceiverId;
	}
	public void setCallReceiverId(String callReceiverId) {
		this.callReceiverId = callReceiverId;
	}
	public Float getTowCharge() {
		return towCharge;
	}
	public void setTowCharge(Float towCharge) {
		this.towCharge = towCharge;
	}
	public Float getStorageCharge() {
		return storageCharge;
	}
	public void setStorageCharge(Float storageCharge) {
		this.storageCharge = storageCharge;
	}
	public Float getMileageCharge() {
		return mileageCharge;
	}
	public Float getLaborCharge() {
		return laborCharge;
	}
	public void setLaborCharge(Float laborCharge) {
		this.laborCharge = laborCharge;
	}
	public void setMileageCharge(Float mileageCharge) {
		this.mileageCharge = mileageCharge;
	}
	public Float getWinchCharge() {
		return winchCharge;
	}
	public void setWinchCharge(Float winchCharge) {
		this.winchCharge = winchCharge;
	}
	public Float getGateCharge() {
		return gateCharge;
	}
	public void setGateCharge(Float gateCharge) {
		this.gateCharge = gateCharge;
	}
	public Float getAdminCharge() {
		return adminCharge;
	}
	public void setAdminCharge(Float adminCharge) {
		this.adminCharge = adminCharge;
	}
	public String getMiscChargeDesc() {
		return miscChargeDesc;
	}
	public void setMiscChargeDesc(String miscChargeDesc) {
		this.miscChargeDesc = miscChargeDesc;
	}
	public Float getMiscCharge() {
		return miscCharge;
	}
	public void setMiscCharge(Float miscCharge) {
		this.miscCharge = miscCharge;
	}
	public Float getDiscounts() {
		return discounts;
	}
	public void setDiscounts(Float discounts) {
		this.discounts = discounts;
	}
	public Float getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(Float totalCharges) {
		this.totalCharges = totalCharges;
	}
	public Integer getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Integer amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getInStorage() {
		return inStorage;
	}
	public void setInStorage(String inStorage) {
		this.inStorage = inStorage;
	}
	public String getBillTo() {
		return billTo;
	}
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}
	public String getReleasedTo() {
		return releasedTo;
	}
	public void setReleasedTo(String releasedTo) {
		this.releasedTo = releasedTo;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getAvrSentDate() {
		return avrSentDate;
	}
	public void setAvrSentDate(String avrSentDate) {
		this.avrSentDate = avrSentDate;
	}
	public String getAvrReceivedDate() {
		return avrReceivedDate;
	}
	public void setAvrReceivedDate(String avrReceivedDate) {
		this.avrReceivedDate = avrReceivedDate;
	}
	public String getTitleDate() {
		return titleDate;
	}
	public void setTitleDate(String titleDate) {
		this.titleDate = titleDate;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public String getClosed() {
		return closed;
	}
	public void setClosed(String closed) {
		this.closed = closed;
	}
	
}
