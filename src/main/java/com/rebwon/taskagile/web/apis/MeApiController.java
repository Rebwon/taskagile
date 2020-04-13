package com.rebwon.taskagile.web.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.rebwon.taskagile.domain.application.BoardService;
import com.rebwon.taskagile.domain.application.TeamService;
import com.rebwon.taskagile.domain.common.security.CurrentUser;
import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.team.Team;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.MyDataResult;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MeApiController {
  private final TeamService teamService;
  private final BoardService boardService;

  @GetMapping("/api/me")
  public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
    List<Team> teams = teamService.findTeamsByUserId(currentUser.getUserId());
    List<Board> boards = boardService.findBoardsByMembership(currentUser.getUserId());
    return MyDataResult.build(currentUser, teams, boards);
  }
}
