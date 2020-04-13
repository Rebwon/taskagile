package com.rebwon.taskagile.domain.application;

import java.util.List;

import com.rebwon.taskagile.domain.application.commands.AddCardListCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardListPositionsCommand;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.cardlist.CardList;

public interface CardListService {
  List<CardList> findByBoardId(BoardId boardId);
  CardList addCardList(AddCardListCommand command);
  void changePositions(ChangeCardListPositionsCommand command);
}
