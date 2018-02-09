package mhdanh.ejbdoc.arquillian.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import mhdanh.ejbdoc.arquillian.entity.StudentEntity;
import mhdanh.ejbdoc.arquillian.service.StudentService;

@Path("students")
public class StudentResource {
	
	@Inject
	private StudentService studentService;
	
	@GET
	public List<StudentEntity> getAllStudent() {
		return studentService.getAll();
	}
	
	@POST
	public StudentEntity saveStudent(StudentEntity studentEntity) {
		return studentService.addStudent(studentEntity);
	}
	
}
