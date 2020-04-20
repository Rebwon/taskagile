package com.rebwon.taskagile.web.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rebwon.taskagile.domain.common.file.FileUrlCreator;
import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.domain.model.cardlist.CardList;
import com.rebwon.taskagile.domain.model.cardlist.CardListId;
import com.rebwon.taskagile.domain.model.team.Team;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.utils.ImageUtils;
import lombok.Getter;

public class BoardResult {
  public static ResponseEntity<ApiResult> build(Team team, Board board, List<User> members,
    List<CardList> cardLists, List<Card> cards, FileUrlCreator fileUrlCreator) {
    Map<String, Object> boardData = new HashMap<>();
    boardData.put("id", board.getId().value());
    boardData.put("name", board.getName());
    boardData.put("personal", board.isPersonal());

    List<MemberData> membersData = new ArrayList<>();
    for (User user: members) {
      membersData.add(new MemberData(user));
    }

    List<CardListData> cardListsData = new ArrayList<>();
    Map<CardListId, List<Card>> cardsByList = new HashMap<>();
    for (Card card: cards) {
      cardsByList.computeIfAbsent(card.getCardListId(), k -> new ArrayList<>()).add(card);
    }

    for (CardList cardList: cardLists) {
      cardListsData.add(new CardListData(cardList, cardsByList.get(cardList.getId()), fileUrlCreator));
    }

    ApiResult result = ApiResult.blank()
      .add("board", boardData)
      .add("members", membersData)
      .add("cardLists", cardListsData);

    if (!board.isPersonal()) {
      Map<String, String> teamData = new HashMap<>();
      teamData.put("name", team.getName());
      result.add("team", teamData);
    }
    return Result.ok(result);
  }

  @Getter
  private static class MemberData {
    private long userId;
    private String shortName;
    private String name;

    MemberData(User user) {
      this.userId = user.getId().value();
      this.shortName = user.getInitials();
      this.name = user.getFirstName() + " " + user.getLastName();
    }
  }

  @Getter
  private static class CardListData {
    private long id;
    private String name;
    private int position;
    private List<CardData> cards = new ArrayList<>();

    CardListData(CardList cardList, List<Card> cards, FileUrlCreator fileUrlCreator) {
      this.id = cardList.getId().value();
      this.name = cardList.getName();
      this.position = cardList.getPosition();
      if (cards != null) {
        for (Card card: cards) {
          this.cards.add(new CardData(card, fileUrlCreator));
        }
      }
    }
  }

  @Getter
  private static class CardData {
    private long id;
    private String title;
    private int position;
    private String coverImage;

    CardData(Card card, FileUrlCreator fileUrlCreator) {
      this.id = card.getId().value();
      this.title = card.getTitle();
      this.position = card.getPosition();
      this.coverImage = card.hasCoverImage() ?
        ImageUtils.getThumbnailVersion(fileUrlCreator.url(card.getCoverImage())) : "";
    }
  }
}
