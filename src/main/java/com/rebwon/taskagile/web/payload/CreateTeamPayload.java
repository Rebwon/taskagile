package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.CreateTeamCommand;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.Setter;

@Setter
public class CreateTeamPayload {
  private String name;

  public CreateTeamCommand toCommand(UserId userId) {
    return new CreateTeamCommand(userId, name);
  }
}
