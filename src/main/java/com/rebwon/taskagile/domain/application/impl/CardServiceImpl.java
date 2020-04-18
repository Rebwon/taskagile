package com.rebwon.taskagile.domain.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rebwon.taskagile.domain.application.CardService;
import com.rebwon.taskagile.domain.application.commands.AddCardAttachmentCommand;
import com.rebwon.taskagile.domain.application.commands.AddCardCommand;
import com.rebwon.taskagile.domain.application.commands.AddCardCommentCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardDescriptionCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardTitleCommand;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.model.activity.Activity;
import com.rebwon.taskagile.domain.model.activity.ActivityRepository;
import com.rebwon.taskagile.domain.model.activity.CardActivities;
import com.rebwon.taskagile.domain.model.attachment.Attachment;
import com.rebwon.taskagile.domain.model.attachment.AttachmentManagement;
import com.rebwon.taskagile.domain.model.attachment.AttachmentRepository;
import com.rebwon.taskagile.domain.model.attachment.event.CardAttachmentAddedEvent;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.domain.model.card.CardId;
import com.rebwon.taskagile.domain.model.card.CardRepository;
import com.rebwon.taskagile.domain.model.card.event.CardAddedEvent;
import com.rebwon.taskagile.domain.model.card.event.CardDescriptionChangedEvent;
import com.rebwon.taskagile.domain.model.card.event.CardTitleChangedEvent;
import com.rebwon.taskagile.domain.model.cardlist.CardList;
import com.rebwon.taskagile.domain.model.cardlist.CardListRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
  private final CardRepository cardRepository;
  private final CardListRepository cardListRepository;
  private final ActivityRepository activityRepository;
  private final AttachmentManagement attachmentManagement;
  private final AttachmentRepository attachmentRepository;
  private final DomainEventPublisher domainEventPublisher;

  @Override
  public List<Card> findByBoardId(
    BoardId boardId) {
    return cardRepository.findByBoardId(boardId);
  }

  @Override
  public Card findById(CardId cardId) {
    return cardRepository.findById(cardId);
  }

  @Override
  public List<Activity> findCardActivities(CardId cardId) {
    return activityRepository.findCardActivities(cardId);
  }

  @Override
  public List<Attachment> getAttachments(
    CardId cardId) {
    return attachmentRepository.findAttachments(cardId);
  }

  @Override
  public Card addCard(AddCardCommand command) {
    CardList cardList = cardListRepository.findById(command.getCardListId());
    Assert.notNull(cardList, "Card list must not be null");

    Card card = Card.create(cardList, command.getUserId(), command.getTitle(), command.getPosition());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardAddedEvent(card, command));
    return card;
  }

  @Override
  public void changePositions(ChangeCardPositionsCommand command) {
    cardRepository.changePositions(command.getCardPositions());
  }

  @Override
  public void changeCardTitle(ChangeCardTitleCommand command) {
    Assert.notNull(command, "Parameter `command` must not be null");

    Card card = findCard(command.getCardId());
    String oldTitle = card.getTitle();
    card.changeTitle(command.getTitle());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardTitleChangedEvent(card, oldTitle, command));
  }

  @Override
  public void changeCardDescription(ChangeCardDescriptionCommand command) {
    Assert.notNull(command, "Parameter `command` must not be null");

    Card card = findCard(command.getCardId());
    String oldDescription = card.getDescription();
    card.changeDescription(command.getDescription());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardDescriptionChangedEvent(card, oldDescription, command));
  }

  @Override
  public Activity addComment(AddCardCommentCommand command) {
    Assert.notNull(command, "Parameter `command` must not be null");

    Card card = findCard(command.getCardId());
    Activity cardActivity = CardActivities.from(
      card, command.getUserId(), command.getComment(), command.getIpAddress());

    activityRepository.save(cardActivity);
    return cardActivity;
  }

  @Override
  public Attachment addAttachment(AddCardAttachmentCommand command) {
    Assert.notNull(command, "Parameter `command` must not be null");

    Card card = findCard(command.getCardId());
    Attachment attachment = attachmentManagement.save(
      command.getCardId(), command.getFile(), command.getUserId());

    if(!card.hasCoverImage() && attachment.isThumbnailCreated()) {
      card.addCoverImage(attachment.getFilePath());
      cardRepository.save(card);
    }

    domainEventPublisher.publish(new CardAttachmentAddedEvent(card, attachment, command));
    return attachment;
  }

  private Card findCard(CardId cardId) {
    Card card = cardRepository.findById(cardId);
    Assert.notNull(card, "Card of id " + card + " must exist");
    return card;
  }
}
