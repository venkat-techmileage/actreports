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
	
	$('#dispatchedInvoicesTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4]});
			
	$('#dispatchClearGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			getDispatchedInvoices();			
		}else
			alert("Invalid Date");
	});
	
	// Code for export to excel.
	$('#dispatchClearExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportDispatchedInvoices();			
		}else
			alert("Invalid Date");		
	});
});

function getDispatchedInvoices()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getDispatchedInvoices()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="dispatchedInvoicesForm"></form>');
	$('#dispatchedInvoicesForm').append(formData);
	$('#dispatchedInvoicesForm').attr("action","/actreports/dispatchedInvoices");
	$('#dispatchedInvoicesForm').attr("method","POST");
	$('#dispatchedInvoicesForm').submit();
    $('#dispatchedInvoicesForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportDispatchedInvoices()
{
	//alert('In exportDispatchedInvoices()...');
	var data=dispatchedInvoicesTableToJSON();
	var dispatchedInvoices=data.dispatchedInvoicesData;
	var formData= '';
	for(i in dispatchedInvoices)
	{
		formData+='<input name="callTakerId" type="hidden" value="'+dispatchedInvoices[i].callTakerId+'"/>';
		formData+='<input name="dispatchId" type="hidden" value="'+dispatchedInvoices[i].dispatchId+'"/>';
		formData+='<input name="driverId" type="hidden" value="'+dispatchedInvoices[i].driverId+'"/>';
		formData+='<input name="noOwnerInfo" type="hidden" value="'+dispatchedInvoices[i].noOwnerInfo+'"/>';
		formData+='<input name="poRequired" type="hidden" value="'+dispatchedInvoices[i].poRequired+'"/>';		
	}
	$('body').append('<form id="dispatchedInvoicesTableForm"></form>');
	$('#dispatchedInvoicesTableForm').append(formData);
	$('#dispatchedInvoicesTableForm').attr("action","/actreports/exportDispatchedInvoices");
	$('#dispatchedInvoicesTableForm').attr("method","POST");
	$('#dispatchedInvoicesTableForm').submit();
    $('#dispatchedInvoicesTableForm').remove();
	//return false;
}

function dispatchedInvoicesTableToJSON()
{
	var dispatchedInvoicesList = {dispatchedInvoicesData:[]};
	
	$("#dispatchedInvoicesTable tbody tr").each(function(){
		var dispatchedInvoices={
				callTakerId:$(this).children('td').eq(0).text(),
				dispatchId:$(this).children('td').eq(1).text(),
				driverId:$(this).children('td').eq(2).text(),
				noOwnerInfo:$(this).children('td').eq(3).text(),
				poRequired:$(this).children('td').eq(4).text()				
		};
		dispatchedInvoicesList.dispatchedInvoicesData.push(dispatchedInvoices);
	});
	
	return dispatchedInvoicesList;
}
