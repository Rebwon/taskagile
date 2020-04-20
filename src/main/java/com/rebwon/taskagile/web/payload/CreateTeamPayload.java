package com.rebwon.taskagile.web.payload;

import com.rebwon.taskagile.domain.application.commands.CreateTeamCommand;
import lombok.Setter;

@Setter
public class CreateTeamPayload {
  private String name;

  public CreateTeamCommand toCommand() {
    return new CreateTeamCommand(name);
  }
}
