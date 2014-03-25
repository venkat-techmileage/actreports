<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Accounting - Receipts</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/driverSalesVsCommission.js" type="text/javascript"></script>
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
<script type="text/javascript">
//var driverSlsDetails="${driverSalesDetails}";
//var driverCommDetails="${driverCommissionDetails}";
var driverID="${driverId}";
var driverNme="${driverName}";
</script>
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
					<div class="act-radio-control">
        				<label class="act-radio"><input type="radio" name="driverSearch" id="driverIdRadio"/>Driver ID</label><input type="text" name="driverId" value="${driverId}" id="driverId"/>        				
        			</div>
        			<BR>
        			<div class="act-radio-control">
        				<label class="act-radio"><input type="radio" name="driverSearch" id="driverNameRadio"/>Driver Name</label><input type="text" name="driverName" value="${driverName}" id="driverName"/>
        			</div>
        			<div class="act-calender-control">
						<label>From</label> 
						<input type="text" id="fromDate" name="fromDate" value="${fromDate}"/>
						<label>To</label> 
						<input type="text" id="toDate" name="toDate" value="${toDate}"/>
					</div>
					<div class="act-button-controls">
						<button type="submit" id="driverSalesVsCommGo" class="blue-button">Go</button>
					</div>

					<div class="act-button-controls">
						<button type="submit" id="driverSalesVsCommExport" class="green-center">Export To Excel</button>
					</div>							
				</div>						
				<div class="sales-suumary-table-box">
					<fieldset>
						<div class="all-invoice-full-wrapper-controls">
							<h4>From ${fromDate} to ${toDate} </h4>
							<h5>Driver Sales Vs Commission</h5>
							<h6>Today's Date: <%=DateUtility.getCurrentDate("MM/dd/yyyy")%></h6>
						</div>

						<div class="sales-sum-table-wrapper-box">
							<div class="receipts-total-table-wrapper">
								<table class="act-table" id="driverSalesTable">
									<thead>
										<tr>
											<th>Sales</th>
											<th></th>											
										</tr>
									</thead>
									<tbody>
										<tr>										
											<td>Towing</td>
											<td>$ ${driverSalesDetails.totalTowingCharges}</td>
										</tr>
										<tr>										
											<td>Mileage</td>
											<td>$ ${driverSalesDetails.totalMileageCharges}</td>
										</tr>
										<tr>										
											<td>Labor</td>
											<td>$ ${driverSalesDetails.totalLaborCharges}</td>
										</tr>
										<tr>										
											<td>Gate</td>
											<td>$ ${driverSalesDetails.totalGateCharges}</td>
										</tr>
										<tr>										
											<td>Admin</td>
											<td>$ ${driverSalesDetails.totalAdminCharges}</td>
										</tr>
										<tr>										
											<td>subtotal</td>
											<td>$ ${driverSalesDetails.subTotal}</td>
										</tr>
										<tr>										
											<td>Discounts</td>
											<td>$ ${driverSalesDetails.totalDiscounts}</td>
										</tr>
										<tr>
											<td><label>Total:</label></td>
                            				<td>$ ${driverSalesDetails.totalSales}</td>										
									</tbody>
								</table>
							</div>
							<table>
								<tr>
									<td></td>
								</tr>
							</table>
							<div class="receipts-table-wrapper">
								<table class="act-table" id="driverCommissionTable">
                        			<thead>
										<tr>
											<th></th>
											<th>Commission</th>
											<th>Hours/Invoices</th>											
											<th>$/Hour Or Invoice</th>
										</tr>
									</thead>
									<tbody>
										<tr>										
											<td>Hourly</td>
											<td>$ ${driverCommissionDetails.hourlyCommTotal}</td>
											<td>${driverCommissionDetails.hourlyHoursOrInvoices}</td>
											<td>$ ${driverCommissionDetails.hourlyRatePerHourOrInvoice}</td>
										</tr>
										<tr>										
											<td>Percentage</td>
											<td>$ ${driverCommissionDetails.percentCommTotal}</td>
											<td>${driverCommissionDetails.percentHoursOrInvoices}</td>
											<td>$ ${driverCommissionDetails.percentRatePerHourOrInvoice}</td>
										</tr>
										<tr>										
											<td>Flat Rate</td>
											<td>$ ${driverCommissionDetails.flatRateCommTotal}</td>
											<td>${driverCommissionDetails.flatRateHoursOrInvoices}</td>
											<td>$ ${driverCommissionDetails.flatRatePerHourOrInvoice}</td>
										</tr>										
										<tr>
											<td><div class="act-billing"><label>Total:</label></div></td>
                            				<td>$ ${driverCommissionDetails.totalCommission}</td>	
                            				<td></td>
                            				<td></td>
                            			</tr>								
									</tbody>
								</table>
								<BR><BR><BR>
							</div>							
							<p align="center">								
								<b>Total Invoices: ${totalInvoices}</b>
							</p>
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