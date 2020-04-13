package com.rebwon.taskagile.domain.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rebwon.taskagile.domain.application.CardService;
import com.rebwon.taskagile.domain.application.commands.AddCardCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.domain.model.card.CardRepository;
import com.rebwon.taskagile.domain.model.card.event.CardAddedEvent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
  private final CardRepository cardRepository;
  private final DomainEventPublisher domainEventPublisher;

  @Override
  public List<Card> findByBoardId(
    BoardId boardId) {
    return cardRepository.findByBoardId(boardId);
  }

  @Override
  public Card addCard(AddCardCommand command) {
    Card card = Card.create(command.getCardListId(), command.getUserId(), command.getTitle(), command.getPosition());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardAddedEvent(this, card));
    return card;
  }

  @Override
  public void changePositions(ChangeCardPositionsCommand command) {
    cardRepository.changePositions(command.getCardPositions());
  }
}
