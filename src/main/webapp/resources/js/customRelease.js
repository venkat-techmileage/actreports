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

	$('#customReleaseTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23]});

	$("#accountName").click(function(){
		getAutocomleteDetails("accountName","#accountName" );
	});

	$('#customReleaseGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#accountName").val()!=""){
			getCustomRelease();			
		}else
			$("#inputError").show();
		$("#inputError").text("Please enter an Account Name ");
		$("#accountName").focus();
	});

	// Code for export to excel.
	$('#customReleaseExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#accountName").val()!=""){
			exportCustomRelease();			
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
		url : "/actreports/customPDRelease/get/accountNames",
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

function getCustomRelease()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getCustomRelease()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	formData+='<input name="accountName" type="hidden" value="'+$("#accountName").val()+'"/>';
	$('body').append('<form id="customReleaseForm"></form>');
	$('#customReleaseForm').append(formData);
	$('#customReleaseForm').attr("action","/actreports/customReleaseDetails");
	$('#customReleaseForm').attr("method","POST");
	$('#customReleaseForm').submit();
	$('#customReleaseForm').remove();
	$.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportCustomRelease()
{
	//alert('In exportCustomRelease()...');
	var data=customReleaseTableToJSON();
	var customRelease=data.customReleaseData;
	var formData= '';
	for(i in customRelease)
	{
		formData+='<input name="id" type="hidden" value="'+customRelease[i].id+'"/>';
		formData+='<input name="contract" type="hidden" value="'+customRelease[i].contract+'"/>';
		formData+='<input name="storageReport" type="hidden" value="'+customRelease[i].storageReport+'"/>';
		formData+='<input name="year" type="hidden" value="'+customRelease[i].year+'"/>';
		formData+='<input name="make" type="hidden" value="'+customRelease[i].make+'"/>';
		formData+='<input name="model" type="hidden" value="'+customRelease[i].model+'"/>';
		formData+='<input name="vin" type="hidden" value="'+customRelease[i].vin+'"/>';
		formData+='<input name="precinct" type="hidden" value="'+customRelease[i].precinct+'"/>';
		formData+='<input name="impoundDate" type="hidden" value="'+customRelease[i].impoundDate+'"/>';
		formData+='<input name="releasedDate" type="hidden" value="'+customRelease[i].releasedDate+'"/>';
		formData+='<input name="releasedTo" type="hidden" value="'+customRelease[i].releasedTo+'"/>';
		formData+='<input name="towCharge" type="hidden" value="'+customRelease[i].towCharge+'"/>';
		formData+='<input name="ofStorageDays" type="hidden" value="'+customRelease[i].ofStorageDays+'"/>';
		formData+='<input name="storageCharge" type="hidden" value="'+customRelease[i].storageCharge+'"/>';
		formData+='<input name="afterHoursGateFee" type="hidden" value="'+customRelease[i].afterHoursGateFee+'"/>';
		formData+='<input name="standByFee" type="hidden" value="'+customRelease[i].standByFee+'"/>';
		formData+='<input name="supervisorFee" type="hidden" value="'+customRelease[i].supervisorFee+'"/>';
		formData+='<input name="refrigFee" type="hidden" value="'+customRelease[i].refrigFee+'"/>';
		formData+='<input name="outSideCity" type="hidden" value="'+customRelease[i].outSideCity+'"/>';
		formData+='<input name="dryRunFee" type="hidden" value="'+customRelease[i].druRunFee+'"/>';
		formData+='<input name="pccCharge" type="hidden" value="'+customRelease[i].pccCharge+'"/>';
		formData+='<input name="level" type="hidden" value="'+customRelease[i].level+'"/>';

	}
	$('body').append('<form id="customReleaseTableForm"></form>');
	$('#customReleaseTableForm').append(formData);
	$('#customReleaseTableForm').attr("action","/actreports/exportCustomReleaseDetalis");
	$('#customReleaseTableForm').attr("method","POST");
	$('#customReleaseTableForm').submit();
	$('#customReleaseTableForm').remove();
	//return false;
}

function customReleaseTableToJSON()
{
	var customAllReleaseDetailsList = {customReleaseData:[]};

	$("#customReleaseTable tbody tr").each(function(){
		var customReleaseDetails={
				id:$(this).children('td').eq(0).text(),
				contract:$(this).children('td').eq(1).text(),
				storageReport:$(this).children('td').eq(2).text(),
				year:$(this).children('td').eq(3).text(),
				make:$(this).children('td').eq(4).text(),
				model:$(this).children('td').eq(5).text(),
				vin:$(this).children('td').eq(6).text(),
				precinct:$(this).children('td').eq(7).text(),
				impoundDate:$(this).children('td').eq(8).text(),
				releasedDate:$(this).children('td').eq(9).text(),
				releasedTo:$(this).children('td').eq(10).text(),
				towCharge:$(this).children('td').eq(11).text(),
				ofStorageCharge:$(this).children('td').eq(12).text(),
				storageCharge:$(this).children('td').eq(13).text(),
				afterHoursGateFee:$(this).children('td').eq(14).text(),
				standByFee:$(this).children('td').eq(15).text(),
				supervisorFee:$(this).children('td').eq(16).text(),
				refrigFee:$(this).children('td').eq(17).text(),
				outSideCity:$(this).children('td').eq(18).text(),
				dryRunFee:$(this).children('td').eq(19).text(),
				pccCharge:$(this).children('td').eq(20).text(),
				level:$(this).children('td').eq(21).text()

		};
		customAllReleaseDetailsList.customReleaseData.push(customReleaseDetails);
	});

	return customAllReleaseDetailsList;
}
