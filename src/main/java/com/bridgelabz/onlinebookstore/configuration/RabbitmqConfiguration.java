package com.bridgelabz.onlinebookstore.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiguration {
	
	@Value("${rabbitmq.queue}")
	private String queueName;
	
	@Value("${rabbitmq.exchange}")
	private String exchangeKey;
	
	@Value("${rabbitmq.routingkey}")
	private String routingKey;

	@Bean
	public Queue createQueue() {
		return new Queue(queueName, true);
	}
	
	@Bean
	public DirectExchange createExchange() {
		return new DirectExchange(exchangeKey);
	}
	
	@Bean
	public Binding createRoutingKey(Queue queue, DirectExchange exchangeType) {
		return BindingBuilder.bind(queue).to(exchangeType).with(routingKey);
	}
	
	
	

}
