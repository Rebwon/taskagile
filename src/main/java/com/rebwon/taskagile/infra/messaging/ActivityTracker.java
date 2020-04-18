package com.rebwon.taskagile.infra.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rebwon.taskagile.domain.application.ActivityService;
import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.model.activity.Activity;
import com.rebwon.taskagile.domain.model.activity.DomainEventToActivityConverter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ActivityTracker {
  private final static Logger log = LoggerFactory.getLogger(ActivityTracker.class);

  private final ActivityService activityService;
  private final DomainEventToActivityConverter domainEventToActivityConverter;

  @RabbitListener(queues = "#{activityTrackingQueue.name}")
  public void receive(DomainEvent domainEvent) {
    log.debug("Receive domain event: " + domainEvent);

    Activity activity = domainEventToActivityConverter.toActivity(domainEvent);
    // Save the activity only when there is an activity
    // result from the domain event
    if (activity != null) {
      activityService.saveActivity(activity);
    }
  }
}
