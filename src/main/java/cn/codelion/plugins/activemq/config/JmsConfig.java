/**
 * @fileName:  ActiveMqConfig.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年3月28日 下午12:01:22
 */
package cn.codelion.plugins.activemq.config;

import java.util.concurrent.Executors;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import cn.codelion.plugins.activemq.handler.JmsErrorHandler;

/**
 * @author QiaoYu[www.codelion.cn]
 */
@Configuration
@EnableJms
public class JmsConfig {
	private static final Logger logger = LoggerFactory.getLogger(JmsConfig.class);
	@Bean
	public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setPubSubDomain(true);
		factory.setConnectionFactory(connectionFactory);
		factory.setTaskExecutor(Executors.newFixedThreadPool(6));
		factory.setConcurrency("6");
		factory.setAutoStartup(true);
		factory.setSessionAcknowledgeMode(2);
		//就是当设置sessionAcknowledgeMode为2时，虽然是客户端手动确认，但是却被spring自动确认了，造成设置无效。这时只需要把sessionAcknowledgeMode的值设置成activemq自定义的类型INDIVIDUAL_ACKNOWLEDGE = 4即可。
		//factory.setSessionAcknowledgeMode(2);'
		factory.setErrorHandler(new JmsErrorHandler());
		logger.debug("初始化成功ActiveMQ的主题模式.........");
		return factory;
	}

	@Bean
	public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setPubSubDomain(false);
		factory.setConnectionFactory(connectionFactory);
		factory.setTaskExecutor(Executors.newFixedThreadPool(6));
		factory.setConcurrency("6");
		factory.setAutoStartup(true);
		factory.setSessionAcknowledgeMode(2);
		//就是当设置sessionAcknowledgeMode为2时，虽然是客户端手动确认，但是却被spring自动确认了，造成设置无效。这时只需要把sessionAcknowledgeMode的值设置成activemq自定义的类型INDIVIDUAL_ACKNOWLEDGE = 4即可。
		//factory.setSessionAcknowledgeMode(2);'
		factory.setErrorHandler(new JmsErrorHandler());
		logger.debug("初始化成功ActiveMQ的队列模式.........");
		return factory;
	}
}