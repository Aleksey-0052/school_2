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
class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldCreateFaculty() throws Exception {

        ResponseEntity<Faculty> newFacultyRs = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                MOCK_FACULTY_1,
                Faculty.class
        );

        assertThat(newFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty newFaculty = newFacultyRs.getBody();
        assertThat(newFaculty.getName()).isEqualTo(MOCK_FACULTY_1.getName());
        assertThat(newFaculty.getColor()).isEqualTo(MOCK_FACULTY_1.getColor());
    }

    @Test
    public void shouldReturnFacultyById() throws Exception {
        // создаем объект типа Faculty путем вызова метода сервиса, сервис вызывает репозиторий, а репозиторий добавляет
        // объект-факультет в базу данных; факультет не моковый; при добавлении факультета в базу данных ему автоматически
        // присваивается id, который не соответствует тому id, с которым факультет был отправлен на вход в метод

        Faculty createdFaculty = createMockFaculty();

        ResponseEntity<Faculty> getFacultyRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + createdFaculty.getId(),
                Faculty.class
        );

        assertThat(getFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = getFacultyRs.getBody();
        assertThat(faculty.getName()).isEqualTo(createdFaculty.getName());
        assertThat(faculty.getColor()).isEqualTo(createdFaculty.getColor());
    }

    @Test
    public void shouldDeleteFaculty() throws Exception {

        Faculty createdFaculty = createMockFaculty(2L, MOCK_FACULTY_NAME_2, MOCK_FACULTY_COLOR_2);

        restTemplate.delete(
                "http://localhost:" + port + "/faculty/" + createdFaculty.getId(),
                Faculty.class
        );

        ResponseEntity<Faculty> getFacultyRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + createdFaculty.getId(),
                Faculty.class
        );

        assertThat(getFacultyRs.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldEditFaculty() throws Exception {

        Faculty createdFaculty = createMockFaculty();
        createdFaculty.setColor(MOCK_FACULTY_NEW_COLOR);

        restTemplate.put(
                "http://localhost:" + port + "/faculty",
                createdFaculty,
                Faculty.class
        );

        ResponseEntity<Faculty> getFacultyRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + createdFaculty.getId(),
                Faculty.class
        );

        assertThat(getFacultyRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = getFacultyRs.getBody();
        assertThat(faculty.getColor()).isEqualTo(createdFaculty.getColor());
    }

    @Test
    public void shouldReturnAllFaculties() throws Exception {

        facultyService.create(MOCK_FACULTY_1);
        facultyService.create(MOCK_FACULTY_2);
        facultyService.create(MOCK_FACULTY_3);
        facultyService.create(MOCK_FACULTY_4);

        List<Faculty> faculties = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/all",
                HttpMethod.GET,
                null,
                // заголовки
                new ParameterizedTypeReference<List<Faculty>>() {}
                // данный объект используется для того, чтобы в ответе пришел список факультетов
        ).getBody();

        assertFalse(faculties.isEmpty());
        assertTrue(faculties.size() == 4);
        assertThat(faculties).isEqualTo(MOCK_FACULTIES);
    }

    @Test
    public void shouldReturnFacultiesByNameOrColor() throws Exception {

        facultyService.create(MOCK_FACULTY_1);
        facultyService.create(MOCK_FACULTY_2);
        facultyService.create(MOCK_FACULTY_3);
        facultyService.create(MOCK_FACULTY_4);


        // Получаем коллекцию факультетов по имени
        List<Faculty> faculties1 = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/get-by-name-or-color?name=" + MOCK_FACULTY_NAME_2,
                HttpMethod.GET,
                null,
                // заголовки
                new ParameterizedTypeReference<List<Faculty>>() {
                }
                // данный объект используется для того, чтобы в ответе пришел список факультетов
        ).getBody();

        assertTrue(faculties1.size() == 1);
        assertThat(faculties1).isEqualTo(MOCK_FACULTIES_BY_NAME);


        // Получаем коллекцию факультетов по цвету
        List<Faculty> faculties2 = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/get-by-name-or-color?color=Green",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {
                }
        ).getBody();

        assertTrue(faculties2.size() == 2);
        assertThat(faculties2).isEqualTo(MOCK_FACULTIES_BY_COLOR);
    }

    @Test
    public void shouldReturnStudentsOfFaculty() throws Exception {

        Faculty createdFaculty = facultyService.create(MOCK_FACULTY_4);
        // сохраняем объект факультет в базу данных

        MOCK_STUDENT_1.setFaculty(createdFaculty);
        MOCK_STUDENT_2.setFaculty(createdFaculty);
        MOCK_STUDENT_3.setFaculty(createdFaculty);
        MOCK_STUDENT_4.setFaculty(createdFaculty);
        MOCK_STUDENT_5.setFaculty(createdFaculty);
        MOCK_STUDENT_6.setFaculty(createdFaculty);

        Student createdStudent1 = studentService.create(MOCK_STUDENT_1);
        Student createdStudent2 = studentService.create(MOCK_STUDENT_2);
        Student createdStudent3 = studentService.create(MOCK_STUDENT_3);
        Student createdStudent4 = studentService.create(MOCK_STUDENT_4);
        Student createdStudent5 = studentService.create(MOCK_STUDENT_5);
        Student createdStudent6 = studentService.create(MOCK_STUDENT_6);

        List<Student> students = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/students/" + createdFaculty.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {}
        ).getBody();

        assertNotNull(students);
        assertThat(students).isEqualTo(MOCK_STUDENTS);
    }

    @Test
    public void shouldReturnLongestFacultyName() throws Exception {

        facultyService.create(MOCK_FACULTY_1);
        facultyService.create(MOCK_FACULTY_2);
        facultyService.create(MOCK_FACULTY_3);
        facultyService.create(MOCK_FACULTY_4);

        ResponseEntity<String> getLongestFacultyNameRs = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/longest-faculty-name",
                String.class
        );

        assertThat(getLongestFacultyNameRs.getStatusCode()).isEqualTo(HttpStatus.OK);
        String longestName = getLongestFacultyNameRs.getBody();
        assertNotNull(longestName);
        assertThat(longestName).isEqualTo(MOCK_FACULTY_NAME_3);

    }





    public Faculty createMockFaculty() {
        return facultyService.create(MOCK_FACULTY_1);
    }

    // метод для создания студента в базе данных с определенными именем и возрастом
    public Faculty createMockFaculty(Long id, String name, String color) {
        return facultyService.create(new Faculty(id, name, color));
    }

}