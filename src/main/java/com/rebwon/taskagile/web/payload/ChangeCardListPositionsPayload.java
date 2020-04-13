package com.rebwon.taskagile.web.payload;

import java.util.List;

import com.rebwon.taskagile.domain.application.commands.ChangeCardListPositionsCommand;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.cardlist.CardListPosition;
import lombok.Setter;

@Setter
public class ChangeCardListPositionsPayload {
  private long boardId;
  private List<CardListPosition> cardListPositions;

  public ChangeCardListPositionsCommand toCommand() {
    return new ChangeCardListPositionsCommand(new BoardId(boardId), cardListPositions);
  }
}
