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
<script src="${pageContext.request.contextPath}/resources/js/accountsReceivableDetail.js" type="text/javascript"></script>
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
						<input type="text" class="name" name="searchQuery" value="${accName}" placeholder="Account" id="accName"/>
						<div class="act-calender-control">
							<label>As Of:</label> 
							<input type="text" id="asOfDate" name="asOfDate" value="${asOfDate}"/>
							<!--  <span class="calender-box"><a href="#" class="calender-icon"></a></span>-->
						</div>
						<div class="act-button-controls">
							<button type="submit" id="accountsReceivableDetailGo" class="blue-button">Go</button>
							<button type="submit" id="accountsReceivableDetailExport" class="green-center">Export To Excel</button>
							<button type="button" id="clrSearch" class="green-center">Clear</button>
						</div><br/>	
						<span id='inputError' style='color:red;display: none'></span>						
					</div>
				</div>						
				<div class="sales-suumary-table-box">
					<h3 align="center"><font color="green">Accounts Receivable Detail Report</font></h3>
					<fieldset>											
						<div class="all-invoice-full-wrapper-controls">
							<h5>Account Name: ${accName}</h5>
							<h5>Account Primary Contact: ${accountContact}</h5>							
							<h5>As of ${asOfDate}</h5>							
						</div>						
						<div class="sales-sum-table-wrapper-box">
							<div class="sales-sum-table-wrapper">
								<table class="act-table" id="accountsReceivableDetailDueTable">
									<thead>
										<tr class="act-table-odd">
											<th>Current</th>
											<th>1-30 Days</th>
											<th>31-60 Days</th>
											<th>61-90 Days</th>											
											<th>90+ Days</th>
											<th>Total</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>${currentDue}</td>
                            				<td>${thirtyDaysDue}</td>
                            				<td>${sixtyDaysDue}</td>
                            				<td>${nintyDaysDue}</td>
                            				<td>${moreThanNintyDaysDue}</td>
                            				<td>${total}</td>                             				
                             		</tr>
									</tbody>
								</table>
								<BR>
								<table class="act-table" id="accountsReceivableDetailTable">
									<thead>
										<tr>
											<th>Invoice #</th>
											<th>Service Call Date</th>
											<th>Due Date</th>
											<th>Age</th>											
											<th>Balance</th>											
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${accountsReceivableDetail}" var="accountsReceivableDetail">
										<tr>										
											<td><c:out value="${accountsReceivableDetail.invoiceId}"/></td>
											<td><c:out value="${accountsReceivableDetail.serviceCallDate}"/></td>
											<td><c:out value="${accountsReceivableDetail.dueDate}"/></td>
											<td><c:out value="${accountsReceivableDetail.age}"/></td>
											<td><c:out value="${accountsReceivableDetail.balance}"/></td>
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