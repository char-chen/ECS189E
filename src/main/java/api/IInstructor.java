package api;

public interface IInstructor {

    /**
     * Add homework to class {@code className}, taught in year {@code year}, with title {@code homeworkName}, provided this instructor has been assigned to this class.
     *
     * @param instructorName The name of the instructor who assigns the homework
     * @param className Class for which the homework should be added
     * @param year Year of class
     * @param homeworkName Name of the homework assignment to be added
     * @param homeworkDescription Description of homework
     */
    void addHomework(String instructorName, String className, int year, String homeworkName, String homeworkDescription);

    /**
     * Assign grade {@code grade} to student {@code studentName} for homework {@code homeworkName} in class {@code className},
     * provided this instructor has been assigned to this class, the homework has been assigned and the student has submitted this homework.
     *
     * @param instructorName The name of the instructor who assigns the grade to the homework
     * @param className Name of the class to assign grade for
     * @param year Year in which said class is taught
     * @param homeworkName Name of homework to grade
     * @param studentName Name of student to grade
     * @param grade Percentage grade of student
     */
    void assignGrade(String instructorName, String className, int year, String homeworkName, String studentName, int grade);


    // Getters for testing purposes
    /**
     * @return Whether homework with name {@code homeworkName} exists for this class
     */
    boolean homeworkExists(String className, int year, String homeworkName);

    /**
     * @return The grade for student {@code studentName} on homework {@code homeworkName} for this class, or null if not applicable
     */
    Integer getGrade(String className, int year, String homeworkName, String studentName);
}
