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

	$('#customTowsTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6,7,8,9,10,11,12,13]});

	$("#accountName").click(function(){
		getAutocomleteDetails("accountName","#accountName" );
	});

	$('#customTowsGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#accountName").val()!=""){
			getCustomAllTows();			
		}else
			$("#inputError").show();
		$("#inputError").text("Please enter an Account Name ");
		$("#accountName").focus();
	});

	// Code for export to excel.
	$('#customTowsExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#accountName").val()!=""){
			exportCustomAllTows();			
		}else
			$("#inputError").show();
		$("#inputError").text("Please enter an Account Name");
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
		url : "/actreports/customAllTows/get/accountNames",
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

function getCustomAllTows()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });

	//alert('In getCustomAllTows()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	formData+='<input name="accountName" type="hidden" value="'+$("#accountName").val()+'"/>';
	$('body').append('<form id="customTowsForm"></form>');
	$('#customTowsForm').append(formData);
	$('#customTowsForm').attr("action","/actreports/customDetails");
	$('#customTowsForm').attr("method","POST");
	$('#customTowsForm').submit();
	$('#customTowsForm').remove();
	$.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportCustomAllTows()
{
	//alert('In exportCustomAllTows()...');
	var data=customTowsTableToJSON();
	var customAllTows=data.customTowsData;
	var formData= '';
	for(i in customAllTows)
	{
		formData+='<input name="id" type="hidden" value="'+customAllTows[i].id+'"/>';
		formData+='<input name="contract" type="hidden" value="'+customAllTows[i].contract+'"/>';
		formData+='<input name="storageReport" type="hidden" value="'+customAllTows[i].storageReport+'"/>';
		formData+='<input name="pdRequestDate" type="hidden" value="'+customAllTows[i].pdRequestDate+'"/>';
		formData+='<input name="pdRequestTime" type="hidden" value="'+customAllTows[i].pdRequestTime+'"/>';
		formData+='<input name="towArrivalDate" type="hidden" value="'+customAllTows[i].towArrivalDate+'"/>';
		formData+='<input name="towArrivalTime" type="hidden" value="'+customAllTows[i].towArrivalTime+'"/>';
		formData+='<input name="year" type="hidden" value="'+customAllTows[i].year+'"/>';
		formData+='<input name="make" type="hidden" value="'+customAllTows[i].make+'"/>';
		formData+='<input name="model" type="hidden" value="'+customAllTows[i].model+'"/>';
		formData+='<input name="vin" type="hidden" value="'+customAllTows[i].vin+'"/>';
		formData+='<input name="precinct" type="hidden" value="'+customAllTows[i].precinct+'"/>';
		formData+='<input name="towedTo" type="hidden" value="'+customAllTows[i].towedTo+'"/>';
		formData+='<input name="towedType" type="hidden" value="'+customAllTows[i].towedType+'"/>';
	}
	$('body').append('<form id="customTowsTableForm"></form>');
	$('#customTowsTableForm').append(formData);
	$('#customTowsTableForm').attr("action","/actreports/exportCustomAllTowsDetalis");
	$('#customTowsTableForm').attr("method","POST");
	$('#customTowsTableForm').submit();
	$('#customTowsTableForm').remove();
	//return false;
}

function customTowsTableToJSON()
{
	var customAllTowsDetailsList = {customTowsData:[]};

	$("#customTowsTable tbody tr").each(function(){
		var customTowsDetails={
				id:$(this).children('td').eq(0).text(),
				contract:$(this).children('td').eq(1).text(),
				storageReport:$(this).children('td').eq(2).text(),
				pdRequestDate:$(this).children('td').eq(3).text(),
				pdRequestTime:$(this).children('td').eq(4).text(),
				towArrivalDate:$(this).children('td').eq(5).text(),
				towArrivalDate:$(this).children('td').eq(6).text(),
				year:$(this).children('td').eq(7).text(),
				make:$(this).children('td').eq(8).text(),
				model:$(this).children('td').eq(9).text(),
				vin:$(this).children('td').eq(10).text(),
				precinct:$(this).children('td').eq(11).text(),
				towedTo:$(this).children('td').eq(12).text(),
				towedType:$(this).children('td').eq(13).text()
		};
		customAllTowsDetailsList.customTowsData.push(customTowsDetails);
	});

	return customAllTowsDetailsList;
}
