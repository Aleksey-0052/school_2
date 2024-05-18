package hogwarts.school_2.repository;

import hogwarts.school_2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AllStudentsRepository extends JpaRepository<Student, Long> {

    @Query(value = "select count(*) as amountOfStudents from students", nativeQuery = true)
    Integer getAmountOfStudents();

}
