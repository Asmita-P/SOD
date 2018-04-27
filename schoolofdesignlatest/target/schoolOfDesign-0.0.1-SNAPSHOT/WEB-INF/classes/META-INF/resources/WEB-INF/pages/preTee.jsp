<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@include file="header.jsp"%>

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
							<i class="icon_piechart"></i> ICE
						</h3>

					</div>
				</div>
				<div class="row page-body">
					<div class="col-sm-12 column">
						<form id="console" action="/getConsilatedTotal" method="post">
							<fieldset>
								<legend> Consolidate report </legend>

								<div class="row">
									<div class="col-sm-4 column">
										<div class="form-group">
											<label for="subject">Course Name</label> <select
												id="courseName" name="courseId" class="form-control"
												required="required">
												<option value="">Please select Course</option>
												<c:forEach items="${courseData}" var="entry"
													varStatus="loop">
													<option value='<c:out value="${entry.key}"/>'>
														<c:out value="${entry.value}" /></option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-sm-4 column">
										<div class="form-group">
											<label for="teeType">Select TEE Type</label> <select
												id="teeType" name="teeType" class="form-control"
												required="required">
												<option value="">Please select Type</option>
												<option value="Internal">Internal</option>
												<option value="External">External</option>

											</select>
										</div>
									</div>
									<div class="col-sm-4 column">
										<div class="form-group">
											<label for="tee_percent">Enter Percentage</label> <input
												name="tee_percent" type="number" class="form-control"
												required="required" min="0" step="0.01" />
										</div>
									</div>


								</div>
								<div class="col-sm-12 column">
									<div class="form-group">
										<input type="submit" name="submit"
											class="btn btn-large btn-primary"
											value="Get consolidated report">
										<button id="reset" type="reset" class="btn btn-danger">Reset</button>

									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>

			</section>
			<div class="clearfix"></div>
			<section class="container">
				<div id="grid_array"></div>


				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<fieldset>

							<div class="table-responsive" id="showData"></div>
							<br>
						</fieldset>
					</div>
				</div>

			</section>
</body>

<%@include file="footer.jsp"%>

</html>
