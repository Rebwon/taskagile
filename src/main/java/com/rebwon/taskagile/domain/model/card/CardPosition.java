package com.rebwon.taskagile.domain.model.card;

import com.rebwon.taskagile.domain.model.cardlist.CardListId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardPosition {
  private long cardListId;
  private long cardId;
  private int position;

  public CardListId getCardListId() {
    return new CardListId(cardListId);
  }

  public CardId getCardId() {
    return new CardId(cardId);
  }
}
