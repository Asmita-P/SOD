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

.container {
	position: relative;
}

.nav {
	position: fixed;
	z-index: 500;
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
				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						Course name : ${courseName}<br /> Year : ${year}<br /> Sem :
						${sem}<br /> ICA Name:${icName} <br />

						<form id="grade"
							action="${pageContext.request.contextPath}/gradeSubmit"
							method="post">
							<input type="hidden" name="icId" value="${icId}"></input>
							<fieldset>

								<table id="fixedhd1">
									<thead>
										<tr>

											<td rowspan="2"><b>Sr No</b></td>
											<td rowspan="2"><b>SAP ID</b></td>
											<td rowspan="2"><b>Roll No.</b></td>
											<td rowspan="2"><b>Name</b></td>
											<td><b>Criteria</b></td>
											<!-- 										<td><b>Criteria 1</b></td> -->
											<!-- 										<td><b>Criteria 2</b></td> -->
											<!-- 										<td><b>Criteria 3</b></td> -->
											<!-- 										<td><b>Criteria 4</b></td> -->
											<c:forEach items="${tableheader}" var="entry"
												varStatus="loop">
												<td>${entry}</td>
											</c:forEach>
											<td><b>Total</b></td>
											<!-- <td><b>Remarks</b></td> -->

											<!-- 										 -->
										</tr>



										<%-- <tr>
										<c:forEach items="${tableheader}" var="h" varStatus="loop">
											<c:if test="${loop.index < 3}">
												<td rowspan="2">${h}</td>
											</c:if>
											<c:if test="${loop.index >= 3}">
											</c:if>
											<td>${h}</td>
										</c 
									:forEach></tr>--%>

										<tr>

											<td>Weightage</td>
											<c:forEach items="${weightage}" var="entry" varStatus="loop">
												<td id="c${loop.index+1}">${entry}</td>
											</c:forEach>
											<td>100</td>
										</tr>

									</thead>
									<tbody>
										<c:forEach items="${mrkList}" var="entry" varStatus="loop">

											<tr>
												<td rowspan="2">${loop.index+1}</td>
												<td rowspan="2">${entry.sapId}</td>
												<td rowspan="2">${entry.rollNo}</td>
												<td rowspan="2">${entry.name}</td>
												<td>out of 100</td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="criteria1${entry.sapId}"
															name="criteria1${entry.sapId}" class="form-control notes"
															value="${entry.criteria_1_marks}">

													</div></td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="criteria2${entry.sapId}"
															name="criteria2${entry.sapId}" class="form-control notes"
															value="${entry.criteria_2_marks}">

													</div></td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="criteria3${entry.sapId}"
															name="criteria3${entry.sapId}" class="form-control notes"
															value="${entry.criteria_3_marks}">

													</div></td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="criteria4${entry.sapId}"
															name="criteria4${entry.sapId}" class="form-control notes"
															value="${entry.criteria_4_marks}">

													</div></td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="total${entry.sapId}" name="total${entry.sapId}"
															class="form-control notes" value="${entry.criteriaTotal}"
															readonly="readonly"> <input type="hidden"
															id="didChange${entry.sapId}"
															name="didChange${entry.sapId}" value="false">

													</div></td>


												<%-- <td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="remarks12${entry.sapId}" name="remarks${entry.sapId}"
															class="form-control notes" value="${entry.remarks}">
														<input type="hidden" id="didChange${entry.sapId}"
															name="didChange${entry.sapId}" value="false">

													</div></td> --%>

											</tr>

											<tr>


												<td>Weightage</td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="output1${entry.sapId}" name="output1${entry.sapId}"
															class="form-control notes" value="${entry.weightage_1}"
															readonly="readonly">

													</div></td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="output2${entry.sapId}" name="output2${entry.sapId}"
															class="form-control notes" value="${entry.weightage_2}"
															readonly="readonly">

													</div></td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="output3${entry.sapId}" name="output3${entry.sapId}"
															class="form-control notes" value="${entry.weightage_3}"
															readonly="readonly">

													</div></td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="output4${entry.sapId}" name="output4${entry.sapId}"
															class="form-control notes" value="${entry.weightage_4}"
															readonly="readonly">

													</div></td>
												<td><div class="input-group">
														<input type="text" data-tier-id="1"
															id="weighted${entry.sapId}" name="weighted${entry.sapId}"
															class="form-control" value="${entry.weightedTotal}"
															readonly="readonly">

													</div></td>
												<%-- 	<td><div class="input-group">
														<input type="text" data-tier-id="1" hidden="true"
															id="remarks12${entry.sapId}" name="remarks${entry.sapId}"
															class="form-control" value=""
															readonly="readonly">

													</div></td> --%>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</fieldset>
							<div class="clearfix"></div>
							<div id="savedStatus"></div>
							<div class="form-group">
								<input type="button" id="saveas" value="Save as Draft" /> <input
									type="button" id="genId" value="Submit" />
								<!-- <button class="btn btn-danger" id="genId">GENERATE</button>-->
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
	

	$(".notes")
			.change(
					function() {
						//alert("On change called")

						var idOfCriteria = $(this).attr("id");
						//alert("idOfCriteria ------ "+idOfCriteria)

						var sumOfWeightages = 0;
						var total = 0;
						var multipliedValue;
						var sapId = idOfCriteria.substring(9);

						$("#didChange".concat(sapId)).val("true");
						
						//alert("Value is -"+$("#didChange".concat(sapId)).val())
						/* if (idOfCriteria.startsWith('criteria1')) {
							
						} */

						if (idOfCriteria.startsWith('criteria1')) {
							if (!isNumber($(this).val())
									|| !isNumber($("#c1").text())) {
								alert('Some mark entered is not a number or not in range. Please check again');
								return;
							}

							multipliedValue = multiply($(this).val(), $("#c1")
									.text());
							var str = "#output1".concat(sapId);
							//alert('Multiplied Value ------- '+Math.round(multipliedValue / 100))
							$(str).val(Math.round(multipliedValue / 100));
						}
						if (idOfCriteria.startsWith('criteria2')) {
							if (!isNumber($(this).val())
									|| !isNumber($("#c2").text())) {
								alert('Some mark entered is not a number or not in range. Please check again');
								return;
							}
							multipliedValue = multiply($(this).val(), $("#c2")
									.text());
							var str = "#output2".concat(sapId);
							$(str).val(Math.round(multipliedValue / 100));
						}
						if (idOfCriteria.startsWith('criteria3')) {
							if (!isNumber($(this).val())
									|| !isNumber($("#c3").text())) {
								alert('Some mark entered is not a number or not in range. Please check again');
								return;
							}
							multipliedValue = multiply($(this).val(), $("#c3")
									.text());
							var str = "#output3".concat(sapId);
							$(str).val(Math.round(multipliedValue / 100));
						}
						if (idOfCriteria.startsWith('criteria4')) {
							if (!isNumber($(this).val())
									|| !isNumber($("#c4").text())) {
								alert('Some mark entered is not a number or not in range.. Please check again');
								return;
							}
							multipliedValue = multiply($(this).val(), $("#c4")
									.text());
							var str = "#output4".concat(sapId);
							$(str).val(Math.round(multipliedValue / 100));
						}

						sumOfWeightages = performsumOfWeightagesCalc(sapId);

						total = performtotalCalc(sapId);
						if (!isNaN(sumOfWeightages))
							$("#weighted".concat(sapId)).val(sumOfWeightages);
						if (!isNaN(total))
							$("#total".concat(sapId)).val(total);
					});

	function performsumOfWeightagesCalc(sapId) {
		var sumOfWeightages = parseFloat($("#output1" + sapId).val())
				+ parseFloat($("#output2".concat(sapId)).val())
				+ parseFloat($("#output3".concat(sapId)).val())
				+ parseFloat($("#output4".concat(sapId)).val());
		//alert('This is sumOfWeightages------------'+Math.round(sumOfWeightages))
		//return sumOfWeightages;
		return Math.round(sumOfWeightages);

	}

	function performtotalCalc(sapId) {
		var total = parseFloat($("#criteria1".concat(sapId)).val())
				+ parseFloat($("#criteria2".concat(sapId)).val())
				+ parseFloat($("#criteria3".concat(sapId)).val())
				+ parseFloat($("#criteria4".concat(sapId)).val());
		//alert('This is total---------- '+Math.round(total))
		return total;
	}

	function multiply(x, y) {
		var one = parseFloat(x);
		var two = parseFloat(y);
		return one * two;
	}

	$("")
</script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						/* $('#fixedhd1').DataTable( {
						    fixedHeader: true
						} ); */

						$("#genId")
								.click(
										function() {

											var cf = confirm("Warning! Ensure grading of all students has been done?");
											if (cf == true) {
												$("#grade").submit();
											} else {

											}

										});

						$("#saveas")
								.click(
										function() {
											//alert("here");
											var datastring = $("#grade")
													.serialize();
											$
													.ajax({
														type : "POST",
														url : "${pageContext.request.contextPath}/saveasdraft",
														data : datastring,
														success : function(data) {
															$("#savedStatus")
																	.html(data);
														},
														error : function() {
															alert('Error here');
														}
													});
										});
					});
</script>


</html>
