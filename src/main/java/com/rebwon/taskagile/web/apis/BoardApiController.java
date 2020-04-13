package com.rebwon.taskagile.web.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rebwon.taskagile.domain.application.BoardService;
import com.rebwon.taskagile.domain.common.security.CurrentUser;
import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.web.payload.CreateBoardPayload;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.CreateBoardResult;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardApiController {
  private final BoardService boardService;

  @PostMapping("/api/boards")
  public ResponseEntity<ApiResult> createBoard(@RequestBody CreateBoardPayload payload,
    @CurrentUser SimpleUser currentUser) {
    Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
    return CreateBoardResult.build(board);
  }
}
