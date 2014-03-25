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
	
	populateAccountTypesList();
			
	$('#accountsReceivableDetailByAccTypeTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2]});
			
	if($('#selectAccountType option').size() > 1 && accountType!=""){
		$("#selectAccountType option[value='"+accountType+"']").prop("selected","selected");
	}
	
	$('#accountsReceivableDetailByAccTypeGo').click(function(){		
		if($("#asOfDate").val()!="" && $("#selectAccountType").val()!=""){
			getAccountsReceivableDetailByAccType();			
		}else
			$("#inputError").show();
		 	$("#inputError").text("Please select an Account Type & enter as of date to search.");		 	
	});
	
	// Code for export to excel.
	$('#accountsReceivableDetailByAccTypeExport').click(function(){
		if($("#asOfDate").val()!="" && $("#selectAccountType").val()!=""){
			exportAccountsReceivableDetailByAccType();			
		}else
			$("#inputError").show();
	 		$("#inputError").text("Please enter an Account Type & enter as of date to search.");	 				
	});
	
	$("#clrSearch").click(function () {
		  $("#selectAccountType").val("") ;
		  $("#asOfDate").val("") ;
		  $("#inputError").hide();

	});
		
});

function populateAccountTypesList(){
	$.ajax({
		async: false,
		method:"post",
		url : "accountsReceivableDetailByAccType/accountTypes",
		dataType:"json",
		contentType:"application/json",
		//data:callListqryToJSON(),
		success:function(result)
		{
			for ( var i = 0, len = result.length; i < len; i++) {
	            var accountTypesList = result[i];
	            $('#selectAccountType').append("<option value=\"" + accountTypesList.accountType + "\">" + accountTypesList.accountType + "</option>");
			}
		},
		error:function(error)
		{
			alert("error:"+error);
		}		 
	});		
}

function getAccountsReceivableDetailByAccType()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getAccountsReceivableDetailByAccType()...');
	var formData= '';
	formData+='<input name="accountType" type="hidden" value="'+$("#selectAccountType").val()+'"/>';
	formData+='<input name="asOfDate" type="hidden" value="'+$("#asOfDate").val()+'"/>';
	$('body').append('<form id="accountsReceivableDetailByAccTypeForm"></form>');
	$('#accountsReceivableDetailByAccTypeForm').append(formData);
	$('#accountsReceivableDetailByAccTypeForm').attr("action","/actreports/showAccountsReceivableDetailByAccType");
	$('#accountsReceivableDetailByAccTypeForm').attr("method","POST");
	$('#accountsReceivableDetailByAccTypeForm').submit();
    $('#accountsReceivableDetailByAccTypeForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportAccountsReceivableDetailByAccType()
{
	//alert('In exportAccountsReceivableDetailByAccType()...');
	var data=accountsReceivableDetailByAccTypeTableToJSON();
	var accountsReceivableDetailByAccType=data.accountsReceivableDetailByAccTypeData;
	var formData= '';
	for(i in accountsReceivableDetailByAccType)
	{
		formData+='<input name="account" type="hidden" value="'+accountsReceivableDetailByAccType[i].account+'"/>';
		formData+='<input name="unPaid" type="hidden" value="'+accountsReceivableDetailByAccType[i].unPaid+'"/>';
		formData+='<input name="balance" type="hidden" value="'+accountsReceivableDetailByAccType[i].balance+'"/>';		
	}
	$('body').append('<form id="accountsReceivableDetailByAccTypeTableForm"></form>');
	$('#accountsReceivableDetailByAccTypeTableForm').append(formData);
	$('#accountsReceivableDetailByAccTypeTableForm').attr("action","/actreports/exportAccountsReceivableDetailByAccType");
	$('#accountsReceivableDetailByAccTypeTableForm').attr("method","POST");
	$('#accountsReceivableDetailByAccTypeTableForm').submit();
    $('#accountsReceivableDetailByAccTypeTableForm').remove();
	//return false;
}

function accountsReceivableDetailByAccTypeTableToJSON()
{
	var accountsReceivableDetailByAccTypeList = {accountsReceivableDetailByAccTypeData:[]};
	
	$("#accountsReceivableDetailByAccTypeTable tbody tr").each(function(){
		var accountsReceivableDetailByAccType={
				account:$(this).children('td').eq(0).text(),
				unPaid:$(this).children('td').eq(1).text(),
				balance:$(this).children('td').eq(2).text()				
		};
		accountsReceivableDetailByAccTypeList.accountsReceivableDetailByAccTypeData.push(accountsReceivableDetailByAccType);
	});
	
	return accountsReceivableDetailByAccTypeList;
}
