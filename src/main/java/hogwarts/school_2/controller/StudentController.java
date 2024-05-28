package hogwarts.school_2.controller;


import hogwarts.school_2.exception.EntityNotFoundException;
import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
@Tag(name = "API для работы со студентами")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public Student create(@RequestBody Student student) {
        return service.create(student);
        // необходимо использовать аннотацию @RequestBody org.springframework, но не одноименную аннотацию i.o. swagger
    }

    @PutMapping
    @Operation(summary = "Обновление студента" )
    public Student edit(@RequestBody Student student) {
        return service.edit(student);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента" )
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение студента по id" )
    public Student get(@PathVariable ("id") Long id) {
        Student student = null;
        try {
             student = service.find(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Студент с таким id не найден " + e);
        }
        return student;
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех студентов" )
    public Collection<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("get-by/{minAge}/{maxAge}")
    @Operation(summary = "Получение студентов по возрасту" )
    public Collection<Student> getStudentsByAge(
            @PathVariable ("minAge") Integer minAge,
            @PathVariable ("maxAge") Integer maxAge
    ) {
        return service.getStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("faculty/{studentId}")
    @Operation(summary = "Получение факультета студента по id" )
    public Faculty getFaculty(@PathVariable ("studentId") Long studentId) {
        return service.getFaculty(studentId);
    }



    // получение количества всех студентов
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalCountOfStudents() {
        int count = service.getTotalCountOfStudents();
        return ResponseEntity.ok(count);
    }

    // получение среднего возраста студентов
    @GetMapping("/average-age")
    public Double getAverageAgeOfStudents() {
        return service.getAverageAgeOfStudents();
    }

    // получение части студентов
    @GetMapping("/limit")
    public ResponseEntity<List<Student>> getLimitOfStudents() {
        List<Student> students = service.getLimitOfStudents();
        return ResponseEntity.ok(students);
    }


    // получение количества всех студентов через создание interface projection
    @GetMapping("/amount")
    public Integer getAmountOfStudents() {
        return service.getAmountOfStudents();
    }




}
