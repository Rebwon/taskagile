package com.rebwon.taskagile.domain.model.attachment.event;

import com.rebwon.taskagile.domain.common.event.TriggeredBy;
import com.rebwon.taskagile.domain.model.attachment.Attachment;
import com.rebwon.taskagile.domain.model.attachment.AttachmentId;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.domain.model.card.event.CardDomainEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CardAttachmentAddedEvent extends CardDomainEvent {

  private static final long serialVersionUID = -7962885726212050836L;

  private String cardTitle;
  private AttachmentId attachmentId;
  private String fileName;

  public CardAttachmentAddedEvent(Card card, Attachment attachment, TriggeredBy triggeredBy) {
    super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
    this.cardTitle = card.getTitle();
    this.attachmentId = attachment.getId();
    this.fileName = attachment.getFileName();
  }
}
