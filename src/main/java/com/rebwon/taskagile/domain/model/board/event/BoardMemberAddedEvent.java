package com.rebwon.taskagile.domain.model.board.event;

import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.user.User;
import lombok.Getter;

@Getter
public class BoardMemberAddedEvent extends DomainEvent {
  private static final long serialVersionUID = -8979992986207557039L;

  private BoardId boardId;
  private User user;

  public BoardMemberAddedEvent(Object source, BoardId boardId, User user) {
    super(source);
    this.boardId = boardId;
    this.user = user;
  }
}
