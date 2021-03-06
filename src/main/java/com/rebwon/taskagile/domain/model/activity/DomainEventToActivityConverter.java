package com.rebwon.taskagile.domain.model.activity;

import org.springframework.stereotype.Component;

import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.model.attachment.event.CardAttachmentAddedEvent;
import com.rebwon.taskagile.domain.model.board.event.BoardCreatedEvent;
import com.rebwon.taskagile.domain.model.board.event.BoardMemberAddedEvent;
import com.rebwon.taskagile.domain.model.card.event.CardAddedEvent;
import com.rebwon.taskagile.domain.model.card.event.CardDescriptionChangedEvent;
import com.rebwon.taskagile.domain.model.card.event.CardTitleChangedEvent;
import com.rebwon.taskagile.domain.model.cardlist.event.CardListAddedEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DomainEventToActivityConverter {

  public Activity toActivity(DomainEvent event) {
    if(event instanceof BoardCreatedEvent){
      return BoardActivities.from((BoardCreatedEvent) event);
    } else if (event instanceof BoardMemberAddedEvent) {
      return BoardActivities.from((BoardMemberAddedEvent) event);
    } else if (event instanceof CardAttachmentAddedEvent) {
      return CardActivities.from((CardAttachmentAddedEvent) event);
    } else if (event instanceof CardAddedEvent) {
      return CardActivities.from((CardAddedEvent) event);
    } else if (event instanceof CardDescriptionChangedEvent) {
      return CardActivities.from((CardDescriptionChangedEvent) event);
    } else if (event instanceof CardTitleChangedEvent) {
      return CardActivities.from((CardTitleChangedEvent) event);
    } else if (event instanceof CardListAddedEvent) {
      return CardListActivities.from((CardListAddedEvent) event);
    }

    log.debug("No activity converted from " + event);
    return null;
  }
}
