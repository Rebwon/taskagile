package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.ChangeCardTitleCommand;
import com.rebwon.taskagile.domain.model.card.CardId;
import lombok.Setter;

@Setter
public class ChangeCardTitlePayload {
  private String title;

  public ChangeCardTitleCommand toCommand(long cardId) {
    return new ChangeCardTitleCommand(new CardId(cardId), title);
  }
}
