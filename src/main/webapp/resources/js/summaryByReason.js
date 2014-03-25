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
		
	$('#summaryByReason').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[1,2,3,4]});
	//alert(baseUrl);
	$('#summaryByReasonGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			getSummaryByReasonDetails();
			//$("#summaryByReasonForm").remove();
		}else
			alert("Invalid Date");
	});
	
	// Code for export to excel.
	$('#summaryByReasonExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportSummaryByReasonDetails();			
		}else
			alert("Invalid Date");		
	});
});


function getSummaryByReasonDetails()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getSummaryByReasonDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="summaryByReasonForm"></form>');
	$('#summaryByReasonForm').append(formData);
	$('#summaryByReasonForm').attr("action","/actreports/summaryByReasonDetails");
	$('#summaryByReasonForm').attr("method","POST");
	$('#summaryByReasonForm').submit();
    $('#summaryByReasonForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportSummaryByReasonDetails()
{
	//alert('In exportSummaryByReasonDetails()...');
	var data=summaryByReasonTableToJSON();
	var reasonDetails=data.reasonDetailsData;
	var formData= '';
	for(i in reasonDetails)
	{
		formData+='<input name="reason" type="hidden" value="'+reasonDetails[i].reason+'"/>';
		formData+='<input name="noOfTows" type="hidden" value="'+reasonDetails[i].noOfTows+'"/>';
		formData+='<input name="totalCharges" type="hidden" value="'+reasonDetails[i].totalCharges+'"/>';
		formData+='<input name="use" type="hidden" value="'+reasonDetails[i].use+'"/>';
		formData+='<input name="chargesPerTow" type="hidden" value="'+reasonDetails[i].chargesPerTow+'"/>';			
	}
	$('body').append('<form id="summaryByReasonTableForm"></form>');
	$('#summaryByReasonTableForm').append(formData);
	$('#summaryByReasonTableForm').attr("action","/actreports/exportSummaryByReason");
	$('#summaryByReasonTableForm').attr("method","POST");
	$('#summaryByReasonTableForm').submit();
    $('#summaryByReasonTableForm').remove();
	return false;
}

function summaryByReasonTableToJSON()
{
	var reasonDetailsList = {reasonDetailsData:[]};
	
	$("#summaryByReason tbody tr").each(function(){
		var reasonDetails={
				reason:$(this).children('td').eq(0).text(),
				noOfTows:$(this).children('td').eq(1).text(),
				totalCharges:$(this).children('td').eq(2).text(),
				use:$(this).children('td').eq(3).text(),
				chargesPerTow:$(this).children('td').eq(4).text()				
		};
		reasonDetailsList.reasonDetailsData.push(reasonDetails);
	});
	
	return reasonDetailsList;
}
