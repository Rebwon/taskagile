package com.rebwon.taskagile.infra.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import com.rebwon.taskagile.domain.model.board.Board;
import com.rebwon.taskagile.domain.model.board.BoardRepository;
import com.rebwon.taskagile.domain.model.user.UserId;

@Repository
public class HibernateBoardRepository extends HibernateSupport<Board> implements BoardRepository {
  HibernateBoardRepository(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public List<Board> findBoardsByMembership(UserId userId) {
    String sql = "SELECT b.* FROM board b LEFT JOIN board_member bm ON b.id = bm.board_id WHERE bm.user_id = :userId";
    NativeQuery<Board> query = getSession().createNativeQuery(sql, Board.class);
    query.setParameter("userId", userId.value());
    return query.list();
  }
}
