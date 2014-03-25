<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Summaries - Response Time Report</title>
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
<script src="${pageContext.request.contextPath}/resources/js/billing.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.columnfilters.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/modalPopLite1.3.0.min.js"></script>
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
</head>
<script type="text/javascript">
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
								<td width="25%">
									<table CELLSPACING="3" width="100%">
										<tr>
											<td><input type="radio" name="searchByRadio"
												id="searchAll"></td>
											<td align="left">All Billable</td>
											<td></td>
										</tr>
										<tr>
											<td><input type="radio" name="searchByRadio"
												id="accountRadio"></td>
											<td align="left">Account</td>
											<td><input type="text" class="act-input-search-big"
												name="searchByInput" value="${accountName}"
												placeholder="Account Name" id="accountName" /></td>
										</tr>
									</table>
								</td>
								<td width="75%">
									<div class="act-calender-control">
										<label>As Of: </label> <input type="text" id="asOfDate"
											name="asOfDate" value="${asOfDate}" /> 
									</div>
									<div class="act-select-control">
										<select class="select-3" id="statementType">
											<option value="pdf">PDF</option>
											<option value="doc">Doc</option>
											<option value="xls">Excel</option>
										</select>
									</div>
									<div class="act-button-controls">
										<button type="submit" id="billingView" class="green-center">View
											Statements(s)</button>
										<button type="submit" id="billingSend" class="blue-button">Send
											Bill</button>
									</div>
								</td>
							</tr>
						</table>
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