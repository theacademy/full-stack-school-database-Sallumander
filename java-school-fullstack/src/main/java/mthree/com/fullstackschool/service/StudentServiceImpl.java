package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE

    // DAO dependencies
    private final StudentDao studentDao;
    private final CourseServiceInterface courseService;

    // Constructor for injection
    @Autowired
    public StudentServiceImpl(StudentDao studentDao, CourseServiceInterface courseService) {
        this.studentDao = studentDao;
        this.courseService = courseService;
    }
    

    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE

        return studentDao.getAllStudents();

        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE
        try {
            return studentDao.findStudentById(id);  
        } catch (DataAccessException e) {
            Student notFound = new Student();
            notFound.setStudentId(id);
            notFound.setStudentFirstName("Student Not Found");
            notFound.setStudentLastName("Student Not Found");
            return notFound;
            
        }
        

        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE

        boolean firstNameBlank = student.getStudentFirstName() == null || student.getStudentFirstName().isEmpty();
        boolean lastNameBlank = student.getStudentLastName() == null || student.getStudentLastName().isEmpty();

        if (firstNameBlank || lastNameBlank) {
            student.setStudentFirstName("Name blank, student NOT added");
            student.setStudentLastName("Name blank, student NOT added");
            return student;
        }
        return studentDao.createNewStudent(student);

        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE
        if (id != student.getStudentId()) {
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
            return student;
        }

        studentDao.updateStudent(student);
        try {
            return studentDao.findStudentById(id);
        } catch (DataAccessException ex) {
            Student notFound = new Student();
            notFound.setStudentId(id);
            notFound.setStudentFirstName("Student Not Found");
            notFound.setStudentLastName("Student Not Found");
            return notFound;
        }

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE

        studentDao.deleteStudent(id);
        // System.out.println("Student with ID " + id + " has been deleted.");

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        Student student = getStudentById(studentId);
        if ("Student Not Found".equals(student.getStudentFirstName())) {
            System.out.println("Student not found");
            return;
        }

        Course course = courseService.getCourseById(courseId);
        if ("Course Not Found".equals(course.getCourseName())) {
            System.out.println("Course not found");
            return;
        }

        studentDao.deleteStudentFromCourse(studentId, courseId);
        System.out.println("Student: " + studentId + " deleted from course: " + courseId);
        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        Student student = getStudentById(studentId);
        if ("Student Not Found".equals(student.getStudentFirstName())) {
            System.out.println("Student not found");
            return;
        }

        Course course = courseService.getCourseById(courseId);
        if ("Course Not Found".equals(course.getCourseName())) {
            System.out.println("Course not found");
            return;
        }

        try {
            studentDao.addStudentToCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " added to course: " + courseId);
        } catch (DataAccessException ex) {
            System.out.println("Student: " + studentId + " already enrolled in course: " + courseId);
        }

        //YOUR CODE ENDS HERE
    }
}
