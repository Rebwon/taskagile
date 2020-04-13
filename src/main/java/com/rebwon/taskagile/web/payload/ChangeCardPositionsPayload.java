package com.rebwon.taskagile.web.payload;

import java.util.List;

import com.rebwon.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.CardPosition;
import lombok.Setter;

@Setter
public class ChangeCardPositionsPayload {
  private long boardId;
  private List<CardPosition> cardPositions;

  public ChangeCardPositionsCommand toCommand() {
    return new ChangeCardPositionsCommand(new BoardId(boardId), cardPositions);
  }
}
