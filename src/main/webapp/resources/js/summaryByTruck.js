var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#summaries_r").addClass("selected");
	
	var pickerOpts = {
			  changeMonth :true,
			  showOtherMonths: true,
			  changeYear :true,
			  showOn: "both",
	  		  buttonImage: "/actreports/resources/images/calender-icon-blue.png",
	  		  buttonImageOnly: true,
	  		
        };
			$( "#fromDate" ).datepicker(pickerOpts);
			$( "#toDate" ).datepicker(pickerOpts);
		
	$('#summaryByTruck').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[1,2,3,4,5,6]});
	
	$('#summaryByTruckGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			getSummaryByTruckDetails();			
		}else
			alert("Invalid Date");
	});
	
	// Code for export to excel.
	$('#summaryByTruckExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportSummaryByTruckDetails();			
		}else
			alert("Invalid Date");		
	});
});


function getSummaryByTruckDetails()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getSummaryByTruckDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="summaryByTruckForm"></form>');
	$('#summaryByTruckForm').append(formData);
	$('#summaryByTruckForm').attr("action","/actreports/summaryByTruckDetails");
	$('#summaryByTruckForm').attr("method","POST");
	$('#summaryByTruckForm').submit();
    $('#summaryByTruckForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportSummaryByTruckDetails()
{
	//alert('In exportSummaryByTruckDetails()...');
	var data=summaryByTruckTableToJSON();
	var truckDetails=data.truckDetailsData;
	var formData= '';
	for(i in truckDetails)
	{
		formData+='<input name="truck" type="hidden" value="'+truckDetails[i].truck+'"/>';
		formData+='<input name="noOfTows" type="hidden" value="'+truckDetails[i].noOfTows+'"/>';
		formData+='<input name="totalCharges" type="hidden" value="'+truckDetails[i].totalCharges+'"/>';
		formData+='<input name="use" type="hidden" value="'+truckDetails[i].use+'"/>';
		formData+='<input name="chargesPerTow" type="hidden" value="'+truckDetails[i].chargesPerTow+'"/>';			
		formData+='<input name="actualMileage" type="hidden" value="'+truckDetails[i].actualMileage+'"/>';
		formData+='<input name="milesPerTow" type="hidden" value="'+truckDetails[i].milesPerTow+'"/>';
	}
	$('body').append('<form id="summaryByTruckTableForm"></form>');
	$('#summaryByTruckTableForm').append(formData);
	$('#summaryByTruckTableForm').attr("action","/actreports/exportSummaryByTruck");
	$('#summaryByTruckTableForm').attr("method","POST");
	$('#summaryByTruckTableForm').submit();
    $('#summaryByTruckTableForm').remove();
	return false;
}

function summaryByTruckTableToJSON()
{
	var truckDetailsList = {truckDetailsData:[]};
	
	$("#summaryByTruck tbody tr").each(function(){
		var truckDetails={
				truck:$(this).children('td').eq(0).text(),
				noOfTows:$(this).children('td').eq(1).text(),
				totalCharges:$(this).children('td').eq(2).text(),
				use:$(this).children('td').eq(3).text(),
				chargesPerTow:$(this).children('td').eq(4).text(),				
				actualMileage:$(this).children('td').eq(5).text(),
				milesPerTow:$(this).children('td').eq(6).text()
		};
		truckDetailsList.truckDetailsData.push(truckDetails);
	});
	
	return truckDetailsList;
}
