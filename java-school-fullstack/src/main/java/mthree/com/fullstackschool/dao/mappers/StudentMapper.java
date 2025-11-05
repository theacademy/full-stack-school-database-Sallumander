package mthree.com.fullstackschool.dao.mappers;

import mthree.com.fullstackschool.model.Student;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        //YOUR CODE STARTS HERE

        Student s = new Student();
        s.setStudentId(rs.getInt("sid"));
        s.setStudentFirstName(rs.getString("firstName"));
        s.setStudentLastName(rs.getString("lastName"));
        return s;

        //YOUR CODE ENDS HERE
    }
}
