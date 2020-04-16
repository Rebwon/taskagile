package com.rebwon.taskagile.domain.model.cardlist.event;

import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.common.event.TriggeredBy;
import com.rebwon.taskagile.domain.model.cardlist.CardList;
import com.rebwon.taskagile.domain.model.cardlist.CardListId;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CardListAddedEvent extends DomainEvent {
  private static final long serialVersionUID = -877934435476435188L;

  private CardListId cardListId;
  private String cardListName;

  public CardListAddedEvent(CardList cardList, TriggeredBy triggeredBy) {
    super(triggeredBy);
    this.cardListId = cardList.getId();
    this.cardListName = cardList.getName();
  }
}
