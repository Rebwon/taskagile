package com.rebwon.taskagile.domain.model.card;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.StringUtils;

import com.rebwon.taskagile.domain.common.model.AbstractBaseEntity;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.cardlist.CardList;
import com.rebwon.taskagile.domain.model.cardlist.CardListId;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Table(name = "card")
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Card extends AbstractBaseEntity {
  private static final long serialVersionUID = 6030626206663838257L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "board_id")
  private long boardId;

  @Column(name = "card_list_id")
  private long cardListId;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "cover_image")
  private String coverImage;

  @Column(name = "position")
  private int position;

  @Column(name = "archived")
  private boolean archived;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  public static Card create(CardList cardList, UserId userId, String title, int position) {
    Card card = new Card();
    card.boardId = cardList.getBoardId().value();
    card.cardListId = cardList.getId().value();
    card.userId = userId.value();
    card.title = title;
    card.description = "";
    card.position = position;
    card.archived = false;
    card.createdDate = new Date();
    return card;
  }

  public void changeTitle(String title) {
    this.title = title;
  }

  public void changeDescription(String description) {
    this.description = description;
  }

  public boolean hasCoverImage() {
    return StringUtils.hasText(coverImage);
  }

  public void addCoverImage(String coverImage) {
    this.coverImage = coverImage;
  }

  public CardId getId() {
    return new CardId(id);
  }

  public BoardId getBoardId() {
    return new BoardId(boardId);
  }

  public CardListId getCardListId() {
    return new CardListId(cardListId);
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public boolean isArchived() {
    return archived;
  }
}
