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
							<i class="icon_piechart"></i> TEE
						</h3>

					</div>
				</div>



				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<form id="consilatedTotal" action="${pageContext.request.contextPath}/getConsilatedTotal"
							method="post">
							<fieldset>
								<table id="consoleTable">
									<tr>
										<td>Sr No</td>
										<td>SAP ID</td>
										<td>Name</td>
										<td>ICE/${ratio}</td>
										<td>Term end out/100</td>
										<td>Term end out/${100-ratio}</td>
										<td>Total</td>
									</tr>

									<c:forEach items="${preList}" var="entry" varStatus="loop">
										<tr>
											<td>${loop.index+1}</td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="sapId${entry.sapId}" name="sapId${entry.sapId}"
														class="form-control notes" value="${entry.sapId}"
														readonly="readonly">
													<div class="input-group-addon"></div>
												</div></td>
											<td>${entry.name}</td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1" id="calc${entry.sapId}"
														name="calc${entry.sapId}" class="form-control notes"
														value="${entry.calc}" readonly="readonly">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1" id="tee${entry.sapId}"
														name="tee${entry.sapId}" class="form-control tee" value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td>
												<div class="input-group">
													<input type="text" data-tier-id="1"
														id="teeWeighted${entry.sapId}"
														name="teeWeighted${entry.sapId}"
														class="form-control notes" value="" readonly="readonly">
													<div class="input-group-addon"></div>
													<input type="hidden" id="teeratio" name="teeratio"
														value="${100-ratio}"> <input type="hidden"
														id="courseId" name="courseId" value="${courseId}">
												</div>
											</td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="total${entry.sapId}" name="total${entry.sapId}"
														class="form-control notes" value="" readonly="readonly">
													<div class="input-group-addon"></div>

												</div></td>
										</tr>
									</c:forEach>
								</table>
							</fieldset>
							Signature

							<div class="clearfix"></div>
							<div id="savedStatus"></div>
							<div class="form-group">
								<input type="button" id="submitForTee" value="Submit" />

							</div>
						</form>
						<div id="status"></div>
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

	});
</script>

<script>
	$(".tee").change(
			function() {

				var idOfCriteria = $(this).attr("id");
				var sapId = idOfCriteria.substring(3);
				var inputVal = $(this).val();
				$("#teeWeighted".concat(sapId)).val(
						(parseFloat(inputVal) / 100)
								* parseFloat($("#teeratio").val()));

				$("#total".concat(sapId)).val(
						parseFloat($("#calc".concat(sapId)).val())
								+ parseFloat($("#teeWeighted".concat(sapId))
										.val()));

			});

	$(document).ready(function() {
		$("#consilatedTotal").submit(function(e) {
			e.preventDefault();
		});

		$("#submitForTee").click(function() {

			var datastring = $("#consilatedTotal").serialize();
			$.ajax({
				type : "POST",
				url : "/saveConsilatedTotal",
				data : datastring,
				success : function(data) {
					$("#status").html(data);
				},
				error : function() {
					alert('Error here' + data);
				}
			});
		});
	});

	$("")
</script>


</html>
