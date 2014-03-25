var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#accounting_r").addClass("selected");
	
	$('#missingPOsTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6,7,8]});
			
	$('#missingPOsGo').click(function(){		
			getMissingPOs();
	});
	
	// Code for export to excel.
	$('#missingPOsExport').click(function(){
			exportMissingPOs();
	});
	
	$('#missingPOsSave').click(function(){
		saveMissingPOs();
	});
});

function getMissingPOs()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getMissingPOs()...');
	$('body').append('<form id="missingPOsForm"></form>');
	//$('#dispatchedInvoicesForm').append(formData);
	$('#missingPOsForm').attr("action","/actreports/showMissingPOs");
	$('#missingPOsForm').attr("method","POST");
	$('#missingPOsForm').submit();
    $('#missingPOsForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportMissingPOs()
{
	//alert('In exportMissingPOs()...');
	var data=missingPOsTableToJSON();
	var missingPOs=data.missingPOsData;
	var formData= '';
	for(i in missingPOs)
	{
		formData+='<input name="invoiceId" type="hidden" value="'+missingPOs[i].invoiceId+'"/>';
		formData+='<input name="serviceCallDate" type="hidden" value="'+missingPOs[i].serviceCallDate+'"/>';
		formData+='<input name="year" type="hidden" value="'+missingPOs[i].year+'"/>';
		formData+='<input name="make" type="hidden" value="'+missingPOs[i].make+'"/>';
		formData+='<input name="model" type="hidden" value="'+missingPOs[i].model+'"/>';		
		formData+='<input name="vin" type="hidden" value="'+missingPOs[i].vin+'"/>';
		formData+='<input name="billedTo" type="hidden" value="'+missingPOs[i].billedTo+'"/>';
		formData+='<input name="amount" type="hidden" value="'+missingPOs[i].amount+'"/>';
		formData+='<input name="poNumber" type="hidden" value="'+missingPOs[i].poNumber+'"/>';
	}
	$('body').append('<form id="missingPOsTableForm"></form>');
	$('#missingPOsTableForm').append(formData);
	$('#missingPOsTableForm').attr("action","/actreports/exportMissingPOs");
	$('#missingPOsTableForm').attr("method","POST");
	$('#missingPOsTableForm').submit();
    $('#missingPOsTableForm').remove();
	//return false;
}

function missingPOsTableToJSON()
{
	var missingPOsList = {missingPOsData:[]};
	
	$("#missingPOsTable tbody tr").each(function(){
		var missingPOs={
				invoiceId:$(this).children('td').eq(0).text(),
				serviceCallDate:$(this).children('td').eq(1).text(),
				year:$(this).children('td').eq(2).text(),
				make:$(this).children('td').eq(3).text(),
				model:$(this).children('td').eq(4).text(),				
				vin:$(this).children('td').eq(5).text(),
				billedTo:$(this).children('td').eq(6).text(),
				amount:$(this).children('td').eq(7).text(),
				//poNumber:$(this).children('td').eq(8).text()
				poNumber:$(this).find("input").val()
		};
		missingPOsList.missingPOsData.push(missingPOs);
	});
	
	return missingPOsList;
}

function saveMissingPOs()
{
	//alert('In saveMissingPOs()...');
	var data=missingPOsTableToJSON();
	var missingPOs=data.missingPOsData;
	var formData= '';
	for(i in missingPOs)
	{
		formData+='<input name="invoiceId" type="hidden" value="'+missingPOs[i].invoiceId+'"/>';
		formData+='<input name="serviceCallDate" type="hidden" value="'+missingPOs[i].serviceCallDate+'"/>';
		formData+='<input name="year" type="hidden" value="'+missingPOs[i].year+'"/>';
		formData+='<input name="make" type="hidden" value="'+missingPOs[i].make+'"/>';
		formData+='<input name="model" type="hidden" value="'+missingPOs[i].model+'"/>';		
		formData+='<input name="vin" type="hidden" value="'+missingPOs[i].vin+'"/>';
		formData+='<input name="billedTo" type="hidden" value="'+missingPOs[i].billedTo+'"/>';
		formData+='<input name="amount" type="hidden" value="'+missingPOs[i].amount+'"/>';
		formData+='<input name="poNumber" type="hidden" value="'+missingPOs[i].poNumber+'"/>';
	}
	$('body').append('<form id="missingPOsTableForm"></form>');
	$('#missingPOsTableForm').append(formData);
	$('#missingPOsTableForm').attr("action","/actreports/saveMissingPOs");
	$('#missingPOsTableForm').attr("method","POST");
	$('#missingPOsTableForm').submit();
    $('#missingPOsTableForm').remove();
	//return false;
}