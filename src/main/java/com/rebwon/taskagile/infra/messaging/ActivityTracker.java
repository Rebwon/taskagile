package com.rebwon.taskagile.infra.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rebwon.taskagile.domain.application.ActivityService;
import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.model.activity.Activity;
import com.rebwon.taskagile.domain.model.activity.DomainEventToActivityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityTracker {
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
