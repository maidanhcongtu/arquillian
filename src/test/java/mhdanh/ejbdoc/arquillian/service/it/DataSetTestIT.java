package mhdanh.ejbdoc.arquillian.service.it;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mhdanh.ejbdoc.arquillian.entity.StudentEntity;
import mhdanh.ejbdoc.arquillian.service.StudentService;
import mhdanh.ejbdoc.arquillian.util.DeploymentFactory;

@RunWith(Arquillian.class)
public class DataSetTestIT extends DeploymentFactory {
	
	
	@Inject
	private StudentService studentService;
	
	@Test
	@InSequence(1)
	@UsingDataSet("datasets/student.xml")
	public void testFirstUsingDataSet() {
		System.out.println("test case 1");
		saveStudent();
		saveStudent();
		saveStudent();
		saveStudent();
		saveStudent();
		List<StudentEntity> studentEntities = studentService.getAll();
		Assert.assertThat(studentEntities.size(), Matchers.is(6));
	}
	
	@Test
	@InSequence(2)
	@UsingDataSet("datasets/student.xml")
	public void testSecondUsingDataSet() {
		System.out.println("test case 2");
		List<StudentEntity> studentEntities = studentService.getAll();
		Assert.assertThat(studentEntities.size(), Matchers.is(1));
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
