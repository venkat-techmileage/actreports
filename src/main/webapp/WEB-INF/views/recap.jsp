<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="act.reports.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>All City Towing - StorageManagement - Recap Report</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/smoothmenu.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalPopLite1.3.0.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-datepicker.custom.min.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-datepicker.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/recap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.columnfilters.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/modalPopLite1.3.0.min.js"></script>
<script type="text/javascript">
	ddsmoothmenu.init({
		mainmenuid : "menu", //menu DIV id
		orientation : 'h', //Horizontal or vertical menu: Set to "h" or "v"
		classname : 'ddsmoothmenu', //class added to menu's outer DIV
		//customtheme: ["#1c5a80", "#18374a"],
		contentsource : "markup" //"markup" or ["container_id", "path_to_menu_file"]
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

#salesSummaryTable {
	margin-top: 20px;
	margin-bottom: 20px;
}

#impoundSummaryTable {
	margin-top: 20px;
	margin-bottom: 20px;
}

#receiptsSummaryTable {
	margin-top: 20px;
	margin-bottom: 40px;
}

#dispatchSummaryTable {
	margin-top: 20px;
	margin-bottom: 40px;
	margin-left: 60px;
}

#calculationsTable {
	margin-top: 20px;
	margin-bottom: 40px;
}
</style>
<!-- <script type="text/javascript">
var dateRangeDetails="${recapDetails.recapDateRangeDetails}";
var compareToDetails="${recapDetails.recapCompareToDetails}";
</script> -->
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
						<label>Date Range:&nbsp;</label> 
						<input type="text" id="dateRangeFrom" name="dateRangeFrom" value="${dateRangeFromDate}"/> 
						<label>To:</label> 
						<input type="text" id="dateRangeTo" name="dateRangeTo" value="${dateRangeToDate}"/> 
					</div>
					<div class="act-button-controls">
						<button type="submit" id="recapGo" class="blue-button">Go</button>
						<button type="submit" id="recapExport" class="green-center">Export To Excel</button>
					</div>
					<BR>
					<div class="act-calender-control">
						<label>Compare To:</label> 
						<input type="text" id="compareFrom" name="compareFrom" value="${compareToFromDate}"/> 
						<label>To:</label> 
						<input type="text" id="compareTo" name="compareTo" value="${compareToToDate}"/> 
					</div>
				</div>

				<div class="sales-suumary-table-box">
					<div id="showTables">
					<h3 align="center">
						<font color="green">Recap Report</font>
					</h3>
						<div class="all-invoice-full-wrapper-controls">
							<h4>From ${dateRangeFromDate} to ${dateRangeToDate}</h4>
							<h6>Today's Date:<%=DateUtility.getCurrentDate("MM/dd/yyyy")%></h6>
						</div>						
						<table width="100%">
							<tr>
							<td align="left">
								<b>Sales Summary</b>
								<table class="act-table" id="salesSummaryTable">									
									<thead>
										<tr>
											<th>Account Type</th>
											<th># of Tows</th>
											<th>* Total Charges</th>
											<th>*Charge Per Tow</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${recapDetails.recapSalesSummaryList}" var="recapSalesSummary">
											<tr>										
												<td><c:out value="${recapSalesSummary.accountType}"/></td>
												<td><c:out value="${recapSalesSummary.noOfTows}"/></td>
												<td><c:out value="${recapSalesSummary.totalCharges}"/></td>
												<td><c:out value="${recapSalesSummary.chargePerTow}"/></td>
											</tr>
										</c:forEach>
									</tbody>									
								</table>
								<p align="left">								
									<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Totals: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${recapDetails.salesSummaryTotalTows} &nbsp;&nbsp; $ ${recapDetails.salesSummaryTotalCharges}&nbsp;&nbsp; Avg Charge Per Tow: $ ${recapDetails.salesSummaryTotalCharges}</b>
								</p>
								<BR><BR>
								<b>Impound Summary</b>
								<table class="act-table" id="impoundSummaryTable">									
									<thead>
										<tr>
											<th></th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>*Tow Charges</td>
											<td>${recapDetails.impoundTowCharges}</td>
										</tr>
										<tr>
											<td>*Storage Charges</td>
											<td>${recapDetails.impoundStorageCharges}</td>
										</tr>
										<tr>
											<td>*Mileage Charges</td>
											<td>${recapDetails.impoundMileageCharges}</td>
										</tr>
										<tr>
											<td>*Other Charges</td>
											<td>${recapDetails.impoundOtherCharges}</td>
										</tr>
										<tr>
											<td>*Discounts</td>
											<td>- ${recapDetails.impoundDiscounts}</td>
										</tr>
										<tr>
											<td>*Total</td>
											<td>${recapDetails.totalImpoundCharges}</td>
										</tr>
									</tbody>
								</table>
								<p>
									<b>*released from impound only</b>
								</p>
								<BR><BR>
								<table>
									<tr>
										<td>
											<b>Receipts Summary</b>
											<table class="act-table" id="receiptsSummaryTable">												
												<thead>
													<tr>
														<th></th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>Cash</td>
														<td>${recapDetails.cashReceiptsTotal}</td>
													</tr>
													<tr>
														<td>Check</td>
														<td>${recapDetails.chequeReceiptsTotal}</td>
													</tr>
													<tr>
														<td>Credit</td>
														<td>${recapDetails.creditReceiptsTotal}</td>
													</tr>
													<tr>
														<td>Transfer</td>
														<td>${recapDetails.transferReceiptsTotal}</td>
													</tr>
													<tr>
														<td>Employee Advance</td>
														<td>${recapDetails.employeeAdvanceTotal}</td>
													</tr>
													<tr>
														<td>Total Receipts</td>
														<td>${recapDetails.totalReceipts}</td>
													</tr>
												</tbody>
											</table>

										</td>
										<td align="center">
											<b>Dispatch Summary</b>
											<table class="act-table" id="dispatchSummaryTable">												
												<thead>
													<tr>
														<th></th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>Total Calls</td>
														<td>${recapDetails.totalCalls}</td>
													</tr>
													<tr>
														<td>Total Completed Calls</td>
														<td>${recapDetails.totalCompletedCalls}</td>
													</tr>
													<tr>
														<td>Total Cancelled Calls</td>
														<td>${recapDetails.totalCancelledCalls}</td>
													</tr>
													<tr>
														<td>Total In Progress Calls</td>
														<td>${recapDetails.totalInProgressCalls}</td>
													</tr>
													<tr>
														<td>Total On Hold Calls</td>
														<td>${recapDetails.totalOnHoldCalls}</td>
													</tr>
													<tr>
														<td>Average Call To Arrival Time</td>
														<td>${recapDetails.avgCallToArrivalTime}</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td align="left" valign="top">
								<b>Calculations</b>
								<table class="act-table" id="calculationsTable">									
									<thead>
										<tr>
											<th></th>
											<th>Date Range</th>
											<th>Compare To</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>Released Per Day</td>
											<td>${recapDateRangeDetails.releasedPerDay}</td>
											<td>${recapCompareToDetails.diffInReleasedPerDay} %</td>
										</tr>
										<tr>
											<td>Added To Impound Per Day</td>
											<td>${recapDateRangeDetails.addedToImpoundPerDay}</td>
											<td>${recapCompareToDetails.diffInAddedToImpoundPerDay} %</td>
										</tr>
										<tr>
											<td>Charges Per Release</td>
											<td>${recapDateRangeDetails.chargesPerRelease}</td>
											<td>${recapCompareToDetails.diffInChargesPerRelease} %</td>
										</tr>
										<tr>
											<td>Storage Per Release</td>
											<td>${recapDateRangeDetails.storagePerRelease}</td>
											<td>${recapCompareToDetails.diffInStoragePerRelease} %</td>
										</tr>
										<tr>
											<td>Discount Per Release</td>
											<td>${recapDateRangeDetails.discountPerRelease}</td>
											<td>${recapCompareToDetails.diffInDiscountPerRelease} %</td>
										</tr>
										<tr>
											<td># Of AVR Sent</td>
											<td>${recapDateRangeDetails.noOfAVRSent}</td>
											<td>${recapCompareToDetails.diffInNoOfAVRSent} %</td>
										</tr>
										<tr>
											<td># Of Re-Hooks</td>
											<td>${recapDateRangeDetails.noOfReHooks}</td>
											<td>${recapCompareToDetails.diffInNoOfReHooks} %</td>
										</tr>
										<tr>
											<td># Of Salvage Titles From TOA</td>
											<td>${recapDateRangeDetails.noOfSalvageTitlesFromTOA}</td>
											<td>${recapCompareToDetails.diffInNoOfSalvageTitlesFromTOA} %</td>
										</tr>
										<tr>
											<td># Of Title Release</td>
											<td>${recapDateRangeDetails.noOfTitleReleases}</td>
											<td>${recapCompareToDetails.diffInNoOfTitleReleases} %</td>
										</tr>
										<tr>
											<td>Revenue Per Tow</td>
											<td>${recapDateRangeDetails.revenuePerTow}</td>
											<td>${recapCompareToDetails.diffInRevenuePerTow} %</td>
										</tr>
										<tr>
											<td>Call to Arrival Time(In Minutes)</td>
											<td>${recapDateRangeDetails.callToArrivalTime}</td>
											<td>${recapCompareToDetails.diffInCallToArrivalTime} %</td>
										</tr>
										<tr>
											<td>Dispatch TO Clear Time(In Minutes)</td>
											<td>${recapDateRangeDetails.dispatchToClearTime}</td>
											<td>${recapCompareToDetails.diffInDispatchToClearTime} %</td>
										</tr>
										<tr>
											<td>Fleet Utilization</td>
											<td>${recapDateRangeDetails.fleetUtilization}</td>
											<td>${recapCompareToDetails.diffInFleetUtilization} %</td>
										</tr>
										<tr>
											<td>Invoices</td>
											<td>${recapDateRangeDetails.invoices}</td>
											<td>${recapCompareToDetails.diffInInvoices} %</td>
										</tr>
										<tr>
											<td>Calls</td>
											<td>${recapDateRangeDetails.calls}</td>
											<td>${recapCompareToDetails.diffInCalls} %</td>
										</tr>
										<tr>
											<td>Cancelled Calls</td>
											<td>${recapDateRangeDetails.cancelledCalls}</td>
											<td>${recapCompareToDetails.diffInCancelledCalls} %</td>
										</tr>
										<tr>
											<td>Late Calls</td>
											<td>${recapDateRangeDetails.lateCalls}</td>
											<td>${recapCompareToDetails.diffInLateCalls} %</td>
										</tr>
										<tr>
											<td>Miles Per Tow Driven</td>
											<td>${recapDateRangeDetails.milesPerTowDriven}</td>
											<td>${recapCompareToDetails.diffInMilesPerTowDriven} %</td>
										</tr>
										<!-- <tr>
											<td>Miles Per Tow Gps Mapped</td>
											<td></td>
											<td>%</td>
										</tr> -->
										<tr>
											<td>Receipts</td>
											<td>${recapDateRangeDetails.receipts}</td>
											<td>${recapCompareToDetails.diffInReceipts} %</td>
										</tr>
										<tr>
											<td>Beginning Impound As Of ${recapDetails.beginingImpundAsOfDate}</td>
											<td>${recapDateRangeDetails.beginingImpoundAsOfDate}</td>
											<td>${recapCompareToDetails.diffInBeginingImpoundAsOfDate} %</td>
										</tr>
										<tr>
											<td>Added To Impound</td>
											<td>${recapDateRangeDetails.addedToImpound}</td>
											<td>${recapCompareToDetails.diffInBeginingImpoundAsOfDate} %</td>
										</tr>
										<tr>
											<td>Impound Released</td>
											<td>${recapDateRangeDetails.impoundReleased}</td>
											<td>${recapCompareToDetails.diffInImpoundReleased} %</td>
										</tr>
										<tr>
											<td>Ending Impound As Of ${recapDetails.endingImpundAsOfDate}</td>
											<td>${recapDateRangeDetails.endingImpundAsOfDate}</td>
											<td>${recapCompareToDetails.diffInEndingImpundAsOfDate} %</td>
										</tr>
										<tr>
											<td>Average A/R Aging</td>
											<td>$ ${recapDateRangeDetails.avgAccountReceivableAging}</td>
											<td>$ ${recapCompareToDetails.avgAccountReceivableAging}</td>
										</tr>
										<tr>
											<td>A/R Amount Outstanding</td>
											<td>$ ${recapDateRangeDetails.accountReceivableAmountOutstanding}</td>
											<td>$ ${recapCompareToDetails.accountReceivableAmountOutstanding}</td>
										</tr>
										<tr>
											<td>A/R Invoices Amount</td>
											<td>$ ${recapDateRangeDetails.accountReceivableInvoicesAmount}</td>
											<td>$ ${recapCompareToDetails.accountReceivableInvoicesAmount}</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
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