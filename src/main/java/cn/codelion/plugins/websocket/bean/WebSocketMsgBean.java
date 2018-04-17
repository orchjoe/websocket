/**
 * @fileName:  WebSocketMsgBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月17日 下午4:54:03
 */ 
package cn.codelion.plugins.websocket.bean; 

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
public class WebSocketMsgBean {
	private String id;
	private String userId;
	private String msg;
	private String type = "1";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
