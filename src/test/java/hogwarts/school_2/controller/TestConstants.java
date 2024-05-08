package hogwarts.school_2.controller;

import hogwarts.school_2.model.Faculty;
import hogwarts.school_2.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestConstants {

    public static final Long MOCK_FACULTY_ID_1 = 1L;

    public static final String MOCK_FACULTY_NAME_1 = "Faculty 1";

    public static final String MOCK_FACULTY_COLOR_1 = "Green";

    public static final Faculty MOCK_FACULTY_1 = new Faculty(
            MOCK_FACULTY_ID_1,
            MOCK_FACULTY_NAME_1,
            MOCK_FACULTY_COLOR_1
    );

    public static final Long MOCK_FACULTY_ID_2 = 2L;

    public static final String MOCK_FACULTY_NAME_2 = "Faculty 2";

    public static final String MOCK_FACULTY_COLOR_2 = "Green";

    public static final Faculty MOCK_FACULTY_2 = new Faculty(
            MOCK_FACULTY_ID_2,
            MOCK_FACULTY_NAME_2,
            MOCK_FACULTY_COLOR_2
    );

    public static final Long MOCK_FACULTY_ID_3 = 3L;

    public static final String MOCK_FACULTY_NAME_3 = "Faculty 3";

    public static final String MOCK_FACULTY_COLOR_3 = "Red";

    public static final Faculty MOCK_FACULTY_3 = new Faculty(
            MOCK_FACULTY_ID_3,
            MOCK_FACULTY_NAME_3,
            MOCK_FACULTY_COLOR_3
    );

    public static final String MOCK_FACULTY_NEW_COLOR = "Blue";


    public static final Long MOCK_STUDENT_ID_1 = 1L;

    public static final String MOCK_STUDENT_NAME_1 = "Student name 1";

    public static final Integer MOCK_STUDENT_AGE_1 = 20;

    public static final Student MOCK_STUDENT_1 = new Student(
            MOCK_STUDENT_ID_1,
            MOCK_STUDENT_NAME_1,
            MOCK_STUDENT_AGE_1
    );

    public static final Long MOCK_STUDENT_ID_2 = 2L;

    public static final String MOCK_STUDENT_NAME_2 = "Student name 2";

    public static final Integer MOCK_STUDENT_AGE_2 = 21;

    public static final Student MOCK_STUDENT_2 = new Student(
            MOCK_STUDENT_ID_2,
            MOCK_STUDENT_NAME_2,
            MOCK_STUDENT_AGE_2
    );

    public static final Long MOCK_STUDENT_ID_3 = 3L;

    public static final String MOCK_STUDENT_NAME_3 = "Student name 3";

    public static final Integer MOCK_STUDENT_AGE_3 = 22;

    public static final Student MOCK_STUDENT_3 = new Student(
            MOCK_STUDENT_ID_3,
            MOCK_STUDENT_NAME_3,
            MOCK_STUDENT_AGE_3
    );

    public static final String MOCK_STUDENT_NEW_NAME = "Student new name";


    public static final List<Student> MOCK_STUDENTS = new ArrayList<>(Arrays.asList(MOCK_STUDENT_1, MOCK_STUDENT_2, MOCK_STUDENT_3));

    // public static final List<Student> MOCK_STUDENTS = Collections.singletonList(MOCK_STUDENT_1);

    public static final List<Student> MOCK_STUDENTS_BY_AGE = new ArrayList<>(Arrays.asList(MOCK_STUDENT_2, MOCK_STUDENT_3));


    public static final List<Faculty> MOCK_FACULTIES = new ArrayList<>(List.of(MOCK_FACULTY_1, MOCK_FACULTY_2, MOCK_FACULTY_3));

    public static final List<Faculty> MOCK_FACULTIES_BY_COLOR = new ArrayList<>(List.of(MOCK_FACULTY_1, MOCK_FACULTY_2));

    public static final List<Faculty> MOCK_FACULTIES_BY_NAME = new ArrayList<>(List.of(MOCK_FACULTY_2));

}
