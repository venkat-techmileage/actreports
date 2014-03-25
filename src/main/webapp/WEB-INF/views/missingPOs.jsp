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
<script src="${pageContext.request.contextPath}/resources/js/missingPOs.js" type="text/javascript"></script>
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
					<div class="act-button-controls">
						<button type="submit" id="missingPOsGo" class="blue-button">Go</button>
					</div>
					<div class="act-button-controls">
						<button type="submit" id="missingPOsExport" class="green-center">Export To Excel</button>
					</div>
					<div class="act-button-controls">
						<button type="submit" id="missingPOsSave" class="blue-button">Save</button>
					</div>
				</div>						
				<div class="sales-suumary-table-box">
					<fieldset>
						<h3 align="center"><font color="green">Missing P.O. Report</font></h3>
						<h4 align="right"><font color="green">Today's Date: <%=DateUtility.getCurrentDate("MM/dd/yyyy")%></font></h4>
						<div class="sales-sum-table-wrapper-box">
							<div class="sales-sum-table-wrapper">
								<table class="act-table" id="missingPOsTable">
									<thead>
										<tr>
											<th>Invoice</th>
											<th>Service Call Date</th>
											<th>Year</th>
											<th>Make</th>
											<th>Model</th>											
											<th>VIN</th>
											<th>Billed To</th>
											<th>Amount</th>
											<th>P.O. Number</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${missingPOs}" var="missingPOs">
										<tr>										
											<td><c:out value="${missingPOs.invoiceId}"/></td>
											<td><c:out value="${missingPOs.serviceCallDate}"/></td>
											<td><c:out value="${missingPOs.year}"/></td>
											<td><c:out value="${missingPOs.make}"/></td>
											<td><c:out value="${missingPOs.model}"/></td>											
											<td><c:out value="${missingPOs.vin}"/></td>
											<td><c:out value="${missingPOs.billedTo}"/></td>
											<td><c:out value="${missingPOs.amount}"/></td>
											<td><input type="text" id="poNumber_${missingPOs.invoiceId}" maxlength="20" value="${missingPOs.poNumber}"/></td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<h4 align="right">Total: ${totalInvoices}</h4>
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