<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">



<body>
	<header class="header dark-bg" style="height:80px;">
		<div class="toggle-nav">
			<div class="icon-reorder tooltips"
				data-original-title="Toggle Navigation" data-placement="bottom">
				<!-- <i class= href="index.html"></i> -->
			</div>
		</div>

		<!--logo start-->
		<img src="${pageContext.request.contextPath}/resources/images/logoSOD.png" height="80px" width="190px"
                              alt="Logo" style="margin-left: -30px;">
		<!--logo end-->



		<div class="top-nav notification-row">
			<!-- notificatoin dropdown start-->
			<ul class="nav pull-right top-menu">

				<!-- task notificatoin start -->


				<!-- user login dropdown start-->

				<li class="dropdown "><a data-toggle="dropdown"
					class="dropdown-toggle" href="#"> <span class="profile-ava">
					</span> <span class="username"><c:out
								value="${sessionScope.user.username}" /></span> <b class="caret"></b>
				</a>
					<ul class="dropdown-menu extended logout">
						<div class="log-arrow-up"></div>

						<li class="eborder-top"><a
							href="${pageContext.request.contextPath}/logout"><i
								class="icon_profile"></i> Logout</a></li>

					</ul></li>
				<!-- user login dropdown end -->
			</ul>
			<!-- notificatoin dropdown end-->
		</div>
	</header>
	<!--header end-->
</html>
