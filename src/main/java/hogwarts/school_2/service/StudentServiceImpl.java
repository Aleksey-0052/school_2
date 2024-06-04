package hogwarts.school_2.service;

import hogwarts.school_2.exception.EntityNotFoundException;
import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;
import hogwarts.school_2.repository.AllStudentsRepository;
import hogwarts.school_2.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    // в параметры метода getLogger() передаем объект класса StudentServiceImpl

    private final StudentRepository studentRepository;

    private final AllStudentsRepository allStudentsRepository;

    public StudentServiceImpl(StudentRepository studentRepository, AllStudentsRepository allStudentsRepository) {
        this.studentRepository = studentRepository;
        this.allStudentsRepository = allStudentsRepository;
    }


    @Override
    public Student create(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    // редактируем данные студента (имя и/или возраст)
    @Override
    public Student edit(Student student) {
        logger.warn("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    @Override
    public Student delete(Long id) {
        logger.info("Was invoked method for delete student");
        Student student = find(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Student find(Long id) {
        logger.debug("Was invoked method for find student");
        logger.error("There is not student with id = {}", id);
        return studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        // или можно так: return studentRepository.findById(id).get();
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method for find students by age between");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFaculty(Long studentId) {
        logger.info("Was invoked method for find faculty by studentId");
        return find(studentId).getFaculty();
        // находим по идентификатору студента и вызываем у объекта "student" геттер для получения поля faculty
    }


//    public Collection<Student> getStudentsByAge(Integer age) {
//        return studentRepository.findByAge(age);
//
//    }


    public Integer getTotalCountOfStudents() {
        logger.info("Was invoked method for get count of students");
        return studentRepository.getTotalCountOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLastFive() {
        logger.info("Was invoked method for get last five of students");
        return studentRepository.getLastFive();
    }

    // получение количества всех студентов через создание interface projection
    public Integer getAmountOfStudents() {
        logger.info("Was invoked method for get amount of students");
        return allStudentsRepository.getAmountOfStudents();
    }

    @Override
    public List<String> getStudentNamesStartingWithA() {
        logger.info("Was invoked method for find names of students starting with A");
        List<Student> students = studentRepository.findAll();
        List<String> studentNames = students.stream()
                .filter(student -> student.getName().toUpperCase().startsWith("А"))
                .map(Student::getName)
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        return studentNames;
    }

    @Override
    public Double getAverageAgeByStream() {
        logger.info("Was invoked method for get average age by stream");
        List<Student> students = studentRepository.findAll();
        double averageAge = students.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0f);
        return averageAge;
    }

    @Override
    public void printStudents() {
        List<Student> students = studentRepository.findAll();

        if (students.size() >= 6) {

            students.subList(0, 2).forEach(this::printStudentName);
            printStudents(students.subList(2, 4));
            printStudents(students.subList(4, 6));
        }
    }

    @Override
    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll();

        if (students.size() >= 6) {

            students.subList(0, 2).forEach(this::printStudentNameSync);
            printStudentsSync(students.subList(2, 4));
            printStudentsSync(students.subList(4, 6));
        }
    }

    private void printStudentName(Student student) {
        logger.info("Student, id: {}, name: {}", student.getId(), student.getName());
    }

    private void printStudents(List<Student> list) {

        new Thread(() -> {
            list.forEach(this::printStudentName);
        }).start();

    }

    private synchronized void printStudentNameSync(Student student) {
        logger.info("Sync Student, id: {}, name: {}", student.getId(), student.getName());
    }

    private void printStudentsSync(List<Student> list) {

        new Thread(() -> {
            list.forEach(this::printStudentNameSync);
        }).start();

    }






}
