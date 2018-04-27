<!DOCTYPE html>
<html lang="en">
<%@include file="header.jsp"%>



<body>
	<!-- container section start -->

	<section id="container" class="">
		<!--header start-->



		<%@include file="top.jsp"%>


		<%@include file="sidebar.jsp"%>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h2 class="page-header" style="color: #A9A9A9">Map Faculty
							with Module</h2>

					</div>
				</div>
			</section>

			<section class="container">
				<form id="teeForm"
					action="${pageContext.request.contextPath}/addFacultyCourse"
					method="post">

					<div class="row page-body">
						<legend> &nbsp;&nbsp;Select Faculty and Module </legend>
						<div class="col-sm-12 column">

							<div class="row">
								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="sap_id">Faculty</label> <select id="sap_id"
											name="sap_id" required="required" class="form-control">
											<option value="">Select Faculty</option>
											<c:forEach var="course" items="${facultyList}"
												varStatus="status">
												<option value="${course.sap_id}">${course.faculty_name}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="sap_course_id">Module Name</label> <select
											id="sap_course_id" name="sap_course_id" required="required"
											class="form-control">
											<option value="">Select Module Name</option>
											<c:forEach var="course" items="${allCourses}"
												varStatus="status">
												<option value="${course.sap_course_id}">${course.course_name}</option>
											</c:forEach>
										</select>
									</div>
								</div>

							</div>
							<button type="submit" formaction="addFacultyCourse"
								class="btn btn-large btn-primary">Add Faculty</button>
							<button id="reset" type="reset" class="btn btn-danger">Reset</button>


						</div>

					</div>


				</form>


				<br>
			</section>
		</section>
		<%@include file="modal.jsp"%>

	</section>


</body>

<%@include file="footer.jsp"%>

</html>