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

	$("#accountRadio").click(function(){
		$('input:text[name="searchByRadio"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});
		$("#accountName").attr("disabled", false);
		getAutocomleteDetails("accountName","#accountName" );
	});


	if(searchBy!="" && searchBy=="allBillable")
		$("#searchAll").prop("checked", "checked");
	else if(searchBy!="" && searchBy=="byAccount")
		$("#accountRadio").prop("checked", "checked");

	if($("#accountName").val()==""){
		$("#searchAll").prop("checked", "checked");
		$("#accountName").val("");
		$("#accountName").prop("disabled", "disabled");		
	}
	else{
		$("#accountRadio").prop("checked", "checked");
		$("#accountName").focus();
	}

	$("#searchAll").click(function(){		 
		$("#accountName").val("");
		$("#accountName").prop("disabled", "disabled");		  
	});

	$("#accountRadio").click(function(){		 
		$("#accountName").attr("disabled", false);
		$("#accountName").focus();
	});

	$('#billingView').click(function(){
		if($("#searchAll").is(":checked")){
			if($("#asOfDate").val()!=""){
				viewStatement();
			}else
				alert("Please enter valid input details to search.");			
		}
		else if($("#accountRadio").is(":checked")){
			if($("#accountName").val()!="" && $("#asOfDate").val()!=""){
				viewStatement();
			}else
				alert("Please enter valid input details to search.");
		}			
	});	
	
	$('#billingSend').click(function(){
		if($("#searchAll").is(":checked")){
			if($("#asOfDate").val()!=""){
				sendStatements();
			}else
				alert("Please enter valid input details to search.");			
		}
		else if($("#accountRadio").is(":checked")){
			if($("#accountName").val()!="" && $("#asOfDate").val()!=""){
				sendStatements();
			}else
				alert("Please enter valid input details to search.");
		}			
	});	

});

function getAutocomleteDetails(searchid,inputId ){
	var searchIdList=[];
	if(searchid=='accountName')
		searchIdList=getAccountNames();


	$(inputId).autocomplete({
		source: searchIdList	
	});
}
function getAccountNames(){
	var accountNamesList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/billing/get/accountNames",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				accountNamesList.push(obj.accountName);
			});			
		},
		error:function(error){
			alert("error in getAccountNames() :"+error);
		}		 
	});	
	return accountNamesList;
}

function viewStatement()
{
	//alert('In viewStatement()...');
	var searchQuery ="";
	
	if($("#accountRadio").is(":checked"))
		searchQuery = "byAccount";
	else
		searchQuery="allBillable";
	
	var formData= '';
	formData+='<input name="searchString" type="hidden" value="'+searchQuery+'"/>';
	formData+='<input name="asOfDate" type="hidden" value="'+$("#asOfDate").val()+'"/>';
	formData+='<input name="accountName" type="hidden" value="'+$("#accountName").val()+'"/>';
	formData+='<input name="statementType" type="hidden" value="'+$("#statementType option:selected").val()+'"/>';
	
	$('body').append('<form id="viewStatementForm"></form>');
	$('#viewStatementForm').append(formData);
	$('#viewStatementForm').attr("action","/actreports/billing/viewStatement");
	$('#viewStatementForm').attr("method","POST");
	$('#viewStatementForm').submit();
    $('#viewStatementForm').remove();
	//return false;	
}

function sendStatements(){

	$.ajax({
		async: true,
		method:"post",
		url : "billing/sendStatementsEmail",
		dataType:"json",
		contentType:"application/json",
		data:callListQryToJSON(),
		success:function(result)
		{
			//alert(result.message);
		},
		error:function(error)
		{
			alert("error in sendStatements() :"+error);
		}		 
	});	

}

function callListQryToJSON()
{	
	var searchQuery ="";
	if($("#accountRadio").is(":checked"))
		searchQuery = "byAccount";
	else
		searchQuery="allBillable";

	return JSON.stringify({
		"searchString": searchQuery, 
		"asOfDate": $("#asOfDate").val(),
		"accountName": $("#accountName").val(),
		"statementType": $("#statementType option:selected").val()
	});
}