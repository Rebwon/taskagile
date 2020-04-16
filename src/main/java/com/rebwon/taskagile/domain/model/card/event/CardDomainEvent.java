package com.rebwon.taskagile.domain.model.card.event;

import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.common.event.TriggeredBy;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.CardId;
import lombok.Getter;

@Getter
public abstract class CardDomainEvent extends DomainEvent {
  private static final long serialVersionUID = 8301463735426628027L;

  private CardId cardId;
  private String cardTitle;
  private BoardId boardId;

  public CardDomainEvent(CardId cardId, String cardTitle, BoardId boardId, TriggeredBy triggeredBy) {
    super(triggeredBy);
    this.cardId = cardId;
    this.cardTitle = cardTitle;
    this.boardId = boardId;
  }
}
