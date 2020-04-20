package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.AddBoardMemberCommand;
import com.rebwon.taskagile.domain.model.board.BoardId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBoardMemberPayload {
  private String usernameOrEmailAddress;

  public AddBoardMemberCommand toCommand(BoardId boardId) {
    return new AddBoardMemberCommand(boardId, usernameOrEmailAddress);
  }
}
