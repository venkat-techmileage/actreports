<%@page import="act.reports.util.DateUtility,act.reports.model.UserDetail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Reports - Dashboard</title>
</head>
<%
UserDetail userDtl = (UserDetail)session.getAttribute("userDetails");
System.out.println("userDtl in JSP>>>" + userDtl);
%>
<body>
<!--header start-->
<div id="header">
<div class="container">
<div id="logo"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/logo.gif" alt="All City Towing"/></a></div>
<%-- <div id="welcome-box"><span class="welcome-user">Welcome ${userDtl.firstName} ${userDtl.lastName}</span> <span class="date"><%= DateUtility.getDisplayDate(new java.util.Date(),"MMMM dd")%>,&nbsp;<%= DateUtility.getDisplayDate(new java.util.Date(),"yyyy")%> </span><a href="/acthome/logout" class="signout">Sign Out</a></div> --%>
<div id="welcome-box"><span class="welcome-user">Welcome <%= userDtl.getLastName()  %> , <%= userDtl.getFirstName() %></span> <span class="date"><%= DateUtility.getDisplayDate(new java.util.Date(),"MMMM dd")%>,&nbsp;&nbsp;&nbsp;<%= DateUtility.getDisplayDate(new java.util.Date(),"yyyy")%> </span><a href="/actreports/logout" class="signout">Sign Out</a></div>
</div>
</div>
<!--header end-->

<!--nav bar start-->
<div id="nav-bar">
<div class="container">
<div id="menu">
<ul>
<li style="cursor: pointer;"><a href="/actreports/goHome">Home</a></li>
<%-- <li id="dashboard_r"><a href="${pageContext.request.contextPath}/dashboard.html">Dash Board</a></li> --%>
<li id="allInvoices_r" style="cursor: pointer;"><a href="${pageContext.request.contextPath}/allInvoices.html">All Invoices</a></li>
<li id="accounting_r" style="cursor: pointer;"><a href="#">Accounting</a>
	<ul>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/accountsReceivableDetail.html">A/R Details</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/accountsReceivableDetailByAccType.html">A/R Detail By Account Type</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/accountReceivableSummary.html">A/R Summary</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/receipts.html">Receipts</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/missingPOs.html">Missing PO Number</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/billing.html">Billing</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/multipleInvoicePayment.html">Multiple Invoice Payment</a></li>
 	</ul>
</li>
<li id="commissions_r" style="cursor: pointer;"><a href="#">Commissions</a>
	<ul>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/singleDriverCommission.html">Single Driver Commission</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/driverCommissionSummary.html">Driver Commission Summary</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/driverSalesVsCommission.html">Driver Sales Vs Commission</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/customAllTows.html">Custom:Phoenix PD All Tows</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/customRelease.html">Custom:Phoenix PD Release</a></li>
	</ul>
</li>
<li id="storageManagement_r" style="cursor: pointer;"><a href="#">Storage Management</a>
<ul>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/storageInventory.html">Storage Inventory</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/auctionListInvoices.html">Auction List</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/noAbandonedVehicle.html">No AVR Filed</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/abandonedVehicle.html">Abandoned Vehicle Status Report</a></li>
          <!-- <li><a href="#">ACT Titled Vehicles</a></li> -->
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/viewVCR.html">View VCR Report</a></li>
        </ul>
</li>
<li id="summaries_r" style="cursor: pointer;"><a href="#">Summaries</a>
<ul>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/recap.html">Recap Report</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/summaryByAccountRatePlan.html">Summary By Account Rate Plan</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/summaryByDriver.html">Summary By Driver</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/summaryByReason.html">Summary By Reason Code</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/summaryByTowType.html">Summary By Tow Type</a></li>          
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/summaryByTruck.html">Summary By Truck</a></li>          
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/dispatchClear.html">Dispatch Clear Report</a></li>
          <li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/responseTime.html">Response Time Report</a></li>
        </ul>
</li>
<li id="audit_r" style="cursor: pointer;"><a href="#">Audit</a>
<ul>
     	<li style="cursor: pointer;"><a href="${pageContext.request.contextPath}/audit.html">Charges Audit</a></li>
</ul>
</li>
</ul>
</div>
</div>
</div>
<!--nav bar end-->
</body>
</html>