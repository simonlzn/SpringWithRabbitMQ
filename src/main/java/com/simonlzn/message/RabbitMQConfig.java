package com.simonlzn.message;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.UUID;

@Configuration
@ComponentScan("com.simonlzn")
public class RabbitMQConfig {
    private String queueName = "queue." + UUID.randomUUID().toString().replace("-","");
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
            new CachingConnectionFactory();

        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(TopicExchange topicExchange, FanoutExchange fanoutExchange, Queue queue, Binding binding) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setAutoStartup(false);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareExchange(fanoutExchange);
        rabbitAdmin.declareBinding(binding);
        return rabbitAdmin;
    }

    @Bean
    public Binding binding() {
        return new Binding(queueName, Binding.DestinationType.QUEUE, "java", "queue1",null);
    }

    @Bean
    public Queue queue(){
        return new Queue(queueName, false, true, false);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("python");
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("java");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    @Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter, RabbitAdmin rabbitAdmin) throws IOException {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMissingQueuesFatal(false);
        container.setRabbitAdmin(rabbitAdmin);
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(MessageQueue messageQueue) {
        return new MessageListenerAdapter(messageQueue, "recv");
	}
}