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
				<div class="row"></div>
			</section>

			<section class="container">
				<form id="updateIc">


				<div class="row">
							

							<div class="col-sm-7 column">
								<fieldset>
									<legend id="generateIce">&nbsp;ICA ID ${icid} <label for="subject">&nbsp;&nbsp;&nbsp;&nbsp;<b>ICA Name</b></label> <input type="text"
										name="iceName" value="${iceName}" /></legend>
									<div class="table-responsive">
										<table class="table table-striped table-hover"
											style="font-size: 12px">
											<thead>
												<tr>
													<th>Criteria</th>
													<th>Name</th>
													<th>Weight</th>
													<th>Skill</th>


												</tr>
											</thead>
											<tbody>
												<input type="hidden" name="icId" value="${icid}"></input>
												<c:forEach items="${iceCriteriaList}" var="entry"
													varStatus="loop">
													<tr>
														<td>${loop.index+1}</td>
														<td><input type="text"
															name="criteriaDesc${loop.index+1}"
															value="${entry.criteria_desc}" SIZE="30"></input></td>

														<td><input type="text"
															name="weightage${loop.index+1}" class="weightageClass"
															value="${entry.weightage}" SIZE="20"></input></td>
														<td><select name="skill${loop.index+1}"
															class="form-control" required="required">

																<c:forEach items="${skillData}" var="skill">
																	<c:choose>
																		<c:when test="${skill eq entry.mapping_desc}">
																			<option selected value='${skill}'>
																				<c:out value="${skill}" /></option>
																		</c:when>
																		<c:otherwise>
																			<option value='${skill}'>
																				<c:out value="${skill}" /></option>
																		</c:otherwise>
																	</c:choose>


																</c:forEach>
														</select></td>

													</tr>
												</c:forEach>
											</tbody>
										</table>

										<button id="updateIcButton" name="updateIcButton"
											value="Create ICA" class="btn btn-large btn-primary">Update
											ICA</button>
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
		$("#updateIc").submit(function(e) {
			e.preventDefault();
		});

		var sumOfWeightage;

		$("#updateIcButton").click(function() {

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
				var datastring = $("#updateIc").serialize();
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/updateIcJson",
					data : datastring,
					success : function(data) {
						$("#generateIce").html(data);
					},
					error : function() {
						alert('Error here');
					}
				});
			} else {

			}

		});
	});
</script>
</html>
