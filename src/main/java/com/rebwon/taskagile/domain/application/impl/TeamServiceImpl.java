package com.rebwon.taskagile.domain.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rebwon.taskagile.domain.application.TeamService;
import com.rebwon.taskagile.domain.application.commands.CreateTeamCommand;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.model.team.Team;
import com.rebwon.taskagile.domain.model.team.TeamId;
import com.rebwon.taskagile.domain.model.team.TeamRepository;
import com.rebwon.taskagile.domain.model.team.event.TeamCreatedEvent;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
  private final TeamRepository teamRepository;
  private final DomainEventPublisher domainEventPublisher;

  @Override
  public List<Team> findTeamsByUserId(
    UserId userId) {
    return teamRepository.findTeamsByUserId(userId);
  }

  @Override
  public Team createTeam(CreateTeamCommand command) {
    Team team = Team.create(command.getName(), command.getUserId());
    teamRepository.save(team);
    domainEventPublisher.publish(new TeamCreatedEvent(team, command));
    return team;
  }

  @Override
  public Team findById(TeamId teamId) {
    return teamRepository.findById(teamId);
  }
}
