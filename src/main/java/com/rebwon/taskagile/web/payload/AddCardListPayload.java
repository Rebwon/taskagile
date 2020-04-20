package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.AddCardListCommand;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.Setter;

@Setter
public class AddCardListPayload {
  private String name;
  private long boardId;
  private int position;

  public AddCardListCommand toCommand() {
    return new AddCardListCommand(name, new BoardId(boardId), position);
  }
}
