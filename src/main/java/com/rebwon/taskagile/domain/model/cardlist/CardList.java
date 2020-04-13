package com.rebwon.taskagile.domain.model.cardlist;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rebwon.taskagile.domain.common.model.AbstractBaseEntity;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "card_list")
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class CardList extends AbstractBaseEntity {
  private static final long serialVersionUID = -6547708151192480923L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "board_id")
  private long boardId;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "position")
  private int position;

  @Column(name = "archived")
  private boolean archived;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  public static CardList create(BoardId boardId, UserId userId, String name, int position) {
    CardList cardList = new CardList();
    cardList.boardId = boardId.value();
    cardList.userId = userId.value();
    cardList.name = name;
    cardList.position = position;
    cardList.archived = false;
    cardList.createdDate = new Date();
    return cardList;
  }

  public CardListId getId() {
    return new CardListId(id);
  }

  public BoardId getBoardId() {
    return new BoardId(boardId);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public boolean isArchived() {
    return archived;
  }
}
