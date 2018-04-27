<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@include file="header.jsp"%>


<style>
table, th, td {
	border: 1px solid #ddd;
	text-align: left;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	padding: 15px;
}
</style>

<body>
	<!-- container section start -->

	<section id="container" class="">
		<!--header start-->

		<%@include file="top.jsp"%>
		<!--header end-->

		<!--sidebar start-->
		<%@include file="sidebar.jsp"%>S

		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">
							<i class="icon_piechart"></i> Upload Course
						</h3>

					</div>
				</div>



				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<fieldset>

							<form action="/uploadCourseMaster" method="post"
								enctype="multipart/form-data">
								<label>Select the operation</label> <select name="operation"
									class="form-control" required="required">
									<c:forEach items="${operations}" var="oper">
										<option value='<c:out value="${oper}"/>'>
											<c:out value="${oper}" /></option>
									</c:forEach>
								</select> <br /> <input type="file" name="file" size="50" /> <br /> <input
									type="submit" value="Upload File" />

							</form>



						</fieldset>

						<div class="clearfix"></div>
						<div id="savedStatus"></div>
						<div class="form-group"></div>

					</div>
				</div>
			</section>




		</section>
		<!--main content end-->

	</section>
	<%@include file="footer.jsp"%>

</body>

<script type="text/javascript">
	$(document).ready(function() {

		$("#exportConsolePdf").click(function() {
			$('#consoleTable').tableExport({
				type : 'pdf',
				pdfFontSize : '7',
				escape : 'false'
			});
		});

		$("#exportConsoleXls").click(function() {
			$('#consoleTable').tableExport({
				type : 'xls',
				pdfFontSize : '7',
				escape : 'false'
			});
		});
	});
</script>


</html>
