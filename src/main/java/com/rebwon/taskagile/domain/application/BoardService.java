package com.rebwon.taskagile.domain.application;

import java.util.List;

import com.rebwon.taskagile.domain.application.commands.CreateBoardCommand;
import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.board.BoardId;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserId;
import com.rebwon.taskagile.domain.model.user.UserNotFoundException;

public interface BoardService {
  List<Board> findBoardsByMembership(UserId userId);
  Board findById(BoardId boardId);
  List<User> findMembers(BoardId boardId);
  Board createBoard(CreateBoardCommand command);
  User addMember(BoardId boardId, String usernameOrEmailAddress) throws UserNotFoundException;
}
