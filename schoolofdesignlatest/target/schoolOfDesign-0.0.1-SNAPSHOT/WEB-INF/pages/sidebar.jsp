<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.schoolOfDesign.jforce.beans.icbeans.*"%>
<html lang="en">
<%@include file="header.jsp"%>
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet">
<body>
	<!--sidebar start-->
	<aside>
		<div id="sidebar" class="nav-collapse ">
			<!-- sidebar menu start-->
			<ul class="sidebar-menu">
				<%
					UserBean userBean = (UserBean) session.getAttribute("user");
					String userRole = userBean.getRoleName();

					if ("cordinator".equals(userRole) || "authority".equals(userRole)
							|| "faculty".equals(userRole)) {
				%>
				<li class="sub-menu"><a class=""
					href="${pageContext.request.contextPath}/iceList"> <span>ICA
							List</span>
				</a></li>
				<li class="sub-menu"><a class=""
					href="${pageContext.request.contextPath}/preSelect"> <span>Create
							ICA</span>
				</a></li>

				<%
					}
				%>

				<%
					if ("cordinator".equals(userRole) || "authority".equals(userRole)
							|| "faculty".equals(userRole) || "exam".equals(userRole)) {
				%>

				<li class="sub-menu"><a class=""
					href="${pageContext.request.contextPath}/teeList"> <span>TEE
							List</span>
				</a></li>
				<li class="sub-menu"><a class=""
					href="${pageContext.request.contextPath}/preTee"> <span>Create
							TEE</span>
				</a></li>

				<%
					}
				%>
				<%
					if ("cordinator".equals(userRole) || "authority".equals(userRole)) {
				%>
				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/preConsole" class="">
						<span>Consolidated ICA</span>
				</a></li>

				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/uploadStudent" class="">
						<span> Student Upload </span>
				</a></li>
				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/uploadFaculty" class="">
						<span> Faculty Upload </span>
				</a></li>
				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/uploadCourse" class="">
						<span> Course Upload </span>
				</a></li>
				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/uploadStudentCourseFaculty"
					class=""> <span> Student/Faculty Upload </span>
				</a></li>
				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/preDeleteIca" class="">
						<span>Delete ICA</span>
				</a></li>
				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/addExternalFacultyForm"
					class=""> <span>Add External Faculty</span></a></li>



				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/addFacultyCourseForm"
					class=""> <span>Assign Course to Faculty</span></a></li>
				<%
					}
				%>
				<%
					if ("cordinator".equals(userRole) || "authority".equals(userRole)
							|| "exam".equals(userRole)) {
				%>
				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/iceTeeDashBoard" class="">
						<span>ICA TEE Dashboard</span>
				</a></li>
				<li class="sub-menu"><a
					href="${pageContext.request.contextPath}/report" class=""> <span>Reports</span>
				</a></li>
				<%
					}
				%>
			</ul>
			<!-- sidebar menu end-->
		</div>
	</aside>

	<!--sidebar end-->
</html>
