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
			
	populateAccountNamesList();
	
	if($('#selectAccount option').size() > 1 && accountName!=""){
		$("#selectAccount option[value='"+accountName+"']").prop("selected","selected");
	}	
		
	$('#summaryByAccount').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[1,2,3,4]});
	$('#summaryByAccountGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $('#selectAccount').val()!=""){
			getSummaryByAccountDetails();
		}else
			alert("Invalid Input Data!");
	});
	
	// Code for export to excel.
	$('#summaryByAccountExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $('#selectAccount').val()!=""){
			exportSummaryByAccountDetails();			
		}else
			alert("Invalid Input Data!");		
	});
});

function populateAccountNamesList(){
	$.ajax({
		async: false,
		method:"post",
		url : "summaryByAccountRatePlan/accountNames",
		dataType:"json",
		contentType:"application/json",
		//data:callListqryToJSON(),
		success:function(result)
		{
			for ( var i = 0, len = result.length; i < len; i++) {
	            var accountNamesList = result[i];
	            $('#selectAccount').append("<option value=\"" + accountNamesList.accountName + "\">" + accountNamesList.accountName + "</option>");
			}
		},
		error:function(error)
		{
			alert("error:"+error);
		}		 
	});		
}

function getSummaryByAccountDetails()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getSummaryByAccountDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	formData+='<input name="accountName" type="hidden" value="'+$('#selectAccount').val()+'"/>';
	$('body').append('<form id="summaryByAccountForm"></form>');
	$('#summaryByAccountForm').append(formData);
	$('#summaryByAccountForm').attr("action","/actreports/summaryByAccountDetails");
	$('#summaryByAccountForm').attr("method","POST");
	$('#summaryByAccountForm').submit();
    $('#summaryByAccountForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportSummaryByAccountDetails()
{
	//alert('In exportSummaryByAccountDetails()...');
	var data=summaryByAccountTableToJSON();
	var accountDetails=data.accountDetailsData;
	var formData= '';
	for(i in accountDetails)
	{
		formData+='<input name="accountRatePlan" type="hidden" value="'+accountDetails[i].accountRatePlan+'"/>';
		formData+='<input name="noOfTows" type="hidden" value="'+accountDetails[i].noOfTows+'"/>';
		formData+='<input name="totalCharges" type="hidden" value="'+accountDetails[i].totalCharges+'"/>';
		formData+='<input name="use" type="hidden" value="'+accountDetails[i].use+'"/>';
		formData+='<input name="chargesPerTow" type="hidden" value="'+accountDetails[i].chargesPerTow+'"/>';			
	}
	$('body').append('<form id="summaryByAccountTableForm"></form>');
	$('#summaryByAccountTableForm').append(formData);
	$('#summaryByAccountTableForm').attr("action","/actreports/exportSummaryByAccountRatePlan");
	$('#summaryByAccountTableForm').attr("method","POST");
	$('#summaryByAccountTableForm').submit();
    $('#summaryByAccountTableForm').remove();
	return false;
}

function summaryByAccountTableToJSON()
{
	var accountDetailsList = {accountDetailsData:[]};
	
	$("#summaryByAccount tbody tr").each(function(){
		var accountDetails={
				accountRatePlan:$(this).children('td').eq(0).text(),
				noOfTows:$(this).children('td').eq(1).text(),
				totalCharges:$(this).children('td').eq(2).text(),
				use:$(this).children('td').eq(3).text(),
				chargesPerTow:$(this).children('td').eq(4).text()				
		};
		accountDetailsList.accountDetailsData.push(accountDetails);
	});
	
	return accountDetailsList;
}
