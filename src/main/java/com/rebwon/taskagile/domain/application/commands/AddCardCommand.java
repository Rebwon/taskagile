package com.rebwon.taskagile.domain.application.commands;

import com.rebwon.taskagile.domain.model.cardlist.CardListId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCardCommand extends UserCommand {
  private CardListId cardListId;
  private String title;
  private int position;
}
