<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Create Ic page</title>
</head>
<body>


	<form action="${pageContext.request.contextPath}/createIc" method="post">
		<label>Select Course Name</label> <select name="courseId">
			<c:forEach items="${courseData}" var="entry" varStatus="loop">
				<option value='<c:out value="${entry.key}"/>'>
					<c:out value="${entry.value}" /></option>
			</c:forEach>
		</select> <br /> <br /> <input type="submit" name="submit"
			" value="Create IC"></input>

	</form>



</body>
</html>