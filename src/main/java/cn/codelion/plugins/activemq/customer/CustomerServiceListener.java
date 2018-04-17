/**
 * @fileName:  CustomerService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 下午12:02:28
 */
package cn.codelion.plugins.activemq.customer;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.codelion.plugins.activemq.bean.ActivemqMsgBean;
import cn.codelion.plugins.activemq.contents.JMSContent;
import cn.codelion.plugins.websocket.handler.StudentWebSocketHandler;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Component
public class CustomerServiceListener {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceListener.class);
	@Autowired
	StudentWebSocketHandler studentWebSocketHandler;

	@JmsListener(destination = JMSContent.QUEUE_MESSAGE_TALK, containerFactory = "queueListenerFactory")
	public void receiveTalkQueue(TextMessage message) throws Exception {
		logger.debug(Thread.currentThread().getName() + ":[" + JMSContent.QUEUE_MESSAGE_TALK + "]收到的报文为:" + message.getText());
	}

	@JmsListener(destination = JMSContent.TOPIC_MESSAGE_TALK, containerFactory = "topicListenerFactory")
	public void receiveTalkTopic(TextMessage message) throws JMSException {
		logger.debug(Thread.currentThread().getName() + ":[" + JMSContent.TOPIC_MESSAGE_TALK + "]收到的报文为:" + message.getText());
		String text = message.getText();
		ActivemqMsgBean activemqMsgBean = JSONObject.parseObject(text, ActivemqMsgBean.class);
		studentWebSocketHandler.sendMessageToUsers(activemqMsgBean.getUsers(), activemqMsgBean.getMsg());
		message.acknowledge();
		
	}

	@JmsListener(destination = JMSContent.QUEUE_MESSAGE_EMAIL, containerFactory = "queueListenerFactory")
	public void receiveEmailTopic(TextMessage message) throws JMSException {
		logger.debug(Thread.currentThread().getName() + ":[" + JMSContent.QUEUE_MESSAGE_EMAIL + "]收到的报文为:" + message.getText());
	}

}
