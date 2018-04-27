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

					</div>
				</div>



				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<fieldset>
							<table id="consoleTable">
								<tr>
									<td rowspan="2">Sr No</td>
									<td rowspan="2">SAP ID</td>
									<td rowspan="2">Name</td>
									<c:forEach var="i" begin="1" end="${iceCount}">
										<td>Ice Id ${i}</td>
									</c:forEach>
									<td>out/${total}</td>
									<td>ICE/${ratio}</td>
								</tr>
								<tr>

									<c:forEach var="i" begin="1" end="${iceCount}">
										<td>out/100</td>
									</c:forEach>

								</tr>


								<c:forEach items="${preList}" var="entry" varStatus="loop">

									<tr>
										<td>${loop.index}</td>
										<td>${entry.sapId}</td>
										<td>${entry.name}</td>
										<c:forEach items="${entry.iceMarks}" var="mapEntry">
											<td>${mapEntry.value}</td>
										</c:forEach>
										<td>${entry.scored}</td>
										<td>${entry.calc}</td>
									</tr>

								</c:forEach>
							</table>
						</fieldset>
						Signature
						<div class="clearfix"></div>
						<div id="savedStatus"></div>
						<div class="form-group">
							<a href="${reportUrl}">Download report</a>

						</div>

					</div>
				</div>
			</section>




		</section>
		<!--main content end-->

	</section>
	<%@include file="footer.jsp"%>

</body>



</html>
