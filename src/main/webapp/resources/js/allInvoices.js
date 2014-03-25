var baseUrl='${pageContext.request.contextPath}';
var global_callList=[];
var callFilter=[];
var index;
var headers=[];

$(document).ready(function(){

	$("#allInvoices_r").addClass("selected");

	var pickerOpts = {
			  changeMonth :true,
			  showOtherMonths: true,
			  changeYear :true,
			  showOn: "both",
	  		  buttonImage: "/actreports/resources/images/calender-icon-blue.png",
	  		  buttonImageOnly: true,
	  		
        };
	
	$( "#startDate" ).datepicker(pickerOpts);
	$( "#endDate" ).datepicker(pickerOpts);

	$('#view-export-head').click(function(){
		$(".view-export").slideToggle(function() { 
			var property = $('.view-export').css('display');
			if(property == 'block')
				$('#view-export-head, #mainNav').addClass("active-toggle");
			else
				$('#view-export-head, #mainNav').removeClass("active-toggle");
		});
	});
	
	function resizeInput() {
	    $(this).attr('size', $(this).val().length);
	}

	$('.all-invoice-table-wrapper .act-table input[type="text"]').keyup(resizeInput).each(resizeInput);

	//$("#radioAll").prop("checked", "checked");
	$('input:text[name="searchInput"]').each(function(){
		$(this).attr("disabled", true);
	});			

	$("#byDataGroup").prop("checked", "checked");
	$("#alphabetical").hide();
	$('#alphabetical').find('input[type=checkbox]:checked').removeAttr('checked');
	$("#byDataGroup").click(function(){				
		$("#alphabetical").hide();
		$("#dataGroup").show();
		if($("#startDate").val()!="" && $("#endDate").val()!="" && ($("#serviceCallDate").is(":checked") || $("#releaseDate").is(":checked"))){
			//$('input:checkbox[name="checkAll"]').prop("checked", "checked");
			//$('input:checkbox[name="check"]').prop("checked", "checked");
			$('#alphabetical').find('input[type=checkbox]:checked').removeAttr('checked');
			setCallListTable(global_callList);
		}				
	});
	
	$("#byAlphabetical").click(function(){
		$("#dataGroup").hide();
		$("#alphabetical").show();
		if($("#startDate").val()!="" && $("#endDate").val()!="" && ($("#serviceCallDate").is(":checked") || $("#releaseDate").is(":checked"))){
			//$('input:checkbox[name="checkAll"]').prop("checked", "checked");
			//$('input:checkbox[name="check"]').prop("checked", "checked");
			$('#dataGroup').find('input[type=checkbox]:checked').removeAttr('checked');
			setCallListTable(global_callList);
		}				
	});

	$("#go").click(function(){				
		if($("#startDate").val()!="" && $("#endDate").val()!="" && ($("#serviceCallDate").is(":checked") || $("#releaseDate").is(":checked"))){
			//$('input:checkbox[name="checkAll"]').prop("checked", "checked");
			//$('input:checkbox[name="check"]').prop("checked", "checked");
			$(".view-export").css("display", "block");
			if($("#byDataGroup").is(":checked")){
				$('#alphabetical').find('input[type=checkbox]:checked').removeAttr('checked');
				if($("div#dataGroup input:checkbox[name='check']:checked").length == 0) {
					$('div#dataGroup input:checkbox[name="checkAll"]').prop("checked", "checked");
					$('div#dataGroup input:checkbox[name="check"]').prop("checked", "checked");
				}
			}
			if($("#byAlphabetical").is(":checked")){
				$('#dataGroup').find('input[type=checkbox]:checked').removeAttr('checked');
				if($("div#alphabetical input:checkbox[name='check']:checked").length == 0) {
					$('div#alphabetical input:checkbox[name="checkAll"]').prop("checked", "checked");
					$('div#alphabetical input:checkbox[name="check"]').prop("checked", "checked");
				}
			}
			populateCallList();
			/*//$("#radioAll").prop("checked", "checked");
				$('input:text[name="searchInput"]').each(function(){
					   $(this).attr("disabled", true);
				   });*/
		}
		else
			alert("Invalid Date");
	});

	$("#invoiceExport").click(function() {
		if($("#startDate").val()!="" && $("#endDate").val()!="" && ($("#serviceCallDate").is(":checked") || $("#releaseDate").is(":checked"))){
			exportInvoicesList(global_callList);
		}
		else
			alert("Invalid Date");
	});

	$("#clrInvSearch").click(function () {				
		$("#startDate").val("") ;
		$("#endDate").val("") ;
		$("#byDataGroup").prop("checked", "checked");
		$("#dataGroup").show();
		$("#alphabetical").hide();
		$('input:checkbox[name="checkAll"]').removeAttr("checked");
		$('input:checkbox[name="check"]').removeAttr("checked");
		$(".view-export").css("display", "none");
		//$("#radioAll").prop("checked", "checked");
		$("#driverSearch").val("") ;
		$("#truckSearch").val("") ;
		$("#reasonSearch").val("") ;
		$("#accountSearch").val("") ;
		$("#salesRepSearch").val("") ;
		$('input:text[name="searchInput"]').each(function(){
			$(this).attr("disabled", true);
		});							  
		$("#Output").html("");				  
	});

	$("#driver").click(function(){
		$('input:text[name="searchInput"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});
		$("#driverSearch").attr("disabled", false);
		getAutocomleteDetails("driverId","#driverSearch" );
		if(global_callList.allInvoices.length>0)
			setCallListTable(global_callList);		
	});

	$("#truck").click(function(){
		$('input:text[name="searchInput"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});
		$("#truckSearch").attr("disabled", false);
		getAutocomleteDetails("truck","#truckSearch" );
		if(global_callList.allInvoices.length>0)
			setCallListTable(global_callList);		
	});

	$("#towType").click(function(){
		$('input:text[name="searchInput"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});
		$("#towTypeSearch").attr("disabled", false);
		getAutocomleteDetails("towType","#towTypeSearch" );
		if(global_callList.allInvoices.length>0)
			setCallListTable(global_callList);		
	});

	$("#reason").click(function(){
		$('input:text[name="searchInput"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});
		$("#reasonSearch").attr("disabled", false);
		getAutocomleteDetails("reason","#reasonSearch" );
		if(global_callList.allInvoices.length>0)
			setCallListTable(global_callList);		
	});

	$("#account").click(function(){
		$('input:text[name="searchInput"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});
		$("#accountSearch").attr("disabled", false);
		getAutocomleteDetails("account","#accountSearch" );
		if(global_callList.allInvoices.length>0)
			setCallListTable(global_callList);		
	});

	$("#salesRep").click(function(){
		 $('input:text[name="searchInput"]').each(function(){
			 $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#salesRepSearch").attr("disabled", false);
		 getAutocomleteDetails("salesRep","#salesRepSearch" );
		 if(global_callList.allInvoices.length>0)
			setCallListTable(global_callList);		 
	 });

	/*$("#radioAll").click(function(){
		$('input:text[name="searchInput"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});
		setCallListTable(global_callList); 
	});*/


	$('input:checkbox[name="check"]').click(function(){
		if($("#startDate").val()!="" && $("#endDate").val()!="" && ($("#serviceCallDate").is(":checked") || $("#releaseDate").is(":checked"))){
			if($('div#dataGroup input:checkbox[name="check"]').length == $("div#dataGroup input:checkbox[name='check']:checked").length) {
				$('div#dataGroup input:checkbox[name="checkAll"]').prop("checked", "checked");
			}else {
				$('div#dataGroup input:checkbox[name="checkAll"]').removeAttr("checked");
			}
			if($('div#alphabetical input:checkbox[name="check"]').length == $("div#alphabetical input:checkbox[name='check']:checked").length) {
				$('div#alphabetical input:checkbox[name="checkAll"]').prop("checked", "checked");
			}else {
				$('div#alphabetical input:checkbox[name="checkAll"]').removeAttr("checked");
			}

			setCallListTable(global_callList); //displaying selected fields.
		}
		else
			alert("Invalid Date");
	});

	/* Call List Check All filter table */
	$('input:checkbox[name="checkAll"]').click(function(){
		if($("#startDate").val()!="" && $("#endDate").val()!="" && ($("#serviceCallDate").is(":checked") || $("#releaseDate").is(":checked"))){
			if($('input:checkbox[name="checkAll"]').is(':checked')){
				$('input:checkbox[name="check"]').each(function(){
					$(this).prop("checked", "checked");
				});
				if($("#byDataGroup").is(":checked"))
					$('#alphabetical').find('input[type=checkbox]:checked').removeAttr('checked');
				if($("#byAlphabetical").is(":checked"))
					$('#dataGroup').find('input[type=checkbox]:checked').removeAttr('checked');
				setCallListTable(global_callList);//for displaying all data	    	  
			}
			else 
			{
				$('input:checkbox[name="check"]').each(function(){
					$(this).attr("checked", false);
				});
				$("#Output").html("");
			}
		}
		else
			alert("Invalid Date");
	});	
	
});

/*code for auto completing the text boxes in left pane*/
function getAutocomleteDetails(searchid,inputId ){
	var searchIdList=[];
	if(searchid=='driverId')
		searchIdList=getDriverIDs();
	else if(searchid=='truck')
		searchIdList=getTruckIDs();
	else if(searchid=='towType')
		searchIdList=getTowTypes();
	else if(searchid=='reason')
		searchIdList=getReasons();
	else if(searchid=='account')
		searchIdList=getAccountNames();
	else if(searchid=='salesRep')
		searchIdList=getSalesReps();

	$(inputId).autocomplete({
		source: searchIdList
	});
	
	/*$(inputId).autocomplete({
		source: searchIdList,
		select: function( event, obj ){
			var allInvoices=[];
			for (var j = 0; j < global_callList.allInvoices.length; j++) {
				var invoices = global_callList.allInvoices[j];
				if(invoices[searchid]==obj.item.value){					
					allInvoices.push(invoices);
					searchData= {"allInvoices":allInvoices};
					setCallListTable(searchData);
				}
			}

		}
	});*/
}

function getDriverIDs(){
	var driverIDsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/get/driverIDsList",
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

function getTruckIDs(){
	var truckIDsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/get/truckIDsList",
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

function getTowTypes(){
	var towTypesList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/get/towTypesList",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				towTypesList.push(obj.towType);
			});			
		},
		error:function(error){
			alert("error:"+error);
		}		 
	});	
	return towTypesList;
}

function getReasons(){
	var reasonsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/get/reasonsList",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				reasonsList.push(obj.reason);
			});			
		},
		error:function(error){
			alert("error:"+error);
		}		 
	});	
	return reasonsList;
}

function getAccountNames(){
	var accountNamesList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/get/accountNamesList",
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

function getSalesReps(){
	var salesRepsList=[];
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/get/salesRepsList",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				salesRepsList.push(obj.salesRep);
			});			
		},
		error:function(error){
			alert("error:"+error);
		}		 
	});	
	return salesRepsList;
}

function createTable(obj, cols) {
	var table = $('<table id="invoiceSearch" class="act-table"></table>');
	var th = $('<thead><tr></tr></thead>');
	for (var i = 0; i < cols.length; i++) {
		th.append('<th>' + headers[i] + '</th>');
	}
	table.append(th);
	
	/*//code for dynamic search text boxes in dynamic table.
	var sth=$('<tr class="act-table-odd"></tr>');
	for (var i = 0; i < cols.length; i++) {
		sth.append('<td></td>');
	}
	table.append(sth);*/
	
	var tbody = $('<tbody style="cursor: pointer;"></tbody>');
	
	for (var j = 0; j < obj.allInvoices.length; j++) {
		var invoices = obj.allInvoices[j];
		var tr = $('<tr info="'+invoices['invoice']+','+invoices['serviceCallId']+'"></tr>');
		for (var k = 0; k < cols.length; k++) {
			var columnName = cols[k];
			if(columnName=='towCharge' || columnName=='storageCharge' || columnName=='laborCharge' || columnName=='mileageCharge' || columnName=='winchCharge' || columnName=='gateCharge' || columnName=='adminCharge' || columnName=='miscChargeDesc' || columnName=='miscCharge' || columnName=='discounts' || columnName=='totalCharges' || columnName=='amountPaid')
				tr.append('<td align="right">' + invoices[columnName] + '</td>');
			else
				tr.append('<td align="center">' + invoices[columnName] + '</td>');
		}
		tbody.append(tr);
	}
	
	table.append(tbody);
	
	return table;
}

function GetKeys(obj) {
	var cols = new Array();
	var p = obj.allInvoices[0];
	for (var key in p) {
		//alert(' name=' + key + ' value=' + p[key]);
		cols.push(key);
	}
	return cols;
}

function setCallListTable(global_callList){
	callFilter=[];
	headers=[];
	$('input:checkbox[name="check"]:checked').each(function(){
		callFilter.push($(this).val());
		headers.push($(this).attr("data"));
	});

	if(callFilter.length>0){
		$('#Output').html(createTable(global_callList, callFilter));
		$('#invoiceSearch').columnFilters({alternateRowClassNames:['act-table-odd']});
		$('#invoiceSearch tbody tr').click(function(){
			var ids=$(this).attr("info").split(",");
			$("#serviceCallId").val(ids[1]);
			$("#invoiceId").val(ids[0]);
			$("#editServiceCall").attr('action','/actdispatcher/dashboard/edit/serviceCall?session='+$('#sessionId').val()+'&userIdKey='+$('#usrIdKey').val());
			$("#editServiceCall").attr('method','POST');
			$("#editServiceCall").submit();
		});
	}else {		   
		$('#Output').html("");
	}
}

function populateCallList(){
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });

	$.ajax({
		async: false,
		method:"post",
		url : "allInvoices/details",
		dataType:"json",
		contentType:"application/json",
		data:callListqryToJSON(),
		success:function(result)
		{
			global_callList=result;
			setCallListTable(global_callList);
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
	var searchQuery ="";
	/*if($("#serviceCallDate").is(":checked"))
		searchQuery = "byServiceCallDate";
	else
		searchQuery="byReleaseDate";*/

	if($("#serviceCallDate").is(":checked") && $("#driver").is(":checked"))
		searchQuery = "byServiceCallDate-driver";
	else if($("#serviceCallDate").is(":checked") && $("#truck").is(":checked"))
		searchQuery = "byServiceCallDate-truck";
	else if($("#serviceCallDate").is(":checked") && $("#towType").is(":checked"))
		searchQuery = "byServiceCallDate-towType";
	else if($("#serviceCallDate").is(":checked") && $("#reason").is(":checked"))
		searchQuery = "byServiceCallDate-reason";
	else if($("#serviceCallDate").is(":checked") && $("#account").is(":checked"))
		searchQuery = "byServiceCallDate-account";
	else if($("#serviceCallDate").is(":checked") && $("#salesRep").is(":checked"))
		searchQuery = "byServiceCallDate-salesRep";	
	else if($("#releaseDate").is(":checked") && $("#driver").is(":checked"))
		searchQuery="byReleaseDate-driver";
	else if($("#releaseDate").is(":checked") && $("#truck").is(":checked"))
		searchQuery="byReleaseDate-truck";						
	else if($("#releaseDate").is(":checked") && $("#towType").is(":checked"))
		searchQuery="byReleaseDate-towType";			
	else if($("#releaseDate").is(":checked") && $("#reason").is(":checked"))
		searchQuery="byReleaseDate-reason";
	else if($("#releaseDate").is(":checked") && $("#account").is(":checked"))
		searchQuery="byReleaseDate-account";			
	else if($("#releaseDate").is(":checked") && $("#salesRep").is(":checked"))
		searchQuery="byReleaseDate-salesRep";
	else if($("#serviceCallDate").is(":checked"))
		searchQuery = "byServiceCallDate";
	else  if($("#releaseDate").is(":checked"))
		searchQuery="byReleaseDate";
		
	return JSON.stringify({
		"searchQuery": searchQuery, 
		"fromDate": $("#startDate").val(),
		"toDate": $("#endDate").val(),
		"driver": $("#driverSearch").val(),
		"truck": $("#truckSearch").val(),
		"towType": $("#towTypeSearch").val(),
		"reason": $("#reasonSearch").val(),
		"account": $("#accountSearch").val(),
		"salesRep": $("#salesRepSearch").val()
	});
}

function exportInvoicesList(obj)
{
	var formData = '';
	var recCount = 0;
	cols=[];
	$('input:checkbox[name="check"]:checked').each(function(){
		cols.push($(this).val());
	});		
	if(cols.length>0){
		recCount = '<input name="recCount" type="hidden" value="'+obj.allInvoices.length+'"/>';
		for (var j = 0; j < obj.allInvoices.length; j++) {
			var invoices = obj.allInvoices[j];
			for (var k = 0; k < cols.length; k++) {
				var columnName = cols[k];
				formData+='<input name="'+columnName+'" type="hidden" value="'+invoices[columnName]+'"/>';
			}			   
		}
		$('body').append('<form id="notesTableForm"></form>');
		$('#notesTableForm').append(formData);
		$('#notesTableForm').append(recCount);
		$('#notesTableForm').attr("action","allInvoices/exportInvoicesList");
		$('#notesTableForm').attr("method","POST");
		$('#notesTableForm').submit();
		$('#notesTableForm').remove();		   
	}
	return false;
}