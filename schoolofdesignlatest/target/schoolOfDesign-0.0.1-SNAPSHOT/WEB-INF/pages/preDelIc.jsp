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
						<h2 class="page-header" style="color: #A9A9A9">Delete ICA</h2>

					</div>
				</div>
			</section>

			<section class="container">
				<form action="${pageContext.request.contextPath}/deleteIca"
					method="post">

					<div class="row page-body">
						<legend> Select ICA to delete</legend>
						<div class="col-sm-12 column">

							<div class="row">
								${message}<br />
								<div class="col-sm-3 column">
									<div class="form-group">


										<label for="subject">ICA Name</label> <select id="iceId"
											name="iceId" class="form-control" required="required">
											<option value="">Please select Ica to delete</option>
											<c:forEach items="${icaData}" var="entry" varStatus="loop">
												<option value='<c:out value="${entry.key}"/>'>
													<c:out value="${entry.key}" /></option>
											</c:forEach>
										</select>
									</div>
								</div>


							</div>

						</div>
					</div>
					<input type="submit" name="Delete" value="Delete" /> <input type="reset"
						name="Reset" />
					<div class="row clearfix page-body"></div>
				</form>


				<br>
			</section>
		</section>


	</section>


</body>

<%@include file="footer.jsp"%>


</html>
