package com.rebwon.taskagile.domain.model.board;

import java.util.List;

import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserId;

public interface BoardMemberRepository {
  List<User> findMembers(BoardId boardId);
  void save(BoardMember boardMember);
  void add(BoardId boardId, UserId userId);
}
