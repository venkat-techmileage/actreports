var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#storageManagement_r").addClass("selected");
	
		
	$('#Noabandoned').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6,7,8,9,10]});
	//alert(baseUrl);
	$('#NoAbandonedGo').click(function(){		
		getNoAbandonedVehicle();

	});
	
	// Code for export to excel.
	$('#NoAbandonedExport').click(function(){
		exportNoAbandonedVehicle();
	});
});
function getNoAbandonedVehicle()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getNoAbandoned()...');
	$('body').append('<form id="NoAbandonedForm"></form>');
	//$('#NoAbandonedForm').append(formData);
	$('#NoAbandonedForm').attr("action","/actreports/noAbandonedVehicleDetails");
	$('#NoAbandonedForm').attr("method","POST");
	$('#NoAbandonedForm').submit();
	$('#NoAbandonedForm').remove();
	 $.unblockUI({
 	     fadeIn : 0,
 	     fadeOut : 0,
 	     showOverlay : true
 	 });
}

function exportNoAbandonedVehicle()
{
	//alert('In exportNoAbandonedVehicle()...');
	var data=noAbandonedTableToJSON();
	var noAbandonedVehicleDetails=data.noAbandonedVehicleDetailsData;
	var formData= '';
	for(i in noAbandonedVehicleDetails)
	{
		formData+='<input name="invoice" type="hidden" value="'+noAbandonedVehicleDetails[i].invoice+'"/>';
		formData+='<input name="serviceCallDate" type="hidden" value="'+noAbandonedVehicleDetails[i].serviceCallDate+'"/>';
		formData+='<input name="account" type="hidden" value="'+noAbandonedVehicleDetails[i].account+'"/>';
		formData+='<input name="reason" type="hidden" value="'+noAbandonedVehicleDetails[i].reason+'"/>';
		formData+='<input name="year" type="hidden" value="'+noAbandonedVehicleDetails[i].year+'"/>';
		formData+='<input name="make" type="hidden" value="'+noAbandonedVehicleDetails[i].make+'"/>';			
		formData+='<input name="model" type="hidden" value="'+noAbandonedVehicleDetails[i].model+'"/>';
		formData+='<input name="vin" type="hidden" value="'+noAbandonedVehicleDetails[i].vin+'"/>';
		formData+='<input name="licensePlate" type="hidden" value="'+noAbandonedVehicleDetails[i].licensePlate+'"/>';
		formData+='<input name="daysInStorage" type="hidden" value="'+noAbandonedVehicleDetails[i].dyasInStorage+'"/>';
		formData+='<input name="lotLocation" type="hidden" value="'+noAbandonedVehicleDetails[i].lotLocation+'"/>';
	}
	$('body').append('<form id="NoAbandonedTableForm"></form>');
	$('#NoAbandonedTableForm').append(formData);
	$('#NoAbandonedTableForm').attr("action","/actreports/exportNoAbandonedVehicle");
	$('#NoAbandonedTableForm').attr("method","POST");
	$('#NoAbandonedTableForm').submit();
    $('#NoAbandonedTableForm').remove();
	return false;
}

function noAbandonedTableToJSON()
{
	var noAbandonedVehicleDetailsList = {noAbandonedVehicleDetailsData:[]};
	
	$("#Noabandoned tbody tr").each(function(){
		var noAbandonedVehicleDetails={
				invoice:$(this).children('td').eq(0).text(),
				serviceCallDate:$(this).children('td').eq(1).text(),
				account:$(this).children('td').eq(2).text(),
				reason:$(this).children('td').eq(3).text(),
				year:$(this).children('td').eq(4).text(),
				make:$(this).children('td').eq(5).text(),
				model:$(this).children('td').eq(6).text(),
				vin:$(this).children('td').eq(7).text(),
				licensePlate:$(this).children('td').eq(8).text(),
				daysInStorage:$(this).children('td').eq(9).text(),
				lotLocation:$(this).children('td').eq(10).text()
		};
		noAbandonedVehicleDetailsList.noAbandonedVehicleDetailsData.push(noAbandonedVehicleDetails);
	});
	return noAbandonedVehicleDetailsList;
}
