package otus.amogilevskiy.integration.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessagingException;
import otus.amogilevskiy.integration.domain.Delivery;
import otus.amogilevskiy.integration.domain.Order;
import otus.amogilevskiy.integration.domain.OrderConfirmation;
import otus.amogilevskiy.integration.domain.OrderConfirmationStatus;
import otus.amogilevskiy.integration.service.delivery.DeliveryService;
import otus.amogilevskiy.integration.service.order.OrderValidationService;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class IntegrationConfig {

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public QueueChannel ordersChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public DirectChannel orderConfirmationsChannel() {
        return MessageChannels.direct().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(500).maxMessagesPerPoll(5).get();
    }

    @Bean
    public IntegrationFlow processOrderFlow(
            @Qualifier("location") OrderValidationService locationValidationService,
            @Qualifier("availability") OrderValidationService availabilityValidationService,
            DeliveryService deliveryService,
            ExecutorService executorService
    ) {
        return IntegrationFlows.from("ordersChannel")
                .handle(locationValidationService, "validate")
                .handle(availabilityValidationService, "validate")
                .transform(Order.class, order -> OrderConfirmation.builder()
                        .order(order)
                        .status(OrderConfirmationStatus.CONFIRMED)
                        .trackingId(UUID.randomUUID().toString())
                        .build()
                )
                .publishSubscribeChannel(c ->
                        c.subscribe(s -> s.channel("orderConfirmationsChannel"))
                                .subscribe(s -> s.transform(OrderConfirmation.class,
                                        confirmation -> new Delivery(confirmation.getTrackingId(), confirmation.getOrder()))
                                        .channel(channel -> channel.executor(executorService))
                                        .handle(deliveryService, "send"))
                )
                .get();
    }

    @Bean
    public IntegrationFlow errorsFlow() {
        return IntegrationFlows.from("errorsChannel")
                .transform(message -> {
                    var order = (Order) ((MessagingException) message).getFailedMessage().getPayload();
                    var reason = ((MessagingException) message).getCause().getMessage();
                    return OrderConfirmation.builder()
                            .order(order)
                            .status(OrderConfirmationStatus.REJECTED)
                            .comment(reason)
                            .build();
                })
                .channel("orderConfirmationsChannel")
                .get();
    }

}
