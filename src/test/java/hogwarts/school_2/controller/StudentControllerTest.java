package hogwarts.school_2.controller;

import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.service.FacultyService;
import hogwarts.school_2.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static hogwarts.school_2.controller.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldCreateStudent() throws Exception {

        // выполняем post-запрос путем вызова метода postForEntity() и сразу же получаем ответ в переменной newStudentRs
        ResponseEntity<Student> newStudentRs = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                MOCK_STUDENT,
                Student.class
        );

        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student newStudent = newStudentRs.getBody();
        // из объекта ResponseEntity получаем студента
        assertThat(newStudent.getName()).isEqualTo(MOCK_STUDENT.getName());
        assertThat(newStudent.getAge()).isEqualTo(MOCK_STUDENT.getAge());
        // id не проверяем, так как id будет установлен автоматически и не будет тем, который был передан в запросе
    }

    @Test
    public void shouldReturnStudentById() throws Exception {
        // создаем студента путем вызова метода сервиса, сервис вызывает репозиторий, а репозиторий добавляет студента
        // в базу данных; студент не моковый; при добавлении студента в базу данных ему автоматически присваивается id,
        // который не соответствует тому id, с которым студент был отправлен на вход в метод
        Student createdStudent = createMockStudent();

        ResponseEntity<Student> getStudentRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + createdStudent.getId(),
                Student.class
        );

        assertThat(getStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student = getStudentRs.getBody();
        // из объекта ResponseEntity получаем студента
        assertThat(student.getName()).isEqualTo(createdStudent.getName());
        assertThat(student.getAge()).isEqualTo(createdStudent.getAge());
    }

    @Test
    public void shouldDeleteStudent() throws Exception {

        Student createdStudent = createMockStudent();

        restTemplate.delete(
                "http://localhost:" + port + "/student/" + createdStudent.getId(),
                Student.class
        );

        ResponseEntity<Student> getStudentRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + createdStudent.getId(),
                Student.class
        );

        assertThat(getStudentRs.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldEditStudent() throws Exception {

        // создали в базе данных студента
        Student createdStudent = createMockStudent();
        // изменили у студента имя
        createdStudent.setName(MOCK_STUDENT_NEW_NAME);

        // отправили запрос на обновление студента, ранее созданного в базе данных
        restTemplate.put(
                "http://localhost:" + port + "/student",
                createdStudent,
                Student.class
        );

        // отправили запрос на получение из базы данных студента по id
        ResponseEntity<Student> getStudentRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + createdStudent.getId(),
                Student.class
        );

        assertThat(getStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student = getStudentRs.getBody();
        assertThat(student.getName()).isEqualTo(createdStudent.getName());
    }

    @Test
    public void shouldReturnAllStudents() throws Exception {

        createMockStudent(1L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE);
        createMockStudent(2L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE + 1);
        createMockStudent(3L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE + 3);
        createMockStudent(4L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE + 5);
        createMockStudent(5L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE + 6);

        List<Student> students = restTemplate.exchange(
                "http://localhost:" + port + "/student/all",
                HttpMethod.GET,
                null,
                // заголовки
                new ParameterizedTypeReference<List<Student>>() {}
                // данный объект используется для того, чтобы в ответе пришел список студентов
        ).getBody();


        assertFalse(students.isEmpty());
        // проверяем, что коллекция не пустая
        assertTrue(students.size() == 5);
    }

    @Test
    public void shouldReturnStudentsByAge() throws Exception {

        createMockStudent(1L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE);
        createMockStudent(2L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE + 1);
        createMockStudent(3L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE + 3);
        createMockStudent(4L, MOCK_STUDENT_NAME, MOCK_STUDENT_AGE + 5);

        List<Student> students = restTemplate.exchange(
                "http://localhost:" + port + "/student/get-by/" + MOCK_STUDENT_AGE + "/" + (MOCK_STUDENT_AGE + 2),
                HttpMethod.GET,
                null,
                // заголовки
                new ParameterizedTypeReference<List<Student>>() {
                }
                // данный объект используется для того, чтобы в ответе пришел список студентов
        ).getBody();

        assertTrue(students.size() == 2);
    }

    @Test
    public void shouldReturnFacultyOfStudents() throws Exception {
        Faculty createdFaculty = facultyService.create(MOCK_FACULTY_1);
        MOCK_STUDENT.setFaculty(createdFaculty);
        Student createdStudent = studentService.create(MOCK_STUDENT);

        ResponseEntity<Faculty> getStudentFacultyRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/faculty/" + createdStudent.getId(),
                Faculty.class
        );

        assertThat(getStudentFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = getStudentFacultyRs.getBody();
        assertNotNull(faculty);
        assertThat(faculty).isEqualTo(MOCK_STUDENT.getFaculty());
    }

    // метод для создания студента в базе данных
    public Student createMockStudent() {
        return studentService.create(MOCK_STUDENT);
    }

    // метод для создания студента в базе данных с определенными именем и возрастом
    public Student createMockStudent(Long id, String name, Integer age) {
        return studentService.create(new Student(id, name, age));
    }





}