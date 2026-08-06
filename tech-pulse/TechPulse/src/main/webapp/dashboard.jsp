<%@page import="com.tech.pulse.entities.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page errorPage="error_page.jsp"%>

<%
// User login check
if (session.getAttribute("currentUser") == null) {
	response.sendRedirect("login.jsp");
	return;
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

<title>Dashboard - TechPulse</title>
</head>
<body>

	<%@include file="includes/navbar_login.jsp"%>

	<main>
		<div class="container">

			<div class="row mt-4">

				<div class="col-md-4">

					<!-- Display All Categories -->
					<div class="list-group">
						<a href="#" onclick="getPosts(0,this)"
							class="list-group-item list-group-item-action c-link"
							aria-current="true">All Posts</a>

						<%
						PostDao pd = new PostDao(ConnectionProvider.getConnection());
						ArrayList<Category> catList = pd.getAllCategories();

						for (Category cat : catList) {
						%>

						<a href="#" onclick="getPosts(<%=cat.getId()%>,this)"
							class="list-group-item list-group-item-action c-link"><%=cat.getName()%></a>

						<%
						}
						%>

					</div>

				</div>

				<div class="col-md-8">

					<!-- Display all post as per selected category -->

					<div class="container text-center" id="loader">
						<i class="fa fa-refresh fa-2x fa-spin loader-color"></i>
						<h6 class="mt-2">Loading Posts...</h6>
					</div>

					<div class="container-fluid" id="post-container"></div>

				</div>

			</div>

		</div>
	</main>

	<script src="https://code.jquery.com/jquery-3.7.1.min.js"
		integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
		crossorigin="anonymous"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
		crossorigin="anonymous"></script>

	<script src="js/myjs.js"></script>



	<!-- Load Posts Using AJAX -->
	<script>
		function getPosts(catId,ref) {

			$("#loader").show();
			$("#post-container").hide();
			$(".c-link").removeClass("active");

			$.ajax({
				url : "post.jsp",
				data : {
					cid : catId
				},
				success : function(data) {
					// Properly insert the HTML into the DOM
					$("#loader").hide();
					$("#post-container").show();
					$('#post-container').html(data);
					$(ref).addClass("active");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.error("AJAX Error:", errorThrown);
				}
			});

		}

		$(document).ready(function() {

			let firstListRef = $(".c-link")[0]
			getPosts(0,firstListRef);
		});
	</script>

</body>
</html>