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
						<h2 class="page-header" style="color: #A9A9A9">&nbsp;&nbsp;CREATE ICA</h2>

					</div>
				</div>
			</section>

			<section class="container">
				<form id="createIc"
					action="${pageContext.request.contextPath}/createIcJson"
					method="post">
					
					<div class="row page-body">
						<legend> &nbsp;&nbsp;&nbsp;Select Module to create ICA </legend>
						<div class="col-sm-12 column">

							<div class="row">
								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="subject">ICA Name</label><input type="text"
											name="iceName" value="" class="form-control" />
									</div>
								</div>
								<div class="col-sm-3 column">
									<div class="form-group">


										<label for="subject">Module Name</label> <select
											id="courseName" name="courseId" class="form-control"
											required="required">
											<option value="">Please select Module</option>
											<c:forEach items="${courseData}" var="entry" varStatus="loop">
												<option value='<c:out value="${entry.key}"/>'>
													<c:out value="${entry.value}" /></option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="col-sm-3 column">
									<div class="form-group">
										<label for="subject">Assign Faculty</label> <select
											id="facultyName" name="facultyName" class="form-control">
											<option value="">Please select Faculty</option>
											<c:forEach items="${facultyData}" var="entry"
												varStatus="loop">
												<option value='<c:out value="${entry.key}"/>'>
													<c:out value="${entry.value}" /></option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>

						</div>
					</div>

					<div class="row clearfix page-body">

						<div class="col-sm-12 column">
							<fieldset>
								<legend id="generateIce">&nbsp;ICA</legend>
								<div class="table-responsive">
									<table class="table table-striped table-hover"
										style="font-size: 12px">
										<thead>
											<tr>
												<th>Criteria</th>
												<th>Name</th>
												<th>Weighted Value</th>
												<th>Skill</th>


											</tr>
										</thead>
										<tbody>

											<c:forEach items="${criteria}" var="entry" varStatus="loop">
												<tr>
													<td>${loop.index+1}</td>
													<td><input type="text" name="${entry.key}" SIZE="20"></input></td>

													<td><input type="text" class="weightageClass"
														id="weightage" name="${entry.value}" SIZE="20"></input></td>
													<td><select name="skill${loop.index+1}"
														class="form-control" required="required">
															<c:forEach items="${skillData}" var="skill">
																<option value='<c:out value="${skill}"/>'>
																	<c:out value="${skill}" /></option>
															</c:forEach>
													</select></td>

												</tr>
											</c:forEach>
										</tbody>
									</table>

									<input type="button" id="createIcButton" name="createIcButton"
										value="Create ICA" class="btn btn-large btn-primary">
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

function isNumber(n) {
	if (!isNaN(parseFloat(n)) && isFinite(n)) {
		var value = parseFloat(n);
		if (value >= 0 && value <= 100.0000) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
	$(document).ready(function() {

		$("#createIcButton").click(function() {

			sumOfWeightage = 0;
			$(".weightageClass").each(function() {
				if(!isNumber($(this).val())){
					alert('Some mark entered is not a number or not in range. Please check again');
					return false;
				}
				sumOfWeightage = sumOfWeightage + parseInt($(this).val());

			});
			if (sumOfWeightage != 100) {
				alert("Weightage has to be 100");
				/* $(".weightageClass").each(function() {
					$(this).val('');
				}); */
				return false;
			}

			var cf = confirm("Warning! Do you really want to submit?");
			if (cf == true) {
				$("#createIc").submit();
			} else {

			}

			/* $("#createIc").submit(function(e) {
				e.preventDefault();

			}); */
			//var datastring = $("#createIc").serialize();
			/* $.ajax({
				type : "POST",
				url : "/createIcJson",
				data : datastring,
				success : function(data) {
					$("#generateIce").html(data);
					sumOfWeightage = 0;
				},
				error : function() {
					alert('Error here');
				}
			});
			 */
		});
	});
</script>
</html>
