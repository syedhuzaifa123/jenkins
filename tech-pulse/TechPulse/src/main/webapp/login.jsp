<%@page import="com.tech.pulse.entities.Message"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page errorPage="error_page.jsp"%>
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

<title>Login - TechPulse</title>
</head>
<body>

	<%@include file="includes/navbar_normal.jsp"%>

	<main
		class="d-flex align-items-center secondary-bg banner-login-bg py-5 px-3"
		style="min-height: 70vh">
		<div class="container">
			<div class="row">
				<div class="col-md-4 offset-md-4">
					<div class="card">
						<div
							class="card-header card-header-custom d-flex flex-column align-items-center justify-content-center text-center">
							<span class="fa fa-user-circle fa-2x"></span>
							<p class="mb-0">Login here</p>
						</div>

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

						<div class="card-body">
							<form action="LoginServlet" method="POST">
								<div class="mb-3">
									<label for="email" class="form-label">Email</label> <input
										type="email" class="form-control" name="loginEmail" required
										placeholder="Enter your email">
								</div>
								<div class="mb-3">
									<label for="password" class="form-label">Password</label> <input
										type="password" class="form-control" name="loginPassword"
										required placeholder="Enter your password">
								</div>
								<div class="mb-3 form-check">
									<input class="form-check-input" type="checkbox"
										name="loginRemember"> <label class="form-check-label"
										for="rememberMe">Remember me</label>
								</div>
								<button type="submit" class="btn btn-standard w-100">Sign
									in</button>
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

</body>
</html>
