<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.schoolOfDesign.jforce.beans.icbeans.*"%>
<html lang="en">
<%@include file="header.jsp"%>

<body>
	<!--sidebar start-->
	<aside>
		<div id="sidebar" class="nav-collapse "  >
			<!-- sidebar menu start-->
			<ul class="sidebar-menu">
				<%
					UserBean userBean = (UserBean) session.getAttribute("user");
					String userRole = userBean.getRoleName();

					if ("cordinator".equals(userRole) || "authority".equals(userRole)
							|| "faculty".equals(userRole)) {
				%>
				<li class="sub-menu"><a class="" href="${pageContext.request.serverName}${pageContext.request.contextPath}/iceList">  <span>ICE List</span>
				</a></li>
				<li class="sub-menu"><a class="" href="${pageContext.request.serverName}${pageContext.request.contextPath}/preSelect">  <span>Create ICE</span>
				</a></li>
				<%
					}
				%>
				<%
					if ("cordinator".equals(userRole) || "authority".equals(userRole)) {
				%>
				<li class="sub-menu"><a href="${pageContext.request.serverName}${pageContext.request.contextPath}/preConsole" class=""> <span>Consolidated ICE</span>
				</a></li>

				<li class="sub-menu"><a href="${pageContext.request.serverName}${pageContext.request.contextPath}/uploadStudent" class=""> <span> Student Upload </span>
				</a></li>
				<li class="sub-menu"><a href="${pageContext.request.serverName}${pageContext.request.contextPath}/uploadFaculty" class="">  <span> Faculty Upload </span>
				</a></li>
				<li class="sub-menu"><a href="${pageContext.request.serverName}${pageContext.request.contextPath}/uploadCourse" class="">  <span> Course Upload </span>
				</a></li>
				<li class="sub-menu"><a href="${pageContext.request.serverName}${pageContext.request.contextPath}/uploadStudentCourseFaculty"
					class="">  <span>
							Student/Faculty Upload </span>
				</a></li>
				<%
					}
				%>
				<%
					if ("cordinator".equals(userRole) || "authority".equals(userRole)
							|| "exam".equals(userRole)) {
				%>
				<li class="sub-menu"><a href="${pageContext.request.serverName}${pageContext.request.contextPath}/preTee" class="">  <span>ICE TEE Dashboard</span>
				</a></li>
				<li class="sub-menu"><a href="${pageContext.request.serverName}${pageContext.request.contextPath}/report" class="">  <span>Reports</span>
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
