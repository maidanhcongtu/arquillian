package mhdanh.ejbdoc.arquillian.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import mhdanh.ejbdoc.arquillian.entity.StudentEntity;
import mhdanh.ejbdoc.arquillian.service.StudentService;

@Path("students")
public class StudentResource {
	
	@Inject
	private StudentService studentService;
	
	@GET
	public Response getAllStudent() {
		List<StudentEntity> students = studentService.getAll();
		return Response.ok().entity(students).build();
	}
	
	@POST
	public Response saveStudent(StudentEntity studentEntity) {
		StudentEntity student = studentService.addStudent(studentEntity);
		return Response.ok().entity(student).build();
	}
	
}
