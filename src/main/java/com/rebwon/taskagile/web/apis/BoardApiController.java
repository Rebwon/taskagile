package com.rebwon.taskagile.web.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rebwon.taskagile.domain.application.BoardService;
import com.rebwon.taskagile.domain.application.CardListService;
import com.rebwon.taskagile.domain.application.CardService;
import com.rebwon.taskagile.domain.application.TeamService;
import com.rebwon.taskagile.domain.common.security.CurrentUser;
import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.Card;
import com.rebwon.taskagile.domain.model.cardlist.CardList;
import com.rebwon.taskagile.domain.model.team.Team;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserNotFoundException;
import com.rebwon.taskagile.web.payload.AddBoardMemberPayload;
import com.rebwon.taskagile.web.payload.CreateBoardPayload;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.BoardResult;
import com.rebwon.taskagile.web.results.CreateBoardResult;
import com.rebwon.taskagile.web.results.Result;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardApiController {
  private final BoardService boardService;
  private final TeamService teamService;
  private final CardListService cardListService;
  private final CardService cardService;

  @PostMapping("/api/boards")
  public ResponseEntity<ApiResult> createBoard(@RequestBody CreateBoardPayload payload,
    @CurrentUser SimpleUser currentUser) {
    Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
    return CreateBoardResult.build(board);
  }

  @GetMapping("/api/boards/{boardId}")
  public ResponseEntity<ApiResult> getBoard(@PathVariable("boardId") long rawBoardId) {
    BoardId boardId = new BoardId(rawBoardId);
    Board board = boardService.findById(boardId);
    if(board == null) {
      return Result.notFound();
    }
    List<User> members = boardService.findMembers(boardId);

    Team team = null;
    if(!board.isPersonal()) {
      team = teamService.findById(board.getTeamId());
    }

    List<CardList> cardLists = cardListService.findByBoardId(boardId);
    List<Card> cards = cardService.findByBoardId(boardId);

    return BoardResult.build(team, board, members, cardLists, cards);
  }

  @PostMapping("/api/boards/{boardId}/members")
  public ResponseEntity<ApiResult> addMember(@PathVariable("boardId") long rawBoardId,
    @RequestBody AddBoardMemberPayload payload) {
    BoardId boardId = new BoardId(rawBoardId);
    Board board = boardService.findById(boardId);
    if (board == null) {
      return Result.notFound();
    }

    try {
      User member = boardService.addMember(boardId, payload.getUsernameOrEmailAddress());

      ApiResult apiResult = ApiResult.blank()
        .add("id", member.getId().value())
        .add("shortName", member.getInitials());
      return Result.ok(apiResult);
    } catch (UserNotFoundException e) {
      return Result.failure("No user found");
    }
  }
}
