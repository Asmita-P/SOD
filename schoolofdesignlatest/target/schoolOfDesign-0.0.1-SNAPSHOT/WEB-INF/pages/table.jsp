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
							<i class="icon_piechart"></i> ICA
						</h3>
						<ol class="breadcrumb">
							<li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
							<li><i class="icon_piechart"></i>ICA</li>

						</ol>
					</div>
				</div>


				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<form id="addAnnouncement" action="${pageContext.request.contextPath}/grade">
							<fieldset>
								<table>
									<tr>
										<td rowspan="2">Sr No</td>
										<td rowspan="2">SAP ID</td>
										<td rowspan="2">Name</td>
										<td>Criteria</td>
										<td>Criteria 1</td>
										<td>Criteria 2</td>
										<td>Criteria 3</td>
										<td>Criteria 4</td>
										<td>Total</td>
									</tr>
									<tr>

										<td>Weightage</td>
										<c:forEach items="${weightage}" var="entry" varStatus="loop">
											<td id="c{loop.index+1}">${entry}</td>
										</c:forEach>
										<td>100</td>
									</tr>
									<c:forEach items="${mrkList}" var="entry" varStatus="loop">

										<tr>
											<td rowspan="2">${loop.index}</td>
											<td rowspan="2"><input type="text"
												id="sapId${loop.index}" value="entry.sapId"
												readonly="readonly"></input></td>
											<td rowspan="2">${entry.name}</td>
											<td>out of 100</td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="criteria1${entry.sapId}"
														name="criteria1${entry.sapId}" class="form-control notes"
														value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="criteria2${entry.sapId}"
														name="criteria2${entry.sapId}" class="form-control notes"
														value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="criteria3${entry.sapId}"
														name="criteria3${entry.sapId}" class="form-control notes"
														value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="criteria4${entry.sapId}"
														name="criteria4${entry.sapId}" class="form-control notes"
														value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td>100</td>

										</tr>

										<tr>

											<td></td>
											<td></td>
											<td>Weightage</td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output1${entry.sapId}" name="output1${entry.sapId}"
														class="form-control notes" value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output2${entry.sapId}" name="output2${entry.sapId}"
														class="form-control notes" value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output3${entry.sapId}" name="output3${entry.sapId}"
														class="form-control notes" value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output4${entry.sapId}" name="output4${entry.sapId}"
														class="form-control notes" value="">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output5${entry.sapId}" name="output5${entry.sapId}"
														class="form-control notes" value="">
													<div class="input-group-addon"></div>
												</div></td>

										</tr>
									</c:forEach>




								</table>
							</fieldset>
							<div class="clearfix"></div>
							<div class="form-group">
								<!-- <input type="button" id="genId" value="GENERATE"/> -->
								<button class="btn btn-danger" id="genId">GENERATE</button>
							</div>
						</form>
					</div>
				</div>


			</section>




		</section>
		<!--main content end-->

	</section>
	<%@include file="footer.jsp"%>

</body>
<script>
	$(".notes").change(
			function() {

				var idOfCriteria = $(this).attr("id");
				var sumOfWeightages = 0;
				var multipliedValue;
				if (idOfCriteria.startsWith('criteria1')) {
					multipliedValue = multiply($(this).val(), $("#c1").text());
					$("#output1").html(multipliedValue / 100);
				}
				if (idOfCriteria == 'criteria2') {
					multipliedValue = multiply($(this).val(), $("#c2").text());
					$("#outputC2").html(multipliedValue / 100);
				}
				if (idOfCriteria == 'criteria3') {
					multipliedValue = multiply($(this).val(), $("#c3").text());
					$("#outputC3").html(multipliedValue / 100);
				}
				if (idOfCriteria == 'criteria4') {
					multipliedValue = multiply($(this).val(), $("#c4").text());
					$("#outputC4").html(multipliedValue / 100);
					sumOfWeightages = parseInt($("#outputC1").text)
							+ parseInt($("#outputC2").text)
							+ parseInt($("#outputC3").text)
							+ parseInt($("#outputC4").text)
							+ alert(sumOfWeightages);
					$("totalWeightage").html(sumOfWeightages);
				}
			});

	function multiply(x, y) {
		var one = parseInt(x, 10);
		var two = parseInt(y, 10);
		return one * two;
	}

	$("")
</script>


</html>
