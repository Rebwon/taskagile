package com.rebwon.taskagile.domain.application.commands;

import com.rebwon.taskagile.domain.model.cardlist.CardListId;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCardCommand {
  private CardListId cardListId;
  private UserId userId;
  private String title;
  private int position;
}
