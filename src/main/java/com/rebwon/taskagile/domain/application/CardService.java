package com.rebwon.taskagile.domain.application;

import java.util.List;

import com.rebwon.taskagile.domain.application.commands.AddCardAttachmentCommand;
import com.rebwon.taskagile.domain.application.commands.AddCardCommand;
import com.rebwon.taskagile.domain.application.commands.AddCardCommentCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardDescriptionCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.rebwon.taskagile.domain.application.commands.ChangeCardTitleCommand;
import com.rebwon.taskagile.domain.model.activity.Activity;
import com.rebwon.taskagile.domain.model.attachment.Attachment;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.domain.model.card.CardId;

public interface CardService {
  List<Card> findByBoardId(BoardId boardId);
  Card findById(CardId cardId);
  List<Activity> findCardActivities(CardId cardId);
  List<Attachment> getAttachments(CardId cardId);
  Card addCard(AddCardCommand command);
  void changePositions(ChangeCardPositionsCommand command);
  void changeCardTitle(ChangeCardTitleCommand command);
  void changeCardDescription(ChangeCardDescriptionCommand command);
  Activity addComment(AddCardCommentCommand command);
  Attachment addAttachment(AddCardAttachmentCommand command);
}
