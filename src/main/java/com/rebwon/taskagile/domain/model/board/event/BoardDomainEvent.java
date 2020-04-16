package com.rebwon.taskagile.domain.model.board.event;

import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.common.event.TriggeredBy;
import com.rebwon.taskagile.domain.model.board.BoardId;
import lombok.Getter;

@Getter
public abstract class BoardDomainEvent extends DomainEvent {
  private static final long serialVersionUID = -147308556973863979L;

  private BoardId boardId;

  public BoardDomainEvent(BoardId boardId, TriggeredBy triggeredBy) {
    super(triggeredBy);
    this.boardId = boardId;
  }
}
