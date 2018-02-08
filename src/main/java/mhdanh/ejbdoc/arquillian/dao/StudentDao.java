package mhdanh.ejbdoc.arquillian.dao;

import java.util.List;

import javax.ejb.Stateless;

import mhdanh.ejbdoc.arquillian.entity.StudentEntity;

@Stateless
public class StudentDao extends BaseDao {
	
	public StudentEntity persist(StudentEntity student) {
		getEntityManager().persist(student);
		return student;
	}

	public List<StudentEntity> getAll() {
		return getEntityManager().createQuery("SELECT s FROM StudentEntity s", StudentEntity.class).getResultList();
	}
	
	public void deleteAll() {
		getEntityManager().createQuery("DELETE FROM StudentEntity").executeUpdate();
		getEntityManager().flush();
	}
	
}
