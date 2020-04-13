package com.rebwon.taskagile.infra.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;

abstract class HibernateSupport<T> {
	EntityManager entityManager;

	HibernateSupport(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	public void save(T object) {
	  entityManager.persist(object);
	  entityManager.flush();
  }
}
