/**
 * @fileName:  ServerPublisher.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 上午11:56:50
 */
package cn.codelion.plugins.activemq.publish;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Service
public class MQPublisherServer {
	private static final Logger logger = LoggerFactory.getLogger(MQPublisherServer.class);

	@Resource
	private JmsTemplate jmsTemplate;

	public void publish(String destinationName, String message) {
		logger.debug("给地址主题为" + destinationName + "发送消息:" + message);
		Destination destination = new ActiveMQTopic(destinationName);
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
}
