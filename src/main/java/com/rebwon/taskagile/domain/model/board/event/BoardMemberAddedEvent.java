package com.rebwon.taskagile.domain.model.board.event;

import com.rebwon.taskagile.domain.common.event.TriggeredBy;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class BoardMemberAddedEvent extends BoardDomainEvent {
  private static final long serialVersionUID = -8979992986207557039L;

  private UserId memberUserId;
  private String memberName;

  public BoardMemberAddedEvent(BoardId boardId, User addedUser, TriggeredBy triggeredBy) {
    super(boardId, triggeredBy);
    this.memberUserId = addedUser.getId();
    this.memberName = addedUser.getFirstName() + " " + addedUser.getLastName();
  }

  @Override
  public String toString() {
    return "BoardMemberAddedEvent{" +
      "boardId=" + getBoardId() +
      ", memberUserId=" + memberUserId +
      ", memberName='" + memberName + '\'' +
      '}';
  }
}
