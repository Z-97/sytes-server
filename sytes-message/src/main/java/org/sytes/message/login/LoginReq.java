package org.sytes.message.login;

public class LoginReq {

	private int mssageId;
	
	private String name;
	private String pwd;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getMssageId() {
		return mssageId;
	}
	public void setMssageId(int mssageId) {
		this.mssageId = mssageId;
	}
}
