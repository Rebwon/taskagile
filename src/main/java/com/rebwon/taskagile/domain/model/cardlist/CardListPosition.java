package com.rebwon.taskagile.domain.model.cardlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardListPosition {
  private long cardListId;
  private int position;

  public CardListId getCardListId() {
    return new CardListId(cardListId);
  }
}
