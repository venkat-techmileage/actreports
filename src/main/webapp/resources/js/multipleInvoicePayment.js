var baseUrl='${pageContext.request.contextPath}';
var amount = 0.0;

$(document).ready(function(){

	$("#accounting_r").addClass("selected");

	populateLocations();
	//populatePaymentTypes();	
	
	if($("#accName").val()!="")
		$('#payment-div').show();
	else
		$('#payment-div').hide();

	$('#multipleInvoicePaymentTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4,5,6,7,8]});

	$("#accName").click(function(){
		getAutocomleteDetails("accountName","#accName" );
	});
	
	$('#multipleInvoicePaymentGo').click(function(){
		if($("#accName").val()!=""){
			getInvoicesDetails();
			$('#payment-div').show();
		}
		else
			alert('Please enter an Account Name to search.');
	});

	$('#multipleInvoicePaymentAccept').click(function(){
		if($("#accName").val()!="" && $("#selectLocation").val()!="" && $("#selectPaymentType").val()!="" && $("#amount").val()!=""){
			if(amount==$('#amount').val())
				updateInvoicePayment();
			else{
				alert('Amount should be the total of payment amount of all invoices.');
				$('#amount').focus();
			}
		}else
			alert('Please enter valid input details.');
	});

	// Code for export to excel.
	$('#multipleInvoicePaymentExport').click(function(){
		if($("#accName").val()!="")
			exportInvoicesDetails();		
	});	
	
	$('#multipleInvoicePaymentRefresh').click(function(){
		if($("#accName").val()!="")
			getInvoicesDetails();		
	});
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
		url : "/actreports/multipleInvoicePayment/getAccountNamesList",
		dataType:"json",
		contentType:"application/json",
		success:function(result)
		{
			$.each(result,function(i,obj){
				accountNamesList.push(obj.accountName);
			});			
		},
		error:function(error){
			alert("Error in getAccountNames():"+error);
		}		 
	});	
	return accountNamesList;
}

function populateLocations(){
	$.ajax({
		async: false,
		method:"post",
		url : "/actreports/select/getLotLocations",
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

function getInvoicesDetails()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });

	//alert('In getInvoicesDetails()...');
	var formData= '';
	formData+='<input name="accountName" type="hidden" value="'+$("#accName").val()+'"/>';
	$('body').append('<form id="invoiceDetailsForm"></form>');
	$('#invoiceDetailsForm').append(formData);
	$('#invoiceDetailsForm').attr("action","/actreports/showInvoicesDetails");
	$('#invoiceDetailsForm').attr("method","POST");
	$('#invoiceDetailsForm').submit();
	$('#invoiceDetailsForm').remove();
	
	$.unblockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
}

function exportInvoicesDetails()
{
	//alert('In exportInvoicesDetails()...');
	var data=invoicesDetailsTableToJSON();
	var invoicesDetails=data.invoicesDetailsData;
	var formData= '';
	for(i in invoicesDetails)
	{
		formData+='<input name="invoiceId" type="hidden" value="'+invoicesDetails[i].invoiceId+'"/>';
		formData+='<input name="serviceCallDate" type="hidden" value="'+invoicesDetails[i].serviceCallDate+'"/>';
		formData+='<input name="vehicleYear" type="hidden" value="'+invoicesDetails[i].vehicleYear+'"/>';
		formData+='<input name="vehicleMake" type="hidden" value="'+invoicesDetails[i].vehicleMake+'"/>';
		formData+='<input name="vehicleModel" type="hidden" value="'+invoicesDetails[i].vehicleModel+'"/>';		
		formData+='<input name="vehicleVIN" type="hidden" value="'+invoicesDetails[i].vehicleVIN+'"/>';
		formData+='<input name="balanceAmt" type="hidden" value="'+invoicesDetails[i].balanceAmt+'"/>';
		formData+='<input name="paymentAmt" type="hidden" value="'+invoicesDetails[i].paymentAmt+'"/>';
	}
	$('body').append('<form id="invoicesDetailsTableForm"></form>');
	$('#invoicesDetailsTableForm').append(formData);
	$('#invoicesDetailsTableForm').attr("action","/actreports/exportInvoicesDetails");
	$('#invoicesDetailsTableForm').attr("method","POST");
	$('#invoicesDetailsTableForm').submit();
	$('#invoicesDetailsTableForm').remove();
	//return false;
}

function invoicesDetailsTableToJSON()
{
	var invoicesDetailsList = {invoicesDetailsData:[]};

	$("#multipleInvoicePaymentTable tbody tr").each(function(){
		var invoicesDetails={
				invoiceId:$(this).children('td').eq(0).text(),
				serviceCallDate:$(this).children('td').eq(1).text(),
				vehicleYear:$(this).children('td').eq(2).text(),
				vehicleMake:$(this).children('td').eq(3).text(),
				vehicleModel:$(this).children('td').eq(4).text(),				
				vehicleVIN:$(this).children('td').eq(5).text(),
				balanceAmt:$(this).children('td').eq(6).text(),
				paymentAmt:$(this).find("input").val()				
		};
		invoicesDetailsList.invoicesDetailsData.push(invoicesDetails);
	});

	return invoicesDetailsList;
}

function updatePaymentAmt(obj,val){
	var balanceAmt = val;	
	var chkBoxId = obj.id;
	var invoiceId = chkBoxId.substring(11);
	//var amount = $('#amount').val();
	if($('#paidInFull_'+invoiceId).is(':checked')){
		$('#payment_'+invoiceId).val(balanceAmt);	
		amount = parseFloat(amount) + parseFloat(balanceAmt);
		$('#payment_'+invoiceId).prop("disabled",true);		
	}	
	else{		
		amount = parseFloat(amount) - parseFloat($('#payment_'+invoiceId).val());
		$('#payment_'+invoiceId).val('');
		$('#payment_'+invoiceId).prop("disabled",false);
	}	
}

function updateAmount(obj){
	var txtBoxId = obj.id;
	var invoiceId = txtBoxId.substring(8);
	if($('#payment_'+invoiceId).val()!=' ' || $('#payment_'+invoiceId).val()!=''){	
		amount = parseFloat(amount) + parseFloat($('#payment_'+invoiceId).val());		
	}
	else if($('#payment_'+invoiceId).val()==' ' || $('#payment_'+invoiceId).val()==''){	
		amount = parseFloat(amount) - parseFloat($('#payment_'+invoiceId).val());		
	}
}

function updateInvoicePayment()
{
	//alert('In updateInvoicePayment()...');
	var paymentType = $("#selectPaymentType option:selected").text();
	var accountName = $("#accName").val();
	var amount = $("#amount").val();
	if(paymentType=='Credit')
	{
		var paymentInvoicesdata=updateInvoicePaymentToJSON();
		var paymentInvoiceDetails=paymentInvoicesdata.updateInvoicePaymentsData;
		var paymentDetails= '';
		for(i in paymentInvoiceDetails)
		{
			var paymentAmt = paymentInvoiceDetails[i].paymentAmt;
			if(paymentAmt.indexOf(".")==-1)
			{
				paymentAmt=paymentAmt+".00";
			}
			paymentDetails += paymentInvoiceDetails[i].invoiceId+';'+paymentAmt+';'+paymentInvoiceDetails[i].paymentLoc+'|';	
			//alert('paymentDetails = '+paymentDetails);
		}
		
		var payClient=getPayClientDetails(accountName,amount);
		var amt=payClient.amount+"";
		if(amt.indexOf(".")==-1)
		{
			amt=amt+".0";
		}
		var formData= "<FORM method='post' action='"+payClient.url+"' id='creditForm' >"+
		"<INPUT type='hidden' name='x_login' value='"+payClient.loginID+"' />"+
		"<INPUT type='hidden' name='x_amount' value='"+amt+"' />"+
		"<INPUT type='hidden' name='x_description' value='"+payClient.description+"' />"+		
		"<INPUT type='hidden' name='x_account_name' value='"+payClient.accountName+"' />"+
		"<INPUT type='hidden' name='x_payment_details' value='"+paymentDetails+"' />"+
		"<INPUT type='hidden' name='x_first_name' value='"+payClient.firstName+"' />"+
		"<INPUT type='hidden' name='x_last_name' value='"+payClient.lastName+"'/>"+
		"<INPUT type='hidden' name='x_city' value='"+payClient.city+"'/>"+
		"<INPUT type='hidden' name='x_email' value='"+payClient.email+"'/>"+
		"<INPUT type='hidden' name='x_fp_sequence' value='"+payClient.sequence+"' />"+
		"<INPUT type='hidden' name='x_fp_timestamp' value='"+payClient.timestamp+"' />"+
		"<INPUT type='hidden' name='x_fp_hash' value='"+payClient.hash+"' />"+
		"<INPUT type='hidden' name='x_test_request' value='"+payClient.testMode+"' />"+
		"<INPUT type='hidden' name='x_show_form' value='"+payClient.formType+"' />"+
		"<INPUT type='hidden' name='x_relay_response' value='"+payClient.relayRes+"' />"+
		"<INPUT type='hidden' name='x_relay_url' value='"+payClient.relayUrl+"' />"+
		"</FORM>";

		$("body").append(formData);

		$('#creditForm').submit(function(e){
			// specify a unique target name
			var target = 'windowFormTarget';
			// open a new window and name it
			window.open('', target, 'width=800,height=600,scrollbars=yes');
			this.setAttribute('target', target);

			// allow the form to be submitted normally
		});
		$('#creditForm').submit();
		$("#creditForm").remove();
	}
	else
	{
		var data=updateInvoicePaymentToJSON();
		var updateInvoicePayments=data.updateInvoicePaymentsData;
		var formData= '';
		for(j in updateInvoicePayments)
		{
			formData+='<input name="invoiceId" type="hidden" value="'+updateInvoicePayments[j].invoiceId+'"/>';
			formData+='<input name="paymentType" type="hidden" value="'+updateInvoicePayments[j].paymentType+'"/>';
			formData+='<input name="paymentAmt" type="hidden" value="'+updateInvoicePayments[j].paymentAmt+'"/>';
			formData+='<input name="chequeNo" type="hidden" value="'+updateInvoicePayments[j].chequeNo+'"/>';
			formData+='<input name="accountName" type="hidden" value="'+updateInvoicePayments[j].accountName+'"/>';		
			formData+='<input name="ccTxNo" type="hidden" value="'+updateInvoicePayments[j].ccTxNo+'"/>';
			formData+='<input name="userId" type="hidden" value="'+updateInvoicePayments[j].userId+'"/>';
			formData+='<input name="paymentLoc" type="hidden" value="'+updateInvoicePayments[j].paymentLoc+'"/>';		
		}
		$('body').append('<form id="updateInvoicePaymentsTableForm"></form>');
		$('#updateInvoicePaymentsTableForm').append(formData);
		$('#updateInvoicePaymentsTableForm').attr("action","/actreports/updateInvoicePayment");
		$('#updateInvoicePaymentsTableForm').attr("method","POST");
		$('#updateInvoicePaymentsTableForm').submit();
		$('#updateInvoicePaymentsTableForm').remove();
		//return false;
	}
}

function updateInvoicePaymentToJSON()
{
	var paymentLoc = $("#selectLocation option:selected").text();
	var paymentType = $("#selectPaymentType option:selected").text();
	var accountName = $("#accName").val();
	var chequeNo = $("#chequeNo").val();
	if($("#chequeNo").val().length>0)
		paymentType = 'Check #'+chequeNo;	
	if(!$("#chequeNo").val().length>0)
		chequeNo = ' ';
	var updateInvoicePaymentsList = {updateInvoicePaymentsData:[]};	
	$("#multipleInvoicePaymentTable tbody tr").each(function(){
		var updateInvoicePayments={
				invoiceId:$(this).children('td').eq(0).text(),
				paymentType:paymentType,
				paymentAmt:$(this).find("input").val(),
				chequeNo:chequeNo,
				accountName:accountName,				
				ccTxNo:' ',
				userId:'admin',
				paymentLoc:paymentLoc				
		};
		updateInvoicePaymentsList.updateInvoicePaymentsData.push(updateInvoicePayments);
	});

	return updateInvoicePaymentsList;
}

function getPayClientDetails(accountName,amount)
{
	var paymentClient=new Object();
	$.ajax({
		async: false,
		method:"get",
		url : "/actreports/get/paymentClient?accountName="+accountName+"&amount="+amount,
		dataType:"json",
		success:function(result)
		{
			 paymentClient=result; 
		},
		error:function(error)
		{
			alert("error::"+error);
		}
   });
	
	return paymentClient;
}

function sendBillingEmailReceipt(accountName)
{
	$.ajax({
		async: false,
		method:"POST",
		url : "/actreports/payment/sendEmailReceipt?accountName="+accountName,
		dataType:"json",
		success:function(result)
		{
			 alert(result.message); 
		},
		error:function(error)
		{
			alert("error::"+error);
		}
   });	
}