package com.rebwon.taskagile.domain.model.cardlist;

import java.util.List;

import com.rebwon.taskagile.domain.model.board.BoardId;

public interface CardListRepository {
  CardList findById(CardListId cardListId);
  List<CardList> findByBoardId(BoardId boardId);
  void save(CardList cardList);
  void changePositions(List<CardListPosition> cardListPositions);
}
