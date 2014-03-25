<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Storage Management - Storage Inventory</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css"
	type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/css/smoothmenu.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css"
	type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js"
	type="text/javascript"></script>
<script type="text/javascript" language="javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/storageInventory.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.columnfilters.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/modalPopLite1.3.0.min.js"></script>
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
.pop-div {
	width: 400px;
	height: 200px;
	background-color: #e9e7f8;
	padding-left: 40px;
	padding-top: 50px;
}

.pop-btns {
	padding-left: 75px;
	padding-top: 45px;
}

.pop-label {
	color: #26352e;
}

.disp-search-cal {
	margin-top: 2%;
}
</style>
<script type="text/javascript">
var baseUrl="${pageContext.request.contextPath}";
var lotLocation="${lotLocation}";
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
	     <table style="border-spacing: 0;" CELLSPACING="10" width="100%">
		  <tr>
			<td width="25%">
				<fieldset>
					<legend>Age Range</legend>
					<table CELLSPACING="3" width="100%">
						<tr>
							<td><input type="checkbox" name="ageRange" id="betweenDays">Between</td>
							<td><input type="text" size='3' name="searchInput"
								id="ageRangeStart" value="${ageRangeStart}"></td>
							<td align="left">and</td>
							<td><input type="text" size='3' name="searchInput"
								id="ageRangeEnd" value="${ageRangeEnd}"></td>
							<td align="left">days old.</td>
						</tr>

					</table>
				</fieldset>
			</td>
			<td width="10%">
				<table CELLSPACING="3" width="100%">
					<tr>
						<td><select class="select-3" id="selectLocation">
								<option value="">All</option>
						</select></td>
					</tr>
				</table>
			</td>
			<td width="65%">
				<div class="act-button-controls">
					<button type="submit" id="storageGo" class="blue-button">Go</button>
					<button type="submit" id="storageExport" class="green-center">Export
						To Excel</button>
				</div>
			</td>
		</tr>
	</table>

				<div class="sales-suumary-table-box">
					<h3>Storage Location Current Inventory</h3>
					<div class="all-invoice-full-wrapper-controls">
						<h4>Location: ${lotLocation}</h4>
						<h6>
							Today's Date:<%=DateUtility.getCurrentDate("MM/dd/yyyy")%></h6>
					</div>
					<div class="sales-sum-table-wrapper-box">
						<div class="receipts-table-wrapper">
							<table class="act-table" id="storageInventoryTable">
								<thead>
									<tr>
										<th>Invoice #</th>
										<th>Service Call Date</th>
										<th>Account</th>
										<th>Reason</th>
										<th>Year</th>
										<th>Make</th>
										<th>Model</th>
										<th>VIN</th>
										<th>License Plate Country</th>
										<th>License Plate State</th>
										<th>License Plate #</th>
										<th>Days In Storage</th>
										<th>Marked For Salvage</th>
										<th>Lot Location</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${storageLocationCurrentInventoryDetails}"
										var="storageDetails">
										<tr>
											<td><c:out value="${storageDetails.invoiceId}" /></td>
											<td><c:out value="${storageDetails.serviceCallDate}" /></td>
											<td><c:out value="${storageDetails.account}" /></td>
											<td><c:out value="${storageDetails.reason}" /></td>
											<td><c:out value="${storageDetails.year}" /></td>
											<td><c:out value="${storageDetails.make}" /></td>
											<td><c:out value="${storageDetails.model}" /></td>
											<td><c:out value="${storageDetails.vin}" /></td>
											<td><c:out value="${storageDetails.licensePlateCountry}" /></td>
											<td><c:out value="${storageDetails.licensePlateState}" /></td>
											<td><c:out value="${storageDetails.licensePlate}" /></td>
											<td><c:out value="${storageDetails.daysInStorage}" /></td>
											<td><c:out value="${storageDetails.markedForSalvage}" /></td>
											<td><c:out value="${storageDetails.lotLocation}" /></td>

										</tr>
									</c:forEach>
									<tr>
                            				<td></td>
                            				<td></td>
                            				<td></td>
                            				<td></td>
                            				<td></td>
                            				<td></td>
                            				<td></td>
                            				<td></td> 
                            				<td></td>
                            				<td></td>
                            			    <td></td>
                            				<td></td>
                            			    <td></td>           		
                            				<td><div class="act-billing"><label>Total Vehicle: ${totalVehicles}</label></div></td> 
								</tbody>
							</table>
						</div>
						<div class="clear"></div>
					</div>
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