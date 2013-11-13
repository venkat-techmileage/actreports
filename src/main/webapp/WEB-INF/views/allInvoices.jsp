<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Reports - All Invoices</title>
<style type="text/css">
		.pop-div
		{
			width:400px;
			height:200px;
			background-color:#e9e7f8;
			padding-left:40px;
			padding-top:50px;		
		}
		.pop-btns
		{
			padding-left: 75px;
			padding-top: 45px;
		}
		.pop-label
		{
			color:#26352e;
		}
		.disp-search-cal
		{
			margin-top: 2%;
		}
		
</style>
	
</head>
<body>
	<!--header start-->
	<jsp:include page="header.jsp"/>
	<!--header end-->
	<div class="disp-search-cal">
	<table width="80%">
	<tr><td align="center">
	<table>
	<tr><td align="left"><input type="radio" name="dateSearch" id="serviceCallDate" checked="checked">Service Call Date</td></tr>
	<tr></tr>
	<tr><td align="left"><input type="radio" name="dateSearch" id="releaseDate">Release Date</td></tr>
	</table>
	</td>
	<td></td>
	<td>
	<div class="calender">
	<div class="act-calender-control">
		From<input type="text" id="startDate" name="startDate"/>
		to<input type="text" id="endDate" name="endDate"/>	
	</div>
	</div>
	</td>	
	<td>
		<div class="act-button-controls">
			<button type="submit" id="go" class="export">GO</button>
			<button type="submit" id="invoiceExport" class="export">Export To Excel</button>
			<button type="submit" id="clrInvSearch" class="export">Clear</button>
		</div>
	</td>
	<td></td>
	<td>
		<fieldset>
            <legend>Sort</legend>
    			<div class="disp-search-cal">
					<table>
						<tr><td align="left"><input type="radio" name="sortBy" id="byDataGroup">By Data Group</td></tr>
						<tr></tr>
						<tr><td align="left"><input type="radio" name="sortBy" id="byAlphabetical">Alphabetical</td></tr>
					</table>
	        	</div>
        </fieldset>
   	</td>    	
	</tr>
	</table>
	</div>
	<div id="main-wrapper">
	<div class="container">
	<form id="allInvoices">
	<div class="col-3" style="padding-top: 3%;">
		<fieldset>
			<table>
			<tr>
			<td><input type="radio" name="radioSearch" id="radioAll">All</td>
			<td></td>
			</tr>
			<tr>
			<td><input type="radio" name="radioSearch" id="driver">Driver</td>
			<td><input type="text" id="driverSearch" name="searchInput"/></td>
			</tr>
			<tr>
			<td><input type="radio" name="radioSearch" id="truck">Truck</td>
			<td><input type="text" id="truckSearch" name="searchInput"/></td>
			</tr>
			<tr>
			<td><input type="radio" name="radioSearch" id="reason">Reason</td>
			<td><input type="text" id="reasonSearch" name="searchInput"/></td>
			</tr>
			<tr>
			<td><input type="radio" name="radioSearch" id="account">Account</td>
			<td><input type="text" id="accountSearch" name="searchInput"/></td>
			</tr>
			<tr>
			<td><input type="radio" name="radioSearch" id="salesRep">Sales Rep</td>
			<td><input type="text" id="salesRepSearch" name="searchInput"/></td>
			</tr>
			</table>
		</fieldset>
	</div>
	<div class="wrapper-three-fourth">		
				<fieldset>
		            <legend>View / Export</legend>
		            	<div class="act-checkbox-control" id="dataGroup">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="14%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="invoicesAll" name="checkAll" />ALL</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="serviceCallDate" name="check" data="Service Call Date" />Service Call Date</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="invoice" name="check" data="Invoice #" />Invoice #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="requestedBy" name="check" data="Requested By" />Requested By</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="salesRep" name="check" data="Sales Rep" />Sales Rep</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="ratePlan" name="check" data="Rate Plan" />Rate Plan</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="priorityAndReason" name="check" data="Priority, Reason" />Priority, Reason</label></td></tr>								
											<tr><td><label class="act-checkbox"><input type="checkbox" value="towType" name="check" data="Tow Type" />Tow Type</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="orDr" name="check" data="OR/DR#" />OR/DR#</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="driverLicense" name="check" data="Driver's License #" />Driver's License #</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="driverIsOwner" name="check" data="Driver Is Owner" />Driver Is Owner</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="noOwnerInfo" name="check" data="No Owner Information" />No Owner Information</label></td></tr>
										</table>
									</td>
									<td width="12%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value=registeredOwner name="check" data="Registered Owner" />Registered Owner</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerName" name="check" data="RO Name" />RO Name</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerAddress" name="check" data="RO Address" />RO Address</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerCity" name="check" data="RO City" />RO City</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerState" name="check" data="RO State" />RO State</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerZip" name="check" data="RO Zipcode" />RO Zipcode</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerPhone" name="check" data="RO Phone" />RO Phone</label></td></tr>								
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerEmail" name="check" data="RO Email" />RO Email</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="year" name="check" data="Year" />Year</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="make" name="check" data="Make" />Make</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="model" name="check" data="Model" />Model</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="color" name="check" data="Color" />Color</label></td></tr>								
										</table>
									</td>
									<td width="13%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="style" name="check" data="Style" />Style</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="vin" name="check" data="VIN" />VIN</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="plateCountry" name="check" data="Plate Country" />Plate Country</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="plateState" name="check" data="Plate State" />Plate State</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="plate" name="check" data="Plate #" />Plate #</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="keys" name="check" data="Keys" />Keys</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="radio" name="check" data="Radio" />Radio</label></td></tr>								
											<tr><td><label class="act-checkbox"><input type="checkbox" value="vehicleStatus" name="check" data="Vehicle Status" />Vehicle Status</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="commercialUnit" name="check" data="Commercial Unit #" />Commercial Unit #</label></td></tr>								
											<tr><td><label class="act-checkbox"><input type="checkbox" value="policeImpound" name="check" data="Police Impound #" />Police Impound #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="callTime" name="check" data="Call Time" />Call Time</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dispatchTime" name="check" data="Dispatch Time" />Dispatch Time</label></td></tr>																			
										</table>
									</td>
									<td width="12%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="acceptedTime" name="check" data="Accepted Time" />Accepted Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="enrouteTime" name="check" data="Enroute Time" />Enroute Time</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="arrivedTime" name="check" data="Arrived Time" />Arrived Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="hookedTime" name="check" data="Hooked Time" />Hooked Time</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="droppedTime" name="check" data="Dropped Time" />Dropped Time</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="clearTime" name="check" data="Clear Time" />Clear Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="totalTime" name="check" data="Total Time" />Total Time</label></td></tr>								
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupLocation" name="check" data="Pick Up Location" />Pick Up Location</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupAddress" name="check" data="Pick Up Address" />Pick Up Address</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupCity" name="check" data="Pick Up City" />Pick Up City</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupState" name="check" data="Pick Up State" />Pick Up State</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupZipcode" name="check" data="Pick Up Zipcode" />Pick Up Zipcode</label></td></tr>
										</table>
									</td>
									<td width="12%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffLocation" name="check" data="Drop Off Location" />Drop Off Location</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffAddress" name="check" data="Drop Off Address" />Drop Off Address</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffCity" name="check" data="Drop Off City" />Drop Off City</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffState" name="check" data="Drop Off State" />Drop Off State</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffZipcode" name="check" data="Drop Off Zipcode" />Drop Off Zipcode</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="truck" name="check" data="Truck #" />Truck #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="driverId" name="check" data="Driver ID" />Driver ID</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dispatchId" name="check" data="Dispatch ID" />Dispatch ID</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="callReceiverId" name="check" data="Call Receiver ID" />Call Receiver ID</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="towCharge" name="check" data="Tow Charge" />Tow Charge</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="storageCharge" name="check" data="Storage Charge" />Storage Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="laborCharge" name="check" data="Labor Charge" />Labor Charge</label></td></tr>								
										</table>
									</td>
									<td width="20%">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="mileageCharge" name="check" data="Mileage Charge" />Mileage Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="winchCharge" name="check" data="Winch Charge" />Winch Charge</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="gateCharge" name="check" data="Gate Charge" />Gate Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="adminCharge" name="check" data="Admin Charge" />Admin Charge</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="miscChargeDesc" name="check" data="Miscellaneous Charge Description" />Miscellaneous Charge Description</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="miscCharge" name="check" data="Miscellaneous Charge" />Miscellaneous Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="discounts" name="check" data="Discounts" />Discounts</label></td></tr>								
											<tr><td><label class="act-checkbox"><input type="checkbox" value="totalCharges" name="check" data="Total Charges" />Total Charges</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="amountPaid" name="check" data="Amount Paid" />Amount Paid</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="inStorage" name="check" data="In Storage" />In Storage</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="billTo" name="check" data="Bill To" />Bill To</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="releasedTo" name="check" data="Released To" />Released To</label></td></tr>
										</table>
									</td>
									<td width="22%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="releaseTime" name="check" data="Release Time" />Release Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="releaseDate" name="check" data="Release Date" />Release Date</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="avrFiledDate" name="check" data="AVR Filed Date" />AVR Filed Date</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="trnsfrOfAuth" name="check" data="Transfer Of Authorization" />Transfer Of Authorization</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="titleDate" name="check" data="Title Date" />Title Date</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="locked" name="check" data="Locked" />Locked</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="closed" name="check" data="Closed" />Closed</label></td></tr>								
										</table>
									</td>
								</tr>
							</table>						
				</div>
          		<div class="act-checkbox-control" id="alphabetical">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="13%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="invoicesAll" name="checkAll" />ALL</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="acceptedTime" name="check" data="Accepted Time" />Accepted Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="adminCharge" name="check" data="Admin Charge" />Admin Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="amountPaid" name="check" data="Amount Paid" />Amount Paid</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="arrivedTime" name="check" data="Arrived Time" />Arrived Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="avrFiledDate" name="check" data="AVR Filed Date" />AVR Filed Date</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="billTo" name="check" data="Bill To" />Bill To</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="callReceiverId" name="check" data="Call Receiver ID" />Call Receiver ID</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="callTime" name="check" data="Call Time" />Call Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="clearTime" name="check" data="Clear Time" />Clear Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="closed" name="check" data="Closed" />Closed</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="color" name="check" data="Color" />Color</label></td></tr>
										</table>
									</td>
									<td width="13%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="commercialUnit" name="check" data="Commercial Unit #" />Commercial Unit #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="discounts" name="check" data="Discounts" />Discounts</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dispatchId" name="check" data="Dispatch ID" />Dispatch ID</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dispatchTime" name="check" data="Dispatch Time" />Dispatch Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="driverLicense" name="check" data="Driver's License #" />Driver's License #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="driverId" name="check" data="Driver ID" />Driver ID</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="driverIsOwner" name="check" data="Driver Is Owner" />Driver Is Owner</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffAddress" name="check" data="Drop Off Address" />Drop Off Address</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffCity" name="check" data="Drop Off City" />Drop Off City</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffLocation" name="check" data="Drop Off Location" />Drop Off Location</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffState" name="check" data="Drop Off State" />Drop Off State</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="dropOffZipcode" name="check" data="Drop Off Zipcode" />Drop Off Zipcode</label></td></tr>																			
										</table>
									</td>
									<td width="14%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="droppedTime" name="check" data="Dropped Time" />Dropped Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="enrouteTime" name="check" data="Enroute Time" />Enroute Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="gateCharge" name="check" data="Gate Charge" />Gate Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="hookedTime" name="check" data="Hooked Time" />Hooked Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="inStorage" name="check" data="In Storage" />In Storage</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="invoice" name="check" data="Invoice #" />Invoice #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="keys" name="check" data="Keys" />Keys</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="laborCharge" name="check" data="Labor Charge" />Labor Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="locked" name="check" data="Locked" />Locked</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="make" name="check" data="Make" />Make</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="mileageCharge" name="check" data="Mileage Charge" />Mileage Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="miscCharge" name="check" data="Miscellaneous Charge" />Miscellaneous Charge</label></td></tr>																																									
										</table>
									</td>
									<td width="18%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="miscChargeDesc" name="check" data="Miscellaneous Charge Description" />Miscellaneous Charge Description</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="model" name="check" data="Model" />Model</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="noOwnerInfo" name="check" data="No Owner Information" />No Owner Information</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="orDr" name="check" data="OR/DR#" />OR/DR#</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupAddress" name="check" data="Pick Up Address" />Pick Up Address</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupCity" name="check" data="Pick Up City" />Pick Up City</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupLocation" name="check" data="Pick Up Location" />Pick Up Location</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupState" name="check" data="Pick Up State" />Pick Up State</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="pickupZipcode" name="check" data="Pick Up Zipcode" />Pick Up Zipcode</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="plate" name="check" data="Plate #" />Plate #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="plateCountry" name="check" data="Plate Country" />Plate Country</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="plateState" name="check" data="Plate State" />Plate State</label></td></tr>
										</table>
									</td>
									<td width="13%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="policeImpound" name="check" data="Police Impound #" />Police Impound #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="priorityAndReason" name="check" data="Priority, Reason" />Priority, Reason</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="radio" name="check" data="Radio" />Radio</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="ratePlan" name="check" data="Rate Plan" />Rate Plan</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value=registeredOwner name="check" data="Registered Owner" />Registered Owner</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="releaseDate" name="check" data="Release Date" />Release Date</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="releasedTo" name="check" data="Released To" />Released To</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="releaseTime" name="check" data="Release Time" />Release Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="requestedBy" name="check" data="Requested By" />Requested By</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerAddress" name="check" data="RO Address" />RO Address</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerCity" name="check" data="RO City" />RO City</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerEmail" name="check" data="RO Email" />RO Email</label></td></tr>
										</table>
									</td>
									<td width="12%">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerName" name="check" data="RO Name" />RO Name</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerPhone" name="check" data="RO Phone" />RO Phone</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerState" name="check" data="RO State" />RO State</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="registeredOwnerZip" name="check" data="RO Zipcode" />RO Zipcode</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="salesRep" name="check" data="Sales Rep" />Sales Rep</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="serviceCallDate" name="check" data="Service Call Date" />Service Call Date</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="storageCharge" name="check" data="Storage Charge" />Storage Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="style" name="check" data="Style" />Style</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="titleDate" name="check" data="Title Date" />Title Date</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="totalCharges" name="check" data="Total Charges" />Total Charges</label></td></tr> 
											<tr><td><label class="act-checkbox"><input type="checkbox" value="totalTime" name="check" data="Total Time" />Total Time</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="towCharge" name="check" data="Tow Charge" />Tow Charge</label></td></tr>
										</table>
									</td>
									<td width="20%" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr><td><label class="act-checkbox"><input type="checkbox" value="towType" name="check" data="Tow Type" />Tow Type</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="trnsfrOfAuth" name="check" data="Transfer Of Authorization" />Transfer Of Authorization</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="truck" name="check" data="Truck #" />Truck #</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="vehicleStatus" name="check" data="Vehicle Status" />Vehicle Status</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="vin" name="check" data="VIN" />VIN</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="winchCharge" name="check" data="Winch Charge" />Winch Charge</label></td></tr>
											<tr><td><label class="act-checkbox"><input type="checkbox" value="year" name="check" data="Year" />Year</label></td></tr>																													
										</table>
									</td>
								</tr>
							</table>						
						</div>
          	</fieldset>
		<div id="Output" style="overflow: scroll;"></div>
		</div>
	</form>
	</div>
	</div>
	<!--footer start-->
	<jsp:include page="footer.jsp"/>
	<!--footer end-->
</html>