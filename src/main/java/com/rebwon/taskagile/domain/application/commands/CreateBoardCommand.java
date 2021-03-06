package com.rebwon.taskagile.domain.application.commands;

import com.rebwon.taskagile.domain.model.team.TeamId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardCommand extends UserCommand {
  private String name;
  private String description;
  private TeamId teamId;
}
