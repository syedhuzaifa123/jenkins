<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>

<!-- jQuery first -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
	crossorigin="anonymous">

<!-- Custom Stylesheet -->
<link href="css/mystyle.css" rel="stylesheet" type="text/css">

<meta charset="UTF-8">
<title>Page Not Found</title>
</head>
<body>

	<div class="container text-center">
		<img alt="not found image" src="img/error.png">
		<h3 class="display-4">Sorry! Something Went Wrong</h3>
		<p><%=exception.getMessage()%></p>
		<a href="index.jsp" class="btn btn-standard btn-lg text-white mt-3">Home</a>



	</div>

</body>
</html>