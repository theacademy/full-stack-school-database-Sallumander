package mthree.com.fullstackschool.dao.mappers;

import mthree.com.fullstackschool.model.Teacher;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher> {
    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        //YOUR CODE STARTS HERE
        
    Teacher t = new Teacher();
    t.setTeacherId(rs.getInt("tid"));
    t.setTeacherFName(rs.getString("tFName"));
    t.setTeacherLName(rs.getString("tLName"));
        return t;


        //YOUR CODE ENDS HERE
    }
}
