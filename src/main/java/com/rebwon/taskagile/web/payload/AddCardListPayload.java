package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.AddCardListCommand;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.Setter;

@Setter
public class AddCardListPayload {
  private long boardId;
  private String name;
  private int position;

  public AddCardListCommand toCommand(UserId userId) {
    return new AddCardListCommand(new BoardId(boardId), userId, name, position);
  }
}
