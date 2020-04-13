package com.rebwon.taskagile.web.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.rebwon.taskagile.domain.application.BoardService;
import com.rebwon.taskagile.domain.application.TeamService;
import com.rebwon.taskagile.domain.application.UserService;
import com.rebwon.taskagile.domain.common.security.CurrentUser;
import com.rebwon.taskagile.domain.common.security.TokenManager;
import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.team.Team;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.MyDataResult;

@Controller
public class MeApiController {
  private TeamService teamService;
  private BoardService boardService;
  private UserService userService;
  private TokenManager tokenManager;
  private String realTimeServerUrl;

  public MeApiController(TeamService teamService, BoardService boardService,
    UserService userService, TokenManager tokenManager, @Value("${app.real-time-server-url}") String realTimeServerUrl) {
    this.teamService = teamService;
    this.boardService = boardService;
    this.userService = userService;
    this.tokenManager = tokenManager;
    this.realTimeServerUrl = realTimeServerUrl;
  }

  @GetMapping("/api/me")
  public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
    User user = userService.findById(currentUser.getUserId());
    List<Team> teams = teamService.findTeamsByUserId(currentUser.getUserId());
    List<Board> boards = boardService.findBoardsByMembership(currentUser.getUserId());
    String realTimeToken = tokenManager.jwt(user.getId());
    return MyDataResult.build(user, teams, boards, realTimeServerUrl, realTimeToken);
  }
}
