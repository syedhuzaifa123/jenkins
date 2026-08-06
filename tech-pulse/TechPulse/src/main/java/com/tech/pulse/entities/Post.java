package com.tech.pulse.entities;

import java.sql.Timestamp;

public class Post {

	private int id;
	private String title;
	private String content;
	private String code;
	private String photo;
	private Timestamp date;
	private int catId;
	private int userId;

	public Post() {
	}

	public Post(int id, String title, String content, String code, String photo, Timestamp date, int catId,
			int userId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.code = code;
		this.photo = photo;
		this.date = date;
		this.catId = catId;
		this.userId = userId;
	}

	public Post(String title, String content, String code, String photo, Timestamp date, int catId, int userId) {
		this.title = title;
		this.content = content;
		this.code = code;
		this.photo = photo;
		this.date = date;
		this.catId = catId;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
