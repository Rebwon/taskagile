package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.CreateBoardCommand;
import com.rebwon.taskagile.domain.model.team.TeamId;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.Setter;

@Setter
public class CreateBoardPayload {
  private String name;
  private String description;
  private long teamId;

  public CreateBoardCommand toCommand() {
    return new CreateBoardCommand(name, description, new TeamId(teamId));
  }
}
