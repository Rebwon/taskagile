package com.rebwon.taskagile.domain.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rebwon.taskagile.domain.application.BoardService;
import com.rebwon.taskagile.domain.application.commands.CreateBoardCommand;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.board.BoardManagement;
import com.rebwon.taskagile.domain.model.board.BoardRepository;
import com.rebwon.taskagile.domain.model.board.event.BoardCreatedEvent;
import com.rebwon.taskagile.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
  private final BoardRepository boardRepository;
  private final BoardManagement boardManagement;
  private final DomainEventPublisher domainEventPublisher;

  @Override
  public List<Board> findBoardsByMembership(
    UserId userId) {
    return boardRepository.findBoardsByMembership(userId);
  }

  @Override
  public Board createBoard(CreateBoardCommand command) {
    Board board = boardManagement.createBoard(command.getUserId(), command.getName(),
      command.getDescription(), command.getTeamId());
    domainEventPublisher.publish(new BoardCreatedEvent(this, board));
    return board;
  }
}
