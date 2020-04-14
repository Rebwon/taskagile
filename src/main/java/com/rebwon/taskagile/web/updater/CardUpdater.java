package com.rebwon.taskagile.web.updater;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.utils.JsonUtils;
import com.rebwon.taskagile.web.socket.SubscriptionHub;

@Component
public class CardUpdater {
  public void onCardAdded(BoardId boardId, Card card) {
    Map<String, Object> cardData = new HashMap<>();
    cardData.put("id", card.getId().value());
    cardData.put("title", card.getTitle());
    cardData.put("cardListId", card.getCardListId().value());
    cardData.put("position", card.getPosition());

    Map<String, Object> update = new HashMap<>();
    update.put("type", "cardAdded");
    update.put("card", cardData);

    SubscriptionHub.send("/board/" + boardId.value(), JsonUtils.toJson(update));
  }
}
