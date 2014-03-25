var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){

	$("#storageManagement_r").addClass("selected");

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

	$('#abandonedVehicleTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6]});
	
	$("#accountName").click(function(){
		getAutocomleteDetails("accountName","#accountName" );
	});

	$('#abandonedVehicleGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#accountName").val()!=""){
			getAbandonedVehicle();			
		}else
			$("#inputError").show();
		$("#inputError").text("Please enter an Account Name & released date to search.");
		$("#accountName").focus();
	});

	// Code for export to excel.
	$('#abandonedVehicleExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#accountName").val()!=""){
			exportAbandonedVehicle();			
		}else
			$("#inputError").show();
		$("#inputError").text("Please enter an Account Name & released date to search.");
		$("#accountName").focus();		
	});
});

// Erase error message at invoice qry search
$("#accountName").keyup(function(){
	if($("#accountName").val().length>0) 
		$("#inputError").hide();
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
		url : "/actreports/abandoned/get/accountNames",
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
function getAbandonedVehicle()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getAbandonedVehicleDetail()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	formData+='<input name="accountName" type="hidden" value="'+$("#accountName").val()+'"/>';
	$('body').append('<form id="abandonedVehicleDetailForm"></form>');
	$('#abandonedVehicleDetailForm').append(formData);
	$('#abandonedVehicleDetailForm').attr("action","/actreports/abandonedVehicleDetails");
	$('#abandonedVehicleDetailForm').attr("method","POST");
	$('#abandonedVehicleDetailForm').submit();
	$('#abandonedVehicleDetailForm').remove();
	 $.unblockUI({
 	     fadeIn : 0,
 	     fadeOut : 0,
 	     showOverlay : true
 	 });
}

function exportAbandonedVehicle()
{
	//alert('In exportAbandonedVehicleDetail()...');
	var data=abandonedVehicleTableToJSON();
	var abandonedVehicle=data.abandonedVehicleData;
	var formData= '';
	for(i in abandonedVehicle)
	{
		formData+='<input name="serviceCallDate" type="hidden" value="'+abandonedVehicle[i].serviceCallDate+'"/>';
		formData+='<input name="orDr" type="hidden" value="'+abandonedVehicle[i].orDr+'"/>';
		formData+='<input name="vin" type="hidden" value="'+abandonedVehicle[i].vin+'"/>';
		formData+='<input name="licensePlate" type="hidden" value="'+abandonedVehicle[i].licensePlate+'"/>';
		formData+='<input name="titleRequested" type="hidden" value="'+abandonedVehicle[i].titleRequested+'"/>';
		formData+='<input name="titleReceived" type="hidden" value="'+abandonedVehicle[i].titleReceived+'"/>';
		formData+='<input name="released" type="hidden" value="'+abandonedVehicle[i].released+'"/>';

	}
	$('body').append('<form id="abandonedVehicleDetailTableForm"></form>');
	$('#abandonedVehicleDetailTableForm').append(formData);
	$('#abandonedVehicleDetailTableForm').attr("action","/actreports/exportAbandonedVehicleDetail");
	$('#abandonedVehicleDetailTableForm').attr("method","POST");
	$('#abandonedVehicleDetailTableForm').submit();
	$('#abandonedVehicleDetailTableForm').remove();
	//return false;
}

function abandonedVehicleTableToJSON()
{
	var abandonedVehicleDetailsList = {abandonedVehicleData:[]};

	$("#abandonedVehicleTable tbody tr").each(function(){
		var abandonedVehicleDetails={
				serviceCallDate:$(this).children('td').eq(0).text(),
				orDr:$(this).children('td').eq(1).text(),
				vin:$(this).children('td').eq(2).text(),
				licensePlate:$(this).children('td').eq(3).text(),
				titleRequested:$(this).children('td').eq(4).text(),
				titleReceived:$(this).children('td').eq(5).text(),
				released:$(this).children('td').eq(6).text()
		};
		abandonedVehicleDetailsList.abandonedVehicleData.push(abandonedVehicleDetails);
	});

	return abandonedVehicleDetailsList;
}
