package com.rebwon.taskagile.infra.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.rebwon.taskagile.domain.model.board.BoardMember;
import com.rebwon.taskagile.domain.model.board.BoardMemberRepository;

@Repository
public class HibernateBoardMemberRepository extends HibernateSupport<BoardMember> implements BoardMemberRepository {
  HibernateBoardMemberRepository(EntityManager entityManager) {
    super(entityManager);
  }
}
