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
						<h2 class="page-header" style="color: #A9A9A9">&nbsp;&nbsp;CREATE TEE</h2>

					</div>
				</div>
			</section>

			<section class="container">
				<form id="teeForm"
					action="${pageContext.request.contextPath}/createTEEJson"
					method="post">

					<div class="row page-body">
						<legend> Select Module to create TEE </legend>
						<div class="col-sm-12 column">

							<div class="row">

								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="subject">Module Name</label> <select id="courseId"
											name="courseName" class="form-control" required="required">
											<option value="">Please select Course</option>
											<c:forEach items="${courseData}" var="entry" varStatus="loop">
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
											<option value="">Please select type</option>
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

						</div>

					</div>
					</div>

					<div class="row clearfix page-body">

						<div class="col-sm-12 column">
							<fieldset>
								<legend id="generateIce">&nbsp;TEE</legend>
								<div class="table-responsive">
									<table class="table table-striped table-hover"
										style="font-size: 12px">
										<thead>
											<tr>
												<th>Criteria</th>
												<th>Name</th>
												<th>Weightage</th>
												<th>Skill</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach var="i" begin="1" end="4">
												<tr>

													<td>${i}</td>
													<td><input type="text" name="criteriaDesc${i}"
														SIZE="20"></input></td>

													<td><input type="text" class="weightageClass"
														id="weightage" name="weightage${i}" SIZE="20"></input></td>
													<td><select name="skill${i}" class="form-control"
														required="required">
															<c:forEach items="${skillData}" var="skill">
																<option value='<c:out value="${skill}"/>'>
																	<c:out value="${skill}" /></option>
															</c:forEach>
													</select></td>

												</tr>
											</c:forEach>
										</tbody>
									</table>

									<input type="button" id="createTEEButton"
										name="createTEEButton" value="Create TEE"
										class="btn btn-large btn-primary">
									<button id="reset" type="reset" class="btn btn-danger">Reset</button>

								</div>
							</fieldSet>
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
<script type="text/javascript">
	$(document).ready(function() {

		$("#createTEEButton").click(function() {

			sumOfWeightage = 0;
			$(".weightageClass").each(function() {
				sumOfWeightage = sumOfWeightage + parseInt($(this).val());

			});
			if (sumOfWeightage != 100) {
				alert("Weightage has to be 100");

				return false;
			}

			var cf = confirm("Warning! Do you really want to submit?");
			if (cf == true) {
				$("#teeForm").submit();
			} else {

			}

		});
	});
</script>

</html>