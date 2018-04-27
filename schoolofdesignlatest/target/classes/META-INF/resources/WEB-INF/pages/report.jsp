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

	<section id="container" class="">
		<!--header start-->
		<%@include file="top.jsp"%>

		<!--header end-->

		<!--sidebar start-->

		<%@include file="sidebar.jsp"%>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">
							<i class="icon_piechart"></i> Report
						</h3>

					</div>
				</div>
				<div class="row page-body">
					<div class="col-sm-12 column"></div>
				</div>
				<div class="row page-body">
					<div class="col-sm-12 column">

						<fieldset>


							<div class="row">
								<div class="col-sm-4 column">
									<div class="form-group">
										<label for="subject">Course Name</label> <select
											id="selectedCourse" name="courseId" class="form-control"
											required="required">
											<option value="">Please select Course</option>
											<c:forEach items="${courseData}" var="entry" varStatus="loop">
												<option value='<c:out value="${entry.key}"/>'>
													<c:out value="${entry.value}" /></option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</fieldset>
			</section>
			<div class="clearfix"></div>
			<section class="container">



				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<fieldset>
							<table>
								<tr>
									<td>Report Name</td>
									<td>Format</td>
									<td>Link</td>
								</tr>

								<tr>
									<td>Pending report</td>
									<td>PDF</td>
									<td><a href="${pageContext.request.serverName}${pageContext.request.contextPath}/pendingReport?format=PDF">Download</a>
								</tr>

								<tr>
									<td>ICE consolidated</td>
									<td>XLS</td>
									<td><a id="iceCon" href="">Download</a>
								</tr>


							</table>
							<div class="table-responsive" id="showData"></div>
							<br>
						</fieldset>
					</div>
				</div>


			</section>
</body>



<script type="text/javascript">
	$(document).ready(function() {

		$('#selectedCourse').on('change', function() {
			var str = "${pageContext.request.contextPath}/getIceXlsx?courseId=" + $(this).val() + "&format=xlsx";
			console.log(str);
			$("#iceCon").attr("href", str);

		})
	});
</script>

</html>
