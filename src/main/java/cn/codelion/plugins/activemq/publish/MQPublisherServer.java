/**
 * @fileName:  ServerPublisher.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 上午11:56:50
 */
package cn.codelion.plugins.activemq.publish;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.codelion.plugins.activemq.bean.ActivemqMsgBean;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Service
public class MQPublisherServer {
	private static final Logger logger = LoggerFactory.getLogger(MQPublisherServer.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendMessage(String destination, ActivemqMsgBean activemqMsgBean) {
		logger.debug("给"+destination+"发送消息"+JSONObject.toJSONString(activemqMsgBean));
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				String message = JSONObject.toJSONString(activemqMsgBean);
				return session.createTextMessage(message);
			}
		});

	}

}
