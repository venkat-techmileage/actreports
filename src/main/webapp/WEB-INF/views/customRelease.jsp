<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - Commisssions - Custom Release </title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/customRelease.js" type="text/javascript"></script>
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
			   			<div class="act-calender-control">
							<label>From:</label> 
							<input type="text" id="fromDate" name="fromDate" value="${fromDate}"/>
						</div>
                             <div class="act-calender-control">
							<label>To:</label> 
							<input type="text" id="toDate" name="toDate" value="${toDate}"/>
						</div>
			   			<input type="text" class="act-input-search-big" name="searchQuery" value="${accountName}" placeholder="Account" id="accountName"/>												
						<div class="act-button-controls">
				        	<button type="submit" id="customReleaseGo" class="blue-button">Go</button>
							<button type="submit" id="customReleaseExport" class="green-center">Export ToExcel</button>
						</div><br/>	
						<span id='inputError' style='color:red;display: none'></span>						
					</div>
				</div>						
				<div class="sales-suumary-table-box">
					<h3 align="center"><font color="green">Release Report</font></h3>
					<fieldset>											
						<div class="all-invoice-full-wrapper-controls">
							<h4> ${accountName}</h4>							
					         <h6>All City Towing</h6>					
						</div>						
						<div class="sales-sum-table-wrapper-box">
							<div class="sales-sum-table-wrapper">
							<div style="overflow:scroll">
								<table class="act-table" id="customReleaseTable" style="overflow:scroll">
									<thead>
										<tr>
											<th>ID #</th>
											<th>Contract #</th>
											<th>Storage Report #</th>
                                            <th>Year</th>					
											<th>Make</th>
											<th>Model</th>
                                            <th>VIN</th>
                                            <th>Precinct</th>					
											<th>Impound Date</th>
											<th>Released Date</th>
											<th>Released To</th>
											<th>Tow Charge</th>
											<th># Of Storage Days</th>
                                            <th>Storage Charge</th>					
											<th>After Hours Gate Fee</th>
											<th>Standby Fee</th>
                                            <th>Supervisor Fee</th>
                                            <th>Refrig Fee</th>					
											<th>Outside City</th>
											<th>Dry Run Fee</th>
											<th>PCC 36 Charge</th>
											<th>Level</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${customReleaseDetailsList}" var="customReleaseDetails">
										<tr>										
											<td><c:out value="${customReleaseDetails.id}"/></td>
											<td><c:out value="${customReleaseDetails.contract}"/></td>
											<td><c:out value="${customReleaseDetails.storageReport}"/></td>
											<td><c:out value="${customReleaseDetails.year}"/></td>
											<td><c:out value="${customReleaseDetails.make}"/></td>
											<td><c:out value="${customReleaseDetails.model}"/></td>
											<td><c:out value="${customReleaseDetails.vin}"/></td>
											<td><c:out value="${customReleaseDetails.precinct}"/></td>
											<td><c:out value="${customReleaseDetails.impoundDate}"/></td>
											<td><c:out value="${customReleaseDetails.releasedDate}"/></td>
											<td><c:out value="${customReleaseDetails.releasedTo}"/></td>
											<td><c:out value="${customReleaseDetails.towCharge}"/></td>
											<td><c:out value="${customReleaseDetails.ofStorageDays}"/></td>
										     <td><c:out value="${customReleaseDetails.storageCharge}"/></td>
											<td><c:out value="${customReleaseDetails.afterHoursGateFee}"/></td>
									        <td><c:out value="${customReleaseDetails.standByFee}"/></td>
									        <td><c:out value="${customReleaseDetails.supervisorFee}"/></td>
											<td><c:out value="${customReleaseDetails.refrigFee}"/></td>
											<td><c:out value="${customReleaseDetails.outSideCity}"/></td>
											<td><c:out value="${customReleaseDetails.dryRunFee}"/></td>
									        <td><c:out value="${customReleaseDetails.pccCharge}"/></td>
									        <td><c:out value="${customReleaseDetails.level}"/></td>
									        
								       </tr>
									</c:forEach>
										
									</tbody>
								</table>
								</div>
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