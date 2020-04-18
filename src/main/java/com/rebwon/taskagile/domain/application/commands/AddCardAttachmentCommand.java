package com.rebwon.taskagile.domain.application.commands;

import org.springframework.web.multipart.MultipartFile;

import com.rebwon.taskagile.domain.model.card.CardId;
import lombok.Getter;

@Getter
public class AddCardAttachmentCommand extends UserCommand {
  private CardId cardId;
  private MultipartFile file;

  public AddCardAttachmentCommand(long cardId, MultipartFile file) {
    this.cardId = new CardId(cardId);
    this.file = file;
  }
}
