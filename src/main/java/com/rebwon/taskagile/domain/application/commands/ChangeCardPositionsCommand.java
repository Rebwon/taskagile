package com.rebwon.taskagile.domain.application.commands;

import java.util.List;

import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.card.CardPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCardPositionsCommand {
  private BoardId boardId;
  private List<CardPosition> cardPositions;
}
