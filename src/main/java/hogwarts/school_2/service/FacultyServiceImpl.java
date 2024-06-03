package hogwarts.school_2.service;

import hogwarts.school_2.exception.EntityNotFoundException;
import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;


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

    @Override
    public String getLongestFacultyName() {
        logger.info("Was invoked method for find faculty by longest name");
        List<Faculty> faculties = facultyRepository.findAll();
        String longestName = faculties.stream()
                .map(Faculty::getName)
                // .max((str1, str2) -> str1.length() - str2.length()).get();
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        return longestName;
    }

    @Override
    public void calculate(int limit) {
        calculate1(limit);
        calculate2(limit);
        calculate3(limit);
    }

    private void calculate1(int limit) {
        long start = System.currentTimeMillis();

        long sum = Stream.iterate(1, a -> a + 1)
                .limit(limit)
                .reduce(0, (a, b) -> a + b );

        long end = System.currentTimeMillis();

        logger.info("Time 1 : {}", end - start);
    }

    private void calculate2(int limit) {
        long start = System.currentTimeMillis();

        long sum = Stream.iterate(1, a -> a + 1)
                .limit(limit)
                .parallel()
                .reduce(0, (a, b) -> a + b );

        long end = System.currentTimeMillis();

        logger.info("Time 2 : {}", end - start);
    }

    private void calculate3(int limit) {
        long start = System.currentTimeMillis();

        long sum = LongStream
                .range(1, limit)
                .sum();

        long end = System.currentTimeMillis();

        logger.info("Time 3 : {}", end - start);
    }


//    @Override
//    public Collection<Faculty> getFacultiesByColor(String color) {
//        return facultyRepository.findByColor(color);
//    }


}
