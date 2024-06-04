package hogwarts.school_2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.repository.AllStudentsRepository;
import hogwarts.school_2.repository.StudentRepository;
import hogwarts.school_2.service.StudentServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static hogwarts.school_2.controller.TestConstants.*;
import static hogwarts.school_2.controller.TestConstants.MOCK_STUDENTS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTestMock {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AllStudentsRepository allStudentsRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper mapper = new ObjectMapper();
    // с помощью данного класса можно сериализовать объект в JSON
    // необходим для теста shouldAllStudents()


    @Test
    public void shouldCreateStudent() throws Exception {

        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT_1);
        // должен вернуться MOCK_STUDENT

        // Создадим переменную studentObject и заполним ее данными. Эту переменную будем использовать в качестве входных данных.
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", MOCK_STUDENT_NAME_1);
        studentObject.put("age", MOCK_STUDENT_AGE_1);

        // с помощью метода perform() осуществляем вызов эндпоинта
        mockmvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // проверяем, что статус ответа 200
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME_1))
                // проверяем, что имя в json соответствует имени MOCK_FACULTY_NAME, то есть тому, которое передали
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE_1));
        // проверяем, что возраст в json соответствует возрасту MOCK_STUDENT_AGE, то есть тому, который передали
        // при помощи $ извлекаем из json значение соответствующего поля и сравниваем с входящими данными
    }

    @Test
    public void shouldReturnStudentById() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT_1));

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/" + MOCK_STUDENT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME_1))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE_1));
    }

    @Test
    public void shouldDeleteStudent() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT_1));

        mockmvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + MOCK_STUDENT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditStudent() throws Exception {

        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT_1);

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", MOCK_STUDENT_ID_1);
        studentObject.put("name", MOCK_STUDENT_NAME_1);
        studentObject.put("age", MOCK_STUDENT_AGE_1);

        mockmvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME_1))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE_1));
    }

    @Test
    public void shouldReturnAllStudents() throws Exception {
        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
        // проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
        // ObjectMapper метода writeValueAsString() и передачи в параметры метода списка студентов
        // в результате вызова эндпоинта "/student/all" должен вернуться список студентов - MOCK_STUDENTS
    }

    @Test
    public void shouldReturnStudentsByAge() throws Exception {
        when(studentRepository.findByAgeBetween(anyInt(), anyInt()))
                .thenReturn(MOCK_STUDENTS_BY_AGE);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/get-by/" + (MOCK_STUDENT_AGE_2) + "/" + (MOCK_STUDENT_AGE_3))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS_BY_AGE)));
    }
    // данный тест выполняется при любых значениях минимального и максимального возраста

    @Test
    public void shouldReturnFacultyOfStudents() throws Exception {

        MOCK_STUDENT_3.setFaculty(MOCK_FACULTY_3);
        // у объекта типа Student вызываем сеттер и инициализируем его поле - факультет

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT_3));
        // должен быть возвращен объект типа Student с привязанным к нему факультетом

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/" + MOCK_STUDENT_ID_3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTY_3)));
        // проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
        // ObjectMapper метода writeValueAsString() и передачи в параметры метода установленного факультета
    }


    // получение количества всех студентов
    @Test
    public void  shouldReturnTotalCountOfStudents() throws Exception {

        when(studentRepository.getTotalCountOfStudents()).thenReturn(MOCK_STUDENTS.size());

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS.size())));
    }


    // получение среднего возраста студентов
    @Test
    public void shouldReturnAverageAgeOfStudents() throws Exception {

        when(studentRepository.getAverageAgeOfStudents())
                .thenReturn((double) ((MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 + MOCK_STUDENT_AGE_3) / 3));

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/average-age")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString((double) ((MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 +
                        MOCK_STUDENT_AGE_3) / 3))));
    }


    // получение пяти последних студентов
    @Test
    public void shouldReturnLimitOfStudents() throws Exception {

        when(studentRepository.getLastFive()).thenReturn(MOCK_STUDENTS_LIMIT);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/last-five")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS_LIMIT)));
    }


    // получение количества всех студентов через создание interface projection
    @Test
    public void shouldReturnAmountOfStudents() throws Exception {

        when(allStudentsRepository.getAmountOfStudents()).thenReturn(MOCK_STUDENTS.size());

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/amount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS.size())));
    }


    @Test
    public void shouldReturnStudentNamesStartingWithA() throws Exception {

        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);

        when(studentService.getStudentNamesStartingWithA())
                .thenReturn(MOCK_STUDENT_NAMES_WITH_STARTING_A);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/names-by-a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENT_NAMES_WITH_STARTING_A)));
    }

    // получение среднего возраста студентов через stream
    @Test
    public void shouldReturnAverageAgeOfStudentsByStream() throws Exception {

        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);

        when(studentService.getAverageAgeByStream())
                .thenReturn((double) ((MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 + MOCK_STUDENT_AGE_3) / 3));

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/student/average-age-stream")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString((double) ((MOCK_STUDENT_AGE_1 + MOCK_STUDENT_AGE_2 +
                        MOCK_STUDENT_AGE_3) / 3))));
    }

}
