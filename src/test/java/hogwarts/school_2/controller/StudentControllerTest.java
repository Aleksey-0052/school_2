package hogwarts.school_2.controller;

import hogwarts.school_2.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    // Метод getForObject() принимает URL для запроса и тип ответа. В результате он возвращает сам ответ.
    // получение студента по id
    @Test
    public void testGet() throws Exception {

        Long id = 5L;
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty/" +
                        id, String.class))
                .isNotNull();
    }
    // работает при любом идентификаторе

    // получение всех студентов
    @Test
    public void testGetAllStudents() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all", String.class))
                .isNotNull();
    }

    // получение студентов по возрасту
    @Test
    public void testGetStudentsByAge() throws Exception {

        Integer minAge = 20;
        Integer maxAge = 22;
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/get-by/" +
                        minAge + maxAge, String.class))
                .isNotNull();
    }
    // работает при любом возрасте

    @Test
    public void testGetFaculty() throws Exception {

        Long studentId = 2L;
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty/" +
                        studentId, String.class))
                .isNotNull();
    }
    // работает при любом идентификаторе


    // Метод postForObject() в качестве одного из параметров он принимает объект, который будет находиться в body запроса.
    // создание студента
    @Test
    public void testCreate() throws Exception {

        Student student = new Student();
        student.setName("Васильев");
        student.setAge(27);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }


    // удаление студента
    @Test
    public void testDelete() throws Exception {

        Long id = 11L;
//        Assertions
//                .assertThat(this.restTemplate.delete("http://localhost:" + port + "/student/" + id, String.class))
//                .isNotNull();
    }


    // обновление студента
    @Test
    public void testEdit() throws Exception {


    }

}