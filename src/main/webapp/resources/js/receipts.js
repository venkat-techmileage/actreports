var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#accounting_r").addClass("selected");
	
	populateLocations();
	
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
	
	$('#receiptsTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6]});
	$('#receiptTotalsTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1]});		
			
	if($('#selectLocation option').size() > 1 && loc!=""){
		$("#selectLocation option[value='"+loc+"']").prop("selected","selected");
	}
	
	$('#receiptsGo').click(function(){		
		if($("#fromDate").val()!="" && $("#toDate").val()!="" && $("#selectLocation").val()!=""){
			getReceiptsDetails();			
		}else
			alert("Invalid Date");
	});
	
	// Code for export to excel.
	$('#receiptsExport').click(function(){
		if($("#fromDate").val()!="" && $("#toDate").val()!=""){
			exportReceiptsDetails();			
		}else
			alert("Invalid Date");		
	});
});

function populateLocations(){
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/receipts/getLotLocations",
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

function getReceiptsDetails()
{
	 $.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getReceiptsDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#fromDate").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#toDate").val()+'"/>';
	formData+='<input name="location" type="hidden" value="'+$("#selectLocation option:selected").text()+'"/>';
	
	$('body').append('<form id="receiptsForm"></form>');
	$('#receiptsForm').append(formData);
	$('#receiptsForm').attr("action","/actreports/displayReceipts");
	$('#receiptsForm').attr("method","POST");
	$('#receiptsForm').submit();
    $('#receiptsForm').remove();
    
    $.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportReceiptsDetails()
{
	//alert('In exportReceiptsDetails()...');
	var data=receiptsTableToJSON();
	var receiptsDetails=data.receiptsDetailsData;
	var formData= '';
	for(i in receiptsDetails)
	{
		formData+='<input name="invoiceId" type="hidden" value="'+receiptsDetails[i].invoiceId+'"/>';
		formData+='<input name="account" type="hidden" value="'+receiptsDetails[i].account+'"/>';
		formData+='<input name="billTo" type="hidden" value="'+receiptsDetails[i].billTo+'"/>';
		formData+='<input name="paymentMethod" type="hidden" value="'+receiptsDetails[i].paymentMethod+'"/>';
		formData+='<input name="amount" type="hidden" value="'+receiptsDetails[i].amount+'"/>';
		formData+='<input name="userId" type="hidden" value="'+receiptsDetails[i].userId+'"/>';			
		formData+='<input name="location" type="hidden" value="'+receiptsDetails[i].location+'"/>';
	}
	$('body').append('<form id="receiptDetailsTableForm"></form>');
	$('#receiptDetailsTableForm').append(formData);
	$('#receiptDetailsTableForm').attr("action","/actreports/exportReceipts");
	$('#receiptDetailsTableForm').attr("method","POST");
	$('#receiptDetailsTableForm').submit();
    $('#receiptDetailsTableForm').remove();
	//return false;
}

function receiptsTableToJSON()
{
	var receiptsDetailsList = {receiptsDetailsData:[]};
	
	$("#receiptsTable tbody tr").each(function(){
		var receiptsDetails={
				invoiceId:$(this).children('td').eq(0).text(),
				account:$(this).children('td').eq(1).text(),
				billTo:$(this).children('td').eq(2).text(),
				paymentMethod:$(this).children('td').eq(3).text(),
				amount:$(this).children('td').eq(4).text(),
				userId:$(this).children('td').eq(5).text(),				
				location:$(this).children('td').eq(6).text()
		};
		receiptsDetailsList.receiptsDetailsData.push(receiptsDetails);
	});
	
	return receiptsDetailsList;
}
