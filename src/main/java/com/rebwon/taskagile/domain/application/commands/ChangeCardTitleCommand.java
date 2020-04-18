package com.rebwon.taskagile.domain.application.commands;

import com.rebwon.taskagile.domain.model.card.CardId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCardTitleCommand extends UserCommand {
  private CardId cardId;
  private String title;
}
