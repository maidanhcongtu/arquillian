package mhdanh.ejbdoc.arquillian.service.it;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mhdanh.ejbdoc.arquillian.dao.StudentDao;
import mhdanh.ejbdoc.arquillian.entity.StudentEntity;
import mhdanh.ejbdoc.arquillian.service.StudentService;
import mhdanh.ejbdoc.arquillian.util.DeploymentFactory;

@RunWith(Arquillian.class)
public class StudentServiceIT extends DeploymentFactory {
	
	@Inject
	private StudentService studentService;
	
	@Inject
	private StudentDao studentDao;
	
	@Test
	public void testSaveStudent() {
		studentDao.deleteAll();
		StudentEntity result = saveStudent();
		Assert.assertThat(result.getId(), Matchers.greaterThan(0l));
	}
	
	@Test
	public void testGetAllStudent() {
		studentDao.deleteAll();
		saveStudent();
		List<StudentEntity> students = studentService.getAll();
		Assert.assertThat(students.size(), Matchers.is(1));
	}
	
	@Test
	@UsingDataSet(value="datasets/student.xml")
	public void testUsingDataSet() {
		List<StudentEntity> students = studentService.getAll();
		Assert.assertThat(students.size(), Matchers.is(1));
	}

	private StudentEntity saveStudent() {
		StudentEntity studentEntity = new StudentEntity();
		studentEntity.setName("mai huu danh");
		studentEntity.setBirthday(LocalDate.now());
		studentEntity.setAge(30);
		StudentEntity result = studentService.addStudent(studentEntity);
		return result;
	}
	
}
