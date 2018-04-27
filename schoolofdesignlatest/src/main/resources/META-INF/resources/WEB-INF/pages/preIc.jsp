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
						<h2 class="page-header" style="color:	#A9A9A9">
							 CREATE ICE
						</h2>

					</div>
				</div>
			</section>

			<section class="container">
				<form id="createIc">
					<div class="row page-body">
						<div class="col-sm-12 column">
							<legend> Select Course to create ICE </legend>

							<div class="row">
								<div class="col-sm-4 column">
									<div class="form-group">
										<label for="subject">Course Name</label> <select
											id="courseName" name="courseId" class="form-control"
											required="required">
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
										<label for="subject">Faculty Name</label> <select
											id="facultyName" name="facultyName" class="form-control"
											required="required">
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
								<legend id="generateIce">&nbsp;ICE</legend>
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

											<c:forEach items="${criteria}" var="entry" varStatus="loop">
												<tr>
													<td>${loop.index+1}</td>
													<td><input type="text" name="${entry.key}" SIZE="20"></input></td>

													<td><input type="text" class="weightageClass" id="weightage" name="${entry.value}" SIZE="20"></input></td>
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

									<button id="createIcButton" name="createIcButton"
										value="Create ICE" class="btn btn-large btn-primary">Create
										IC</button>
									<button id="reset" type="reset" class="btn btn-danger">Reset</button>
								</div>
							</fieldSet>
						</div>
					</div>
				</form>


				<br>
			</section>
		</section>
		<!--main content end-->

	</section>
</body>

<%@include file="footer.jsp"%>

<script type="text/javascript">

	

$(document).ready(function() {
      

      $("#createIcButton").click(function() {
            sumOfWeightage = 0;
            $(".weightageClass").each(function() {
                  sumOfWeightage = sumOfWeightage + parseInt($(this).val());

            });
            if (sumOfWeightage != 100) {
                  alert("Weightage has to be 100");
                  $(".weightageClass").each(function() {
                        $(this).val('');
                  });
                  return false;
            }
            alert("Successful");
            $("#createIc").submit(function(e) {
                  e.preventDefault();

            });
            var datastring = $("#createIc").serialize();
            $.ajax({
                  type : "POST",
                  url : "/createIcJson",
                  data : datastring,
                  success : function(data) {
                        $("#generateIce").html(data);
                        sumOfWeightage = 0;
                  },
                  error : function() {
                        alert('Error here' + data);
                  }
            });

      });
});

</script>
</html>
