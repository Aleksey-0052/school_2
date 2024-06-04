package hogwarts.school_2.controller;


import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.service.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
@Tag(name = "API для работы с факультетами")
public class FacultyController {

    private final FacultyService service;


    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создание факультета" )
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        Faculty f = service.create(faculty);
        return ResponseEntity.ok(f);
        // необходимо использовать аннотацию @RequestBody org.springframework, но не одноименную аннотацию i.o. swagger
    }

    @PutMapping
    @Operation(summary = "Обновление факультета" )
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty) {
        Faculty f = service.edit(faculty);
        return ResponseEntity.ok(f);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета" )
    public ResponseEntity<Faculty> delete(@PathVariable Long id) {
        Faculty f = service.delete(id);
        return ResponseEntity.ok(f);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение факультета по id" )
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty f = service.find(id);
        return ResponseEntity.ok(f);
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех факультетов" )
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        Collection<Faculty> faculties = service.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("get-by-name-or-color")
    @Operation(summary = "Получение факультетов по имени или цвету" )
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color) {
        Collection<Faculty> faculties = service.getFacultiesByNameOrColor(name, color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("students/{facultyId}")
    @Operation(summary = "Получение студентов факультета" )
    public ResponseEntity<Collection<Student>> getStudents(@PathVariable("facultyId") Long facultyId) {
        Collection<Student> students = service.getStudents(facultyId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/longest-faculty-name")
    @Operation(summary = "Получение самого длинного имени факультета" )
    public ResponseEntity<String> getLongestFacultyName() {
        String longestName = service.getLongestFacultyName();
        return ResponseEntity.ok(longestName);
    }

}
