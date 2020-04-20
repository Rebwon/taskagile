package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.AddCardCommentCommand;
import com.rebwon.taskagile.domain.model.card.CardId;
import lombok.Setter;

@Setter
public class AddCardCommentPayload {
  private String comment;

  public AddCardCommentCommand toCommand(CardId cardId) {
    return new AddCardCommentCommand(cardId, comment);
  }
}
