package com.rebwon.taskagile.domain.application.commands;

import com.rebwon.taskagile.domain.model.board.BoardId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddBoardMemberCommand extends UserCommand {
  private BoardId boardId;
  private String usernameOrEmailAddress;
}
