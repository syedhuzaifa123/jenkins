package com.tech.pulse.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tech.pulse.dao.UserDao;
import com.tech.pulse.entities.Message;
import com.tech.pulse.entities.User;
import com.tech.pulse.helper.ConnectionProvider;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String email = request.getParameter("loginEmail");
		String password = request.getParameter("loginPassword");

		UserDao userDao = new UserDao(ConnectionProvider.getConnection());
		User user = userDao.getUserByEmailAndPassword(email, password);

		if (user == null) {

//			out.println("Invalid details, try again");
			Message msg = new Message("Invalid details, try again", "error", "alert-danger");
			HttpSession session = request.getSession();
			session.setAttribute("msg", msg);
			response.sendRedirect("login.jsp");

		} else {

			HttpSession session = request.getSession();
			session.setAttribute("currentUser", user);
			response.sendRedirect("dashboard.jsp");
		}

	}

}
