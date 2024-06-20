package hogwarts.school_2.service;

import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student create(Student student);
    Student edit(Student student);
    Student delete(Long id);
    Student find(Long id);
    Collection<Student> getAllStudents();
    Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge);
    Faculty getFaculty(Long studentId);
//    Collection<Student> getStudentsByAge(Integer age);



    // получение количества всех студентов
    Integer getTotalCountOfStudents();

    // получение среднего возраста студентов
    Double getAverageAgeOfStudents();
    // получение части студентов
    List<Student> getLastFive();

    // получение количества всех студентов через создание interface projection
    Integer getAmountOfStudents();

    // получение отсортированных в алфавитном порядке имен всех студентов в верхнем регистре, чье имя начинается на
    // букву А, через использование stream
    List<String> getStudentNamesStartingWithA();

    // получение среднего возраста студентов через использование stream
    Double getAverageAgeByStream();

    // выведение в консоль шести студентов (первых двух в главном потоке, третьего и четвертого во втором потоке и
    // пятого и шестого в третьем потоке
    void printStudents();

    // выведение в консоль студентов в трех потоках в синхронизированном методе
    void printStudentsSync();


}
