var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){

	$("#accounting_r").addClass("selected");

	var pickerOpts = {
			  changeMonth :true,
			  showOtherMonths: true,
			  changeYear :true,
			  showOn: "both",
	  		  buttonImage: "/actreports/resources/images/calender-icon-blue.png",
	  		  buttonImageOnly: true,
	  		
        };
	$( "#asOfDate" ).datepicker(pickerOpts);

	$('#accountsReceivableDetailTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3,4]});
	$("#accName").click(function(){
		getAutocomleteDetails("accName","#accName" );
	});

	$('#accountsReceivableDetailGo').click(function(){		
		if($("#asOfDate").val()!="" && $("#accName").val()!=""){
			getAccountsReceivableDetail();			
		}else
			$("#inputError").show();
		$("#inputError").text("Please enter an Account Name & as of date to search.");
		$("#accName").focus();
	});

	// Code for export to excel.
	$('#accountsReceivableDetailExport').click(function(){
		if($("#asOfDate").val()!="" && $("#accName").val()!=""){
			exportAccountsReceivableDetail();			
		}else
			$("#inputError").show();
		$("#inputError").text("Please enter an Account Name & as of date to search.");
		$("#accName").focus();		
	});

	$("#clrSearch").click(function () {
		$("#accName").val("") ;
		$("#asOfDate").val("") ;
		$("#inputError").hide();

	});

	// Erase error message at invoice qry search
	$("#accName").keyup(function(){
		if($("#accName").val().length>0) 
			$("#inputError").hide();
	});

});

/*code for auto completing the text boxes in left pane*/
function getAutocomleteDetails(searchid,inputId ){
	var searchIdList=[];
	if(searchid=='accName')
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
		url : "/actreports/get/accountNames",
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

function getAccountsReceivableDetail()
{
	$.blockUI({
	     fadeIn : 0,
	     fadeOut : 0,
	     showOverlay : true
	 });
	//alert('In getAccountsReceivableDetail()...');
	var formData= '';
	formData+='<input name="accountName" type="hidden" value="'+$("#accName").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#asOfDate").val()+'"/>';
	$('body').append('<form id="accountsReceivableDetailForm"></form>');
	$('#accountsReceivableDetailForm').append(formData);
	$('#accountsReceivableDetailForm').attr("action","/actreports/showAccountsReceivableDetail");
	$('#accountsReceivableDetailForm').attr("method","POST");
	$('#accountsReceivableDetailForm').submit();
	$('#accountsReceivableDetailForm').remove();
	 $.unblockUI({
 	     fadeIn : 0,
 	     fadeOut : 0,
 	     showOverlay : true
 	 });
}

function exportAccountsReceivableDetail()
{
	//alert('In exportAccountsReceivableDetail()...');
	var data=accountsReceivableDetailTableToJSON();
	var accountsReceivableDetail=data.accountsReceivableDetailData;
	var formData= '';
	for(i in accountsReceivableDetail)
	{
		formData+='<input name="invoiceId" type="hidden" value="'+accountsReceivableDetail[i].invoiceId+'"/>';
		formData+='<input name="serviceCallDate" type="hidden" value="'+accountsReceivableDetail[i].serviceCallDate+'"/>';
		formData+='<input name="dueDate" type="hidden" value="'+accountsReceivableDetail[i].dueDate+'"/>';
		formData+='<input name="age" type="hidden" value="'+accountsReceivableDetail[i].age+'"/>';
		formData+='<input name="balance" type="hidden" value="'+accountsReceivableDetail[i].balance+'"/>';		
	}
	$('body').append('<form id="accountsReceivableDetailTableForm"></form>');
	$('#accountsReceivableDetailTableForm').append(formData);
	$('#accountsReceivableDetailTableForm').attr("action","/actreports/exportAccountsReceivableDetail");
	$('#accountsReceivableDetailTableForm').attr("method","POST");
	$('#accountsReceivableDetailTableForm').submit();
	$('#accountsReceivableDetailTableForm').remove();
	//return false;
}

function accountsReceivableDetailTableToJSON()
{
	var accountsReceivableDetailList = {accountsReceivableDetailData:[]};

	$("#accountsReceivableDetailTable tbody tr").each(function(){
		var accountsReceivableDetail={
				invoiceId:$(this).children('td').eq(0).text(),
				serviceCallDate:$(this).children('td').eq(1).text(),
				dueDate:$(this).children('td').eq(2).text(),
				age:$(this).children('td').eq(3).text(),
				balance:$(this).children('td').eq(4).text()				
		};
		accountsReceivableDetailList.accountsReceivableDetailData.push(accountsReceivableDetail);
	});

	return accountsReceivableDetailList;
}
