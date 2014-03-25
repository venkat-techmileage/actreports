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

	$('#driverSalesTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1]});
	$('#driverCommissionTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3]});		

	$("#driverName").click(function(){
		$("#driverNameRadio").prop("checked", "checked");	
		$("#driverId").val("");
		getAutocomleteDetails("driverName","#driverName" );
		
	});

	$("#driverId").click(function(){
		$("#driverIdRadio").prop("checked", "checked");		
		$("#driverName").val("");
		getAutocomleteDetails("driverId","#driverId" );
	});

	if(driverID!=null && driverID!='')
		$("#driverIdRadio").prop("checked", "checked");
	else if(driverNme!=null && driverNme!='')
		$("#driverNameRadio").prop("checked", "checked");
	
	$('#driverSalesVsCommGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""  && ($("#driverIdRadio").is(":checked") || $("#driverNameRadio").is(":checked"))){
			getDriverSalesVsCommissionDetails();			
		}else
			alert("Please enter valid input details to search.");
	});

	// Code for export to excel.
	$('#driverSalesVsCommExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""  && ($("#driverIdRadio").is(":checked") || $("#driverNameRadio").is(":checked"))){
			exportDriverSalesVsCommissionDetails();			
		}else
			alert("Please enter valid input details to search.");		
	});
});

function getAutocomleteDetails(searchid,inputId ){
	//alert('In getAutocomleteDetails()...');
	var searchIdList=[];
	if(searchid=='driverId')
		searchIdList=getDriverIDs();
	else if(searchid=='driverName')
		searchIdList=getDriverNames();

	$(inputId).autocomplete({
		source: searchIdList
	});
}
function getDriverIDs(){
	//alert('In getDriverIDs()...');
	var driverIDsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/driverSales/getDriverIDsList",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				driverIDsList.push(obj.driverID);
			});			
		},
		error:function(error){
			alert("error:"+error);
		}		 
	});	
	return driverIDsList;
}
function getDriverNames(){
	//alert('In getDriverNames()...');
	var driverNamesList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/driverSales/getDriverNamesList",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				driverNamesList.push(obj.driverName);
			});   
		},
		error:function(error){
			alert("error:"+error);
		}   
	}); 
	return driverNamesList;
}
function getDriverSalesVsCommissionDetails()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getDriverSalesVsCommissionDetails()...');
	var formData= '';
	var searchString = '';
	if($("#driverIdRadio").is(":checked")){
		searchString = "byDriverId";
		formData+='<input name="driverId" type="hidden" value="'+$("#driverId").val()+'"/>';
	}
	else{
		searchString="byDriverName";
		formData+='<input name="driverName" type="hidden" value="'+$("#driverName").val()+'"/>';
	}	
	
	formData+='<input name="searchString" type="hidden" value="'+searchString+'"/>';	
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	$('body').append('<form id="driverSalesVsCommissionForm"></form>');
	$('#driverSalesVsCommissionForm').append(formData);
	$('#driverSalesVsCommissionForm').attr("action","/actreports/getDriverSalesVsCommission");
	$('#driverSalesVsCommissionForm').attr("method","POST");
	$('#driverSalesVsCommissionForm').submit();
	$('#driverSalesVsCommissionForm').remove();
	 $.unblockUI({
 	     fadeIn : 0,
 	     fadeOut : 0,
 	     showOverlay : true
 	 });
}

function exportDriverSalesVsCommissionDetails()
{
	//alert('In exportDriverSalesVsCommissionDetails()...');
	var salesData=driverSalesTableToJSON();
	var commissionData=driverCommissionTableToJSON();
	var formData= '';

	var driverSalesDetails=salesData.driverSalesDetailsData;
	for(i in driverSalesDetails)
	{
		formData+='<input name="saleType" type="hidden" value="'+driverSalesDetails[i].saleType+'"/>';
		formData+='<input name="salesAmount" type="hidden" value="'+driverSalesDetails[i].salesAmount+'"/>';		
	}

	var driverCommissionDetails=commissionData.driverCommissionDetailsData;
	for(j in driverCommissionDetails)
	{
		formData+='<input name="commissionType" type="hidden" value="'+driverCommissionDetails[j].commissionType+'"/>';
		formData+='<input name="commissionAmount" type="hidden" value="'+driverCommissionDetails[j].commissionAmount+'"/>';		
		formData+='<input name="hoursOrInvoices" type="hidden" value="'+driverCommissionDetails[j].hoursOrInvoices+'"/>';
		formData+='<input name="ratePerHourOrInvoice" type="hidden" value="'+driverCommissionDetails[j].ratePerHourOrInvoice+'"/>';
	}

	$('body').append('<form id="driverSalesVsCommissionDetailsTableForm"></form>');
	$('#driverSalesVsCommissionDetailsTableForm').append(formData);
	$('#driverSalesVsCommissionDetailsTableForm').attr("action","/actreports/exportDriverSalesVsCommission");
	$('#driverSalesVsCommissionDetailsTableForm').attr("method","POST");
	$('#driverSalesVsCommissionDetailsTableForm').submit();
	$('#driverSalesVsCommissionDetailsTableForm').remove();
	//return false;
}

function driverSalesTableToJSON()
{
	var driverSalesDetailsList = {driverSalesDetailsData:[]};

	$("#driverSalesTable tbody tr").each(function(){
		var driverSalesDetails={
				saleType:$(this).children('td').eq(0).text(),
				salesAmount:$(this).children('td').eq(1).text()				
		};
		driverSalesDetailsList.driverSalesDetailsData.push(driverSalesDetails);
	});

	return driverSalesDetailsList;
}

function driverCommissionTableToJSON()
{
	var driverCommissionDetailsList = {driverCommissionDetailsData:[]};

	$("#driverCommissionTable tbody tr").each(function(){
		var driverCommissionDetails={
				commissionType:$(this).children('td').eq(0).text(),
				commissionAmount:$(this).children('td').eq(1).text(),
				hoursOrInvoices:$(this).children('td').eq(2).text(),
				ratePerHourOrInvoice:$(this).children('td').eq(3).text()				
		};
		driverCommissionDetailsList.driverCommissionDetailsData.push(driverCommissionDetails);
	});

	return driverCommissionDetailsList;
}