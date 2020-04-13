package com.rebwon.taskagile.domain.model.board;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rebwon.taskagile.domain.common.model.AbstractBaseEntity;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board_member")
@NoArgsConstructor
public class BoardMember extends AbstractBaseEntity {

  private static final long serialVersionUID = 1101935717986500672L;

  @EmbeddedId
  private BoardMemberId id;

  public static BoardMember create(BoardId boardId, UserId userId) {
    BoardMember boardMember = new BoardMember();
    boardMember.id = new BoardMemberId(boardId, userId);
    return boardMember;
  }

  public BoardId getBoardId() {
    return id.getBoardId();
  }

  public UserId getUserId() {
    return id.getUserId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BoardMember)) return false;
    BoardMember that = (BoardMember) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "BoardMember{" +
      "id=" + id +
      '}';
  }

  @Embeddable
  @NoArgsConstructor
  public static class BoardMemberId implements Serializable {
    private static final long serialVersionUID = -5739169913659318896L;

    @Column(name = "board_id")
    private long boardId;

    @Column(name = "user_id")
    private long userId;

    private BoardMemberId(BoardId boardId, UserId userId) {
      this.boardId = boardId.value();
      this.userId = userId.value();
    }

    public BoardId getBoardId() {
      return new BoardId(boardId);
    }

    public UserId getUserId() {
      return new UserId(userId);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof BoardMemberId)) return false;
      BoardMemberId that = (BoardMemberId) o;
      return boardId == that.boardId &&
        userId == that.userId;
    }

    @Override
    public int hashCode() {
      return Objects.hash(boardId, userId);
    }

    @Override
    public String toString() {
      return "BoardMemberId{" +
        "boardId=" + boardId +
        ", userId=" + userId +
        '}';
    }
  }
}
