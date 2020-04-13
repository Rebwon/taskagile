package com.rebwon.taskagile.domain.application;

import java.util.List;

import com.rebwon.taskagile.domain.application.commands.CreateTeamCommand;
import com.rebwon.taskagile.domain.model.team.Team;
import com.rebwon.taskagile.domain.model.user.UserId;

public interface TeamService {
  List<Team> findTeamsByUserId(UserId userId);
  Team createTeam(CreateTeamCommand command);
}
