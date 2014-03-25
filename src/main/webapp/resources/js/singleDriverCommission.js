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
	
	$('#singleDriverCommissionTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5]});
	$('#towTypeSummaryTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4]});		
			
	$("#userName").click(function(){
		$("#userNameRadio").prop("checked", "checked");
		$("#userId").val("");
		getAutocomleteDetails("userName","#userName" );
	});

	$("#userId").click(function(){
		$("#userIdRadio").prop("checked", "checked");
		$("#userName").val("");
		getAutocomleteDetails("userId","#userId" );
	});
	
	if(usrId!=null && usrId!='')
		$("#userIdRadio").prop("checked", "checked");
	else if(usrName!=null && usrName!='')
		$("#userNameRadio").prop("checked", "checked");
	
	$('#singleDriverCommissionGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && ($("#userId").val()!="" || $("#userName").val()!="")){
			getSingleDriverCommissionDetails();			
		}else{
			$("#inputError").show();
			$("#inputError").text("Please enter an User ID/User Name & from & to date's to search.");
			$("#userId").focus();
		}
	});
	
	// Code for export to excel.
	$('#singleDriverCommissionExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && ($("#userId").val()!="" || $("#userName").val()!="")){
			exportSingleDriverCommissionDetails();			
		}else{
			$("#inputError").show();
			$("#inputError").text("Please enter an User ID/User Name & from & to date's to search.");
			$("#userId").focus();
		}		
	});
});

function getAutocomleteDetails(searchid,inputId ){
	var searchIdList=[];
	if(searchid=='userId')
		searchIdList=getUserIds();
	else if(searchid=='userName')
		searchIdList=getUserNames();

	$(inputId).autocomplete({
		source: searchIdList	
	});
}
function getUserIds(){
	var userIdsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/singleDriverCommission/getUserIds",
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

function getUserNames(){
	var userNamesList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/singleDriverCommission/getUserNames",
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

function getSingleDriverCommissionDetails()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getSingleDriverCommissionDetails()...');
	var searchString = '';
	if($("#userIdRadio").is(":checked"))
		searchString = "byUserId";
	else
		searchString="byUserName";
	
	var formData= '';
	formData+='<input name="searchString" type="hidden" value="'+searchString+'"/>';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	formData+='<input name="userId" type="hidden" value="'+$("#userId").val()+'"/>';
	formData+='<input name="userName" type="hidden" value="'+$("#userName").val()+'"/>';
	$('body').append('<form id="singleDriverCommissionForm"></form>');
	$('#singleDriverCommissionForm').append(formData);
	$('#singleDriverCommissionForm').attr("action","/actreports/showSingleDriverCommissionDetails");
	$('#singleDriverCommissionForm').attr("method","POST");
	$('#singleDriverCommissionForm').submit();
    $('#singleDriverCommissionForm').remove();
    
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportSingleDriverCommissionDetails()
{
	//alert('In exportSingleDriverCommissionDetails()...');
	var data=singleDriverCommissionTableToJSON();
	var singleDriverCommissionDetails=data.singleDriverCommissionDetailsData;
	var formData= '';
	for(i in singleDriverCommissionDetails)
	{
		formData+='<input name="date" type="hidden" value="'+singleDriverCommissionDetails[i].date+'"/>';
		formData+='<input name="invoiceId" type="hidden" value="'+singleDriverCommissionDetails[i].invoiceId+'"/>';
		formData+='<input name="towType" type="hidden" value="'+singleDriverCommissionDetails[i].towType+'"/>';
		formData+='<input name="totalCharge" type="hidden" value="'+singleDriverCommissionDetails[i].totalCharge+'"/>';
		formData+='<input name="totalStorageCharge" type="hidden" value="'+singleDriverCommissionDetails[i].totalStorageCharge+'"/>';
		formData+='<input name="commissionAmount" type="hidden" value="'+singleDriverCommissionDetails[i].commissionAmount+'"/>';		
	}
	$('body').append('<form id="singleDriverCommissionTableForm"></form>');
	$('#singleDriverCommissionTableForm').append(formData);
	$('#singleDriverCommissionTableForm').attr("action","/actreports/exportSingleDriverCommissionDetails");
	$('#singleDriverCommissionTableForm').attr("method","POST");
	$('#singleDriverCommissionTableForm').submit();
    $('#singleDriverCommissionTableForm').remove();
	//return false;
}

function singleDriverCommissionTableToJSON()
{
	var singleDriverCommissionDetailsList = {singleDriverCommissionDetailsData:[]};
	
	$("#singleDriverCommissionTable tbody tr").each(function(){
		var singleDriverCommissionDetails={
				date:$(this).children('td').eq(0).text(),
				invoiceId:$(this).children('td').eq(1).text(),
				towType:$(this).children('td').eq(2).text(),
				totalCharge:$(this).children('td').eq(3).text(),
				totalStorageCharge:$(this).children('td').eq(4).text(),
				commissionAmount:$(this).children('td').eq(5).text()
		};
		singleDriverCommissionDetailsList.singleDriverCommissionDetailsData.push(singleDriverCommissionDetails);
	});
	
	return singleDriverCommissionDetailsList;
}