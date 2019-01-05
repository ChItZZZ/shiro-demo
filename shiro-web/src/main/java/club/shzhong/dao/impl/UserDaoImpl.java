package club.shzhong.dao.impl;

import club.shzhong.dao.UserDao;
import club.shzhong.domain.User;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUsername(String username) {

        String sql = "select username,password from users where username = ?";

        List<User> list = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });

        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> queryRolesByUsername(String username)  {
        String sql = "select role_name from users_roles where username = ?";
        return jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("role_name");
            }
        });
    }
}
