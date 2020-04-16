package com.rebwon.taskagile.domain.model.board.event;

import com.rebwon.taskagile.domain.common.event.TriggeredBy;
import com.rebwon.taskagile.domain.model.board.Board;
import lombok.Getter;

@Getter
public class BoardCreatedEvent extends BoardDomainEvent {
  private static final long serialVersionUID = 533290197204620246L;

  private String boardName;

  public BoardCreatedEvent(Board board, TriggeredBy triggeredBy) {
    super(board.getId(), triggeredBy);
    this.boardName = board.getName();
  }

  @Override
  public String toString() {
    return "BoardCreatedEvent{" +
      "boardId=" + getBoardId() +
      ", boardName='" + boardName + '\'' +
      '}';
  }
}
