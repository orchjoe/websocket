/**
 * @fileName:  StudentDialogTopicService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月17日 下午4:58:28
 */ 
package cn.codelion.plugins.activemq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.codelion.plugins.activemq.bean.ActivemqMsgBean;
import cn.codelion.plugins.activemq.contents.JMSContent;
import cn.codelion.plugins.activemq.publish.MQPublisherServer;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
@Service
public class StudentDialogTopicService {
	@Autowired
	MQPublisherServer publisherServer;

	public void sendMsg(ActivemqMsgBean activemqMsgBean) {
		publisherServer.sendMessage(JMSContent.TOPIC_MESSAGE_TALK, activemqMsgBean);
	}
}
