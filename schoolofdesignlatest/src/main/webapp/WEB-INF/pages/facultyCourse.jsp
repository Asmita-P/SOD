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
						<h2 class="page-header" style="color: #A9A9A9"> &nbsp;&nbsp;&nbsp;Map Faculty
							with Module</h2>

					</div>
				</div>
			</section>

			<section class="container">
				<form id="teeForm"
					action="${pageContext.request.contextPath}/addFacultyCourse"
					method="post">

					<div class="row page-body">
						<legend>&nbsp;&nbsp; Select Faculty and Module </legend>
						<div class="col-sm-12 column">

							<div class="well">
								

								${status}

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