$(document).ready(function(){
	
	$("#storageManagement_r").addClass("selected");
	
	populateAuctionList();
			
	$('#auctionListInvoicesTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6,7,8,9,10]});
			
	if($('#auctionListSelect option').size() > 1 && auctionListId!=""){
		$("#auctionListSelect option[value='"+auctionListId+"']").prop("selected","selected");
	}
	
	$('#auctionListInvoicesGo').click(function(){		
		if($("#auctionListSelect").val()!=""){
			getAuctionListInvoices();			
		}else
			alert('Please select an Auction List to search.');		 	
	});
	
	// Code for export to excel.
	$('#auctionListInvoicesExport').click(function(){
		if($("#auctionListSelect").val()!=""){
			exportAuctionListInvoices();			
		}else
			alert('Please select an Auction List to search.');	 				
	});			
});

function populateAuctionList(){
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/select/getAuctionList",
		dataType:"json",
		contentType:"application/json",
		//data:callListqryToJSON(),
		success:function(result)
		{
			auctionList=$("#auctionListSelect"); 
			auctionList.children("option:gt(0)").remove();
			$.each(result,function(i,opt){
				auctionList.append($("<option></option>").attr("value", opt.optionVal).text(opt.optionTxt));
			});
		},
		error:function(error)
		{
			alert("error:"+error);
		}		 
	});		
}

function getAuctionListInvoices()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getAuctionListInvoices()...');
	var formData= '';
	formData+='<input name="auctionListId" type="hidden" value="'+$("#auctionListSelect option:selected").val()+'"/>';
	formData+='<input name="auctionListName" type="hidden" value="'+$("#auctionListSelect option:selected").text()+'"/>';
	$('body').append('<form id="auctionListInvoicesForm"></form>');
	$('#auctionListInvoicesForm').append(formData);
	$('#auctionListInvoicesForm').attr("action","/actreports/showAuctionListInvoices");
	$('#auctionListInvoicesForm').attr("method","POST");
	$('#auctionListInvoicesForm').submit();
    $('#auctionListInvoicesForm').remove();
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportAuctionListInvoices()
{
	//alert('In exportAuctionListInvoices()...');
	var data=auctionListInvoicesTableToJSON();
	var auctionListInvoices=data.auctionListInvoicesData;
	var formData= '';
	for(i in auctionListInvoices)
	{
		formData+='<input name="invoiceId" type="hidden" value="'+auctionListInvoices[i].invoiceId+'"/>';
		formData+='<input name="vehicleYear" type="hidden" value="'+auctionListInvoices[i].vehicleYear+'"/>';
		formData+='<input name="vehicleMake" type="hidden" value="'+auctionListInvoices[i].vehicleMake+'"/>';		
		formData+='<input name="vehicleModel" type="hidden" value="'+auctionListInvoices[i].vehicleModel+'"/>';
		formData+='<input name="vehicleVIN" type="hidden" value="'+auctionListInvoices[i].vehicleVIN+'"/>';
		formData+='<input name="licensePlateCountry" type="hidden" value="'+auctionListInvoices[i].licensePlateCountry+'"/>';
		formData+='<input name="licensePlateState" type="hidden" value="'+auctionListInvoices[i].licensePlateState+'"/>';
		formData+='<input name="licensePlateNo" type="hidden" value="'+auctionListInvoices[i].licensePlateNo+'"/>';
		formData+='<input name="markedAsSalvage" type="hidden" value="'+auctionListInvoices[i].markedAsSalvage+'"/>';
		formData+='<input name="lotLocation" type="hidden" value="'+auctionListInvoices[i].lotLocation+'"/>';
		formData+='<input name="releasedTo" type="hidden" value="'+auctionListInvoices[i].releasedTo+'"/>';		
	}
	$('body').append('<form id="auctionListInvoicesTableForm"></form>');
	$('#auctionListInvoicesTableForm').append(formData);
	$('#auctionListInvoicesTableForm').attr("action","/actreports/exportAuctionListInvoices");
	$('#auctionListInvoicesTableForm').attr("method","POST");
	$('#auctionListInvoicesTableForm').submit();
    $('#auctionListInvoicesTableForm').remove();
	//return false;
}

function auctionListInvoicesTableToJSON()
{
	var auctionListInvoicesList = {auctionListInvoicesData:[]};
	
	$("#auctionListInvoicesTable tbody tr").each(function(){
		var auctionListInvoices={
				invoiceId:$(this).children('td').eq(0).text(),
				vehicleYear:$(this).children('td').eq(1).text(),
				vehicleMake:$(this).children('td').eq(2).text(),				
				vehicleModel:$(this).children('td').eq(3).text(),
				vehicleVIN:$(this).children('td').eq(4).text(),
				licensePlateCountry:$(this).children('td').eq(5).text(),
				licensePlateState:$(this).children('td').eq(6).text(),
				licensePlateNo:$(this).children('td').eq(7).text(),
				markedAsSalvage:$(this).children('td').eq(8).text(),
				lotLocation:$(this).children('td').eq(9).text(),
				releasedTo:$(this).children('td').eq(10).text()				
		};
		auctionListInvoicesList.auctionListInvoicesData.push(auctionListInvoices);
	});
	
	return auctionListInvoicesList;
}