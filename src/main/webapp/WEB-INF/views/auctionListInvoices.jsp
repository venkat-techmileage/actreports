<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Storage Management - Auction List</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/auctionListInvoices.js" type="text/javascript"></script>
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
var auctionListId="${auctionListId}";
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
						<div class="act-select-control">
	                		<select class="select-3" id="auctionListSelect">
	                			<option value="">Auction List</option>
	                		</select>
						</div>
						<div class="act-button-controls">
							<button type="submit" id="auctionListInvoicesGo" class="blue-button">Go</button>
							<button type="submit" id="auctionListInvoicesExport" class="green-center">Export To Excel</button>							
						</div>												
					</div>
				</div>						
				<div class="sales-suumary-table-box">
					<fieldset>											
						<div class="all-invoice-full-wrapper-controls">
							<h4>Auction Date: ${auctionDate}</h4>
							<h5>Auction List: ${auctionListName}</h5>
							<h6>Today's Date: <%=DateUtility.getCurrentDate("MM/dd/yyyy")%></h6>							
						</div>						
						<div class="sales-sum-table-wrapper-box">
							<div class="sales-sum-table-wrapper">
								<table class="act-table" id="auctionListInvoicesTable">
									<thead>
										<tr class="act-table-odd">
											<th>Invoice #</th>
											<th>Year</th>
											<th>Make</th>
											<th>Model</th>											
											<th>VIN</th>
											<th>License Plate Country</th>
											<th>License Plate State</th>
											<th>License Plate #</th>
											<th>Marked As Salvage</th>
											<th>Lot Location</th>
											<th>Released To</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${auctionListInvoicesDetailsList}" var="auctionListInvoicesDetails">
											<tr>										
												<td><c:out value="${auctionListInvoicesDetails.invoiceId}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.vehicleYear}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.vehicleMake}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.vehicleModel}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.vehicleVIN}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.licensePlateCountry}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.licensePlateState}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.licensePlateNo}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.markedAsSalvage}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.lotLocation}"/></td>
												<td><c:out value="${auctionListInvoicesDetails.releasedTo}"/></td>												
											</tr>
										</c:forEach>
									</tbody>
								</table>								
							</div>
							<p align="right">								
								<b>Total Vehicles: ${totalVehicles}</b>
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