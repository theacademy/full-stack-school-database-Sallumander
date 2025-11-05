package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    private final StudentDao studentDao;

    // single-arg constructor keeps tests simple
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public Student getStudentById(int id) {
        try {
            return studentDao.findStudentById(id);
        } catch (DataAccessException e) {
            Student notFound = new Student();
            notFound.setStudentId(id);
            notFound.setStudentFirstName("Student Not Found");
            notFound.setStudentLastName("Student Not Found");
            return notFound;
        }
    }

    @Override
    public Student addNewStudent(Student student) {
        boolean firstNameBlank = student.getStudentFirstName() == null || student.getStudentFirstName().trim().isEmpty();
        boolean lastNameBlank = student.getStudentLastName() == null || student.getStudentLastName().trim().isEmpty();

        if (firstNameBlank || lastNameBlank) {
            student.setStudentFirstName("First Name blank, student NOT added");
            student.setStudentLastName("Last Name blank, student NOT added");
            return student;
        }
        return studentDao.createNewStudent(student);
    }

    @Override
    public Student updateStudentData(int id, Student student) {
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
    }

    @Override
    public void deleteStudentById(int id) {
        studentDao.deleteStudent(id);
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        Student student = getStudentById(studentId);
        if ("Student Not Found".equals(student.getStudentFirstName())) {
            System.out.println("Student not found");
            return;
        }

        studentDao.deleteStudentFromCourse(studentId, courseId);
        System.out.println("Student: " + studentId + " deleted from course: " + courseId);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        Student student = getStudentById(studentId);
        if ("Student Not Found".equals(student.getStudentFirstName())) {
            System.out.println("Student not found");
            return;
        }

        try {
            studentDao.addStudentToCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " added to course: " + courseId);
        } catch (DataAccessException ex) {
            System.out.println("Student: " + studentId + " already enrolled in course: " + courseId);
        }
    }
}
