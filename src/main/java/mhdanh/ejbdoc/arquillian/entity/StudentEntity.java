package mhdanh.ejbdoc.arquillian.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.websocket.OnClose;

@Entity
@Table(name = "student")
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;
	private int age;
	private LocalDate birthday;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "class_student_id", nullable = true)
	private ClassStudentEntity classStudentEntity;

	public StudentEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public ClassStudentEntity getClassStudentEntity() {
		return classStudentEntity;
	}

	public void setClassStudentEntity(ClassStudentEntity classStudentEntity) {
		this.classStudentEntity = classStudentEntity;
	}

}
