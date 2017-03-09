import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testCreateClass() {
        this.admin.createClass("Test", 2017, "Instructor", 30);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCreateSameClassDifferentYear() {
        this.admin.createClass("Test", 2017, "Instructor", 30);
        this.admin.createClass("Test", 2018, "Instructor", 30);
        assertTrue(this.admin.classExists("Test", 2017));
        assertTrue(this.admin.classExists("Test", 2018));
    }

    @Test
    public void testCreateClassCapacityGreaterThanZero() {
        this.admin.createClass("Test",2017,"Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCreateFourClassesForInstructorInSameYear() {
        this.admin.createClass("Test1", 2017, "Instructor", 30);
        this.admin.createClass("Test2", 2017, "Instructor", 30);
        this.admin.createClass("Test3", 2017, "Instructor", 30);
        this.admin.createClass("Test4", 2017, "Instructor", 30);
        assertTrue(this.admin.classExists("Test1", 2017));
        assertTrue(this.admin.classExists("Test2", 2017));
        assertFalse(this.admin.classExists("Test3", 2017));
        assertFalse(this.admin.classExists("Test4", 2017));
    }

    @Test
    public void testClassWithMultipleInstructors() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        this.admin.createClass("Test", 2017, "Instructor3", 15);
        assertNotEquals("Instructor", this.admin.getClassInstructor("Test", 2017));
        assertNotEquals("Instructor", this.admin.getClassInstructor("Test", 2017));
        assertEquals("Instructor3", this.admin.getClassInstructor("Test", 2017));
    }

    @Test
    public void testCreatePastClass() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        this.admin.createClass("Test", 2000, "Instructor", 30);
        assertFalse(this.admin.classExists("Test", 2016));
        assertFalse(this.admin.classExists("Test", 2000));
    }

    @Test
    public void testCreateFutureClass() {
        this.admin.createClass("Test", 2018, "Instructor", 30);
        this.admin.createClass("Test", 3000, "Instructor", 30);
        assertTrue(this.admin.classExists("Test", 2018));
        assertTrue(this.admin.classExists("Test", 3000));
    }

    @Test
    public void testCreateClassChangeCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 30);
        this.admin.changeCapacity("Test", 2017, 20);
        assertEquals(this.admin.getClassCapacity("Test", 2017), 20);
    }

    @Test
    public void testCreateClassZeroCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertTrue(this.admin.classExists("Test", 2017));
        assertEquals(this.admin.getClassCapacity("Test", 2017), 0);
    }

    @Test
    public void testChangeCapacityToZero() { // should not be able to change class capacity to 0
        this.admin.createClass("Test",2017,"Instructor",2);
        this.admin.changeCapacity("Test",2017,0);
        assertEquals(2, this.admin.getClassCapacity("Test",2017));
    }

    @Test
    public void testCreateClassNegativeCapacity() {
        this.admin.createClass("Test",2017,"Instructor",-30);
        this.admin.createClass("Test2",2017,"Instructor",-100);
        assertFalse(this.admin.classExists("Test",2017));
        assertFalse(this.admin.classExists("Test2",2017));
    }

    @Test
    public void testChangeCapacityNegative() {
        this.admin.createClass("Test",2017,"Instructor",30);
        this.admin.changeCapacity("Test",2017,-10);
        assertEquals(this.admin.getClassCapacity("Test",2017), 30);
    }

    @Test
    public void testChangeNonexistentClassCapacity() {
        this.admin.changeCapacity("Test",2017,20);
        assertEquals(-1, this.admin.getClassCapacity("Test",2017));
    }

    @Test
    public void testChangeCapacityToSmallerThanEnrolled() {
        this.admin.createClass("Test",2017,"Instructor",3);
        new Student().registerForClass("Student", "Test", 2017);
        new Student().registerForClass("Student2", "Test", 2017);
        new Student().registerForClass("Student3", "Test", 2017);
        this.admin.changeCapacity("Test",2017,1);
        assertEquals(3, this.admin.getClassCapacity("Test",2017));
        this.admin.changeCapacity("Test",2017,2);
        assertEquals(2, this.admin.getClassCapacity("Test",2017));
    }
}