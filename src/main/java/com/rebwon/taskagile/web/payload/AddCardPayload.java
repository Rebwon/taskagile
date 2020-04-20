package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.AddCardCommand;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.cardlist.CardListId;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCardPayload {
  private long boardId;
  private long cardListId;
  private String title;
  private int position;

  public AddCardCommand toCommand() {
    return new AddCardCommand(new CardListId(cardListId), title, position);
  }

  public BoardId getBoardId() {
    return new BoardId(boardId);
  }
}
