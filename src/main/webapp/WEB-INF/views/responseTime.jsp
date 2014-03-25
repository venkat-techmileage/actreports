<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Summaries - Response Time Report</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/responseTime.js" type="text/javascript"></script>
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
var lateType="${lateType}";
var searchBy="${searchBy}";
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
					<fieldset>
						<table style="border-spacing: 0;" CELLSPACING="10" width="100%">
							<tr>
								<td width="8%">								
									<table CELLSPACING="3" width="100%">
										<tr>
											<td><input type="radio" name="lateRadio" id="all"></td>
											<td align="left">All</td>
										</tr>
										<tr>
											<td><input type="radio" name="lateRadio" id="lateOnly"></td>
											<td align="left">Late Only</td>
										</tr>
									</table>								
								</td>
								<td width="22%">								
									<table CELLSPACING="3" width="100%">									
										<tr>
											<td><input type="radio" name="searchByRadio" id="searchAll"></td>
											<td align="left">All</td>
											<td></td>
										</tr>
										<tr>
											<td><input type="radio" name="searchByRadio" id="userNameRadio"></td>
											<td align="left">User Name</td>
											<td><input type="text" class="act-input-search-big" name="searchByInput" value="${userName}" placeholder="User Name" id="userName"/></td>
										</tr>
										<tr>
											<td><input type="radio" name="searchByRadio" id="userIdRadio"></td>
											<td align="left">User ID</td>
											<td><input type="text" class="act-input-search-big" name="searchByInput" value="${userId}" placeholder="User ID" id="userId"/></td>
										</tr>
										<tr>
											<td><input type="radio" name="searchByRadio" id="accountRadio"></td>
											<td align="left">Account</td>
											<td><input type="text" class="act-input-search-big" name="searchByInput" value="${accountName}" placeholder="Account" id="accountName"/></td>
										</tr>									
									</table>																	
								</td>
								<td width="65%">
									<div class="act-calender-control">
										<label>From</label> 
										<input type="text" id="fromDate" name="fromDate" value="${fromDate}" /> 
										<label>To</label> 
										<input type="text" id="toDate" name="toDate" value="${toDate}" /> 
									</div>
									<div class="act-button-controls">
										<button type="submit" id="responseTimeGo" class="blue-button">Go</button>
										<button type="submit" id="responseTimeExport" class="green-center">Export To Excel</button>
										<button type="submit" id="responseTimeClear" class="green-center">Clear</button>
									</div>								
								</td>
							</tr>
						</table>
					</fieldset>
				</div>													
				<div class="sales-suumary-table-box">
					<fieldset>
						<div class="all-invoice-full-wrapper-controls">
							<h4>From ${fromDate} to ${toDate}</h4>
							<h5 align="center">Response Time Report</h5>
							<h6>Today's Date: <%=DateUtility.getCurrentDate("MM/dd/yyyy")%></h6>
						</div>						
						<div class="sales-sum-table-wrapper-box">
							<div class="sales-sum-table-wrapper">
								<table class="act-table" id="responseTimeDetailsTable">
									<thead>
										<tr>
											<th>Invoice #</th>
											<th>Account</th>
											<th>Call Taker ID</th>
											<th>Dispatch ID</th>
											<th>Driver ID</th>											
											<th>Call Time</th>
											<th>Dispatched Time</th>
											<th>Arrival Time</th>
											<th>Cleared Time</th>
											<th>Call To Dispatch</th>
											<th>Call To Arrival</th>
											<th>Dispatch To Clear</th>
											<th>Late</th>											
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${responseTimeDetailsList}" var="responseTimeDetails">
										<tr>										
											<td><c:out value="${responseTimeDetails.invoiceId}"/></td>
											<td><c:out value="${responseTimeDetails.account}"/></td>
											<td><c:out value="${responseTimeDetails.callerTakerId}"/></td>
											<td><c:out value="${responseTimeDetails.dispatchId}"/></td>
											<td><c:out value="${responseTimeDetails.driverId}"/></td>											
											<td><c:out value="${responseTimeDetails.callTime}"/></td>
											<td><c:out value="${responseTimeDetails.dispatchedTime}"/></td>
											<td><c:out value="${responseTimeDetails.arrivalTime}"/></td>
											<td><c:out value="${responseTimeDetails.clearedTime}"/></td>
											<td><c:out value="${responseTimeDetails.callToDispatch}"/></td>
											<td><c:out value="${responseTimeDetails.callToArrival}"/></td>
											<td><c:out value="${responseTimeDetails.dispatchToClear}"/></td>
											<td><c:out value="${responseTimeDetails.late}"/></td>											
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
							<p align="right">								
								<b>Total Records: ${totalRecords}</b>
							</p>
							<div class="clear"></div>
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