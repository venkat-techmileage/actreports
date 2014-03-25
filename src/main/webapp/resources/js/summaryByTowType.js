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
		
	$('#summaryByTowType').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[1,2,3,4]});
	
	$('#summaryByTowTypeGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			getSummaryByTowTypeDetails();			
		}else
			alert("Invalid Date");
	});
	
	// Code for export to excel.
	$('#summaryByTowTypeExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportSummaryByTowTypeDetails();			
		}else
			alert("Invalid Date");		
	});
});


function getSummaryByTowTypeDetails()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getSummaryByTowTypeDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="summaryByTowTypeForm"></form>');
	$('#summaryByTowTypeForm').append(formData);
	$('#summaryByTowTypeForm').attr("action","/actreports/summaryByTowTypeDetails");
	$('#summaryByTowTypeForm').attr("method","POST");
	$('#summaryByTowTypeForm').submit();
    $('#summaryByTowTypeForm').remove();
    $.unblockUI({
   	     	     fadeIn : 0,
   	     	     fadeOut : 0,
   	     	     showOverlay : true
   	     	 });
}

function exportSummaryByTowTypeDetails()
{
	//alert('In exportSummaryByTowTypeDetails()...');
	var data=summaryByTowTypeTableToJSON();
	var towTypeDetails=data.towTypeDetailsData;
	var formData= '';
	for(i in towTypeDetails)
	{
		formData+='<input name="type" type="hidden" value="'+towTypeDetails[i].type+'"/>';
		formData+='<input name="noOfTows" type="hidden" value="'+towTypeDetails[i].noOfTows+'"/>';
		formData+='<input name="totalCharges" type="hidden" value="'+towTypeDetails[i].totalCharges+'"/>';
		formData+='<input name="use" type="hidden" value="'+towTypeDetails[i].use+'"/>';
		formData+='<input name="chargesPerTow" type="hidden" value="'+towTypeDetails[i].chargesPerTow+'"/>';			
	}
	$('body').append('<form id="summaryByTowTypeTableForm"></form>');
	$('#summaryByTowTypeTableForm').append(formData);
	$('#summaryByTowTypeTableForm').attr("action","/actreports/exportSummaryByTowType");
	$('#summaryByTowTypeTableForm').attr("method","POST");
	$('#summaryByTowTypeTableForm').submit();
    $('#summaryByTowTypeTableForm').remove();
	return false;
}

function summaryByTowTypeTableToJSON()
{
	var towTypeDetailsList = {towTypeDetailsData:[]};
	
	$("#summaryByTowType tbody tr").each(function(){
		var towTypeDetails={
				type:$(this).children('td').eq(0).text(),
				noOfTows:$(this).children('td').eq(1).text(),
				totalCharges:$(this).children('td').eq(2).text(),
				use:$(this).children('td').eq(3).text(),
				chargesPerTow:$(this).children('td').eq(4).text()				
		};
		towTypeDetailsList.towTypeDetailsData.push(towTypeDetails);
	});
	
	return towTypeDetailsList;
}
