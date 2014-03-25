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
		
	$('#summaryByDriver').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[2,3,4,5]});
	//alert(baseUrl);
	$('#summaryByDriverGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			getSummaryByDriverDetails();
			//$("#summaryByDriverForm").remove();
		}else
			alert("Invalid Date");
	});
	
	// Code for export to excel.
	$('#summaryByDriverExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportSummaryByDriverDetails();			
		}else
			alert("Invalid Date");		
	});
});


function getSummaryByDriverDetails()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getSummaryByDriverDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="summaryByDriverForm"></form>');
	$('#summaryByDriverForm').append(formData);
	$('#summaryByDriverForm').attr("action","/actreports/summaryByDriverDetails");
	$('#summaryByDriverForm').attr("method","POST");
	$('#summaryByDriverForm').submit();
    $('#summaryByDriverForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportSummaryByDriverDetails()
{
	//alert('In exportSummaryByDriverDetails()...');
	var data=summaryByDriverTableToJSON();
	var driverDetails=data.driverDetailsData;
	var formData= '';
	for(i in driverDetails)
	{
		formData+='<input name="driverId" type="hidden" value="'+driverDetails[i].driverId+'"/>';
		formData+='<input name="driverName" type="hidden" value="'+driverDetails[i].driverName+'"/>';
		formData+='<input name="noOfTows" type="hidden" value="'+driverDetails[i].noOfTows+'"/>';
		formData+='<input name="totalCharges" type="hidden" value="'+driverDetails[i].totalCharges+'"/>';
		formData+='<input name="use" type="hidden" value="'+driverDetails[i].use+'"/>';
		formData+='<input name="chargesPerTow" type="hidden" value="'+driverDetails[i].chargesPerTow+'"/>';			
	}
	$('body').append('<form id="summaryByDriverTableForm"></form>');
	$('#summaryByDriverTableForm').append(formData);
	$('#summaryByDriverTableForm').attr("action","/actreports/exportSummaryByDriver");
	$('#summaryByDriverTableForm').attr("method","POST");
	$('#summaryByDriverTableForm').submit();
    $('#summaryByDriverTableForm').remove();
	return false;
}

function summaryByDriverTableToJSON()
{
	var driverDetailsList = {driverDetailsData:[]};
	
	$("#summaryByDriver tbody tr").each(function(){
		var driverDetails={
				driverId:$(this).children('td').eq(0).text(),
				driverName:$(this).children('td').eq(1).text(),
				noOfTows:$(this).children('td').eq(2).text(),
				totalCharges:$(this).children('td').eq(3).text(),
				use:$(this).children('td').eq(4).text(),
				chargesPerTow:$(this).children('td').eq(5).text()				
		};
		driverDetailsList.driverDetailsData.push(driverDetails);
	});
	
	return driverDetailsList;
}
