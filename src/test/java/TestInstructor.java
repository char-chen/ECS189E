import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TestInstructor {

    private IInstructor instructor;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        IAdmin admin = new Admin();
        admin.createClass("Test1", 2017, "Instructor1", 30);
        admin.createClass("Test2", 2017, "Instructor2", 30);
        admin.createClass("Test3", 2017, "Instructor3", 30);
    }

    @Test
    public void testAddHomework() {
        new Instructor().addHomework("Instructor1","Test1",2017,"HW","Homework");
        assertTrue(instructor.homeworkExists("Test1", 2017, "HW"));
        assertFalse(instructor.homeworkExists("Test1", 2017, "HW2"));
    }

    @Test
    public void testAddHomeworkToUncreatedClass() {
        instructor.addHomework("Instructor3", "Test", 2017, "HW", "Homework");
        assertFalse(instructor.homeworkExists("Test",2017,"HW"));
    }

    @Test
    public void testAddHomeworkPastYear() {
        new Admin().createClass("Test", 2016, "Instructor", 30);
        instructor.addHomework("Instructor", "Test", 2016, "HW", "Homework");
        assertFalse(instructor.homeworkExists("Test",2016,"HW"));
    }

    @Test
    public void testAddHomeworkUnassignedInstructor() {
        instructor.addHomework("Instructor2", "Test1", 2017, "HW", "Homework");
        assertFalse(instructor.homeworkExists("Test1", 2017, "HW"));
    }

    @Test
    public void testAddHomeworkAssignGrade() {
        IStudent student = new Student();
        student.registerForClass("Student","Test1",2017);
        instructor.addHomework("Instructor","Test1",2017,"HW","Homework");
        student.submitHomework("Student","HW","Answer","Test",2017);
        instructor.assignGrade("Instructor","Test1",2017,"HW","Student",90);
        assertEquals(new Integer(90), instructor.getGrade("Test1",2017,"HW","Student"));
    }

    @Test
    public void testAssignGradeNonexistentStudent() {
        instructor.addHomework("Instructor","Test",2017,"HW","Homework");
        instructor.assignGrade("Instructor","Test",2017,"HW","Student",80);
        assertNull(instructor.getGrade("Test",2017,"HW","Student"));
    }

    @Test
    public void testAssignGradeNoStudentSubmission() {
        IStudent student = new Student();
        student.registerForClass("Student","Test1",2017);
        instructor.addHomework("Instructor","Test1",2017,"HW","Homework");
        instructor.assignGrade("Instructor","Test1",2017,"HW","Student",40);
        assertNull(instructor.getGrade("Test",2017,"HW","Student"));
    }

    @Test
    public void testNonexistentStudent() {
        instructor.addHomework("Instructor","Test1",2017,"HW","Homework");
        instructor.assignGrade("Instructor","Test1",2017,"HW","Student",40);
        assertNull(instructor.getGrade("Test",2017,"HW","Student"));
    }

    @Test
    public void testAssignGradeNoHomework() {
        IStudent studentent = new Student();
        studentent.registerForClass("Student","Test1",2017);
        instructor.assignGrade("Instructor","Test1",2017,"HW","Student",70);
        assertNull(instructor.getGrade("Test",2017,"HW","Student"));
    }

    @Test
    public void testAssignGradeUnassignedInstructor() {
        IStudent student = new Student();
        student.registerForClass("Student","Test1",2017);
        instructor.addHomework("Instructor","Test1",2017,"HW","Homework");
        student.submitHomework("Student","HW","Answer","Test",2017);
        instructor.assignGrade("Instructor","Test1",2017,"HW","Student",1);
        assertNull(instructor.getGrade("Test", 2017,"HW","Student"));
    }
}
