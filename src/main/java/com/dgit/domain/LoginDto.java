package com.dgit.domain;

public class LoginDto {
	private String userid;
	private String userpw;
	private String username;

	public LoginDto() {
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "LoginDto [userid=" + userid + ", userpw=" + userpw + ", username=" + username + "]";
	}

}