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
	
	$('#responseTimeDetailsTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[2,3,4,5,6,7,8,9,10,11,12]});
	
	if(lateType!="" && lateType=="All")
		$("#all").prop("checked", "checked");
	else if(lateType!="" && lateType=="LateOnly")
		$("#lateOnly").prop("checked", "checked");
	else
		$("#all").prop("checked", "checked");
	
	if($("#userName").val()=="" && $("#userId").val()=="" && $("#accountName").val()==""){
		//$("#all").prop("checked", "checked");
		$("#searchAll").prop("checked", "checked");
		$('input:text[name="searchByInput"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});		
	}
	else if($("#userName").val()!=""){
		$("#userNameRadio").prop("checked", "checked");
		$("#userId").prop("disabled", "disabled");
		$("#accountName").prop("disabled", "disabled");
	}
	else if($("#userId").val()!=""){
		$("#userIdRadio").prop("checked", "checked");
		$("#userName").prop("disabled", "disabled");
		$("#accountName").prop("disabled", "disabled");
	}
	else if($("#accountName").val()!=""){
		$("#accountRadio").prop("checked", "checked");
		$("#userName").prop("disabled", "disabled");
		$("#userId").prop("disabled", "disabled");
	}
	
	$("#searchAll").click(function(){		 
		 $('input:text[name="searchByInput"]').each(function(){
			 $(this).val("");
			 $(this).attr("disabled", true);
		 });		  
	 });
	
	$("#userNameRadio").click(function(){		 
		 $('input:text[name="searchByInput"]').each(function(){
			   $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#userName").attr("disabled", false);	
		 $("#userName").focus();
		 getAutocomleteDetails("userName","#userName" );
	 });
	
	$("#userIdRadio").click(function(){		 
		 $('input:text[name="searchByInput"]').each(function(){
			   $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#userId").attr("disabled", false);
		 $("#userId").focus();
		 getAutocomleteDetails("userId","#userId" );
	 });
	
	$("#accountRadio").click(function(){		 
		 $('input:text[name="searchByInput"]').each(function(){
			   $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#accountName").attr("disabled", false);
		 $("#accountName").focus();
		 getAutocomleteDetails("accountName","#accountName" );
	 });
	
	$('#responseTimeGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			getResponseTimeDetails();			
		}else
			alert("Please enter valid input details to search.");
	});
	
	// Code for export to excel.
	$('#responseTimeExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportResponseTimeDetails();			
		}else
			alert("Please enter valid input details to search.");		
	});
	
	$("#responseTimeClear").click(function () {				
		  $("#fromDate").val("") ;
		  $("#toDate").val("") ;
		  $("#all").prop("checked", "checked");
		  $("#searchAll").prop("checked", "checked");
		  $('input:text[name="searchByInput"]').each(function(){
			  $(this).val("");
			  $(this).attr("disabled", true);
		  });				  
	});
});

function getAutocomleteDetails(searchid,inputId ){
	var searchIdList=[];
	if(searchid=='userName')
		searchIdList=getUserNames();
	else if(searchid=='userId')
		searchIdList=getUserIds();
	else if(searchid=='accountName')
		searchIdList=getAccountNames();
	
	$(inputId).autocomplete({
		source: searchIdList
	});
}

function getUserNames(){
	var userNamesList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/responseTime/getUserNames",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				userNamesList.push(obj.userName);
			});			
		},
		error:function(error){
			alert("error:"+error);
		}		 
	});	
	return userNamesList;
}

function getUserIds(){
	var userIdsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/responseTime/getUserIds",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				userIdsList.push(obj.userId);
			});			
		},
		error:function(error){
			alert("error:"+error);
		}		 
	});	
	return userIdsList;
}

function getAccountNames(){
	var accountNamesList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/responseTime/getAccountNames",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				accountNamesList.push(obj.accountName);
			});			
		},
		error:function(error){
			alert("error:"+error);
		}		 
	});	
	return accountNamesList;
}

function getResponseTimeDetails()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getResponseTimeDetails()...');
	var formData= '';
	var searchString ="";
	if($("#all").is(":checked")){
		if($("#searchAll").is(":checked"))
			searchString = "All-All";
		else if($("#userNameRadio").is(":checked")){
			searchString = "All-UserName";
			formData+='<input name="userName" type="hidden" value="'+$("#userName").val()+'"/>';
		}
		else if($("#userIdRadio").is(":checked")){
			searchString = "All-UserID";
			formData+='<input name="userId" type="hidden" value="'+$("#userId").val()+'"/>';
		}
		else if($("#accountRadio").is(":checked")){
			searchString = "All-Account";
			formData+='<input name="accountName" type="hidden" value="'+$("#accountName").val()+'"/>';
		}
	}
	else if($("#lateOnly").is(":checked")){		
		if($("#searchAll").is(":checked"))
			searchString="LateOnly-All";
		else if($("#userNameRadio").is(":checked")){
			searchString="LateOnly-UserName";
			formData+='<input name="userName" type="hidden" value="'+$("#userName").val()+'"/>';
		}
		else if($("#userIdRadio").is(":checked")){
			searchString="LateOnly-UserID";
			formData+='<input name="userId" type="hidden" value="'+$("#userId").val()+'"/>';
		}
		else if($("#accountRadio").is(":checked")){
			searchString="LateOnly-Account";
			formData+='<input name="accountName" type="hidden" value="'+$("#accountName").val()+'"/>';
		}
	}
	formData+='<input name="searchString" type="hidden" value="'+searchString+'"/>';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="responseTimeDetailsForm"></form>');
	$('#responseTimeDetailsForm').append(formData);
	$('#responseTimeDetailsForm').attr("action","/actreports/getResponseTimeDetails");
	$('#responseTimeDetailsForm').attr("method","POST");
	$('#responseTimeDetailsForm').submit();
    $('#responseTimeDetailsForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportResponseTimeDetails()
{
	//alert('In exportResponseTimeDetails()...');
	var data=responseTimeDetailsTableToJSON();
	var responseTimeDetails=data.responseTimeDetailsData;
	var formData= '';
	for(i in responseTimeDetails)
	{
		formData+='<input name="invoiceId" type="hidden" value="'+responseTimeDetails[i].invoiceId+'"/>';
		formData+='<input name="account" type="hidden" value="'+responseTimeDetails[i].account+'"/>';
		formData+='<input name="callerTakerId" type="hidden" value="'+responseTimeDetails[i].callerTakerId+'"/>';
		formData+='<input name="dispatchId" type="hidden" value="'+responseTimeDetails[i].dispatchId+'"/>';
		formData+='<input name="driverId" type="hidden" value="'+responseTimeDetails[i].driverId+'"/>';		
		formData+='<input name="callTime" type="hidden" value="'+responseTimeDetails[i].callTime+'"/>';
		formData+='<input name="dispatchedTime" type="hidden" value="'+responseTimeDetails[i].dispatchedTime+'"/>';
		formData+='<input name="arrivalTime" type="hidden" value="'+responseTimeDetails[i].arrivalTime+'"/>';
		formData+='<input name="clearedTime" type="hidden" value="'+responseTimeDetails[i].clearedTime+'"/>';
		formData+='<input name="callToDispatch" type="hidden" value="'+responseTimeDetails[i].callToDispatch+'"/>';
		formData+='<input name="callToArrival" type="hidden" value="'+responseTimeDetails[i].callToArrival+'"/>';
		formData+='<input name="dispatchToClear" type="hidden" value="'+responseTimeDetails[i].dispatchToClear+'"/>';
		formData+='<input name="late" type="hidden" value="'+responseTimeDetails[i].late+'"/>';		
	}
	$('body').append('<form id="responseTimeDetailsTableForm"></form>');
	$('#responseTimeDetailsTableForm').append(formData);
	$('#responseTimeDetailsTableForm').attr("action","/actreports/exportResponseTimeDetails");
	$('#responseTimeDetailsTableForm').attr("method","POST");
	$('#responseTimeDetailsTableForm').submit();
    $('#responseTimeDetailsTableForm').remove();
	//return false;
}

function responseTimeDetailsTableToJSON()
{
	var responseTimeDetailsList = {responseTimeDetailsData:[]};
	
	$("#responseTimeDetailsTable tbody tr").each(function(){
		var responseTimeDetails={
				invoiceId:$(this).children('td').eq(0).text(),
				account:$(this).children('td').eq(1).text(),
				callerTakerId:$(this).children('td').eq(2).text(),
				dispatchId:$(this).children('td').eq(3).text(),
				driverId:$(this).children('td').eq(4).text(),
				callTime:$(this).children('td').eq(5).text(),
				dispatchedTime:$(this).children('td').eq(6).text(),
				arrivalTime:$(this).children('td').eq(7).text(),
				clearedTime:$(this).children('td').eq(8).text(),
				callToDispatch:$(this).children('td').eq(9).text(),
				callToArrival:$(this).children('td').eq(10).text(),
				dispatchToClear:$(this).children('td').eq(11).text(),
				late:$(this).children('td').eq(12).text()				
		};
		responseTimeDetailsList.responseTimeDetailsData.push(responseTimeDetails);
	});
	
	return responseTimeDetailsList;
}
