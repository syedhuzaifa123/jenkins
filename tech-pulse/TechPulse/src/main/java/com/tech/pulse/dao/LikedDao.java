package com.tech.pulse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LikedDao {

	Connection con;

	public LikedDao(Connection con) {
		this.con = con;
	}

	// Add Like on Post

	public boolean insertLike(int postId, int userId) {

		boolean flag = false;

		try {

			String query = "INSERT INTO liked(postId,userId) VALUES(?,?)";
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setInt(1, postId);
			pstmt.setInt(2, userId);

			pstmt.executeUpdate();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Count Like on Post

	public int countLikeOnPost(int postId) {

		int count = 0;

		try {

			String query = "SELECT COUNT(*) FROM liked WHERE postId=?";
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setInt(1, postId);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {

				count = result.getInt("count(*)");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	// User Liked Post
	public boolean isLikedByUser(int postId, int userId) {

		boolean flag = false;

		try {

			String query = "SELECT * FROM liked WHERE postId=? AND userId=?";
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setInt(1, postId);
			pstmt.setInt(2, userId);

			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	// Delete Post Like/Unlike

	public boolean deleteLike(int postId, int userId) {

		boolean flag = false;

		try {

			String query = "DELETE FROM liked WHERE postId=? AND userId=?";
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setInt(1, postId);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

}
