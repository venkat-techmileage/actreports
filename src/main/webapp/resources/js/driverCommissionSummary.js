var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#commissions_r").addClass("selected");
	
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
	
	$('#driverCommissionSummaryTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6,7,8,9]});
			
	$('#driverCommissionSummaryGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#userId").val()!=""){
			getDriverCommissionSummaryDetails();			
		}else
			alert('Please select From & To dates to search.');
	});
	
	// Code for export to excel.
	$('#driverCommissionSummaryExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#userId").val()!=""){
			exportDriverCommissionSummaryDetails();			
		}else
			alert('Please select From & To dates to search.');	
	});
});

function getDriverCommissionSummaryDetails()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getDriverCommissionSummaryDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="driverCommissionSummaryForm"></form>');
	$('#driverCommissionSummaryForm').append(formData);
	$('#driverCommissionSummaryForm').attr("action","/actreports/showDriverCommissionSummaryDetails");
	$('#driverCommissionSummaryForm').attr("method","POST");
	$('#driverCommissionSummaryForm').submit();
    $('#driverCommissionSummaryForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportDriverCommissionSummaryDetails()
{
	//alert('In exportDriverCommissionSummaryDetails()...');
	var data=driverCommissionSummaryTableToJSON();
	var driverCommissionSummaryDetails=data.driverCommissionSummaryDetailsData;
	var formData= '';
	for(i in driverCommissionSummaryDetails)
	{
		formData+='<input name="payrollId" type="hidden" value="'+driverCommissionSummaryDetails[i].payrollId+'"/>';
		formData+='<input name="userId" type="hidden" value="'+driverCommissionSummaryDetails[i].userId+'"/>';
		formData+='<input name="fullName" type="hidden" value="'+driverCommissionSummaryDetails[i].fullName+'"/>';
		formData+='<input name="startDate" type="hidden" value="'+driverCommissionSummaryDetails[i].startDate+'"/>';
		formData+='<input name="level" type="hidden" value="'+driverCommissionSummaryDetails[i].level+'"/>';
		formData+='<input name="noOfTows" type="hidden" value="'+driverCommissionSummaryDetails[i].noOfTows+'"/>';		
		formData+='<input name="weeklyBase" type="hidden" value="'+driverCommissionSummaryDetails[i].weeklyBase+'"/>';
		formData+='<input name="commission" type="hidden" value="'+driverCommissionSummaryDetails[i].commission+'"/>';
		formData+='<input name="commissionAdj" type="hidden" value="'+driverCommissionSummaryDetails[i].commissionAdj+'"/>';
		formData+='<input name="totalPay" type="hidden" value="'+driverCommissionSummaryDetails[i].totalPay+'"/>';
	}
	$('body').append('<form id="driverCommissionSummaryTableForm"></form>');
	$('#driverCommissionSummaryTableForm').append(formData);
	$('#driverCommissionSummaryTableForm').attr("action","/actreports/exportDriverCommissionSummaryDetails");
	$('#driverCommissionSummaryTableForm').attr("method","POST");
	$('#driverCommissionSummaryTableForm').submit();
    $('#driverCommissionSummaryTableForm').remove();
	//return false;
}

function driverCommissionSummaryTableToJSON()
{
	var driverCommissionSummaryDetailsList = {driverCommissionSummaryDetailsData:[]};
	
	$("#driverCommissionSummaryTable tbody tr").each(function(){
		var driverCommissionSummaryDetails={
				payrollId:$(this).children('td').eq(0).text(),
				userId:$(this).children('td').eq(1).text(),
				fullName:$(this).children('td').eq(2).text(),
				startDate:$(this).children('td').eq(3).text(),
				level:$(this).children('td').eq(4).text(),
				noOfTows:$(this).children('td').eq(5).text(),
				weeklyBase:$(this).children('td').eq(6).text(),
				commission:$(this).children('td').eq(7).text(),
				commissionAdj:$(this).children('td').eq(8).text(),
				totalPay:$(this).children('td').eq(9).text()
		};
		driverCommissionSummaryDetailsList.driverCommissionSummaryDetailsData.push(driverCommissionSummaryDetails);
	});
	
	return driverCommissionSummaryDetailsList;
}