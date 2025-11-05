package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.TeacherDao;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherServiceInterface {

    // DAO dependency
    private final TeacherDao teacherDao;

    // Constructor for injection
    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    //YOUR CODE ENDS HERE

    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE

        return teacherDao.getAllTeachers();

        //YOUR CODE ENDS HERE
    }

    public Teacher getTeacherById(int id) {
        //YOUR CODE STARTS HERE

        try {
            return teacherDao.findTeacherById(id);
        } catch (DataAccessException ex) {
            Teacher notFound = new Teacher();
            notFound.setTeacherId(id);
            notFound.setTeacherFName("Teacher Not Found");
            notFound.setTeacherLName("Teacher Not Found");
            return notFound;
        }

        //YOUR CODE ENDS HERE
    }

    public Teacher addNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        boolean firstBlank = teacher.getTeacherFName() == null || teacher.getTeacherFName().trim().isEmpty();
        boolean lastBlank = teacher.getTeacherLName() == null || teacher.getTeacherLName().trim().isEmpty();

        if (firstBlank || lastBlank) {
            teacher.setTeacherFName("First Name blank, teacher NOT added");
            teacher.setTeacherLName("Last Name blank, teacher NOT added");
            return teacher;
        }

        return teacherDao.createNewTeacher(teacher);

        //YOUR CODE ENDS HERE
    }

    public Teacher updateTeacherData(int id, Teacher teacher) {
        //YOUR CODE STARTS HERE

        if (id != teacher.getTeacherId()) {
            teacher.setTeacherFName("IDs do not match, teacher not updated");
            teacher.setTeacherLName("IDs do not match, teacher not updated");
            return teacher;
        }

        teacherDao.updateTeacher(teacher);
        try {
            return teacherDao.findTeacherById(id);
        } catch (DataAccessException ex) {
            Teacher notFound = new Teacher();
            notFound.setTeacherId(id);
            notFound.setTeacherFName("Teacher Not Found");
            notFound.setTeacherLName("Teacher Not Found");
            return notFound;
        }

        //YOUR CODE ENDS HERE
    }

    public void deleteTeacherById(int id) {
        //YOUR CODE STARTS HERE

        teacherDao.deleteTeacher(id);

        //YOUR CODE ENDS HERE
    }
}
