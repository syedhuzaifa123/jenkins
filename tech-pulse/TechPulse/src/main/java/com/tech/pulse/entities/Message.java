package com.tech.pulse.entities;

public class Message {

	private String content;
	private String type;
	private String cssClasss;

	public Message(String content, String type, String cssClasss) {
		this.content = content;
		this.type = type;
		this.cssClasss = cssClasss;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCssClasss() {
		return cssClasss;
	}

	public void setCssClasss(String cssClasss) {
		this.cssClasss = cssClasss;
	}

}
