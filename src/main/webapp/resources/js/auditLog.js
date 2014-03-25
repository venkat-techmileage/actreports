var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#audit_r").addClass("selected");
	
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
	
	$('#auditTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6]});
	$('#auditTotalTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4]});		
			
	$('#auditGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			getAuditDetails();			
		}else
			alert("Invalid Date");
	});
	
	// Code for export to excel.
	$('#auditExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportAuditDetails();			
		}else
			alert("Invalid Date");		
	});
});

function getAuditDetails()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });

	//alert('In getAuditDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="auditForm"></form>');
	$('#auditForm').append(formData);
	$('#auditForm').attr("action","/actreports/displayAudits");
	$('#auditForm').attr("method","POST");
	$('#auditForm').submit();
    $('#auditForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });

}

function exportAuditDetails()
{
	//alert('In exportAuditDetails()...');
	var data=auditTableToJSON();
	var auditDetails=data.auditDetailsData;
	var formData= '';
	for(i in auditDetails)
	{
		formData+='<input name="userId" type="hidden" value="'+auditDetails[i].userId+'"/>';
		formData+='<input name="changeDate" type="hidden" value="'+auditDetails[i].changeDate+'"/>';
		formData+='<input name="changeTime" type="hidden" value="'+auditDetails[i].changeTime+'"/>';
		formData+='<input name="invoice" type="hidden" value="'+auditDetails[i].invoice+'"/>';
		formData+='<input name="originalTotalCharges" type="hidden" value="'+auditDetails[i].originalTotalCharges+'"/>';
		formData+='<input name="newTotalCharges" type="hidden" value="'+auditDetails[i].newTotalCharges+'"/>';			
		formData+='<input name="chargesDifference" type="hidden" value="'+auditDetails[i].chargesDifference+'"/>';
	}
	$('body').append('<form id="auditDetailsTableForm"></form>');
	$('#auditDetailsTableForm').append(formData);
	$('#auditDetailsTableForm').attr("action","/actreports/exportAudits");
	$('#auditDetailsTableForm').attr("method","POST");
	$('#auditDetailsTableForm').submit();
    $('#auditDetailsTableForm').remove();
	//return false;
}

function auditTableToJSON()
{
	var auditDetailsList = {auditDetailsData:[]};
	
	$("#auditTable tbody tr").each(function(){
		var auditDetails={
				userId:$(this).children('td').eq(0).text(),
				changeDate:$(this).children('td').eq(1).text(),
				changeTime:$(this).children('td').eq(2).text(),
				invoice:$(this).children('td').eq(3).text(),
				originalTotalCharges:$(this).children('td').eq(4).text(),
				newTotalCharges:$(this).children('td').eq(5).text(),				
				chargesDifference:$(this).children('td').eq(6).text()
		};
		auditDetailsList.auditDetailsData.push(auditDetails);
	});
	
	return auditDetailsList;
}
