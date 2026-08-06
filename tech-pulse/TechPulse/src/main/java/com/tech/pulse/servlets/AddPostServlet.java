package com.tech.pulse.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.tech.pulse.dao.PostDao;
import com.tech.pulse.entities.Message;
import com.tech.pulse.entities.Post;
import com.tech.pulse.entities.User;
import com.tech.pulse.helper.ConnectionProvider;
import com.tech.pulse.helper.Helper;

@MultipartConfig
@WebServlet("/AddPostServlet")
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		int catID = Integer.parseInt(request.getParameter("catID"));
		String postTitle = request.getParameter("postTitle");
		String postContent = request.getParameter("postContent");
		String postCode = request.getParameter("postCode");
		Part part = request.getPart("postImage");
		String imageName = part.getSubmittedFileName();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("currentUser");
		int userId = user.getId();

		Post post = new Post(postTitle, postContent, postCode, imageName, null, catID, userId);
		PostDao postDao = new PostDao(ConnectionProvider.getConnection());

		if (postDao.savePost(post)) {

			String path = "C:" + File.separator + "Users" + File.separator + "Dell" + File.separator + "git"
					+ File.separator + "tech-pulse" + File.separator + "TechPulse" + File.separator + "src"
					+ File.separator + "main" + File.separator + "webapp" + File.separator + "blog_pics"
					+ File.separator + imageName;

			try {

				Helper.updateFile(part.getInputStream(), path);
				out.println("done");

			} catch (IOException e) {

				e.printStackTrace();
			}

		} else {
			out.println("error");
		}

	}

}
