package com.rebwon.taskagile.domain.application.commands;

import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCardListCommand {
  private BoardId boardId;
  private UserId userId;
  private String name;
  private int position;
}
