package mhdanh.ejbdoc.arquillian.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDao {
	
	@PersistenceContext(name = "EJBDocArquillianPU")
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
