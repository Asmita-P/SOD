<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@include file="header.jsp"%>


<style>
table, th, td {
	border: 1px solid #ddd;
	text-align: left;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	padding: 15px;
}
</style>

<body>
	<!-- container section start -->

	<section id="container" class="">
		<!--header start-->

		<%@include file="top.jsp"%>
		<!--header end-->

		<!--sidebar start-->
		<%@include file="sidebar.jsp"%>S

		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">
							<i class="icon_piechart"></i> ICE
						</h3>
						<ol class="breadcrumb">
							<li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
							<li><i class="icon_piechart"></i>ICE</li>

						</ol>
					</div>
				</div>


				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<table>
							<tr>
								<td>ICE Id</td>
								<td>${icBean.id}</td>
							</tr>
							<tr>
								<td>Owner faculty</td>
								<td>${icBean.owner_faculty}</td>
							</tr>
							<tr>
								<td>Assigned faculty</td>
								<td>${icBean.assigned_faculty}</td>
							</tr>
							<tr>
								<td>Course</td>
								<td>${course}</td>
							</tr>
							<tr>
								<td>Submitted date</td>
								<td>${subDate}</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<fieldset>
							<table>
								<tr>
									<td rowspan="2">Sr No</td>
									<td rowspan="2">SAP ID</td>
									<td rowspan="2">Name</td>
									<td>Criteria</td>
									<td>Criteria 1</td>
									<td>Criteria 2</td>
									<td>Criteria 3</td>
									<td>Criteria 4</td>
									<td>Total</td>
								</tr>
								<%-- <tr>
										<c:forEach items="${tableheader}" var="h" varStatus="loop">
											<c:if test="${loop.index < 3}">
												<td rowspan="2">${h}</td>
											</c:if>
											<c:if test="${loop.index >= 3}">
											</c:if>
											<td>${h}</td>
										</c 
									:forEach></tr>--%>

								<tr>

									<td>Weightage</td>
									<c:forEach items="${weightage}" var="entry" varStatus="loop">
										<td id="c${loop.index+1}">${entry}</td>
									</c:forEach>
									<td>100</td>
								</tr>
								<c:forEach items="${mrkList}" var="entry" varStatus="loop">

									<tr>
										<td rowspan="2">${loop.index}</td>
										<td rowspan="2">${entry.sapId}</td>
										<td rowspan="2">${entry.name}</td>
										<td>out of 100</td>
										<td>${entry.criteria_1_marks}</td>
										<td>${entry.criteria_2_marks}</td>
										<td>${entry.criteria_3_marks}</td>
										<td>${entry.criteria_4_marks}</td>
										<td>${entry.criteriaTotal}</td>
									</tr>
									<tr>

										<td>Weightage</td>
										<td>${entry.weightage_1}</td>
										<td>${entry.weightage_2}</td>
										<td>${entry.weightage_3}</td>
										<td>${entry.weightage_4}</td>
										<td>${entry.weightedTotal}</td>

									</tr>
								</c:forEach>
							</table>
						</fieldset>
						Signature
						<div class="clearfix"></div>
						<div id="savedStatus"></div>
						<div class="form-group">
							<input type="button" id="print" value="PRINT" />
							<!-- <button class="btn btn-danger" id="genId">GENERATE</button>-->
						</div>

					</div>
				</div>
			</section>




		</section>
		<!--main content end-->

	</section>
	<%@include file="footer.jsp"%>

</body>

<script type="text/javascript">
	$(document).ready(function() {

		$("#print").click(function() {
			window.print();
		});
	});
</script>


</html>
