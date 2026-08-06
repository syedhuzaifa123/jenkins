package com.tech.pulse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tech.pulse.entities.Category;
import com.tech.pulse.entities.Post;

public class PostDao {

	Connection con;

	public PostDao(Connection con) {
		this.con = con;
	}

	public ArrayList<Category> getAllCategories() {

		ArrayList<Category> list = new ArrayList<Category>();
		String photo = "";

		try {

			String query = "SELECT * FROM category";
			Statement stmt = this.con.createStatement();
			ResultSet result = stmt.executeQuery(query);

			while (result.next()) {

				int id = result.getInt("id");
				String name = result.getString("name");
				String desc = result.getString("description");
				Category category = new Category(id, name, desc, photo);
				list.add(category);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean savePost(Post post) {

		boolean flag = false;

		try {

			String query = "INSERT INTO post(title, content, code, photo, catId, userId) VALUES(?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, post.getTitle());
			pstmt.setString(2, post.getContent());
			pstmt.setString(3, post.getCode());
			pstmt.setString(4, post.getPhoto());
			pstmt.setInt(5, post.getCatId());
			pstmt.setInt(6, post.getUserId());

			pstmt.executeUpdate();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();

		}

		return flag;
	}

	// Get All Post

	public List<Post> getAllPost() {

		List<Post> list = new ArrayList<>();

		try {

			String query = "SELECT * FROM post ORDER BY id";
			Statement stmt = this.con.createStatement();
			ResultSet result = stmt.executeQuery(query);

			while (result.next()) {

				int id = result.getInt("id");
				String title = result.getString("title");
				String content = result.getString("content");
				String code = result.getString("code");
				String photo = result.getString("photo");
				Timestamp date = result.getTimestamp("date");
				int catId = result.getInt("catId");
				int userId = result.getInt("userId");

				Post post = new Post(id, title, content, code, photo, date, catId, userId);
				list.add(post);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// Get Post by Selected Category

	public List<Post> getPostByCategory(int catId) {

		List<Post> list = new ArrayList<>();

		try {

			String query = "SELECT * FROM post WHERE catId = ? ORDER BY id";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setInt(1, catId);

			ResultSet result = pstmt.executeQuery();

			while (result.next()) {

				int id = result.getInt("id");
				String title = result.getString("title");
				String content = result.getString("content");
				String code = result.getString("code");
				String photo = result.getString("photo");
				Timestamp date = result.getTimestamp("date");
				int userId = result.getInt("userId");

				Post post = new Post(id, title, content, code, photo, date, catId, userId);
				list.add(post);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// Get Post by Post ID

	public Post getPostById(int postId) {

		Post post = null;

		try {

			String query = "SELECT * FROM post WHERE id = ? ORDER BY id";
			PreparedStatement pstmt = this.con.prepareStatement(query);
			pstmt.setInt(1, postId);

			ResultSet result = pstmt.executeQuery();

			while (result.next()) {

				String title = result.getString("title");
				String content = result.getString("content");
				String code = result.getString("code");
				String photo = result.getString("photo");
				Timestamp date = result.getTimestamp("date");
				int catID = result.getInt("catId");
				int userId = result.getInt("userId");

				post = new Post(postId, title, content, code, photo, date, catID, userId);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return post;
	}
}
