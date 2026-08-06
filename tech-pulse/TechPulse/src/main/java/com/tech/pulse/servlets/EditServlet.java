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

import com.tech.pulse.dao.UserDao;
import com.tech.pulse.entities.Message;
import com.tech.pulse.entities.User;
import com.tech.pulse.helper.ConnectionProvider;
import com.tech.pulse.helper.Helper;

@MultipartConfig
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("currentUser");

		if (user == null) {
			response.sendRedirect("login.jsp"); // or show a friendly error page
			return;
		}

		String name = request.getParameter("editName");
		String email = request.getParameter("editEmail");
		String password = request.getParameter("editPassword");
		String about = request.getParameter("editAbout");
		Part part = request.getPart("editImage");
		String imageName = part.getSubmittedFileName();

		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setAbout(about);
		String oldProfile = user.getProfile();
		user.setProfile(imageName);

		UserDao userDao = new UserDao(ConnectionProvider.getConnection());

		try {

			boolean resp = userDao.updateProfile(user);

			if (resp) {

				// Deletion of Old Profile

				String oldPath = "C:" + File.separator + "Users" + File.separator + "Dell" + File.separator + "git"
						+ File.separator + "tech-pulse" + File.separator + "TechPulse" + File.separator + "src"
						+ File.separator + "main" + File.separator + "webapp" + File.separator + "pics" + File.separator
						+ oldProfile;

				if (!oldProfile.equals("default.png")) {
					try {
						Helper.deleteFile(oldPath);
					} catch (Exception e) {
						// Log any issue with file deletion
						e.printStackTrace();
					}
				}

				// Updation of New Profile

				String path = "C:" + File.separator + "Users" + File.separator + "Dell" + File.separator + "git"
						+ File.separator + "tech-pulse" + File.separator + "TechPulse" + File.separator + "src"
						+ File.separator + "main" + File.separator + "webapp" + File.separator + "pics" + File.separator
						+ imageName;

				try {

					if (Helper.updateFile(part.getInputStream(), path)) {

//						out.println("File Updated Successfully.");

						// File deletion upon JVM shutdown
						Helper.DeletionOnExit(path);

						Message msg = new Message("Profile Picture Updated Successfully!", "success", "alert-success");
						session.setAttribute("msg", msg);

					} else {
						out.println("File Not Updated");

						Message msg = new Message("Something Went Wrong", "error", "alert-danger");
						session.setAttribute("msg", msg);
					}

				} catch (IOException e) {

					Message msg = new Message("Error during file update.", "error", "alert-danger");
					session.setAttribute("msg", msg);
					e.printStackTrace();
				}

			} else {
				out.println("Record Not Updated.");
				Message msg = new Message("Record Not Updated in DB", "error", "alert-danger");
				session.setAttribute("msg", msg);
			}

		} catch (Exception e) {

			Message msg = new Message("Error during database update.", "error", "alert-danger");
			session.setAttribute("msg", msg);
			e.printStackTrace();

		}

		response.sendRedirect("dashboard.jsp");

	}

}
