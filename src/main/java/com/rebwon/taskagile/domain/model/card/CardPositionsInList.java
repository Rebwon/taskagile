package com.rebwon.taskagile.domain.model.card;

import java.util.List;

import com.rebwon.taskagile.domain.model.cardlist.CardListId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardPositionsInList {
  private long cardListId;
  private List<CardPosition> cardPositions;

  public CardListId getCardListId() {
    return new CardListId(cardListId);
  }
}
