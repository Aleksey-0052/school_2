package hogwarts.school_2.controller;


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

    @GetMapping("/count")
    @Operation(summary = "Получение количества студентов")
    public ResponseEntity<Integer> getTotalCountOfStudents() {
        int count = service.getTotalCountOfStudents();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/average-age")
    @Operation(summary = "Получение среднего возраста студентов" )
    public Double getAverageAgeOfStudents() {
        return service.getAverageAgeOfStudents();
    }

    @GetMapping("/last-five")
    @Operation(summary = "Получение пяти последних студентов" )
    public ResponseEntity<List<Student>> getLastFive() {
        List<Student> students = service.getLastFive();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/amount")
    @Operation(summary = "Получение количества студентов через создание интерфейса projection - AllStudentsRepository")
    public Integer getAmountOfStudents() {
        return service.getAmountOfStudents();
    }

    @GetMapping("/names-by-a")
    @Operation(summary = "Получение имен студентов на букву А")
    public ResponseEntity<List<String>> getStudentNamesStartingWithA() {
        List<String> studentNames = service.getStudentNamesStartingWithA();
        return ResponseEntity.ok(studentNames);
    }

    @GetMapping("/average-age-stream")
    @Operation(summary = "Получение среднего возраста студентов")
    public ResponseEntity<Double> getAverageAgeByStream() {
        double averageAge = service.getAverageAgeByStream();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/print-student-names")
    @Operation(summary = "Вывод информации о студентах в разных потоках")
    public ResponseEntity<Void> printStudents() {
         service.printStudents();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/print-student-names-sync")
    @Operation(summary = "Вывод информации о студентах в разных потоках (syncronized)")
    public ResponseEntity<Void> printStudentsSync() {
        service.printStudentsSync();
        return ResponseEntity.ok().build();
    }

}
