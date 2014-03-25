<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Accounting - Multiple Invoice Payment</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/multipleInvoicePayment.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.columnfilters.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/modalPopLite1.3.0.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
ddsmoothmenu.init({
	mainmenuid: "menu", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'ddsmoothmenu', //class added to menu's outer DIV
	//customtheme: ["#1c5a80", "#18374a"],
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
});
</script>
<style type="text/css">
		.pop-div
		{
			width:400px;
			height:200px;
			background-color:#e9e7f8;
			padding-left:40px;
			padding-top:50px;		
		}
		.pop-btns
		{
			padding-left: 75px;
			padding-top: 45px;
		}
		.pop-label
		{
			color:#26352e;
		}
		.disp-search-cal
		{
			margin-top: 2%;
		}		
</style>
</head>
<body>
	<!--header start-->
	<jsp:include page="header.jsp" />
	<!--header end-->
	<!--main wrapper-->
	<div id="main-wrapper">
		<div class="container">			
			<!--main wrapper starts-->
			<div class="tab-full-wrapper-2">
				<div class="all-invoice-full-wrapper-controls">
					<div class="act-search-control">
						<input type="text" class="act-input-search-big" name="accName" value="${accountName}" placeholder="Account Name" id="accName"/>
						<div class="act-button-controls">
							<button type="submit" id="multipleInvoicePaymentGo" class="blue-button">Go</button>							
						</div>
						<BR><BR>
						<div id="payment-div">
							<div class="act-select-control">
		                		<select class="select-3" id="selectLocation">
		                			<option value="">Your Location</option>
		                		</select>
		                		<select class="select-3" id="selectPaymentType">
		                			<option value="">Payment Type</option>
		                			<option value="Cash">Cash</option>
		                			<option value="Credit">Credit</option>
		                			<option value="Check">Check</option>		                			
		                			<option value="Transfer">Transfer</option>
		                			<option value="Employee Advance">Employee Advance</option>
		                		</select>
							</div>
							<input type="text" class="act-input-search-big" name="chequeNo" placeholder="Check #" id="chequeNo"/>
							<input type="text" class="act-input-search-big" name="amount" placeholder="Amount" id="amount"/>
							<div class="act-button-controls">
								<button type="submit" id="multipleInvoicePaymentAccept" class="blue-button">Accept</button>
								<button type="submit" id="multipleInvoicePaymentExport" class="green-center">Export To Excel</button>
								<button type="submit" id="multipleInvoicePaymentRefresh" class="green-center">Refresh</button>
							</div>	
						</div>					
					</div>
				</div>						
				<div class="sales-suumary-table-box">
					<fieldset>
						<div class="sales-sum-table-wrapper-box">
							<p align="right">								
								<b>Total Open Invoices: ${totalOpenInvoies}</b>
							</p>
							<div class="sales-sum-table-wrapper">
								<table class="act-table" id="multipleInvoicePaymentTable">
									<thead>
										<tr>
											<th>Invoice #</th>
											<th>Service Call Date</th>
											<th>Year</th>
											<th>Make</th>
											<th>Model</th>											
											<th>VIN</th>
											<th>Balance</th>
											<th>Payment</th>
											<th>Paid In Full</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${multipleInvoicePaymentDetailsList}" var="multipleInvoicePaymentDetails">
										<tr>										
											<td><c:out value="${multipleInvoicePaymentDetails.invoiceId}"/></td>
											<td><c:out value="${multipleInvoicePaymentDetails.serviceCallDate}"/></td>
											<td><c:out value="${multipleInvoicePaymentDetails.vehicleYear}"/></td>
											<td><c:out value="${multipleInvoicePaymentDetails.vehicleMake}"/></td>
											<td><c:out value="${multipleInvoicePaymentDetails.vehicleModel}"/></td>											
											<td><c:out value="${multipleInvoicePaymentDetails.vehicleVIN}"/></td>
											<td><c:out value="${multipleInvoicePaymentDetails.balanceAmt}"/></td>
											<td><input type="text" id="payment_${multipleInvoicePaymentDetails.invoiceId}" size="8" name="paymentAmt" onchange="updateAmount(this);" value="${multipleInvoicePaymentDetails.paymentAmt}"/></td>
											<td><input type="checkbox" id="paidInFull_${multipleInvoicePaymentDetails.invoiceId}" name="check" onclick="updatePaymentAmt(this,${multipleInvoicePaymentDetails.balanceAmt});"/></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>								
							</div>							
						</div>
					</fieldset>
				</div>
				<div style="clear: both;"></div>
				<!--tab wrapper end-->
			</div>		
		</div>
		<!--container end-->
	</div>
	<!--main wrapper end-->	
	<div style="clear: both;"></div>
	<!--footer start-->
		<%-- <jsp:include page="footer.jsp" /> --%>
		<div id="footer">Copyright &copy; 2013 ACT Towing Company, All Rights Reserved</div>
	<!--footer end-->
</body>
</html>