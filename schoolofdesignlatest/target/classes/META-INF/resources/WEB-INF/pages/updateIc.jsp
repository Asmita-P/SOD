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
						<h2 class="page-header">
							<img
								src="https://cdn4.iconfinder.com/data/icons/web-pages-seo/512/4-512.png"
								alt="Smiley face" width="42" height="42"> ICE
						</h2>
						<ol class="breadcrumb">
						
							<li><img
								src="https://cdn4.iconfinder.com/data/icons/web-pages-seo/512/4-512.png"
								alt="Smiley face" width="42" height="42"></i>ICE</li>

						</ol>
					</div>
				</div>
			</section>

			<section class="container">
				<form id="updateIc">


					<div class="row clearfix page-body">

						<div class="col-sm-12 column">
							<fieldset>
								<legend id="generateIce">&nbsp;ICE ID ${icid}</legend>
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
											<input type="hidden" name="icId" value="${icid}"></input>
											<c:forEach items="${iceCriteriaList}" var="entry"
												varStatus="loop">
												<tr>
													<td>${loop.index+1}</td>
													<td><input type="text"
														name="criteriaDesc${loop.index+1}"
														value="${entry.criteria_desc}" SIZE="20"></input></td>

													<td><input type="text" name="weightage${loop.index+1}"
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
										value="Create ICE" class="btn btn-large btn-primary">Update
										ICE</button>
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
		$("#updateIc").submit(function(e) {
			e.preventDefault();
		});

		$("#updateIcButton").click(function() {
			alert("here");
			var datastring = $("#updateIc").serialize();
			$.ajax({
				type : "POST",
				url : "/updateIcJson",
				data : datastring,
				success : function(data) {
					$("#generateIce").html(data);
				},
				error : function() {
					alert('Error here');
				}
			});
		});
	});
</script>
</html>
