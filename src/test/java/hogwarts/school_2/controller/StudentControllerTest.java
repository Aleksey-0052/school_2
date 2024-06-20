package hogwarts.school_2.controller;

import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.service.FacultyService;
import hogwarts.school_2.service.StudentService;
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


// для создания тестов создана база данных (файл - application.properties)
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
                MOCK_STUDENT_1,
                Student.class
        );

        assertThat(newStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student newStudent = newStudentRs.getBody();
        // из объекта ResponseEntity получаем студента
        assertThat(newStudent.getName()).isEqualTo(MOCK_STUDENT_1.getName());
        assertThat(newStudent.getAge()).isEqualTo(MOCK_STUDENT_1.getAge());
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

        createMockStudent(1L, MOCK_STUDENT_NAME_1, MOCK_STUDENT_AGE_1);
        createMockStudent(2L, MOCK_STUDENT_NAME_2, MOCK_STUDENT_AGE_2);
        createMockStudent(3L, MOCK_STUDENT_NAME_3, MOCK_STUDENT_AGE_3);
        createMockStudent(4L, MOCK_STUDENT_NAME_4, MOCK_STUDENT_AGE_4);
        createMockStudent(5L, MOCK_STUDENT_NAME_5, MOCK_STUDENT_AGE_5);
        createMockStudent(6L, MOCK_STUDENT_NAME_6, MOCK_STUDENT_AGE_6);

        List <Student> students = restTemplate.exchange(
                "http://localhost:" + port + "/student/all",
                HttpMethod.GET,
                null,
                // заголовки
                new ParameterizedTypeReference<List<Student>>() {}
                // данный объект используется для того, чтобы в ответе пришел список студентов
        ).getBody();

        assertFalse(students.isEmpty());
        // проверяем, что коллекция не пустая
        assertTrue(students.size() == MOCK_STUDENTS.size());
        assertThat(students).isEqualTo(MOCK_STUDENTS);

    }

    @Test
    public void shouldReturnStudentsByAge() throws Exception {

        createMockStudent(1L, MOCK_STUDENT_NAME_1, MOCK_STUDENT_AGE_1);
        createMockStudent(2L, MOCK_STUDENT_NAME_2, MOCK_STUDENT_AGE_2);
        createMockStudent(3L, MOCK_STUDENT_NAME_3, MOCK_STUDENT_AGE_3);
        createMockStudent(4L, MOCK_STUDENT_NAME_4, MOCK_STUDENT_AGE_4);
        createMockStudent(5L, MOCK_STUDENT_NAME_5, MOCK_STUDENT_AGE_5);
        createMockStudent(6L, MOCK_STUDENT_NAME_6, MOCK_STUDENT_AGE_6);

        List <Student> students = restTemplate.exchange(
                "http://localhost:" + port + "/student/get-by/" + 21 + "/" + 22,
                HttpMethod.GET,
                null,
                // заголовки
                new ParameterizedTypeReference<List<Student>>() {}
                // данный объект используется для того, чтобы в ответе пришел список студентов
        ).getBody();

        assertTrue(students.size() == MOCK_STUDENTS_BY_AGE.size());
        assertThat(students).isEqualTo(MOCK_STUDENTS_BY_AGE);
    }

    @Test
    public void shouldReturnFacultyOfStudents() throws Exception {
        Faculty createdFaculty = facultyService.create(MOCK_FACULTY_4);

        MOCK_STUDENT_1.setFaculty(createdFaculty);
        Student createdStudent = studentService.create(MOCK_STUDENT_1);

        ResponseEntity<Faculty> getStudentFacultyRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/faculty/" + createdStudent.getId(),
                Faculty.class
        );

        assertThat(getStudentFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = getStudentFacultyRs.getBody();
        assertNotNull(faculty);
        assertThat(faculty).isEqualTo(MOCK_STUDENT_1.getFaculty());
    }

    // получение количества всех студентов
    @Test
    public void  shouldReturnTotalCountOfStudents() throws Exception {

        createMockStudent(1L, MOCK_STUDENT_NAME_1, MOCK_STUDENT_AGE_1);
        createMockStudent(2L, MOCK_STUDENT_NAME_2, MOCK_STUDENT_AGE_2);
        createMockStudent(3L, MOCK_STUDENT_NAME_3, MOCK_STUDENT_AGE_3);
        createMockStudent(4L, MOCK_STUDENT_NAME_4, MOCK_STUDENT_AGE_4);
        createMockStudent(5L, MOCK_STUDENT_NAME_5, MOCK_STUDENT_AGE_5);
        createMockStudent(6L, MOCK_STUDENT_NAME_6, MOCK_STUDENT_AGE_6);

        ResponseEntity<Integer> getAmountOfStudentRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/count",
                Integer.class
        );

        assertThat(getAmountOfStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        int count = getAmountOfStudentRs.getBody();
        // из объекта ResponseEntity получаем количество
        assertThat(count).isEqualTo(MOCK_STUDENTS.size());
    }

    // получение среднего возраста студентов
    @Test
    public void shouldReturnAverageAgeOfStudents() throws Exception {

        createMockStudent(1L, MOCK_STUDENT_NAME_1, MOCK_STUDENT_AGE_1);
        createMockStudent(2L, MOCK_STUDENT_NAME_2, MOCK_STUDENT_AGE_2);
        createMockStudent(3L, MOCK_STUDENT_NAME_3, MOCK_STUDENT_AGE_3);
        createMockStudent(4L, MOCK_STUDENT_NAME_4, MOCK_STUDENT_AGE_4);
        createMockStudent(5L, MOCK_STUDENT_NAME_5, MOCK_STUDENT_AGE_5);
        createMockStudent(6L, MOCK_STUDENT_NAME_6, MOCK_STUDENT_AGE_6);

        ResponseEntity<Double> getAverageAgeOfStudentRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/average-age",
                Double.class
        );

        assertThat(getAverageAgeOfStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        double averageAge = getAverageAgeOfStudentRs.getBody();
        // из объекта ResponseEntity получаем средний возраст
        assertThat(averageAge).isEqualTo(((double) MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 + MOCK_STUDENT_AGE_3 +
                MOCK_STUDENT_AGE_4 + MOCK_STUDENT_AGE_5 + MOCK_STUDENT_AGE_6) / 6);
    }

    // получение количества всех студентов через создание interface projection
    @Test
    public void shouldReturnAmountOfStudents() throws Exception {
        createMockStudent(1L, MOCK_STUDENT_NAME_1, MOCK_STUDENT_AGE_1);
        createMockStudent(2L, MOCK_STUDENT_NAME_2, MOCK_STUDENT_AGE_2);
        createMockStudent(3L, MOCK_STUDENT_NAME_3, MOCK_STUDENT_AGE_3);
        createMockStudent(4L, MOCK_STUDENT_NAME_4, MOCK_STUDENT_AGE_4);
        createMockStudent(5L, MOCK_STUDENT_NAME_5, MOCK_STUDENT_AGE_5);
        createMockStudent(6L, MOCK_STUDENT_NAME_6, MOCK_STUDENT_AGE_6);

        ResponseEntity<Integer> getAmountOfStudentRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/amount",
                Integer.class
        );

        assertThat(getAmountOfStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        int count = getAmountOfStudentRs.getBody();
        // из объекта ResponseEntity получаем количество
        assertThat(count).isEqualTo(MOCK_STUDENTS.size());
    }

    // получение пяти последних студентов
    @Test
    public void shouldReturnLastFive() throws Exception {

        createMockStudent(1L, MOCK_STUDENT_NAME_1, MOCK_STUDENT_AGE_1);
        createMockStudent(2L, MOCK_STUDENT_NAME_2, MOCK_STUDENT_AGE_2);
        createMockStudent(3L, MOCK_STUDENT_NAME_3, MOCK_STUDENT_AGE_3);
        createMockStudent(4L, MOCK_STUDENT_NAME_4, MOCK_STUDENT_AGE_4);
        createMockStudent(5L, MOCK_STUDENT_NAME_5, MOCK_STUDENT_AGE_5);
        createMockStudent(6L, MOCK_STUDENT_NAME_6, MOCK_STUDENT_AGE_6);

        List <Student> students = restTemplate.exchange(
                "http://localhost:" + port + "/student/last-five",
                HttpMethod.GET,
                null,
                // заголовки
                new ParameterizedTypeReference<List<Student>>() {}
                // данный объект используется для того, чтобы в ответе пришел список студентов
        ).getBody();

        assertFalse(students.isEmpty());
        // проверяем, что коллекция не пустая
        assertTrue(students.size() == 5);

        assertThat(students.get(0)).isEqualTo(MOCK_STUDENT_2);
        assertThat(students.get(1)).isEqualTo(MOCK_STUDENT_3);
        assertThat(students.get(2)).isEqualTo(MOCK_STUDENT_4);
        assertThat(students.get(3)).isEqualTo(MOCK_STUDENT_5);
        assertThat(students.get(4)).isEqualTo(MOCK_STUDENT_6);

    }

    @Test
    public void shouldReturnStudentNamesStartingWithA() throws Exception {

        createMockStudent(1L, MOCK_STUDENT_NAME_1, MOCK_STUDENT_AGE_1);
        createMockStudent(2L, MOCK_STUDENT_NAME_2, MOCK_STUDENT_AGE_2);
        createMockStudent(3L, MOCK_STUDENT_NAME_3, MOCK_STUDENT_AGE_3);
        createMockStudent(4L, MOCK_STUDENT_NAME_4, MOCK_STUDENT_AGE_4);
        createMockStudent(5L, MOCK_STUDENT_NAME_5, MOCK_STUDENT_AGE_5);
        createMockStudent(6L, MOCK_STUDENT_NAME_6, MOCK_STUDENT_AGE_6);

        List<String> studentNames = restTemplate.exchange(
                "http://localhost:" + port + "/student/names-by-a",
                HttpMethod.GET,
                null,
                // заголовки
                new ParameterizedTypeReference<List<String>>() {}
                // данный объект используется для того, чтобы в ответе пришел список имен студентов
        ).getBody();

        assertFalse(studentNames.isEmpty());
        assertTrue(studentNames.size() == MOCK_STUDENT_NAMES_WITH_STARTING_A.size());
        assertThat(studentNames.get(0)).isEqualTo(MOCK_STUDENT_NAME_2.toUpperCase());
        assertThat(studentNames.get(1)).isEqualTo(MOCK_STUDENT_NAME_4.toUpperCase());
        assertThat(studentNames.get(2)).isEqualTo(MOCK_STUDENT_NAME_6.toUpperCase());
    }

    // получение среднего возраста студентов
    @Test
    public void shouldReturnAverageAgeOfStudentsByStream() throws Exception {

        createMockStudent(1L, MOCK_STUDENT_NAME_1, MOCK_STUDENT_AGE_1);
        createMockStudent(2L, MOCK_STUDENT_NAME_2, MOCK_STUDENT_AGE_2);
        createMockStudent(3L, MOCK_STUDENT_NAME_3, MOCK_STUDENT_AGE_3);
        createMockStudent(4L, MOCK_STUDENT_NAME_4, MOCK_STUDENT_AGE_4);
        createMockStudent(5L, MOCK_STUDENT_NAME_5, MOCK_STUDENT_AGE_5);
        createMockStudent(6L, MOCK_STUDENT_NAME_6, MOCK_STUDENT_AGE_6);

        ResponseEntity<Double> getAverageAgeOfStudentRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/average-age-stream",
                Double.class
        );

        assertThat(getAverageAgeOfStudentRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        double averageAge = getAverageAgeOfStudentRs.getBody();
        // из объекта ResponseEntity получаем средний возраст
        assertThat(averageAge).isEqualTo(((double) MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 + MOCK_STUDENT_AGE_3 +
                MOCK_STUDENT_AGE_4 + MOCK_STUDENT_AGE_5 + MOCK_STUDENT_AGE_6) / 6);
    }





    // метод для создания студента в базе данных
    public Student createMockStudent() {
        return studentService.create(MOCK_STUDENT_1);
    }

    // метод для создания студента в базе данных с определенными именем и возрастом
    public Student createMockStudent(Long id, String name, Integer age) {
        return studentService.create(new Student(id, name, age));
    }

}