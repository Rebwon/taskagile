package com.rebwon.taskagile.domain.application;

import java.util.List;

import com.rebwon.taskagile.domain.application.commands.CreateBoardCommand;
import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.user.UserId;

public interface BoardService {
  List<Board> findBoardsByMembership(UserId userId);
  Board createBoard(CreateBoardCommand command);
}
