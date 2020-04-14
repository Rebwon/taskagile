package com.rebwon.taskagile.domain.model.board;

import java.util.List;

import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserId;
import com.rebwon.taskagile.domain.model.user.UserNotFoundException;

public interface BoardRepository {
  List<Board> findBoardsByMembership(UserId userId);
  void save(Board board);
	Board findById(BoardId boardId);
}
