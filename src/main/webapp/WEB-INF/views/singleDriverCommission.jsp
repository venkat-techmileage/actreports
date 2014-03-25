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
<script src="${pageContext.request.contextPath}/resources/js/singleDriverCommission.js" type="text/javascript"></script>
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
<script type="text/javascript">
var baseUrl="${pageContext.request.contextPath}";
var usrId="${userId}";
var usrName="${userName}";
</script>
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
						<%-- <input type="text" class="act-input-search-big" name="searchQuery" value="${userId}" placeholder="User ID" id="userId"/> --%>
						<div class="act-radio-control">
							<label class="act-radio"><input type="radio" name="userSearch" id="userIdRadio" />User ID</label><input type="text" class="act-input-search-big" name="searchQuery" value="${userId}" placeholder="User ID" id="userId"/>
						</div>
						<BR>
						<div class="act-radio-control">
							<label class="act-radio"><input type="radio" name="userSearch" id="userNameRadio" />User Name</label><input type="text" class="act-input-search-big" name="searchQuery" value="${userName}" placeholder="User Name" id="userName"/>
						</div>
						<div class="act-calender-control">
							<label>From:</label>
							<input type="text" id="fromDate" name="fromDate" value="${fromDate}"/>
						</div>
	        			<div class="act-calender-control">
							<label>To:</label>
							<input type="text" id="toDate" name="toDate" value="${toDate}"/>
						</div>
						<div class="act-button-controls">
							<button type="submit" id="singleDriverCommissionGo" class="blue-button">Go</button>
							<button type="submit" id="singleDriverCommissionExport" class="green-center">Export To Excel</button>							
						</div><br/>	
						<span id='inputError' style='color:red;display: none'></span>						
					</div>
				</div>						
				<div class="sales-suumary-table-box">
					<h3>Driver's Commission Detail From ${fromDate} To ${toDate}</h3>
					<fieldset>
						<div class="all-invoice-full-wrapper-controls">
							<h4>Driver's Name: ${driverName}</h4>
							<h5>Driver's ID: ${driverId}</h5>
							<h6>Date: <%=DateUtility.getCurrentDate("MM/dd/yyyy")%></h6>
						</div>
						<div class="sales-sum-table-wrapper-box">
							<div class="receipts-table-wrapper">
								<table class="act-table" id="singleDriverCommissionTable">
									<thead>
										<tr>
											<th>Date</th>
											<th>Invoice</th>
											<th>Tow Type</th>
											<th>Total Charges</th>
											<th>Total Storage Charges</th>
											<th>Commission Amount</th>											
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${singleDriverCommissionDetails}" var="singleDriverCommissionDetails">
										<tr>										
											<td><c:out value="${singleDriverCommissionDetails.date}"/></td>
											<td><c:out value="${singleDriverCommissionDetails.invoiceId}"/></td>
											<td><c:out value="${singleDriverCommissionDetails.towType}"/></td>
											<td><c:out value="${singleDriverCommissionDetails.totalCharge}"/></td>
											<td><c:out value="${singleDriverCommissionDetails.storageCharge}"/></td>
											<td><c:out value="${singleDriverCommissionDetails.commissionAmount}"/></td>											
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<BR>
								<table class="act-table">
									<tbody>
										<tr>										
											<td>Total Invoices:</td>
											<td>${totalInvoices}</td>
											<td>Total Charges:</td>
											<td>${totalCharges}</td>
											<td>Total Commission:</td>
											<td>${totalCommission}</td>											
										</tr>
									</tbody>
								</table>
								<BR>
								<table class="act-table" id="towTypeSummaryTable">
	                        		<thead>
	                            		<tr>
	                               			<th>Tow Type Summary</th>
	                               			<th>Total</th>
	                               			<th>Charges</th>
	                               			<th>Storage</th>
	                               			<th>Commission</th>
	                               		</tr>
	                        		</thead>
	                        		<tbody>                         
									<c:forEach items="${towTypeSummaryDetails}" var="towTypeSummaryDetails">
										<tr>										
											<td><c:out value="${towTypeSummaryDetails.towTypeSummary}"/></td>
											<td><c:out value="${towTypeSummaryDetails.total}"/></td>											
											<td><c:out value="${towTypeSummaryDetails.charges}"/></td>
											<td><c:out value="${towTypeSummaryDetails.storage}"/></td>
											<td><c:out value="${towTypeSummaryDetails.commission}"/></td>
										</tr>
									</c:forEach>										
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