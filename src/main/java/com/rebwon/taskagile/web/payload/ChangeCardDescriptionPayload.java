package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.ChangeCardDescriptionCommand;
import com.rebwon.taskagile.domain.model.card.CardId;
import lombok.Setter;

@Setter
public class ChangeCardDescriptionPayload {
  private String description;

  public ChangeCardDescriptionCommand toCommand(long cardId) {
    return new ChangeCardDescriptionCommand(new CardId(cardId), description);
  }
}
