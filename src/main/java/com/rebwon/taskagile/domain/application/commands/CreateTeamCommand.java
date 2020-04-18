package com.rebwon.taskagile.domain.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamCommand extends UserCommand {
  private String name;
}
