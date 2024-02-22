package com.guner.consumer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Value("${single-consumer.topic-exchange.name}")
    private String topicExchange;

    @Value("${single-consumer.queue.name.single-queue}")
    private String queueSingle;

    @Value("${single-consumer.routing.key.single-routing}")
    private String routingKeySingle;

    @Bean
    public Queue queueSingle() {
        return new Queue(queueSingle);
    }


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    public Binding bindingSingle() {
        return BindingBuilder
                .bind(queueSingle())
                .to(topicExchange())
                .with(routingKeySingle);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());

        /* tried bu not worked, retries continuously
        RetryTemplate retry = new RetryTemplate();
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(3000); //  3sn
        policy.setMultiplier(2);
        policy.setMaxInterval(10000); // 10 sn
        retry.setBackOffPolicy(policy);
        factory.setRetryTemplate(retry);
        or
        spring:
          rabbitmq:
            listener:
              simple:
                retry:
                  enabled: true
                  initial-interval: 3s
                  max-attempts: 6
                  max-interval: 10s
                  multiplier: 2
        */

        // factory.setDeBatchingEnabled(true);

        return factory;
    }

}
