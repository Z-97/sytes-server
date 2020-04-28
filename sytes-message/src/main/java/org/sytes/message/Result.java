package org.sytes.message;
/**
 * http返回json
 * @author wang
 *
 */
public class Result {

	/**
	 * 状态码
	 */
	private int status;
	/**
	 * 数据串
	 */
	private String jsonData;
	/**
	 *提示信息
	 */
	private String msg;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
}
