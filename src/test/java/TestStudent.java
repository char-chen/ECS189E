import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestStudent {
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        IAdmin admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
        admin.createClass("Class",2017,"Instructor",3);
    }

    @Test
    public void testSubmitHomework() {
        this.student.registerForClass("Student", "Class", 2017);
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Homework");
        this.student.submitHomework("Student", "HW", "Answer", "Class", 2017);
        assertTrue(this.student.hasSubmitted("Student", "HW", "Class", 2017));
    }

    @Test
    public void testRegisterClass() {
        this.student.registerForClass("Student","Class",2017);
        assertTrue(this.student.isRegisteredFor("Student","Class",2017));
    }

    @Test
    public void testRegisterFullClass() {
        new Student().registerForClass("Student1", "Class", 2017);
        new Student().registerForClass("Student2", "Class", 2017);
        new Student().registerForClass("Student3", "Class", 2017);
        Student student4 = new Student();
        student4.registerForClass("Student3", "Class", 2017);
        assertFalse(student4.isRegisteredFor("Student4", "Class", 2017));
    }

    @Test
    public void testRegisterNonexistentClass() {
        this.student.registerForClass("Student","Test",2017);
        assertFalse(this.student.isRegisteredFor("Student","Class",2017));
    }

    @Test
    public void testDropClass() {
        this.student.registerForClass("Student", "Class",2017);
        this.student.dropClass("Student", "Class", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
    }

    @Test
    public void testSubmitHomeworkNotAssigned() { // should not be able to submit homework that wasn't assigned
        this.student.submitHomework("Student", "HW", "Answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("Student", "HW", "Test", 2017));
    }

    @Test
    public void testStudentSubmitHomeworkNotRegistered() {
        this.instructor.addHomework("Instructor", "Class", 2017, "HW", "Homework");
        this.student.submitHomework("Student", "HW", "Answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("Student", "HW", "Test", 2017));
    }
}