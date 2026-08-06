package com.tech.pulse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.tech.pulse.entities.Post;
import com.tech.pulse.entities.User;

public class UserDao {

	private Connection con;

	public UserDao(Connection con) {
		this.con = con;
	}

	// Method for user insertion

	public boolean saveUser(User user) {

		boolean flag = false;

		try {

			String query = "INSERT INTO user(name, email, password, gender, about) values(?, ?, ?, ?, ?)";

			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getGender());
			pstmt.setString(5, user.getAbout());

			pstmt.executeUpdate();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Get USer by Email & Password

	public User getUserByEmailAndPassword(String email, String password) {

		User user = null;

		try {

			String query = "SELECT * FROM user WHERE email =? and password =?";

			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet result = pstmt.executeQuery();

			if (result.next()) {

				user = new User();

				int userId = result.getInt("id");
				String userName = result.getString("name");
				String userEmail = result.getString("email");
				String userPassword = result.getString("password");
				String gender = result.getString("gender");
				String about = result.getString("about");
				Timestamp timeStamp = result.getTimestamp("reg_date");
				String profile = result.getString("profile");

				user.setId(userId);
				user.setName(userName);
				user.setEmail(userEmail);
				user.setPassword(userPassword);
				user.setGender(gender);
				user.setAbout(about);
				user.setDateTime(timeStamp);
				user.setProfile(profile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	// Update Profile

	public boolean updateProfile(User user) {

		boolean flag = false;

		try {

			String query = "UPDATE user SET name=?, email=?, password=?, about=?, profile=? WHERE id =?";
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getAbout());
			pstmt.setString(5, user.getProfile());
			pstmt.setInt(6, user.getId());

			pstmt.executeUpdate();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Get User by User ID

	public User getUserByUserId(int userId) {

		User user = null;

		try {

			String query = "SELECT * FROM user WHERE id = ?";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setInt(1, userId);

			ResultSet result = pstmt.executeQuery();

			while (result.next()) {

				String name = result.getString("name");
				String email = result.getString("email");
				String pass = result.getString("password");
				String about = result.getString("about");
				String gender = result.getString("gender");

				user = new User(name, email, pass, gender, about);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

}
