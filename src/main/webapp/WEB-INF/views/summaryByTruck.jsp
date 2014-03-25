<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Summaries - Summary By Truck</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/summaryByTruck.js" type="text/javascript"></script>
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
						<button type="submit" id="summaryByTruckGo" class="blue-button">Go</button>
					</div>

					<div class="act-button-controls">
						<button type="submit" id="summaryByTruckExport" class="green-center">Export To Excel</button>
					</div>							
				</div>						
				<div class="sales-suumary-table-box">
					<h3>Sales Summary by Truck</h3>
					<fieldset>
						<div class="all-invoice-full-wrapper-controls">
							<h5>From ${fromDate} to ${toDate}</h5>
							<h6>Today's Date: <%=DateUtility.getCurrentDate("MM/dd/yyyy")%></h6>
						</div>

						<div class="sales-sum-table-wrapper-box">
							<div class="sales-sum-table-wrapper">
								<table class="act-table" id="summaryByTruck">
									<thead>
										<tr>
											<th>Truck ID</th>
											<th>Number of Tows</th>
											<th>* Total Charges</th>
											<th>Use %</th>
											<th>*Charges Per Tow</th>
											<th>Actual Mileage</th>
											<th>Miles Per Tow</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${summaryByTruckList}" var="truckDetails">
										<tr>										
											<td><c:out value="${truckDetails.truck}"/></td>
											<td><c:out value="${truckDetails.noOfTows}"/></td>
											<td>$<c:out value="${truckDetails.totalCharges}"/></td>
											<td><c:out value="${truckDetails.use}"/>%</td>
											<td><c:out value="${truckDetails.chargesPerTow}"/></td>
											<td><c:out value="${truckDetails.actualMileage}"/></td>
											<td><c:out value="${truckDetails.milesPerTow}"/></td>
										</tr>
									</c:forEach>
									<tr>
										<td><div class="act-billing"><label>Totals:</label></div></td>
                            			<td>${totalTows}</td>
                            			<td>$ ${sumOfTotalCharges}</td>
                            			<td><div class="act-billing"><label>Average Charge Per Tow:</label></div></td>
                             			<td>$ ${avgChargePerTow}</td>
                             			<td>${actualMileageTotal}</td>
                             			<td>Average:${avgMilesPerTow}</td>
                             		</tr>
									</tbody>
								</table>
							</div>
							<p>
								<b>* Does not include storage fees</b>
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