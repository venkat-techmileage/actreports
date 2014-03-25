<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Audit - Charges Audit</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/auditLog.js" type="text/javascript"></script>
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
					<div class="act-calender-control">
						<label>From</label> 
						<input type="text" id="fromDate" name="fromDate" value="${fromDate}"/>
						
					</div>
					<div class="act-calender-control">
						<label>To</label> 
						<input type="text" id="toDate" name="toDate" value="${toDate}"/>
						
					</div>
					<div class="act-button-controls">
						<button type="submit" id="auditGo" class="blue-button">Go</button>
					</div>

					<div class="act-button-controls">
						<button type="submit" id="auditExport" class="green-center">Export To Excel</button>
					</div>							
				</div>						
				<div class="sales-suumary-table-box">
					<h3>Audit Log</h3>
					<fieldset>
						<div class="all-invoice-full-wrapper-controls">
							<h4>Today's Date: <%=DateUtility.getCurrentDate("MM/dd/yyyy")%></h4>
		                     <h6>From ${fromDate} to ${toDate}</h6>							
						</div>                         
						<div class="sales-sum-table-wrapper-box">
							<div class="receipts-table-wrapper">
								<table class="act-table" id="auditTable">
									<thead>
										<tr>
											<th>User ID</th>
											<th>Change Date</th>
											<th>Change Time</th>
											<th>Invoice #</th>
											<th>Original Total Charges</th>
											<th>New Total Charges</th>
											<th>Charges Difference</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${auditDetails}" var="auditDetails">
										<tr>										
											<td><c:out value="${auditDetails.userId}"/></td>
											<td><c:out value="${auditDetails.changeDate}"/></td>
											<td><c:out value="${auditDetails.changeTime}"/></td>
											<td><c:out value="${auditDetails.invoice}"/></td>
											<td><c:out value="${auditDetails.originalTotalCharges}"/></td>
											<td><c:out value="${auditDetails.newTotalCharges}"/></td>
											<td><c:out value="${auditDetails.chargesDifference}"/></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<BR>
								<h4>Summary by User ID</h4>
								<table class="act-table" id="auditTotalTable">
                        			<thead>
                            			<tr>
                                			<th>User ID</th>
                                			<th>Total Changes</th>
                                			<th>Original Total Charges</th>
                                			<th>New Total Charges</th>
                                			<th>Charges Difference</th>
                                		</tr>
                        			</thead>
                        			<tbody>                         
									<c:forEach items="${auditTotals}" var="auditTotals">
										<tr>										
											<td><c:out value="${auditTotals.userId}"/></td>
											<td><c:out value="${auditTotals.totalChanges}"/></td>
										    <td><c:out value="${auditTotals.originalTotalCharges}"/></td>
											<td><c:out value="${auditTotals.newTotalCharges}"/></td>
											<td><c:out value="${auditTotals.chargesDifference}"/></td>
																				
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
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