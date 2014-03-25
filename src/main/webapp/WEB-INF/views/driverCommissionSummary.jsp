<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Accounting - A/R Summary</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/driverCommissionSummary.js" type="text/javascript"></script>
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
						<div class="act-calender-control">
							<label>From:</label>
							<input type="text" id="fromDate" name="fromDate" value="${fromDate}"/>
						</div>
	        			<div class="act-calender-control">
							<label>To:</label>
							<input type="text" id="toDate" name="toDate" value="${toDate}"/>
						</div>
						<div class="act-button-controls">
							<button type="submit" id="driverCommissionSummaryGo" class="blue-button">Go</button>
							<button type="submit" id="driverCommissionSummaryExport" class="green-center">Export To Excel</button>							
						</div>												
					</div>
				</div>						
				<div class="sales-suumary-table-box">
					<h3>Commission Summary From ${fromDate} To ${toDate}</h3>
					<fieldset>
						<div class="sales-sum-table-wrapper-box">
							<div class="receipts-table-wrapper">
								<table class="act-table" id="driverCommissionSummaryTable">
									<thead>
										<tr>
											<th>Payroll ID</th>
											<th>User ID</th>
											<th>Full Name</th>
											<th>Start Date</th>
											<th>Level</th>
											<th># of Tows</th>
											<th>Weekly Base</th>
											<th>Commission</th>
											<th>Commission Adjustment</th>
											<th>Total Pay</th>											
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${driverCommissionSummaryDetails}" var="driverCommissionSummaryDetails">
										<tr>										
											<td><c:out value="${driverCommissionSummaryDetails.payrollId}"/></td>
											<td><c:out value="${driverCommissionSummaryDetails.userId}"/></td>
											<td><c:out value="${driverCommissionSummaryDetails.fullName}"/></td>
											<td><c:out value="${driverCommissionSummaryDetails.startDate}"/></td>
											<td><c:out value="${driverCommissionSummaryDetails.level}"/></td>
											<td><c:out value="${driverCommissionSummaryDetails.noOfTows}"/></td>
											<td><c:out value="${driverCommissionSummaryDetails.weeklyBase}"/></td>											
											<td><c:out value="${driverCommissionSummaryDetails.commission}"/></td>
											<td><c:out value="${driverCommissionSummaryDetails.commissionAdj}"/></td>
											<td><c:out value="${driverCommissionSummaryDetails.totalPay}"/></td>											
										</tr>
									</c:forEach>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td><div class="act-billing"><label>Totals:</label></div></td>
	                            			<td>${countOfTows}</td>
	                            			<td>${weeklyBaseTotal}</td>
	                            			<td>${totalCommission}</td>
	                             			<td>${totalCommissionAdj}</td>
	                             			<td>${sumOfTotalPay}</td>
                             			</tr>
									</tbody>
								</table>														
							</div>
						</div>
						<div class="clear"></div>
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