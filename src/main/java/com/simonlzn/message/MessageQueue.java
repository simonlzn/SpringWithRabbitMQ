package com.simonlzn.message;

import com.simonlzn.util.PubSub;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class MessageQueue {

	private AmqpTemplate template;

	@Autowired
	public MessageQueue(AmqpTemplate template){
		this.template = template;
	}

	public void send(String message, String routingKey) {
		template.convertAndSend("python",routingKey, message);
	}

	public void recv(byte[] message) throws UnsupportedEncodingException {
		String messageString = new String(message, "UTF-8");
		System.out.println("Received : " + messageString);

		PubSub.Publish("key", messageString);
	}

}

