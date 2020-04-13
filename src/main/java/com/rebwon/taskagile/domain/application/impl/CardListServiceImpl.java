package com.rebwon.taskagile.domain.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rebwon.taskagile.domain.application.CardListService;
import com.rebwon.taskagile.domain.application.commands.AddCardListCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardListPositionsCommand;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.cardlist.CardList;
import com.rebwon.taskagile.domain.model.cardlist.CardListRepository;
import com.rebwon.taskagile.domain.model.cardlist.event.CardListAddedEvent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CardListServiceImpl implements CardListService {
  private final CardListRepository cardListRepository;
  private final DomainEventPublisher domainEventPublisher;

  @Override
  public List<CardList> findByBoardId(
    BoardId boardId) {
    return cardListRepository.findByBoardId(boardId);
  }

  @Override
  public CardList addCardList(AddCardListCommand command) {
    CardList cardList = CardList.create(command.getBoardId(),
      command.getUserId(), command.getName(), command.getPosition());
    cardListRepository.save(cardList);
    domainEventPublisher.publish(new CardListAddedEvent(this, cardList));
    return cardList;
  }

  @Override
  public void changePositions(ChangeCardListPositionsCommand command) {
    cardListRepository.changePositions(command.getCardListPositions());
  }
}
