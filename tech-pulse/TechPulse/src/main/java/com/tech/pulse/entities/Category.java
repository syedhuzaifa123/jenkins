package com.tech.pulse.entities;

public class Category {

	private int id;
	private String name;
	private String desc;
	private String photo;

	public Category() {
	}

	public Category(int id, String name, String desc, String photo) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.photo = photo;
	}

	public Category(String name, String desc, String photo) {
		this.name = name;
		this.desc = desc;
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
