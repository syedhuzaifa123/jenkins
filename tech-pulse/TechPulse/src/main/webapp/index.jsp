<%@page import="com.tech.pulse.entities.Post"%>
<%@page import="java.util.List"%>
<%@page import="com.tech.pulse.entities.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tech.pulse.dao.PostDao"%>
<%@page import="com.tech.pulse.helper.ConnectionProvider"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
	crossorigin="anonymous">

<!-- Font Awesome (For icons) -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Custom Stylesheet -->
<link href="css/mystyle.css" rel="stylesheet" type="text/css">

<style>
.banner-bg {
	clip-path: polygon(30% 0%, 70% 0%, 100% 0, 100% 92%, 65% 100%, 33% 92%, 0 100%, 0 0
		);
}
</style>

<title>Homepage</title>
</head>
<body>

	<%@include file="includes/navbar_normal.jsp"%>

	<div class="container-fluid secondary-bg text-white banner-bg">
		<div class="container py-5">
			<h3 class="display-3">Welcome to TechPulse,</h3>
			<p>Tech Pulse is a dynamic blog platform focused on the latest
				trends in technology, coding, and software development.</p>
			<p>It empowers developers with insightful tutorials, news, and
				hands-on project guides.</p>

			<a href="register.jsp" class="btn btn-outline-light"> <span
				class="fa fa-user-plus me-2"></span>Start Free
			</a> <a href="login.jsp" class="btn btn-outline-light"> <span
				class="fa fa-user me-2"></span>Login
			</a>
		</div>
	</div>

	<!-- Cards Section -->
	<div class="container py-5">

		<div class="row">
			<%
			PostDao postDao = new PostDao(ConnectionProvider.getConnection());
			List<Post> postList = postDao.getAllPost();

			for (Post post : postList) {
			%>

			<div class="col-md-3 mb-4">
				<div class="card custom-card card-uniform">

					<!-- Fixed-height image container -->
					<div class="card-img-container">
						<img src="blog_pics/<%=post.getPhoto()%>" class="card-img-top">
					</div>

					<div class="card-body">
						<h5 class="card-title"><%=post.getTitle()%></h5>
						<p class="card-text">
							<%=post.getContent()%>
						</p>
						<a href="#" class="btn primary-background text-white mt-auto">Read
							More</a>
					</div>

				</div>
			</div>

			<%
			}
			%>
		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
		crossorigin="anonymous"></script>

</body>
</html>
