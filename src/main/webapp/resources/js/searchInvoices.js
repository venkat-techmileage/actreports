
var baseUrl='${pageContext.request.contextPath}';
var global_callList=[];

var callFilter=[];
var index;

var headers=[];


$(document).ready(function(){
	
	var pickerOpts = {
			changeMonth :true,
			showOtherMonths: true,
			changeYear :true
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

	
			$("#radioAll").prop("checked", "checked");
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
				  $("#radioAll").prop("checked", "checked");
					 $('input:text[name="searchInput"]').each(function(){
						 $(this).attr("disabled", true);
					 });
					 $("#driverSearch").val("") ;
					 $("#truckSearch").val("") ;
					 $("#reasonSearch").val("") ;
					 $("#accountSearch").val("") ;
					 $("#salesRepSearch").val("") ;					  
				  $("#Output").html("");				  
		    });
	
	 $("#driver").click(function(){
		 
		 $('input:text[name="searchInput"]').each(function(){
			   $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#driverSearch").attr("disabled", false);
		 getAutocomleteDetails("driverId","#driverSearch" );
	 });
	 
	 $("#truck").click(function(){
			 
			 $('input:text[name="searchInput"]').each(function(){
				 $(this).val("");
				   $(this).attr("disabled", true);
			   });
			 $("#truckSearch").attr("disabled", false);
			 getAutocomleteDetails("truck","#truckSearch" );
		 });
	 
	 $("#towType").click(function(){
		 
		 $('input:text[name="searchInput"]').each(function(){
			 $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#towTypeSearch").attr("disabled", false);
		 getAutocomleteDetails("towType","#towTypeSearch" );
	 });
	 
	 $("#reason").click(function(){
		 
		 $('input:text[name="searchInput"]').each(function(){
			 $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#reasonSearch").attr("disabled", false);
		 getAutocomleteDetails("priorityAndReason","#reasonSearch" );
		 
	 });
	 
	$("#account").click(function(){
			 
			 $('input:text[name="searchInput"]').each(function(){
				 $(this).val("");
				   $(this).attr("disabled", true);
			   });
			 $("#accountSearch").attr("disabled", false);
			// getAutocomleteDetails("priorityAndReason","#accountSearch" );
			 
		 });
	
	$("#salesRep").click(function(){
		 
		 $('input:text[name="searchInput"]').each(function(){
			 $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 $("#salesRepSearch").attr("disabled", false);
		 getAutocomleteDetails("salesRep","#salesRepSearch" );
		 
	 });
	
	$("#radioAll").click(function(){
		 
		 $('input:text[name="searchInput"]').each(function(){
			 $(this).val("");
			   $(this).attr("disabled", true);
		   });
		 setCallListTable(global_callList); 
	 });
	 
		
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
	var driver=[];
	 for (var j = 0; j < global_callList.allInvoices.length; j++) {
	        var invoices = global_callList.allInvoices[j];
	        driver.push(invoices[searchid].toString());
	 }

	 $(inputId).autocomplete({
	      source: driver,
	      select: function( event, obj ){

	    	  for (var j = 0; j < global_callList.allInvoices.length; j++) {
	    	        var invoices = global_callList.allInvoices[j];
	    	        if(invoices[searchid]==obj.item.value){
	    	        	var allInvoices=[];
	    	        	allInvoices.push(invoices);
	    	        	searchData= {"allInvoices":allInvoices};
	    	        	setCallListTable(searchData);
	    	        }
	    	        }
				
	      }
	 });
	
}
 
function CreateTable(obj, cols) {
    var table = $('<table id="invoiceSearch" class="act-table"></table>');
    var th = $('<thead><tr></tr></thead>');
    for (var i = 0; i < cols.length; i++) {
        th.append('<th>' + headers[i] + '</th>');
    }
    table.append(th);
    //code for dynamic search text boxes in dynamic table.
    var sth=$('<tr class="act-table-odd"></tr>');
    for (var i = 0; i < cols.length; i++) {
        sth.append('<td></td>');
    }
    
    table.append(sth);
    for (var j = 0; j < obj.allInvoices.length; j++) {
        var invoices = obj.allInvoices[j];
        var tr = $('<tr></tr>');
        for (var k = 0; k < cols.length; k++) {
            var columnName = cols[k];
            tr.append('<td align="center">' + invoices[columnName] + '</td>');
        }
        table.append(tr);
    }
    
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
		   $('#Output').html(CreateTable(global_callList, callFilter));
		   $('#invoiceSearch').columnFilters({alternateRowClassNames:['act-table-odd']});
	   }else {
		   
		   $('#Output').html("");
	   }
}

function populateCallList(){
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
}


function callListqryToJSON()
	{	
		var searchQuery ="";
		if($("#serviceCallDate").is(":checked"))
			searchQuery = "byServiceCallDate";
		else
			searchQuery="releaseDate";
				
		return JSON.stringify({
				"searchQuery": searchQuery, 
				"fromDate": $("#startDate").val(),
				"toDate": $("#endDate").val()
				});
			}
	
	/*function exportInvoicesList()
	{	
		var exportInvoicesInfo = invoicesListTableToJSON();
		var invoices_list = exportInvoicesInfo.invoicesListData;
		var formData = '';
		for(i in invoices_list)
		{
			formData+='<input name="serviceCallDate" type="hidden" value="'+invoices_list[i].serviceCallDate+'"/>';
			formData+='<input name="invoice" type="hidden" value="'+invoices_list[i].invoice+'"/>';
			formData+='<input name="requestedBy" type="hidden" value="'+invoices_list[i].requestedBy+'"/>';
			formData+='<input name="salesRep" type="hidden" value="'+invoices_list[i].salesRep+'"/>';
			formData+='<input name="ratePlan" type="hidden" value="'+invoices_list[i].ratePlan+'"/>';
			formData+='<input name="priorityAndReason" type="hidden" value="'+invoices_list[i].priorityAndReason+'"/>';
			formData+='<input name="towType" type="hidden" value="'+invoices_list[i].towType+'"/>';
			formData+='<input name="orDr" type="hidden" value="'+invoices_list[i].orDr+'"/>';
			formData+='<input name="driverLicense" type="hidden" value="'+invoices_list[i].driverLicense+'"/>';
			formData+='<input name="driverIsOwner" type="hidden" value="'+invoices_list[i].driverIsOwner+'"/>';
			formData+='<input name="noOwnerInfo" type="hidden" value="'+invoices_list[i].noOwnerInfo+'"/>';
			formData+='<input name="registeredOwner" type="hidden" value="'+invoices_list[i].registeredOwner+'"/>';
			formData+='<input name="registeredOwnerName" type="hidden" value="'+invoices_list[i].registeredOwnerName+'"/>';
			formData+='<input name="registeredOwnerAddress" type="hidden" value="'+invoices_list[i].registeredOwnerAddress+'"/>';
			formData+='<input name="registeredOwnerCity" type="hidden" value="'+invoices_list[i].registeredOwnerCity+'"/>';
			formData+='<input name="registeredOwnerState" type="hidden" value="'+invoices_list[i].registeredOwnerState+'"/>';
			formData+='<input name="registeredOwnerZip" type="hidden" value="'+invoices_list[i].registeredOwnerZip+'"/>';
			formData+='<input name="registeredOwnerPhone" type="hidden" value="'+invoices_list[i].registeredOwnerPhone+'"/>';
			formData+='<input name="registeredOwnerEmail" type="hidden" value="'+invoices_list[i].registeredOwnerEmail+'"/>';
			formData+='<input name="year" type="hidden" value="'+invoices_list[i].year+'"/>';
			formData+='<input name="make" type="hidden" value="'+invoices_list[i].make+'"/>';
			formData+='<input name="model" type="hidden" value="'+invoices_list[i].model+'"/>';
			formData+='<input name="color" type="hidden" value="'+invoices_list[i].color+'"/>';		
			formData+='<input name="style" type="hidden" value="'+invoices_list[i].style+'"/>';
			formData+='<input name="vin" type="hidden" value="'+invoices_list[i].vin+'"/>';
			formData+='<input name="plateCountry" type="hidden" value="'+invoices_list[i].plateCountry+'"/>';
			formData+='<input name="plateState" type="hidden" value="'+invoices_list[i].plateState+'"/>';
			formData+='<input name="plate" type="hidden" value="'+invoices_list[i].plate+'"/>';
			formData+='<input name="keys" type="hidden" value="'+invoices_list[i].keys+'"/>';
			formData+='<input name="radio" type="hidden" value="'+invoices_list[i].radio+'"/>';
			formData+='<input name="vehicleStatus" type="hidden" value="'+invoices_list[i].vehicleStatus+'"/>';
			formData+='<input name="commercialUnit" type="hidden" value="'+invoices_list[i].commercialUnit+'"/>';
			formData+='<input name="policeImpound" type="hidden" value="'+invoices_list[i].policeImpound+'"/>';
			formData+='<input name="callTime" type="hidden" value="'+invoices_list[i].callTime+'"/>';
			formData+='<input name="dispatchTime" type="hidden" value="'+invoices_list[i].dispatchTime+'"/>';		
			formData+='<input name="acceptedTime" type="hidden" value="'+invoices_list[i].acceptedTime+'"/>';
			formData+='<input name="enrouteTime" type="hidden" value="'+invoices_list[i].enrouteTime+'"/>';
			formData+='<input name="arrivedTime" type="hidden" value="'+invoices_list[i].arrivedTime+'"/>';
			formData+='<input name="hookedTime" type="hidden" value="'+invoices_list[i].hookedTime+'"/>';
			formData+='<input name="droppedTime" type="hidden" value="'+invoices_list[i].droppedTime+'"/>';
			formData+='<input name="clearTime" type="hidden" value="'+invoices_list[i].clearTime+'"/>';
			formData+='<input name="totalTime" type="hidden" value="'+invoices_list[i].totalTime+'"/>';
			formData+='<input name="pickupLocation" type="hidden" value="'+invoices_list[i].pickupLocation+'"/>';
			formData+='<input name="pickupAddress" type="hidden" value="'+invoices_list[i].pickUpAddress+'"/>';
			formData+='<input name="pickupCity" type="hidden" value="'+invoices_list[i].pickupCity+'"/>';
			formData+='<input name="pickupState" type="hidden" value="'+invoices_list[i].pickupState+'"/>';
			formData+='<input name="pickupZipcode" type="hidden" value="'+invoices_list[i].pickupZipcode+'"/>';		
			formData+='<input name="dropOffLocation" type="hidden" value="'+invoices_list[i].dropOffLocation+'"/>';
			formData+='<input name="dropOffAddress" type="hidden" value="'+invoices_list[i].dropOffAddress+'"/>';
			formData+='<input name="dropOffCity" type="hidden" value="'+invoices_list[i].dropOffCity+'"/>';
			formData+='<input name="dropOffState" type="hidden" value="'+invoices_list[i].dropOffState+'"/>';
			formData+='<input name="dropOffZipcode" type="hidden" value="'+invoices_list[i].dropOffZipcode+'"/>';
			formData+='<input name="truck" type="hidden" value="'+invoices_list[i].truck+'"/>';
			formData+='<input name="driverId" type="hidden" value="'+invoices_list[i].driverId+'"/>';
			formData+='<input name="dispatchId" type="hidden" value="'+invoices_list[i].dispatchId+'"/>';
			formData+='<input name="callReceiverId" type="hidden" value="'+invoices_list[i].callReceiverId+'"/>';
			formData+='<input name="towCharge" type="hidden" value="'+invoices_list[i].towCharge+'"/>';
			formData+='<input name="storageCharge" type="hidden" value="'+invoices_list[i].storageCharge+'"/>';
			formData+='<input name="laborCharge" type="hidden" value="'+invoices_list[i].laborCharge+'"/>';		
			formData+='<input name="mileageCharge" type="hidden" value="'+invoices_list[i].mileageCharge+'"/>';
			formData+='<input name="winchCharge" type="hidden" value="'+invoices_list[i].winchCharge+'"/>';
			formData+='<input name="gateCharge" type="hidden" value="'+invoices_list[i].gateCharge+'"/>';
			formData+='<input name="adminCharge" type="hidden" value="'+invoices_list[i].adminCharge+'"/>';
			formData+='<input name="miscChargeDesc" type="hidden" value="'+invoices_list[i].miscChargeDesc+'"/>';
			formData+='<input name="miscCharge" type="hidden" value="'+invoices_list[i].miscCharge+'"/>';
			formData+='<input name="discounts" type="hidden" value="'+invoices_list[i].discounts+'"/>';
			formData+='<input name="totalCharges" type="hidden" value="'+invoices_list[i].totalCharges+'"/>';
			formData+='<input name="amountPaid" type="hidden" value="'+invoices_list[i].amountPaid+'"/>';
			formData+='<input name="inStorage" type="hidden" value="'+invoices_list[i].inStorage+'"/>';
			formData+='<input name="billTo" type="hidden" value="'+invoices_list[i].billTo+'"/>';
			formData+='<input name="releasedTo" type="hidden" value="'+invoices_list[i].releasedTo+'"/>';		
			formData+='<input name="releaseTime" type="hidden" value="'+invoices_list[i].releaseTime+'"/>';
			formData+='<input name="releaseDate" type="hidden" value="'+invoices_list[i].releaseDate+'"/>';
			formData+='<input name="avrFiledDate" type="hidden" value="'+invoices_list[i].avrFiledDate+'"/>';
			formData+='<input name="trnsfrOfAuth" type="hidden" value="'+invoices_list[i].trnsfrOfAuth+'"/>';
			formData+='<input name="titleDate" type="hidden" value="'+invoices_list[i].titleDate+'"/>';
			formData+='<input name="locked" type="hidden" value="'+invoices_list[i].locked+'"/>';
			formData+='<input name="closed" type="hidden" value="'+invoices_list[i].closed+'"/>';	
			
		}
		$('body').append('<form id="notesTableForm"></form>');
		$('#notesTableForm').append(formData);
		$('#notesTableForm').attr("action","allInvoices/exportInvoicesList");
		$('#notesTableForm').attr("method","POST");
		$('#notesTableForm').submit();
	    $('#notesTableForm').remove();		
	}
	function invoicesListTableToJSON()
	{
		var invoicesList = {invoicesListData:[]};	
		$('#invoiceSearch tbody tr:gt(0)').each(function(){
				var invoicesDesc={
						serviceCallDate:$(this).children('td').eq(0).text(),
						invoice:$(this).children('td').eq(1).text(),
						requestedBy:$(this).children('td').eq(2).text(),
						salesRep:$(this).children('td').eq(3).text(),
						ratePlan:$(this).children('td').eq(4).text(),
						priorityAndReason:$(this).children('td').eq(5).text(),
						towType:$(this).children('td').eq(6).text(),
						orDr:$(this).children('td').eq(7).text(),
						driverLicense:$(this).children('td').eq(8).text(),
						driverIsOwner:$(this).children('td').eq(9).text(),
						noOwnerInfo:$(this).children('td').eq(10).text(),
						registeredOwner:$(this).children('td').eq(11).text(),
						registeredOwnerName:$(this).children('td').eq(12).text(),
						registeredOwnerAddress:$(this).children('td').eq(13).text(),
						registeredOwnerCity:$(this).children('td').eq(14).text(),
						registeredOwnerState:$(this).children('td').eq(15).text(),
						registeredOwnerZip:$(this).children('td').eq(16).text(),
						registeredOwnerPhone:$(this).children('td').eq(17).text(),
						registeredOwnerEmail:$(this).children('td').eq(18).text(),
						year:$(this).children('td').eq(19).text(),
						make:$(this).children('td').eq(20).text(),
						model:$(this).children('td').eq(21).text(),
						color:$(this).children('td').eq(22).text(),
						style:$(this).children('td').eq(23).text(),
						vin:$(this).children('td').eq(24).text(),
						plateCountry:$(this).children('td').eq(25).text(),
						plateState:$(this).children('td').eq(26).text(),
						plate:$(this).children('td').eq(27).text(),
						keys:$(this).children('td').eq(28).text(),
						radio:$(this).children('td').eq(29).text(),
						vehicleStatus:$(this).children('td').eq(30).text(),
						commercialUnit:$(this).children('td').eq(31).text(),
						policeImpound:$(this).children('td').eq(32).text(),
						callTime:$(this).children('td').eq(33).text(),
						dispatchTime:$(this).children('td').eq(34).text(),
						acceptedTime:$(this).children('td').eq(35).text(),
						enrouteTime:$(this).children('td').eq(36).text(),
						arrivedTime:$(this).children('td').eq(37).text(),
						hookedTime:$(this).children('td').eq(38).text(),
						droppedTime:$(this).children('td').eq(39).text(),
						clearTime:$(this).children('td').eq(40).text(),
						totalTime:$(this).children('td').eq(41).text(),
						pickupLocation:$(this).children('td').eq(42).text(),
						pickupAddress:$(this).children('td').eq(43).text(),
						pickupCity:$(this).children('td').eq(44).text(),
						pickupState:$(this).children('td').eq(45).text(),
						pickupZipcode:$(this).children('td').eq(46).text(),
						dropOffLocation:$(this).children('td').eq(47).text(),
						dropOffAddress:$(this).children('td').eq(48).text(),
						dropOffCity:$(this).children('td').eq(49).text(),
						dropOffState:$(this).children('td').eq(50).text(),
						dropOffZipcode:$(this).children('td').eq(51).text(),
						truck:$(this).children('td').eq(52).text(),
						driverId:$(this).children('td').eq(53).text(),
						dispatchId:$(this).children('td').eq(54).text(),
						callReceiverId:$(this).children('td').eq(55).text(),
						towCharge:$(this).children('td').eq(56).text(),
						storageCharge:$(this).children('td').eq(57).text(),
						laborCharge:$(this).children('td').eq(58).text(),
						mileageCharge:$(this).children('td').eq(59).text(),
						winchCharge:$(this).children('td').eq(60).text(),
						gateCharge:$(this).children('td').eq(61).text(),
						adminCharge:$(this).children('td').eq(62).text(),
						miscChargeDesc:$(this).children('td').eq(63).text(),
						miscCharge:$(this).children('td').eq(64).text(),
						discounts:$(this).children('td').eq(65).text(),
						totalCharges:$(this).children('td').eq(66).text(),
						amountPaid:$(this).children('td').eq(67).text(),
						inStorage:$(this).children('td').eq(68).text(),
						billTo:$(this).children('td').eq(69).text(),
						releasedTo:$(this).children('td').eq(70).text(),
						releaseTime:$(this).children('td').eq(71).text(),
						releaseDate:$(this).children('td').eq(72).text(),
						avrFiledDate:$(this).children('td').eq(73).text(),
						trnsfrOfAuth:$(this).children('td').eq(74).text(),
						titleDate:$(this).children('td').eq(75).text(),
						locked:$(this).children('td').eq(76).text(),
						closed:$(this).children('td').eq(77).text()					
			};
				invoicesList.invoicesListData.push(invoicesDesc);			
		});
		
		return invoicesList;
	}*/

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
			   $('body').append('<form id="notesTableForm"></form>');
			   $('#notesTableForm').append(formData);
			   $('#notesTableForm').append(recCount);
			   $('#notesTableForm').attr("action","allInvoices/exportInvoicesList");
			   $('#notesTableForm').attr("method","POST");
			   $('#notesTableForm').submit();
			   $('#notesTableForm').remove();
		   }
		}
	}