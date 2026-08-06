<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- jQuery first -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

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

<title>Register - TechPulse</title>
</head>
<body>

	<%@include file="includes/navbar_normal.jsp"%>

	<main
		class="d-flex align-items-center secondary-bg banner-login-bg py-5 px-3"
		style="min-height: 70vh">
		<div class="container">
			<div class="row">
				<div class="col-md-6 offset-md-3">
					<div class="card">
						<div
							class="card-header card-header-custom d-flex flex-column align-items-center justify-content-center text-center">
							<span class="	fa fa-user-plus fa-2x"></span>
							<p class="mb-0">Register here</p>
						</div>
						<div class="card-body">
							<form id="reg-form" action="RegisterServlet" method="POST">

								<div class="mb-3">
									<label for="name" class="form-label">Username</label> <input
										type="text" class="form-control" name="user_name"
										placeholder="Enter your name">
								</div>

								<div class="mb-3">
									<label for="email" class="form-label">Email</label> <input
										type="email" class="form-control" name="user_email"
										placeholder="Enter your email">
								</div>

								<div class="mb-3">
									<label for="password" class="form-label">Password</label> <input
										type="password" class="form-control" name="user_password"
										placeholder="Enter your password">
								</div>


								<fieldset class="row mb-3">
									<legend class="col-form-label col-sm-2 pt-0">Gender</legend>
									<div class="col-sm-10">
										<div class="form-check">
											<input class="form-check-input" type="radio" name="gender"
												value="Male" checked> <label
												class="form-check-label" for="gridRadios1"> Male </label>
										</div>
										<div class="form-check">
											<input class="form-check-input" type="radio" name="gender"
												value="Female"> <label class="form-check-label"
												for="gridRadios2"> Female </label>
										</div>
									</div>
								</fieldset>

								<div class="form-group">

									<textarea name="about" class="form-control" rows="5" cols="30"
										placeholder="Enter something about yourself"></textarea>
								</div>

								<div class="mb-3 form-check">
									<input class="form-check-input" type="checkbox" name="check">
									<label class="form-check-label" for="rememberMe">Agree
										terms and condition</label>
								</div>

								<div id="loader" class="container text-center mb-3"
									style="display: none;">
									<span class="fa fa-circle-o-notch fa-spin fa-2x loader-color"></span>
									<p>Please wait...</p>
								</div>

								<button id="submit-btn" type="submit"
									class="btn btn-standard w-100">Sign up</button>
							</form>
						</div>
						<div class="card-footer"></div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
		crossorigin="anonymous"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>

	<script>
		$(document)
				.ready(
						function() {

							console.log("Document Ready!");

							$("#reg-form")
									.on(
											"submit",
											function(event) {

												event.preventDefault();
												let form = new FormData(this);

												$("#submit-btn").hide();
												$("#loader").show();

												$
														.ajax({
															url : "RegisterServlet",
															type : "POST",
															data : form,

															success : function(
																	data,
																	textStatus,
																	jqXHR) {

																console
																		.log(data);
																$("#submit-btn")
																		.show();
																$("#loader")
																		.hide();

																if (data.trim() == 'Done') {

																	swal(
																			{
																				title : "Congratulations!",
																				text : "You have been registered!",
																				icon : "success",
																				buttons : {
																					confirm : {
																						className : "btn btn-standard swal-login-btn text-center"
																					}
																				}
																			})
																			.then(
																					function() {
																						window.location = "login.jsp";
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
																	})

																}

															},

															error : function(
																	jqXHR,
																	textStatus,
																	errorThrown) {
																console
																		.log(jqXHR);
																$("#submit-btn")
																		.show();
																$("#loader")
																		.hide();

																swal(
																		"Oops!",
																		"Something went wrong",
																		"error")

															},

															processData : false,
															contentType : false

														});

											});

						});
	</script>
</body>
</html>