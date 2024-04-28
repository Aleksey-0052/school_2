package hogwarts.school_2.controller;


import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
        return service.find(id);
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
}
