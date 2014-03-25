var baseUrl='${pageContext.request.contextPath}';

$(document).ready(function(){
	
	$("#summaries_r").addClass("selected");
	
	var pickerOpts = {
			  changeMonth :true,
			  showOtherMonths: true,
			  changeYear :true,
			  showOn: "both",
	  		  buttonImage: "/actreports/resources/images/calender-icon-blue.png",
	  		  buttonImageOnly: true,
	  		
        };
			$( "#dateRangeFrom" ).datepicker(pickerOpts);
			$( "#dateRangeTo" ).datepicker(pickerOpts);
			$( "#compareFrom" ).datepicker(pickerOpts);
			$( "#compareTo" ).datepicker(pickerOpts);
	
	$('#salesSummaryTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2,3]});
	$('#impoundSummaryTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1]});		
	$('#receiptsSummaryTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1]});
	$('#dispatchSummaryTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1]});
	$('#calculationsTable').columnFilters({alternateRowClassNames:['act-table-odd'], excludeColumns:[0,1,2]});
	
	if($("#dateRangeFrom").val()=="" && $("#dateRangeTo").val()=="" && $("#compareFrom").val()=="" && $("#compareTo").val()=="")
		$('#showTables').hide();
	
	$('#recapGo').click(function(){		
		if($("#dateRangeFrom").val()!="" && $("#dateRangeTo").val()!="" && $("#compareFrom").val()!="" && $("#compareTo").val()!=""){
			getRecapDetails();	
			$('#showTables').show();
		}
		else{
			alert("Invalid input data.");
			$('#showTables').hide();
		}
	});
});

function getRecapDetails()
{

	//alert('In getRecapDetails()...');
	var formData= '';
	formData+='<input name="fromDate" type="hidden" value="'+$("#dateRangeFrom").val()+'"/>';
	formData+='<input name="toDate" type="hidden" value="'+$("#dateRangeTo").val()+'"/>';
	formData+='<input name="compareToFromDate" type="hidden" value="'+$("#compareFrom").val()+'"/>';
	formData+='<input name="compareToToDate" type="hidden" value="'+$("#compareTo").val()+'"/>';
	
	$('body').append('<form id="recapForm"></form>');
	$('#recapForm').append(formData);
	$('#recapForm').attr("action","/actreports/getRecapDetails");
	$('#recapForm').attr("method","POST");
	$('#recapForm').submit();
    $('#recapForm').remove();
    
}