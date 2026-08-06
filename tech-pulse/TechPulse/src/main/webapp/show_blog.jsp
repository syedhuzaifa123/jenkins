<%@page import="com.tech.pulse.dao.LikedDao"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.tech.pulse.dao.UserDao"%>
<%@page import="com.tech.pulse.entities.Post"%>
<%@page import="java.util.List"%>
<%@page import="com.tech.pulse.helper.ConnectionProvider"%>
<%@page import="com.tech.pulse.dao.PostDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page errorPage="error_page.jsp"%>

<%
int postId;
Post post;
LikedDao ld;
// User login check
if (session.getAttribute("currentUser") == null) {
	response.sendRedirect("login.jsp");
	return;
} else {

	ld = new LikedDao(ConnectionProvider.getConnection());

	postId = Integer.parseInt(request.getParameter("pid"));
	PostDao postDao = new PostDao(ConnectionProvider.getConnection());
	post = postDao.getPostById(postId);
}
%>

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

<title>TechPulse - <%=post.getTitle()%></title>

</head>
<body>

	<%@include file="includes/navbar_login.jsp"%>

	<div class="container">

		<div class="row my-4">

			<div class="col-md-6 offset-md-3">

				<div class="card">

					<div class="card-header card-header-custom">
						<h5 class="post-title"><%=post.getTitle()%></h5>
					</div>
					<div class="card-body">

						<img src="blog_pics/<%=post.getPhoto()%>"
							class="card-img-top my-2">

						<div class="row mt-1">
							<div class="col-md-8 post-user">
								<%
								UserDao userDao = new UserDao(ConnectionProvider.getConnection());
								User u = userDao.getUserByUserId(post.getUserId());
								%>
								<p>
									Posted by: <a href="#!"><%=u.getName()%></a>
								</p>
							</div>
							<div class="col-md-4 post-date">
								<p><%=DateFormat.getDateTimeInstance().format(post.getDate())%></p>
							</div>
							<hr>
						</div>


						<p class="post-content">
							<%=post.getContent()%>
						</p>
						<br> <br>

						<div class="post-code">
							<pre><%=post.getCode()%></pre>
						</div>

					</div>
					<div
						class="card-footer d-flex justify-content-between align-items-center secondary-bg">
						<div class="d-flex align-items-center">
							<a href="#!" class="btn btn-sm card-footer-custom"
								onclick="doLike(<%=post.getId()%>, <%=user.getId()%>)"><i
								class="fa <%=ld.isLikedByUser(post.getId(), user.getId()) ? "fa-thumbs-up white-thumb" : "fa-thumbs-o-up"%>"
								id="likeBtnIcon<%=post.getId()%>" data-like-color="white-thumb"></i>
								<span class="likeCounter" id="likeCounter<%=post.getId()%>">
									<%=ld.countLikeOnPost(post.getId())%></span></a> <a href="#!"
								class="btn btn-sm card-footer-custom"><i
								class="fa fa-commenting-o"></i><span>17</span></a> <a href="#!"
								class="btn btn-sm card-footer-custom"><i class="fa fa-share"></i><span>
									5</span></a>

						</div>

					</div>

				</div>

			</div>

		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.7.1.min.js"
		integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
		crossorigin="anonymous"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
		crossorigin="anonymous"></script>

	<script src="js/myjs.js"></script>
</body>
</html>