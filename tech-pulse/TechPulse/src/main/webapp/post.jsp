<%@page import="com.tech.pulse.dao.LikedDao"%>
<%@page import="com.tech.pulse.entities.User"%>
<%@page import="com.tech.pulse.entities.Post"%>
<%@page import="java.util.List"%>
<%@page import="com.tech.pulse.helper.ConnectionProvider"%>
<%@page import="com.tech.pulse.dao.PostDao"%>

<div class="row mb-2">

	<%
	Thread.sleep(1000);

	User user = (User) session.getAttribute("currentUser");
	LikedDao ld = new LikedDao(ConnectionProvider.getConnection());

	PostDao postDao = new PostDao(ConnectionProvider.getConnection());
	List<Post> posts = null;
	int catId = Integer.parseInt(request.getParameter("cid"));

	if (catId == 0) {
		posts = postDao.getAllPost();
	} else {
		posts = postDao.getPostByCategory(catId);
	}

	if (posts.size() == 0) {
	%>
	<h6 class='display-6'>No post available against this category</h6>
	<%
	return;
	}

	for (Post p : posts) {
	%>

	<div class="col-md-6 mb-3">
		<div class="card card-uniform">

			<!-- Fixed-height image container -->
			<div class="card-img-container">
				<img src="blog_pics/<%=p.getPhoto()%>" class="card-img-top">
			</div>

			<div class="card-body">
				<h5 class="card-title"><%=p.getTitle()%></h5>
				<p class="card-text">
					<%=p.getContent()%>
				</p>
			</div>

			<div
				class="card-footer d-flex justify-content-between align-items-center">
				<a href="show_blog.jsp?pid=<%=p.getId()%>"
					class="btn btn-white btn-sm">Read More</a>
				<div class="d-flex align-items-center">
					<a href="#!" onclick="doLike(<%=p.getId()%>, <%=user.getId()%>)"
						class="btn btn-sm"><i
						class="fa <%=ld.isLikedByUser(p.getId(), user.getId()) ? "fa-thumbs-up blue-thumb" : "fa-thumbs-o-up"%>"
						id="likeBtnIcon<%=p.getId()%>" data-like-color="blue-thumb"></i> <span
						class="likeCounter" id="likeCounter<%=p.getId()%>"> <%=ld.countLikeOnPost(p.getId())%>
					</span></a> <a href="#!" class="btn btn-sm"><i class="fa fa-commenting-o"></i><span>17</span></a>
					<a href="#!" class="btn btn-sm"><i class="fa fa-share"></i><span>
							5</span></a>

				</div>

			</div>

		</div>
	</div>


	<%
	}
	%>

</div>

<script src="js/myjs.js"></script>