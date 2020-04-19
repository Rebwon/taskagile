package com.rebwon.taskagile.infra.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AmqpDomainEventPublisher implements DomainEventPublisher {
  private RabbitTemplate rabbitTemplate;
  private FanoutExchange exchange;

  public AmqpDomainEventPublisher(RabbitTemplate rabbitTemplate,
    @Qualifier("domainEventsExchange") FanoutExchange exchange) {
    this.rabbitTemplate = rabbitTemplate;
    this.exchange = exchange;
  }

  @Override
  public void publish(DomainEvent event) {
    log.debug("Publishing domain event: " + event);
    try {
      rabbitTemplate.convertAndSend(exchange.getName(), "", event);
    } catch (AmqpException e) {
      log.error("Failed to send domain event to MQ", e);
    }
  }
}
