package br.com.test.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by Eduardo on 11/06/2017.
 */
@Configuration
public class RabbitConfiguration {

    public final static String EX_ASSOCIATE_CAMPAIGN = "ex_associate_campaign";
    public final static String QUEUE_ASSOCIATE_CAMPAIGN = "associate_campaign";

    public final static String EX_DLQ_ASSOCIATE_CAMPAIGN = "ex_dlq_associate_campaign";
    public final static String QUEUE_DLQ_ASSOCIATE_CAMPAIGN = "dlq_associate_campaign";

    @Bean
    Queue queue() {
        final Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", EX_DLQ_ASSOCIATE_CAMPAIGN);
        return new Queue(QUEUE_ASSOCIATE_CAMPAIGN, true, false, false, args);
    }

    @Bean
    Queue dlQueue() {
        return new Queue(QUEUE_DLQ_ASSOCIATE_CAMPAIGN, true, false, false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(EX_ASSOCIATE_CAMPAIGN);
    }

    @Bean
    FanoutExchange dlqExchange() {
        return new FanoutExchange(EX_DLQ_ASSOCIATE_CAMPAIGN);
    }

    @Bean
    RabbitListenerContainerFactory<SimpleMessageListenerContainer> container(ConnectionFactory connectionFactory) {
        RabbitListenerContainerFactory<SimpleMessageListenerContainer> rlcf = endpoint -> {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setDefaultRequeueRejected(false);
            endpoint.setupListenerContainer(container);
            return container;
        };
        return rlcf;
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange());
    }

    @Bean
    Binding dlqBinding() {
        return BindingBuilder.bind(dlQueue()).to(dlqExchange());
    }
}
