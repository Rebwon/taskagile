package com.rebwon.taskagile.domain.application.commands;

import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamCommand {
  private UserId userId;
  private String name;
}
