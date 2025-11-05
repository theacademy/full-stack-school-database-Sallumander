package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.TeacherMapper;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher createNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE

        teacher.setTeacherId(0);
        final String sql = "INSERT INTO teacher (firstName, lastName) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, teacher.getTeacherFName());
            ps.setString(2, teacher.getTeacherLName());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            teacher.setTeacherId(key.intValue());
        }
        return teacher;

        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE

        String sql = "SELECT * FROM teacher";
        List<Teacher> teachers = jdbcTemplate.query(sql, new TeacherMapper());
        return teachers;

        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher findTeacherById(int id) {
        //YOUR CODE STARTS HERE

        String sql = "SELECT * FROM teacher WHERE tid = ?";
        Teacher teacher = jdbcTemplate.queryForObject(sql, new TeacherMapper(), id);
        return teacher;

        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateTeacher(Teacher t) {
        //YOUR CODE STARTS HERE

        String sql = "UPDATE teacher SET firstName = ?, lastName = ? WHERE tid = ?";
        jdbcTemplate.update(sql, t.getTeacherFName(), t.getTeacherLName(), t.getTeacherId());

        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteTeacher(int id) {
        //YOUR CODE STARTS HERE

        String sql = "DELETE FROM teacher WHERE tid = ?";
        jdbcTemplate.update(sql, id);

        //YOUR CODE ENDS HERE
    }
}
