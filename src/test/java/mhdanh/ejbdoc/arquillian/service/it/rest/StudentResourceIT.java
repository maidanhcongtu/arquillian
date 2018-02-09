package mhdanh.ejbdoc.arquillian.service.it.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mhdanh.ejbdoc.arquillian.LocalDateAdapter;
import mhdanh.ejbdoc.arquillian.entity.ClassStudentEntity;
import mhdanh.ejbdoc.arquillian.entity.StudentEntity;
import mhdanh.ejbdoc.arquillian.service.StudentService;
import mhdanh.ejbdoc.arquillian.util.DeploymentFactory;

@RunWith(Arquillian.class)
public class StudentResourceIT {
	
	@Deployment(testable = true)
	public static WebArchive createDeployment() {
		return DeploymentFactory.createFullDeployment();
	}

	@Inject
	private StudentService studentService;
	
	@PersistenceContext(name = "EJBDocArquillianPU")
	private EntityManager em;
	
	@Test
	@InSequence(1)
	@UsingDataSet({"datasets/classStudent.xml", "datasets/student.xml"})
	@Cleanup(phase = TestExecutionPhase.BEFORE)
	public void testSaveStudent() {
		saveStudent();
	}
	
	@Test
	@RunAsClient
	@InSequence(2)
	public void testGetAllStudent(@ArquillianResource URL url) {
		//POST request -> create new student
		WebTarget webTargetPost = ClientBuilder.newClient().target(url.toExternalForm()).path("api").path("students");
		Builder builderPost = webTargetPost.request(MediaType.APPLICATION_JSON);
		postAsResponse(buildSampleObject(), builderPost);
		
		WebTarget webTarget = ClientBuilder.newClient().target(url.toExternalForm()).path("api").path("students");
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		
		List<StudentEntity> students = getAsListOf(StudentEntity[].class, builder);
		Assert.assertThat(students.size(), Matchers.is(3));
	}
	
	@Test
	@InSequence(3)
	@UsingDataSet({"datasets/student2.xml"})
	@Cleanup(phase = TestExecutionPhase.BEFORE)
	public void testClearData() {
		List<StudentEntity> students = studentService.getAll();
		Assert.assertThat(students.size(), Matchers.is(2));

		StudentEntity studentEntity = students.get(0);
		Assert.assertThat(studentEntity.getId(), Matchers.is(2l));
		Assert.assertThat(studentEntity.getName(), Matchers.is("mai danh 2"));

		StudentEntity studentEntity2 = students.get(1);
		Assert.assertThat(studentEntity2.getId(), Matchers.is(3l));
		Assert.assertThat(studentEntity2.getName(), Matchers.is("mai danh 3"));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(4)
	public void testGetAllStudent2(@ArquillianResource URL url) {
		WebTarget webTarget = ClientBuilder.newClient().target(url.toExternalForm()).path("api").path("students");
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		
		List<StudentEntity> students = getAsListOf(StudentEntity[].class, builder);
		Assert.assertThat(students.size(), Matchers.is(2));
		System.out.println("students size " + students.size());

		StudentEntity studentEntity = students.get(0);
		Assert.assertThat(studentEntity.getId(), Matchers.is(2l));
		Assert.assertThat(studentEntity.getName(), Matchers.is("mai danh 2"));

		StudentEntity studentEntity2 = students.get(1);
		Assert.assertThat(studentEntity2.getId(), Matchers.is(3l));
		Assert.assertThat(studentEntity2.getName(), Matchers.is("mai danh 3"));
	}
	private StudentEntity saveStudent() {
		StudentEntity studentEntity = new StudentEntity();
		studentEntity.setName("mai huu danh");
		studentEntity.setBirthday(LocalDate.now());
		studentEntity.setAge(30);
		ClassStudentEntity clazz = em.find(ClassStudentEntity.class, 1L);
		studentEntity.setClassStudentEntity(clazz);
		
		StudentEntity result = studentService.addStudent(studentEntity);
		return result;
	}
	
	private StudentEntity buildSampleObject() {
		StudentEntity result = new StudentEntity();
		result.setName("mai huu danh xxx");
		result.setBirthday(LocalDate.now());
		result.setAge(55);
		return result;
	}
	
	private Response postAsResponse(Object object, Builder builder) {
		Gson gson = createGson();
		String jsonString = gson.toJson(object);
		
		InputStream is = new ByteArrayInputStream(jsonString.getBytes());

		Response response =  builder.post(Entity.entity(is, MediaType.APPLICATION_JSON_TYPE));
		return response;
	}
	
	public <T> List<T> getAsListOf(Class<T[]> clazz, Builder builder) {
		String jsonString = builder.get(String.class);
		T[] jsonToObject = createGson().fromJson(jsonString, clazz);

		return Arrays.asList(jsonToObject);
	}

	private Gson createGson() {
		GsonBuilder gson = new GsonBuilder();
		gson.setDateFormat("yyyy-MM-dd");

		LocalDateAdapter localDateAdapter = () -> "yyyy-MM-dd";
		gson = localDateAdapter.registerTo(gson);
		return gson.create();
	}
	
	
}
