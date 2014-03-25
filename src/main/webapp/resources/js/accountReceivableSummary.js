var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#accounting_r").addClass("selected");
	
	var pickerOpts = {
			  changeMonth :true,
			  showOtherMonths: true,
			  changeYear :true,
			  showOn: "both",
	  		  buttonImage: "/actreports/resources/images/calender-icon-blue.png",
	  		  buttonImageOnly: true,
	  		
        };
	
			$( "#asOfDate" ).datepicker(pickerOpts);
	
	$('#accountReceivableSummaryTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6]});
			
	$('#accountReceivableSummaryGo').click(function(){		
		if($("#asOfDate").val()!=""){
			getAccountReceivableSummary();			
		}else
			alert("Invalid Date");
	});
	
	// Code for export to excel.
	$('#accountReceivableSummaryExport').click(function(){
		if($("#asOfDate").val()!=""){
			exportAccountReceivableSummary();			
		}else
			alert("Invalid Date");		
	});
});

function getAccountReceivableSummary()
{
	//alert('In getAccountReceivableSummary()...');
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	
	var formData= '';
	formData+='<input name="toDate" type="hidden" value="'+$("#asOfDate").val()+'"/>';
	$('body').append('<form id="accountReceivableSummaryForm"></form>');
	$('#accountReceivableSummaryForm').append(formData);
	$('#accountReceivableSummaryForm').attr("action","/actreports/showAccountReceivableSummary");
	$('#accountReceivableSummaryForm').attr("method","POST");
	$('#accountReceivableSummaryForm').submit();
    $('#accountReceivableSummaryForm').remove();
    
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportAccountReceivableSummary()
{
	//alert('In exportAccountReceivableSummary()...');
	var data=accountReceivableSummaryTableToJSON();
	var accountReceivableSummary=data.accountReceivableSummaryData;
	var formData= '';
	for(i in accountReceivableSummary)
	{
		formData+='<input name="accountName" type="hidden" value="'+accountReceivableSummary[i].accountName+'"/>';
		formData+='<input name="current" type="hidden" value="'+accountReceivableSummary[i].current+'"/>';
		formData+='<input name="thirtyDaysDue" type="hidden" value="'+accountReceivableSummary[i].thirtyDaysDue+'"/>';
		formData+='<input name="sixtyDaysDue" type="hidden" value="'+accountReceivableSummary[i].sixtyDaysDue+'"/>';
		formData+='<input name="nintyDaysDue" type="hidden" value="'+accountReceivableSummary[i].nintyDaysDue+'"/>';
		formData+='<input name="moreThanNintyDaysDue" type="hidden" value="'+accountReceivableSummary[i].moreThanNintyDaysDue+'"/>';
		formData+='<input name="total" type="hidden" value="'+accountReceivableSummary[i].total+'"/>';
	}
	$('body').append('<form id="accountReceivableSummaryTableForm"></form>');
	$('#accountReceivableSummaryTableForm').append(formData);
	$('#accountReceivableSummaryTableForm').attr("action","/actreports/exportAccountReceivableSummary");
	$('#accountReceivableSummaryTableForm').attr("method","POST");
	$('#accountReceivableSummaryTableForm').submit();
    $('#accountReceivableSummaryTableForm').remove();
	//return false;
}

function accountReceivableSummaryTableToJSON()
{
	var accountReceivableSummaryList = {accountReceivableSummaryData:[]};
	
	$("#accountReceivableSummaryTable tbody tr").each(function(){
		var accountReceivableSummary={
				accountName:$(this).children('td').eq(0).text(),
				current:$(this).children('td').eq(1).text(),
				thirtyDaysDue:$(this).children('td').eq(2).text(),
				sixtyDaysDue:$(this).children('td').eq(3).text(),
				nintyDaysDue:$(this).children('td').eq(4).text(),
				moreThanNintyDaysDue:$(this).children('td').eq(5).text(),
				total:$(this).children('td').eq(6).text()
		};
		accountReceivableSummaryList.accountReceivableSummaryData.push(accountReceivableSummary);
	});
	
	return accountReceivableSummaryList;
}
