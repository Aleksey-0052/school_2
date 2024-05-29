package hogwarts.school_2.service;

import hogwarts.school_2.exception.EntityNotFoundException;
import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class FacultyServiceImpl implements FacultyService {

    private final static Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    // в параметры метода getLogger() передаем объект класса StudentServiceImpl

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty edit(Faculty faculty) {
        logger.warn("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty delete(Long id) {
        Faculty faculty = find(id);
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty find(Long id) {
        logger.debug("Was invoked method for find faculty");
        logger.error("There is not faculty with id = {}", id);
        return facultyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        // или можно так: return facultyRepository.findById(id).get();
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for find all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getFacultiesByNameOrColor(String name, String color) {
        logger.info("Was invoked method for find faculties by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudents(Long facultyId) {
        logger.info("Was invoked method for find students of faculty");
        return find(facultyId).getStudents();
        // находим по идентификатору факультет и у объекта "faculty" вызываем геттер для получения поля - список студентов
    }

//    @Override
//    public Collection<Faculty> getFacultiesByColor(String color) {
//        return facultyRepository.findByColor(color);
//    }


}
