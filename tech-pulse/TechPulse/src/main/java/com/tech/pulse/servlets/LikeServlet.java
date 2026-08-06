package com.tech.pulse.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tech.pulse.dao.LikedDao;
import com.tech.pulse.helper.ConnectionProvider;

@WebServlet("/LikeServlet")
public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		boolean flag = false;
		String indicator = "";

		String operation = request.getParameter("operation");
		int postId = Integer.parseInt(request.getParameter("pId"));
		int userId = Integer.parseInt(request.getParameter("uId"));

		LikedDao likedDao = new LikedDao(ConnectionProvider.getConnection());

		if (operation.equals("like")) {

			flag = likedDao.isLikedByUser(postId, userId);

			if (flag) {

				flag = likedDao.deleteLike(postId, userId);
				indicator = "delete";

			} else {

				flag = likedDao.insertLike(postId, userId);
				indicator = "add";
			}

			out.print(indicator);
		}

//		System.out.println("Operation: "+operation);
//		System.out.println("Post ID: "+postId);
//		System.out.println("User ID: "+userId);

	}

}
