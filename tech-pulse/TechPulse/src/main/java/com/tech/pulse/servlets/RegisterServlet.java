package com.tech.pulse.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tech.pulse.dao.UserDao;
import com.tech.pulse.entities.User;
import com.tech.pulse.helper.ConnectionProvider;

@MultipartConfig
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String check = request.getParameter("check");

		if (check == null) {

			out.println("Box not checked");

		} else {

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String name = request.getParameter("user_name");
			String email = request.getParameter("user_email");
			String password = request.getParameter("user_password");
			String gender = request.getParameter("gender");
			String about = request.getParameter("about");

			User user = new User(name, email, password, gender, about);
			UserDao dao = new UserDao(ConnectionProvider.getConnection());

			if (dao.saveUser(user)) {
				out.println("Done");
			} else {
				out.println("Error");
			}

		}

	}

}
