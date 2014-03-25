var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#storageManagement_r").addClass("selected");
	
	$('#storageInventoryTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6,7,8,9,10,11,12,13]});
     populateLocationList();
	
	if($('#selectLocation option').size() > 1 && lotLocation!=""){
		$("#selectLocation option[value='"+lotLocation+"']").prop("selected","selected");
		//$('#ddlList option:selected').text();
	}
	
	if($("#ageRangeStart").val()!='' && $("#ageRangeEnd").val()!=''){
		$('#betweenDays').prop("checked","checked");
	}
	else{
		$('input:text[name="searchInput"]').each(function(){
			$(this).val("");
			$(this).attr("disabled", true);
		});
	}
	
	$("#betweenDays").click(function(){
		if($('input:checkbox[name="ageRange"]').is(':checked')){
			$('input:text[name="searchInput"]').each(function(){
				$(this).attr("disabled", false);				
			});
		}
		else{
			$('input:text[name="searchInput"]').each(function(){
				$(this).val("");
				$(this).attr("disabled", true);
			});
		}
	
					
	});

	$('#storageGo').click(function(){
		/*if($("#ageRangeStart").val()!="" && $("#ageRangeEnd").val()!=""){
			getStorageLocationDetails();
		}else
			alert("Invalid Data.");*/
		getStorageLocationDetails();
	});
	
	// Code for export to excel.
	$('#storageExport').click(function(){

			exportStorageLocationDetails();			
				
	});
	
});

function populateLocationList()
{
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/select/getLocationList",
		dataType:"json",
		contentType:"application/json",
		//data:callListqryToJSON(),
		success:function(result)
		{
			locationList=$("#selectLocation"); 
			locationList.children("option:gt(0)").remove();
			$.each(result,function(i,opt){

				locationList.append($("<option></option>").attr("value", opt.optionVal).text(opt.optionTxt));
			});
		},
		error:function(error)
		{
			alert("error:"+error);
		}		 
	});		
}
function getStorageLocationDetails()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
//	alert('In getStorageLocationDetails()...');
	var formData= '';
	formData+='<input name="location" type="hidden" value="'+$("#selectLocation option:selected").text()+'"/>';
	if($('input:checkbox[name="ageRange"]').is(':checked')){
		formData+='<input name="ageRangeStart" type="hidden" value="'+$("#ageRangeStart").val()+'"/>';
		formData+='<input name="ageRangeEnd" type="hidden" value="'+$("#ageRangeEnd").val()+'"/>';
	}
	$('body').append('<form id="storageForm"></form>');
	$('#storageForm').append(formData);
	$('#storageForm').attr("action","/actreports/displayStorageLocationCurrentInventory");
	$('#storageForm').attr("method","POST");
	$('#storageForm').submit();
	$('#storageForm').remove();
	 $.unblockUI({
 	     fadeIn : 0,
 	     fadeOut : 0,
 	     showOverlay : true
 	 });
}

function exportStorageLocationDetails()
{
//	alert('In exportStorageLocationDetails()...');
	var data=storageInventoryTableToJSON();
	var storageDetails=data.storageDetailsData;
	var formData= '';
	for(i in storageDetails)
	{
		formData+='<input name="invoiceId" type="hidden" value="'+storageDetails[i].invoiceId+'"/>';
		formData+='<input name="serviceCallDate" type="hidden" value="'+storageDetails[i].serviceCallDate+'"/>';
		formData+='<input name="account" type="hidden" value="'+storageDetails[i].account+'"/>';
		formData+='<input name="reason" type="hidden" value="'+storageDetails[i].reason+'"/>';
		formData+='<input name="year" type="hidden" value="'+storageDetails[i].year+'"/>';
		formData+='<input name="make" type="hidden" value="'+storageDetails[i].make+'"/>';
		formData+='<input name="model" type="hidden" value="'+storageDetails[i].model+'"/>';			
		formData+='<input name="vin" type="hidden" value="'+storageDetails[i].vin+'"/>';
		formData+='<input name="licensePlateCountry" type="hidden" value="'+storageDetails[i].licensePlateCountry+'"/>';
		formData+='<input name="licensePlateState" type="hidden" value="'+storageDetails[i].licensePlateState+'"/>';
		formData+='<input name="licensePlate" type="hidden" value="'+storageDetails[i].licensePlate+'"/>';
		formData+='<input name="daysInStorage" type="hidden" value="'+storageDetails[i].daysInStorage+'"/>';
		formData+='<input name="markedForSalvage" type="hidden" value="'+storageDetails[i].markedForSalvage+'"/>';
		formData+='<input name="lotLocation" type="hidden" value="'+storageDetails[i].lotLocation+'"/>';
	}
	$('body').append('<form id="storageDetailsTableForm"></form>');
	$('#storageDetailsTableForm').append(formData);
	$('#storageDetailsTableForm').attr("action","/actreports/exportStorageLocationCurrentInventory");
	$('#storageDetailsTableForm').attr("method","POST");
	$('#storageDetailsTableForm').submit();
	$('#storageDetailsTableForm').remove();
	return false;
}

function storageInventoryTableToJSON()
{
	var storageDetailsList = {storageDetailsData:[]};

	$("#storageInventoryTable tbody tr").each(function(){
		var storageDetails={
				invoiceId:$(this).children('td').eq(0).text(),
				serviceCallDate:$(this).children('td').eq(1).text(),
				account:$(this).children('td').eq(2).text(),
				reason:$(this).children('td').eq(3).text(),
				year:$(this).children('td').eq(4).text(),
				make:$(this).children('td').eq(5).text(),				
				model:$(this).children('td').eq(6).text(),
				vin:$(this).children('td').eq(7).text(),
				licensePlateCountry:$(this).children('td').eq(8).text(),
				licensePlateState:$(this).children('td').eq(9).text(),
				licensePlate:$(this).children('td').eq(10).text(),
				daysInStorage:$(this).children('td').eq(11).text(),
				markedForSalvage:$(this).children('td').eq(12).text(),
				lotLocation:$(this).children('td').eq(13).text()

		};
		storageDetailsList.storageDetailsData.push(storageDetails);
	});
	return storageDetailsList;
}