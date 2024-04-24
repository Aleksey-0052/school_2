package hogwarts.school_2.service;

import hogwarts.school_2.exception.EntityNotFoundException;
import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty edit(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty delete(Long id) {
        Faculty  faculty = find(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty find(Long id) {
        return facultyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        // или можно так: return facultyRepository.findById(id).get();
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getFacultiesByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudents(Long facultyId) {
        return find(facultyId).getStudents();
        // находим по идентификатору факультет и у объекта "faculty" вызываем геттер для получения поля - список студентов
    }

//    @Override
//    public Collection<Faculty> getFacultiesByColor(String color) {
//        return facultyRepository.findByColor(color);
//    }


}
