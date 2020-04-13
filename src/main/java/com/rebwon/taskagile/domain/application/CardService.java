package com.rebwon.taskagile.domain.application;

import java.util.List;

import com.rebwon.taskagile.domain.application.commands.AddCardCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.Card;

public interface CardService {
  List<Card> findByBoardId(BoardId boardId);
  Card addCard(AddCardCommand command);
  void changePositions(ChangeCardPositionsCommand command);
}
