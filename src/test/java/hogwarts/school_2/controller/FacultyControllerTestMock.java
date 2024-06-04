package hogwarts.school_2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.repository.FacultyRepository;
import hogwarts.school_2.service.FacultyServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static hogwarts.school_2.controller.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTestMock {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private ObjectMapper mapper = new ObjectMapper();
    // с помощью данного класса можно сериализовать объект в JSON
    // необходим для теста shouldAllFaculties()


    @Test
    public void shouldCreateFaculty() throws Exception {

        // создаем моковый факультет, которого не будет в базе данных
        when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY_1);
        // должен вернуться MOCK_FACULTY

        // Создадим переменную facultyObject и заполним ее данными. Эту переменную будем использовать в качестве входных данных.
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", MOCK_FACULTY_NAME_1);
        facultyObject.put("color", MOCK_FACULTY_COLOR_1);

        // с помощью метода perform() осуществляем вызов эндпоинта
        mockmvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // проверяем, что статус ответа 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(MOCK_FACULTY_NAME_1))
                // проверяем, что имя в json соответствует имени MOCK_FACULTY_NAME, то есть тому, которое передали
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(MOCK_FACULTY_COLOR_1));
                // проверяем, что цвет в json соответствует цвету MOCK_FACULTY_COLOR, то есть тому, который передали
                // при помощи $ извлекаем из json значение соответствующего поля и сравниваем с входящими данными
    }

    @Test
    public void shouldReturnFacultyById() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY_1));

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + MOCK_FACULTY_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(MOCK_FACULTY_NAME_1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(MOCK_FACULTY_COLOR_1));
    }

    @Test
    public void shouldDeleteFaculty() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY_1));

        mockmvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + MOCK_FACULTY_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldEditFaculty() throws Exception {

        when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY_1);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", MOCK_FACULTY_ID_1);
        facultyObject.put("name", MOCK_FACULTY_NAME_1);
        facultyObject.put("color", MOCK_FACULTY_COLOR_1);

        mockmvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(MOCK_FACULTY_NAME_1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(MOCK_FACULTY_COLOR_1));
    }

    @Test
    public void shouldReturnAllFaculties() throws Exception {
        when(facultyRepository.findAll()).thenReturn(MOCK_FACULTIES);

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_FACULTIES)));
        // проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
        // ObjectMapper метода writeValueAsString() и передачи в параметры метода списка факультетов
        // в результате вызова эндпоинта "/faculty/all" должен вернуться список факультетов - MOCK_FACULTIES
    }

    @Test
    public void shouldReturnFacultiesByNameOrColor() throws Exception {

//        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString()))
//                .thenReturn(MOCK_FACULTIES_BY_NAME);
//        // anyString() применяется, если используются оба параметра

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(any(), any()))
                .thenReturn(MOCK_FACULTIES_BY_NAME);

        mockmvc.perform(MockMvcRequestBuilders
                        //.get("/faculty/get-by-name-or-color?name=" + MOCK_FACULTY_NAME_2 + "&color=" + MOCK_FACULTY_COLOR_2)
                        .get("/faculty/get-by-name-or-color?name=" + MOCK_FACULTY_NAME_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_FACULTIES_BY_NAME)));


        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(any(), any()))
                .thenReturn(MOCK_FACULTIES_BY_COLOR);

        mockmvc.perform(MockMvcRequestBuilders
                        //.get("/faculty/get-by-name-or-color?name=" + MOCK_FACULTY_NAME_2 + "&color=" + MOCK_FACULTY_COLOR_2)
                        .get("/faculty/get-by-name-or-color?color=green")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_FACULTIES_BY_COLOR)));
    }

    @Test
    public void shouldReturnStudentsOfFaculty() throws Exception {

        MOCK_FACULTY_1.setStudents(MOCK_STUDENTS);
        // у объекта типа Faculty вызываем сеттер и инициализируем его поле - список студентов

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY_1));
        // должен быть возвращен объект типа Faculty с установленным списком студентов

        mockmvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students/" + MOCK_FACULTY_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
        // проверяем, что содержимое нашего ответа соответствует json, полученному в результате вызова у объекта типа
        // ObjectMapper метода writeValueAsString() и передачи в параметры метода установленного списка студентов
    }

    @Test
    public void  shouldReturnLongestFacultyName() throws Exception {

        when(facultyRepository.findAll()).thenReturn(MOCK_FACULTIES);

        when(facultyService.getLongestFacultyName()).thenReturn(MOCK_FACULTY_NAME_3);

        ResultActions resultActions = mockmvc.perform(MockMvcRequestBuilders
                        .get("/faculty/longest-faculty-name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MOCK_FACULTY_NAME_3));
        // в данном случае результат возвращается без кавычек; поэтому его сравниваем не с json, а со String
    }


}
