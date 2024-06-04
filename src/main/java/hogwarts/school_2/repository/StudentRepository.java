package hogwarts.school_2.repository;

import hogwarts.school_2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

//    Collection<Student> findByAge(Integer age);

    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);


    // получение количества всех студентов
    @Query(value = "select count(*) from students", nativeQuery = true)
    Integer getTotalCountOfStudents();


    // получение среднего возраста студентов
    @Query(value = "select avg(age) from students", nativeQuery = true)
    Double getAverageAgeOfStudents();


    // получение последних пяти студентов в порядке возрастания id
    @Query(value = "SELECT * FROM students ORDER BY id OFFSET (SELECT COUNT(*) FROM students) - 5", nativeQuery = true)
    List<Student> getLastFive();

    // Второй вариант:
    // SELECT * FROM students ORDER BY id DESC - отсортировать в порядке убывания id
    // SELECT * FROM students ORDER BY id DESC LIMIT 5 - отсортировать в порядке убывания id и выбрать только первые 5
    // элементов в порядке убывания id (получится пять элементов с конца в порядке убывания id)
    // SELECT * FROM (SELECT * FROM students ORDER BY id DESC LIMIT 5) t ORDER BY id - пять элементов с конца,
    // но в порядке возрастания id
    // можно указать и без "t"
    // SELECT * FROM (SELECT * FROM students ORDER BY id DESC LIMIT 5) ORDER BY id
}
