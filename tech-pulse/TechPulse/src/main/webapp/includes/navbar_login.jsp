<%@page import="com.tech.pulse.entities.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tech.pulse.helper.ConnectionProvider"%>
<%@page import="com.tech.pulse.dao.PostDao"%>
<%@page import="com.tech.pulse.entities.Message"%>
<%@ page import="com.tech.pulse.entities.User"%>
<%
User user = (User) session.getAttribute("currentUser");
%>

<nav class="navbar navbar-expand-lg navbar-dark primary-background">
	<div class="container-fluid">
		<a class="navbar-brand" href="index.jsp"><span
			class="fa fa-paper-plane-o me-2"></span>TechPulse</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="#">Home</a></li>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" role="button"
					data-bs-toggle="dropdown" aria-expanded="false"> Category </a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="#">Programming</a></li>
						<li><a class="dropdown-item" href="#">Data Structure</a></li>
						<li><hr class="dropdown-divider"></li>
						<li><a class="dropdown-item" href="#">AI</a></li>
					</ul></li>
				<li class="nav-item"><a class="nav-link" href="#">About</a></li>
				<li class="nav-item"><a class="nav-link" href="#!"
					data-bs-toggle="modal" data-bs-target="#addPostModal"><span
						class="fa fa-edit"></span> Post Now</a></li>

			</ul>

			<ul class="navbar-nav me-right">

				<%
				if (user != null) {
				%>

				<li class="nav-item"><a class="nav-link" href="#!"
					data-bs-toggle="modal" data-bs-target="#profileModal"><span
						class="fa fa-user-circle text-white"></span> <%=user.getName()%></a></li>
				<li class="nav-item"><a class="nav-link" href="LogoutServlet"><span
						class="fa fa-sign-out text-white"></span> Logout</a></li>

				<%
				}
				%>


			</ul>

		</div>
	</div>
</nav>

<!-- Profile Modal - Start -->

<div class="modal fade" id="profileModal" tabindex="-1"
	aria-labelledby="profileModalLabel" aria-hidden="true"
	data-bs-backdrop="static" data-bs-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header secondary-bg text-white">
				<h1 class="modal-title fs-5 text-center" id="profileModalLabel">TechPulse</h1>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<div class="container text-center">
					<img alt="profile_pic" class="img-fluid"
						style="border-radius: 50%; max-width: 150px"
						src="pics/<%=user.getProfile()%>">
					<h3 class="modal-title mt-3 fs-5 text-center"
						id="profileModalLabel"><%=user.getName()%></h3>

					<!-- User profile details in the form of table -->

					<div id="profile-details">

						<table class="table">
							<tbody>
								<tr>
									<th scope="row">ID :</th>
									<td><%=user.getId()%></td>
								</tr>
								<tr>
									<th scope="row">Name :</th>
									<td><%=user.getName()%></td>
								</tr>
								<tr>
									<th scope="row">Email :</th>
									<td><%=user.getEmail()%></td>
								</tr>
								<tr>
									<th scope="row">Gender :</th>
									<td><%=user.getGender()%></td>
								</tr>
								<tr>
									<th scope="row">Status :</th>
									<td><%=user.getAbout()%></td>
								</tr>
								<tr>
									<th scope="row">Registered on :</th>
									<td><%=user.getDateTime()%></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div id="profile-edit" style="display: none;">

						<h3 class="mt-3">Please Edit Carefully</h3>

						<form action="EditServlet" method="POST"
							enctype="multipart/form-data">

							<table class="table text-start">

								<tr>
									<td>ID:</td>
									<td><%=user.getId()%></td>
								</tr>

								<tr>
									<td>Name:</td>
									<td><input type="text" class="form-control"
										name="editName" value="<%=user.getName()%>"></td>
								</tr>

								<tr>
									<td>Email:</td>
									<td><input type="text" class="form-control"
										name="editEmail" value="<%=user.getEmail()%>"></td>
								</tr>

								<tr>
									<td>Password:</td>
									<td><input type="text" class="form-control"
										name="editPassword" value="<%=user.getPassword()%>"></td>
								</tr>

								<tr>
									<td>Gender:</td>
									<td><%=user.getGender()%></td>
								</tr>

								<tr>
									<td>About:</td>
									<td><textarea rows="3" class="form-control"
											name="editAbout"><%=user.getAbout()%></textarea></td>
								</tr>

								<tr>
									<td>Profile Pic:</td>
									<td><input type="file" class="form-control"
										name="editImage"></td>
								</tr>

							</table>

							<div class="container">

								<button type="submit" class="btn btn-white">Save</button>

							</div>


						</form>

					</div>

				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Close</button>
				<button id="edit-profile-btn" type="button" class="btn btn-standard">Edit
					Profile</button>
			</div>
		</div>
	</div>
</div>

<!-- Profile Modal - End -->

<!-- Add Post Modal - Start -->

<div class="modal fade" id="addPostModal" tabindex="-1"
	aria-labelledby="addPostModalLabel" aria-hidden="true"
	data-bs-backdrop="static" data-bs-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header secondary-bg text-white">
				<h1 class="modal-title fs-5 text-center" id="addPostModalLabel">New
					post details.</h1>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal" aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<h5>Please enter form details</h5>

				<form action="AddPostServlet" method="POST"
					enctype="multipart/form-data">

					<div class="form-group mt-3">
						<select class="form-control mt-3" name="catID">
							<option selected disabled>---Select Category---</option>

							<%
							PostDao postDao = new PostDao(ConnectionProvider.getConnection());
							ArrayList<Category> list = postDao.getAllCategories();

							for (Category c : list) {
							%>
							<option value="<%=c.getId()%>"><%=c.getName()%></option>
							<%
							}
							%>
						</select>
					</div>

					<div class="form-group mt-3">
						<input type="text" placeholder="Enter post title" name="postTitle"
							class="form-control">
					</div>

					<div class="form-group mt-3">
						<textarea rows="8" class="form-control"
							placeholder="Enter post content" name="postContent"></textarea>
					</div>

					<div class="form-group mt-3">
						<textarea rows="8" class="form-control"
							placeholder="Enter you code (if any)" name="postCode"></textarea>
					</div>

					<div class="form-group mt-3">
						<label>Select Post Images</label> <input type="file"
							class="form-control" name="postImage">
					</div>


					<div class="container text-center mt-3">
						<button type="submit" class="btn btn-white w-100">Post
							Now</button>
					</div>

					<div class="container text-center mt-2 mb-2">
						<button type="button" class="btn btn-secondary w-100"
							data-bs-dismiss="modal">Close</button>
					</div>


				</form>

			</div>

			<!--  
			
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Close</button>
				
				<button id="add-post-btn" type="submit" class="btn btn-white">Post
					Now</button>
	
			</div>
			
			-->

		</div>
	</div>
</div>

<!-- Add Post Modal - End -->

<!-- Alert Message For Profile Picture Updation -->

<%
Message m = (Message) session.getAttribute("msg");

if (m != null) {
%>

<div class="alert <%=m.getCssClasss()%>" role="alert">
	<%=m.getContent()%></div>

<%
session.removeAttribute("msg");
}
%>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
	crossorigin="anonymous"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>

<script>
<!-- JavaScript for Edit Profile Button -->
	$(document).ready(function() {

		let editStatus = false;

		$("#edit-profile-btn").click(function() {
			console.log("Edit Button Clicked")
			if (editStatus == false) {

				$("#profile-details").hide();
				$("#profile-edit").show();
				editStatus = true;
				$(this).text("Back");

			} else {

				$("#profile-details").show();
				$("#profile-edit").hide();
				editStatus = false;
				$(this).text("Edit Profile");
			}

			// alert("Button Clicked");

		});
	});
</script>

<!-- JavaScript for Add Post Button -->

<script>
	$(document)
			.ready(
					function(e) {

						$("form[action='AddPostServlet']")
								.on(
										"submit",
										function(event) {

											// This method will execute when post form submitted.

											event.preventDefault();
											console
													.log("Post Button Clicked...");

											let form = new FormData(this);

											// Requesting to server via AJAX

											$
													.ajax({

														url : "AddPostServlet",
														type : "POST",
														data : form,
														success : function(
																data,
																textStatus,
																jqXHR) {

															if (data.trim() == "done") {

																swal(
																		{
																			title : "Congratulations!",
																			text : "Your post has been posted!",
																			icon : "success",
																			buttons : {
																				confirm : {
																					className : "btn btn-standard swal-login-btn text-center"
																				}
																			}
																		})
																		.then(
																				function() {
																					// window.location = "login.jsp";
																				});
															} else {

																swal({
																	title : "Oops!",
																	text : "Something went wrong",
																	icon : "error",
																	buttons : {
																		confirm : {
																			className : "btn btn-standard swal-login-btn text-center"
																		}
																	}
																});
															}

														},
														error : function(jqXHR,
																textStatus,
																errorThrown) {

															swal({
																title : "Oops!",
																text : "Something went wrong",
																icon : "error",
																buttons : {
																	confirm : {
																		className : "btn btn-standard swal-login-btn text-center"
																	}
																}
															});

														},
														processData : false,
														contentType : false

													});
										});

					});
</script>
