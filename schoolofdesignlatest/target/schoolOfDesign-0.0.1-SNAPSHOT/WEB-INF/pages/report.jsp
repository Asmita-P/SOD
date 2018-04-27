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

	<section id="container" class="">
		<!--header start-->
		<%@include file="top.jsp"%>

		<!--header end-->

		<!--sidebar start-->

		<%@include file="sidebar.jsp"%>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">
							<i class="icon_piechart"></i> Report
						</h3>

					</div>
				</div>
				<div class="row page-body">
					<div class="col-sm-12 column"></div>
				</div>
				<div class="row page-body">
					<div class="col-sm-12 column">

						<fieldset>


							<div class="row">
								<%-- <div class="col-sm-4 column">
									<div class="form-group">
										<label for="subject">Module Name</label> <select
											id="selectedCourse" name="courseId" class="form-control"
											required="required">
											<option value="">Please select Course</option>
											<c:forEach items="${courseData}" var="entry" varStatus="loop">
												<option value='<c:out value="${entry.key}"/>'>
													<c:out value="${entry.value}" /></option>
											</c:forEach>
										</select>
									</div>
								</div> --%>


								<div class="col-sm-4 column">
									<div class="form-group">
										<label for="semester">Select Semester</label> <select
											id="semester" class="form-control">
											<option value="">Select semester</option>
											<option value="Semester I">Semester I</option>
											<option value="Semester II">Semester II</option>
										</select>
									</div>
								</div>
								<div class="col-sm-4 column">
									<div class="form-group">
										<%-- <label for="subject">Module Name</label> <select
												id="courseName" name="courseId" class="form-control"
												required="required" onchange="getIcFacultyCourse(this)">
												<option value="">Please select Module</option>
												<c:forEach items="${courseData}" var="entry"
													varStatus="loop">
													<option value='<c:out value="${entry.key}"/>'>
														<c:out value="${entry.value}" /></option>
												</c:forEach>
											</select> --%>
										<label for="courseId">Module Name</label> <select
											id="selectedCourse" name="courseId" class="form-control"
											required="required">
											<option value="">Select Module</option>
											<c:forEach var="preAssigment" items="${preAssigments}"
												varStatus="status">
												<option value="${preAssigment.sap_course_id}">${preAssigment.course_Name}</option>
											</c:forEach>

										</select>






									</div>
								</div>


								<!-- 								<div class="col-sm-4 column"> -->
								<!-- 									<div class="form-group"> -->
								<!-- 										<label for="year">Year</label> <select -->
								<!-- 											id="selectedYear" name="year" class="form-control" -->
								<!-- 											required="required"> -->
								<!-- 											<option value="">Please select Year</option> -->
								<%-- 											<c:forEach items="${yearData}" var="entry" varStatus="loop"> --%>
								<%-- 												<option value='<c:out value="${entry.key}"/>'> --%>
								<%-- 													<c:out value="${entry.value}" /></option> --%>
								<%-- 											</c:forEach> --%>
								<!-- 										</select> -->
								<!-- 									</div> -->
								<!-- 								</div> -->
							</div>
						</fieldset>
			</section>
			<div class="clearfix"></div>
			<section class="container">



				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<fieldset>
							<table>
								<tr>
									<td>Report Name</td>
									<td>Format</td>
								</tr>
								<tr>
									<td><a
										href="${pageContext.request.contextPath}/downloadStudentWiseMapping">Year
											Student Wise ICA</a></td>
									<td>Excel</td>

								</tr>

								<tr>
									<td><a
										href="${pageContext.request.contextPath}/downloadStudentWiseMappingTee">Year
											Student Wise TEE</a></td>
									<td>Excel</td>

								</tr>
								<tr>
									<td><a
										href="${pageContext.request.contextPath}/pendingReport?format=PDF">Pending
											report</a></td>
									<td>PDF</td>
									<%-- <td><a
										href="${pageContext.request.contextPath}/pendingReport?format=PDF">Download</a> --%>
								</tr>

								<tr>
									<td><a id="icaConXls" href="">ICA consolidated</a></td>
									<td>Excel</td>
									<!-- <td><a id="icaConXls" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="icaCourseXls" href="">Course Wise ICA
											Report</a></td>
									<td>Excel</td>
									<!-- <td><a id="icaConXls" href="">Download</a> -->
								</tr>


								<!-- <tr>
									<td>ICA consolidated</td>
									<td>PDF</td>
									<td><a id="pdfIceCon" href="">Download</a>
								</tr>
								 -->
								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadIcaReportBulk?session=Semester I">Review
											Report(Sem1)</a></td>
									<td>Excel</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadIcaReportBulk?session=Semester I">Download</a> --%>
								</tr>

								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadIcaReportBulk?session=Semester II">Review
											Report(Sem2)</a></td>
									<td>Excel</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadIcaReportBulk?session=Semester II">Download</a> --%>
								</tr>

								<tr>
									<td><a id="sapIca1" href="">ICA Report SAP(Sem1)</a></td>
									<td>Excel</td>
									<!-- <td><a id="sapIca1" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="sapIca2" href="">ICA Report SAP(Sem2)</a></td>
									<td>Excel</td>
									<!-- <td><a id="sapIca2" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="saptee1" href="">TEE Report SAP(Sem1)</a></td>
									<td>Excel</td>
									<!-- <td><a id="saptee1" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="saptee2" href="">TEE Report SAP(Sem2)</a></td>
									<td>Excel</td>
									<!-- <td><a id="saptee2" href="">Download</a> -->
								</tr>


								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadIcaPdfGraphs?session=Semester I">ICA
											Bar chart Report(Sem1)</a></td>
									<td>Pdf files Zipped</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadIcaPdfGraphs?session=Semester I">Download</a> --%>
								</tr>

								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadIcaPdfGraphs?session=Semester II">ICA
											Bar chart Report(Sem2)</a></td>
									<td>Pdf files Zipped</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadIcaPdfGraphs?session=Semester II">Download</a> --%>
								</tr>

								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadAttributeMapGraphs?session=Semester I">Attribute
											Map chart Report ICA(Sem1)</a></td>
									<td>Pdf files Zipped</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadAttributeMapGraphs?session=Semester I">Download</a> --%>
								</tr>

								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadAttributeMapGraphs?session=Semester II">Attribute
											Map chart Report ICA(Sem2)</a></td>
									<td>Pdf files Zipped</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadAttributeMapGraphs?session=Semester II">Download</a> --%>
								</tr>
								
								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadAttributeMapGraphsForTee?session=Semester I">Attribute
											Map chart Report TEE(Sem1)</a></td>
									<td>Pdf files Zipped</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadAttributeMapGraphs?session=Semester I">Download</a> --%>
								</tr>

								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadAttributeMapGraphsForTee?session=Semester II">Attribute
											Map chart Report TEE(Sem2)</a></td>
									<td>Pdf files Zipped</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadAttributeMapGraphs?session=Semester II">Download</a> --%>
								</tr>

								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadMinMax?session=Semester I">Min
											Max report(Sem1)</a></td>
									<td>PDF</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadMinMax?session=Semester I">Download</a> --%>
								</tr>

								<tr>
									<td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadMinMax?session=Semester II">Min
											Max report(Sem2)</a></td>
									<td>PDF</td>
									<%-- <td><a id="reviewCon"
										href="${pageContext.request.contextPath}/downloadMinMax?session=Semester II">Download</a> --%>
								</tr>


								<tr>
									<td><a id="skillCon1" href="">Skill wise total Line
											chart(Sem1)</a></td>
									<td>PDF</td>
									<!-- <td><a id="skillCon1" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="skillCon2" href="">Skill wise total Line
											chart(Sem 2)</a></td>
									<td>PDF</td>
									<!-- <td><a id="skillCon2" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="teeMark1" href="">Internal TEE Marksheet
											(Sem1)</a></td>
									<td>Excel</td>
									<!-- <td><a id="teeMark1" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="teeMark2" href="">Internal TEE Marksheet
											(Sem2)</a></td>
									<td>Excel</td>
									<!-- <td><a id="teeMark2" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="extTeeMark1" href="">External TEE Marksheet
											(Sem1)</a></td>
									<td>Excel</td>
									<!-- <td><a id="extTeeMark1" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="extTeeMark2" href=""> TEE Marksheet (Sem2)</a></td>
									<td>Excel</td>
									<!-- <td><a id="extTeeMark2" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="intextSem1" href="">Internal External TEE
											Marksheet (Sem1)</a></td>
									<td>Excel</td>
									<!-- <td><a id="intextSem1" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="intextSem2" href="">Internal External TEE
											Marksheet (Sem2)</a></td>
									<td>Excel</td>
									<!-- <td><a id="intextSem2" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="classWise1" href="">Class Wise Marksheet
											(Sem1)</a></td>
									<td>Excel</td>
									<!-- <td><a id="classWise1" href="">Download</a> -->
								</tr>



								<tr>
									<td><a id="classWise2" href="">Class Wise Marksheet
											(Sem2)</a></td>
									<td>Excel</td>
									<!-- <td><a id="classWise2" href="">Download</a> -->
								</tr>

								<tr>
									<td><a id="classWise1"
										href="${pageContext.request.contextPath}/downloadStudentWiseReport?session=Semester I">Student
											Wise Mark Sheet (Sem1)</a></td>
									<td>PDF</td>
									<%-- <td><a id="classWise1"
										href="${pageContext.request.contextPath}/downloadStudentWiseReport?session=Semester I">Download</a> --%>
								</tr>

								<tr>
									<td><a id="classWise2"
										href="${pageContext.request.contextPath}/downloadStudentWiseReport?session=Semester II">Student
											Wise Mark Sheet (Sem2)</a></td>
									<td>PDF</td>
									<%-- <td><a id="classWise2"
										href="${pageContext.request.contextPath}/downloadStudentWiseReport?session=Semester II">Download</a> --%>
								</tr>


							</table>
							<div class="table-responsive" id="showData"></div>
							<br>
						</fieldset>
					</div>
				</div>


			</section>
</body>



<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$('#selectedCourse')
								.on(
										'change',
										function() {
											//alert("Trigger called"
											//+ $(this).val());
											var str = "${pageContext.request.contextPath}/getIceXlsx?courseId="
													+ $(this).val()
													+ "&format=xlsx";
											console.log(str);

											var strCourse = "${pageContext.request.contextPath}/getIceCourseXlsx?courseId="
													+ $(this).val()
													+ "&format=xlsx";
											//$("#iceCon").attr("href", str);

											var pdfstr = "${pageContext.request.contextPath}/getIcePdf?courseId="
													+ $(this).val()
													+ "&format=pdf";
											console.log(pdfstr);

											var d = new Date();
											var n = d.getFullYear();

											var skillCon1Str = "${pageContext.request.contextPath}/downloadMappingDescLineChart?session=Semester I&courseId="
													+ $(this).val();

											var skillCon2Str = "${pageContext.request.contextPath}/downloadMappingDescLineChart?session=Semester II&courseId="
													+ $(this).val()

											var sapIca1Str = "${pageContext.request.contextPath}/downloadIcaSap?session=Semester I&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var sapIca2Str = "${pageContext.request.contextPath}/downloadIcaSap?session=Semester II&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var saptee1Str = "${pageContext.request.contextPath}/downloadTeeSap?session=Semester I&year="
													+ n
													+ "&courseId="
													+ $(this).val();

											var saptee2Str = "${pageContext.request.contextPath}/downloadTeeSap?session=Semester II&year="
													+ n
													+ "&courseId="
													+ $(this).val();

											var tee1Str = "${pageContext.request.contextPath}/downloadTeeMarkSheetBlank?session=Semester I&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var tee2Str = "${pageContext.request.contextPath}/downloadTeeMarkSheetBlank?session=Semester II&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var exttee1Str = "${pageContext.request.contextPath}/downloadTeeMarkSheetBlankForExternal?session=Semester I&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var exttee2Str = "${pageContext.request.contextPath}/downloadTeeMarkSheetBlankForExternal?session=Semester II&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var intext1Str = "${pageContext.request.contextPath}/downloadInternalExternalTee?session=Semester I&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var intext2Str = "${pageContext.request.contextPath}/downloadInternalExternalTee?session=Semester II&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var class1Str = "${pageContext.request.contextPath}/downloadClassWise?session=Semester I&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											var class2Str = "${pageContext.request.contextPath}/downloadClassWise?session=Semester II&year="
													+ n
													+ "&courseId="
													+ $(this).val()

											$("#pdfIceCon")
													.attr("href", pdfstr);

											$("#skillCon1").attr("href",
													skillCon1Str);

											$("#skillCon2").attr("href",
													skillCon2Str);

											$("#sapIca1").attr("href",
													sapIca1Str);

											$("#sapIca2").attr("href",
													sapIca2Str);

											$("#saptee1").attr("href",
													saptee1Str);

											$("#saptee2").attr("href",
													saptee2Str);

											$("#icaConXls").attr("href", str);
											$("#icaCourseXls").attr("href",
													strCourse);
											$("#teeMark1")
													.attr("href", tee1Str);
											$("#teeMark2")
													.attr("href", tee2Str);

											$("#extTeeMark1").attr("href",
													exttee1Str);
											$("#extTeeMark2").attr("href",
													exttee2Str);
											$("#classWise1").attr("href",
													class1Str);
											$("#classWise2").attr("href",
													class2Str);
											$("#intextSem1").attr("href",
													intext1Str);
											$("#intextSem2").attr("href",
													intext2Str);

										})

						//$('#selectedCourse').trigger('change');
					});
</script>
<script>
	$(document)
			.ready(
					function() {

						$("#semester")
								.on(
										'change',
										function() {
											var semesterid = $(this).val();
											if (semesterid) {
												$
														.ajax({
															type : 'GET',
															url : '${pageContext.request.contextPath}/getCourseBySemester?'
																	+ 'semesterid='
																	+ semesterid,
															success : function(
																	data) {
																var json = JSON
																		.parse(data);
																var optionsAsString = "";

																$(
																		'#selectedCourse')
																		.find(
																				'option')
																		.remove();
																console
																		.log(json);
																for (var i = 0; i < json.length; i++) {
																	var idjson = json[i];
																	console
																			.log(idjson);

																	for ( var key in idjson) {
																		console
																				.log(key
																						+ ""
																						+ idjson[key]);
																		optionsAsString += "<option value='" +key + "'>"
																				+ idjson[key]
																				+ "</option>";
																	}
																}
																console
																		.log("optionsAsString"
																				+ optionsAsString);

																$(
																		'#selectedCourse')
																		.append(
																				optionsAsString);

																$(
																		'#selectedCourse')
																		.trigger(
																				'change');

															}
														});
											

											} else {
												alert('Error no course');
											}
										});

					});
</script>
</html>
