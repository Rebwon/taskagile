package com.rebwon.taskagile.web.apis;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rebwon.taskagile.domain.application.TeamService;
import com.rebwon.taskagile.domain.application.commands.CreateTeamCommand;
import com.rebwon.taskagile.domain.common.security.CurrentUser;
import com.rebwon.taskagile.domain.model.team.Team;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.web.payload.CreateTeamPayload;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.CreateTeamResult;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TeamApiController extends AbstractBaseController {
  private final TeamService teamService;

  @PostMapping("/api/teams")
  public ResponseEntity<ApiResult> createTeam(@RequestBody CreateTeamPayload payload, HttpServletRequest request) {
    CreateTeamCommand command = payload.toCommand();
    addTriggeredBy(command, request);

    Team team = teamService.createTeam(command);
    return CreateTeamResult.build(team);
  }
}
