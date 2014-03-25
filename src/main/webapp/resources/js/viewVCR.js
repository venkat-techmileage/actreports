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
	
	$('#viewVCRTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[]});
	
	$("#searchAll").click(function(){		 
		 $('input:text[name="searchByInput"]').each(function(){
			 $(this).val("");
			 $(this).attr("disabled", true);
		 });		  
	 });
	
	$("#driverNameRadio").click(function(){		 
		 $('input:text[name="searchByInput"]').each(function(){
			   $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#driverName").attr("disabled", false);	
		 getAutocomleteDetails("driverName","#driverName" );
	 });
	
	$("#driverIdRadio").click(function(){		 
		 $('input:text[name="searchByInput"]').each(function(){
			   $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#driverId").attr("disabled", false);
		 getAutocomleteDetails("driverId","#driverId" );
	 });
	
	$("#truckIdRadio").click(function(){		 
		 $('input:text[name="searchByInput"]').each(function(){
			   $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#truckId").attr("disabled", false);		 
		 getAutocomleteDetails("truckId","#truckId" );
	 });
	
	$('#viewVCRGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			viewVCRDetails();			
		}else
			alert("Please enter valid input details to search.");
	});
	
	// Code for export to excel.
	$('#viewVCRExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportVCRDetails();			
		}else
			alert("Please enter valid input details to search.");		
	});
});

function getAutocomleteDetails(searchid,inputId ){
	var searchIdList=[];
	if(searchid=='driverId')
		searchIdList=getDriverIDs();
	else if(searchid=='driverName')
		searchIdList=getDriverNames();
	else if(searchid=='truckId')
		searchIdList=getTruckIDs();
	
	$(inputId).autocomplete({
		source: searchIdList
	});
}

function getDriverIDs(){
	var driverIDsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/viewVCR/getDriverIDsList",
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
	var driverNamesList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/viewVCR/getDriverNamesList",
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

function getTruckIDs(){
	var truckIDsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/viewVCR/getTruckIDs",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				truckIDsList.push(obj.truckID);
			});			
		},
		error:function(error){
			alert("error:"+error);
		}		 
	});	
	return truckIDsList;
}

function viewVCRDetails(){
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	var vcrData=[];
	$.ajax({
		async: false,
		method:"post",
		url : "viewVCRDetails",
		dataType:"json",
		contentType:"application/json",
		data:callListqryToJSON(),
		success:function(result)
		{
			vcrData = result;
			$('#fromDate').val(vcrData.fromDate);
			$('#toDate').val(vcrData.toDate);
			$("#frmDte").html(vcrData.fromDate);
			$("#toDte").html(vcrData.toDate);			
			if(vcrData.searchType!='')
				$("#withDefects").prop("checked", "checked");
			else
				$("#all").prop("checked", "checked");
			
			if(vcrData.driverName!=''){
				$("#driverNameRadio").prop("checked", "checked");
				$("#driverName").attr("disabled", false);		 
			}
			else if(vcrData.driverId!=''){
				$("#driverIdRadio").prop("checked", "checked");
				$("#driverId").attr("disabled", false);		 
			}
			else if(vcrData.truckId!=''){
				$("#truckIdRadio").prop("checked", "checked");
				$("#truckId").attr("disabled", false);		 
			}
			else{
				$("#searchAll").prop("checked", "checked");
				$('input:text[name="searchByInput"]').each(function(){
					$(this).val("");
					$(this).attr("disabled", true);
				});
			}
			$('#driverId').val(vcrData.driverId);
			$('#driverName').val(vcrData.driverName);
			$('#truckId').val(vcrData.truckId);
			$('#Output').html(createTable(vcrData));
			$("#totalRecords").html(vcrData.totalRecords);
			$('#vcrTable').columnFilters({alternateRowClassNames:['act-table-odd']});		
		},
		error:function(error)
		{
			alert("error::"+error);
		}		 
	});	
	$.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function callListqryToJSON()
{	
	var searchString ="";
	
	if($("#all").is(":checked")){
		if($("#searchAll").is(":checked"))
			searchString = "All-All";
		else if($("#driverNameRadio").is(":checked")){
			searchString = "All-DriverName";
						
		}
		else if($("#driverIdRadio").is(":checked")){
			searchString = "All-DriverID";
						
		}
		else if($("#truckIdRadio").is(":checked")){
			searchString = "All-TruckID";
					
		}
	}
	else if($("#withDefects").is(":checked")){		
		if($("#searchAll").is(":checked"))
			searchString="WithDefects-All";
		else if($("#driverNameRadio").is(":checked")){
			searchString="WithDefects-DriverName";
						
		}
		else if($("#driverIdRadio").is(":checked")){
			searchString="WithDefects-DriverID";
			
		}
		else if($("#truckIdRadio").is(":checked")){
			searchString="WithDefects-TruckID";
					
		}
	}
	return JSON.stringify({
		"searchString": searchString, 
		"driverName": $("#driverName").val(),
		"driverId": $("#driverId").val(),
		"truckId": $("#truckId").val(),
		"fromDate": $("#fromDate").val(),
		"toDate": $("#toDate").val()
	});
}

function createTable(obj) {
	//alert('In createTable()....');
	var table = $('<table id="vcrTable" class="act-table"></table>');
	var thead = $('<thead><tr><th>Truck #</th><th>Date</th><th>Pre or Post</th><th>Defects</th><th>Driver ID</th><th>Mileage</th></tr></thead>');
	table.append(thead);
	var vcrDetails=obj.viewVCRDetails;
	//alert('obj.length:'+vcrDetails.length);
	for (var j = 0; j < vcrDetails.length; j++) {		
		var vcrData = vcrDetails[j];
		var tr = '';
		if(vcrData.defects=='Y')
			tr = $('<tr title="'+vcrData.vehicleInspectionNotes+'\n\n'+vcrData.inspectionNotes+'"></tr>');
		else
			tr = $('<tr></tr>');
		tr.append('<td align="center">' + vcrData.truckId + '</td>');
		tr.append('<td align="center">' + vcrData.date+ '</td>');
		tr.append('<td align="center">' + vcrData.preOrPost + '</td>');
		tr.append('<td align="center">' + vcrData.defects + '</td>');
		tr.append('<td align="center">' + vcrData.driverId + '</td>');
		tr.append('<td align="center">' + vcrData.mileage + '</td>');
		table.append(tr);
	}	
	return table;
}

function exportVCRDetails()
{
	//alert('In exportVCRDetails()...');
	var data=viewVCRDetailsTableToJSON();
	var viewVCRDetails=data.viewVCRDetailsData;
	var formData= '';
	for(i in viewVCRDetails)
	{
		formData+='<input name="truckId" type="hidden" value="'+viewVCRDetails[i].truckId+'"/>';
		formData+='<input name="date" type="hidden" value="'+viewVCRDetails[i].date+'"/>';
		formData+='<input name="preOrPost" type="hidden" value="'+viewVCRDetails[i].preOrPost+'"/>';
		formData+='<input name="defects" type="hidden" value="'+viewVCRDetails[i].defects+'"/>';
		formData+='<input name="driverId" type="hidden" value="'+viewVCRDetails[i].driverId+'"/>';
		formData+='<input name="mileage" type="hidden" value="'+viewVCRDetails[i].mileage+'"/>';	
	}
	$('body').append('<form id="viewVCRDetailsTableForm"></form>');
	$('#viewVCRDetailsTableForm').append(formData);
	$('#viewVCRDetailsTableForm').attr("action","/actreports/exportVCRDetails");
	$('#viewVCRDetailsTableForm').attr("method","POST");
	$('#viewVCRDetailsTableForm').submit();
    $('#viewVCRDetailsTableForm').remove();
	//return false;
}

function viewVCRDetailsTableToJSON()
{
	var viewVCRDetailsList = {viewVCRDetailsData:[]};
	
	$("#viewVCRTable tbody tr").each(function(){
		var viewVCRDetails={
				truckId:$(this).children('td').eq(0).text(),
				date:$(this).children('td').eq(1).text(),
				preOrPost:$(this).children('td').eq(2).text(),
				defects:$(this).children('td').eq(3).text(),
				driverId:$(this).children('td').eq(4).text(),
				mileage:$(this).children('td').eq(5).text()
		};
		viewVCRDetailsList.viewVCRDetailsData.push(viewVCRDetails);
	});
	
	return viewVCRDetailsList;
}
