package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseServiceInterface {

    // DAO dependency
    private final CourseDao courseDao;

    // Constructor for injection
    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE

        return courseDao.getAllCourses(); 

        //YOUR CODE ENDS HERE
    }

    public Course getCourseById(int id) {
        //YOUR CODE STARTS HERE

        try {
            return courseDao.findCourseById(id);
        } catch (DataAccessException ex) {
            Course notFound = new Course();
            notFound.setCourseId(id);
            notFound.setCourseName("Course Not Found");
            notFound.setCourseDesc("Course Not Found");
            return notFound;
        }

        //YOUR CODE ENDS HERE
    }

    public Course addNewCourse(Course course) {
        //YOUR CODE STARTS HERE

        boolean nameBlank = course.getCourseName() == null || course.getCourseName().isEmpty();
        boolean descBlank = course.getCourseDesc() == null || course.getCourseDesc().isEmpty();

        if (nameBlank || descBlank) {
            course.setCourseName("Name blank, course NOT added");
            course.setCourseDesc("Description blank, course NOT added");
            return course;
        }

        return courseDao.createNewCourse(course);

        //YOUR CODE ENDS HERE
    }

    public Course updateCourseData(int id, Course course) {
        //YOUR CODE STARTS HERE

        if (id != course.getCourseId()) {
            course.setCourseName("IDs do not match, course not updated");
            course.setCourseDesc("IDs do not match, course not updated");
            return course;
        }

        courseDao.updateCourse(course);
        
        try {
            return courseDao.findCourseById(id);
        } catch (DataAccessException ex) {
            Course notFound = new Course();
            notFound.setCourseId(id);
            notFound.setCourseName("Course Not Found");
            notFound.setCourseDesc("Course Not Found");
            return notFound;
        }

        //YOUR CODE ENDS HERE
    }

    public void deleteCourseById(int id) {
        //YOUR CODE STARTS HERE
        courseDao.deleteCourse(id);
        System.out.println("Course ID: " + id + " deleted");

        //YOUR CODE ENDS HERE
    }
}
