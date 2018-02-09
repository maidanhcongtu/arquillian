package mhdanh.ejbdoc.arquillian.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mhdanh.ejbdoc.arquillian.entity.StudentEntity;

@Stateless
public class StudentDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public StudentEntity persist(StudentEntity student) {
		em.persist(student);
		return student;
	}

	public List<StudentEntity> getAll() {
		return em.createQuery("SELECT s FROM StudentEntity s", StudentEntity.class).getResultList();
	}
	
	public void deleteAll() {
		em.createQuery("DELETE FROM StudentEntity").executeUpdate();
		em.flush();
	}
	
}
