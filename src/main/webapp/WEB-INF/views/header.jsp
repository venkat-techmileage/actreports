<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Reports - Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/searchInvoices.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.columnfilters.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/modalPopLite1.3.0.min.js"></script>
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
<div id="header">
<div class="container">
<div id="logo"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/logo.gif" alt="All City Towing"/></a></div>
<div id="welcome-box"><span class="welcome-user">Welcome ${userDtl.firstName} ${userDtl.lastName}</span> <span class="date"><%= new java.util.Date() %></span><a href="/acthome/" class="signout">Sign Out</a></div>
</div>
</div>
<!--header end-->

<!--nav bar start-->
<div id="nav-bar">
<div class="container">
<div id="menu">
<ul>
<li><a href="/acthome/goHome">Home</a></li>
<li><a href="${pageContext.request.contextPath}/dashboard.html">Dash Board</a></li>
<li><a href="${pageContext.request.contextPath}/allInvoices.html">All Invoices</a></li>
<li><a href="#">Accounting</a>
<ul>
          <li><a href="#">A/R Details</a></li>
          <li><a href="#">A/R Summery</a></li>
          <li><a href="#">Receipts</a></li>
          <li><a href="#">Missing PO Number</a></li>
          <li><a href="#">Run Statements</a></li>
        </ul>
</li>
<li><a href="#">Commissions</a>
<ul>
          <li><a href="#">Single Driver Commission</a></li>
          <li><a href="#">Driver Commission Summary</a></li>
        </ul>
</li>
<li><a href="#">Storage Management</a>
<ul>
          <li><a href="#">Storage Inventory</a></li>
          <li><a href="#">Auction List</a></li>
          <li><a href="#">No AVR Field</a></li>
          <li><a href="#">Abandoned Vehicle Status Report</a></li>
          <li><a href="#">ACT Titled Vehicles</a></li>
        </ul>
</li>
<li><a href="#">Summaries</a>
<ul>
          <li><a href="#">Recap Report</a></li>
          <li><a href="#">Summery By Tow Type</a></li>
          <li><a href="#">Summery By Reason</a></li>
          <li><a href="#">Summery By Truck</a></li>
          <li><a href="#">Summery By Account Rate Plan</a></li>
         <li><a href="#">Driver Commission Vs Charges</a></li>
        </ul>
</li>
<li><a href="#">Audit</a>
<ul>
     	<li><a href="#">Charges Audit</a></li>
</ul>
</li>
</ul>
</div>
</div>
</div>
<!--nav bar end-->
</body>
</html>