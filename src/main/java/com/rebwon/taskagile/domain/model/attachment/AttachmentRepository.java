package com.rebwon.taskagile.domain.model.attachment;

import java.util.List;

import com.rebwon.taskagile.domain.model.card.CardId;

public interface AttachmentRepository {
  List<Attachment> findAttachments(CardId cardId);
  void save(Attachment attachment);
}
