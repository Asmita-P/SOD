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
						<h2 class="page-header" style="color: #A9A9A9">Add External
							Faculty</h2>

					</div>
				</div>
			</section>

			<section class="container">
				<form id="teeForm"
					action="${pageContext.request.contextPath}/addExternalFaculty"
					method="post">

					<div class="row page-body">
						<legend> &nbsp;&nbsp;Add External Faculty </legend>
						<div class="col-sm-12 column">

							<div class="row">

								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="sap_id">Enter SAP ID</label> <input name="sap_id"
											type="number" class="form-control" required="required"
											min="0" />
									</div>
								</div>
								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="faculty_name">Enter Name</label> <input
											name="faculty_name" type="text" class="form-control"
											required="required" />
									</div>
								</div>
								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="vendor_code">Enter vendor code</label> <input
											name="vendor_code" type="text" class="form-control" />
									</div>
								</div>
							</div>
							<div class="row">

								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="faculty_phone_no">Enter Mobile Number</label> <input
											name="faculty_phone_no" type="number" class="form-control"
											min="0" />
									</div>
								</div>
								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="faculty_email_id">Enter Email Id</label> <input
											name="faculty_email_id" type="text" class="form-control"
											required="required" />
									</div>
								</div>
								
							</div>
							
							
							<button type="submit" formaction="addExternalFaculty"
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