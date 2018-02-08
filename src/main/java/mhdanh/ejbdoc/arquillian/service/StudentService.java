package mhdanh.ejbdoc.arquillian.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import mhdanh.ejbdoc.arquillian.dao.StudentDao;
import mhdanh.ejbdoc.arquillian.entity.StudentEntity;

@Stateless
public class StudentService {
	
	@Inject
	private StudentDao studentDao;
	
	public List<StudentEntity> getAll() {
		return studentDao.getAll();
	}

	public StudentEntity addStudent(StudentEntity studentEntity) {
		return studentDao.persist(studentEntity);
	}

}
