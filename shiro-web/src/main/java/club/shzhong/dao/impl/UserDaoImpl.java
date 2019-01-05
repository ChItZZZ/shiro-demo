package club.shzhong.dao.impl;

import club.shzhong.dao.UserDao;
import club.shzhong.domain.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUsername(String username) {

        String sql = "select username,password from users where username = ?";

        List<User> list = jdbcTemplate.query(sql, new String[]{username}, (rs, rowNum) -> {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        });

        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> queryRolesByUsername(String username)  {
        String sql = "select role_name from users_roles where username = ?";
        return jdbcTemplate.query(sql, new String[]{username}, (rs, rowNum) -> rs.getString("role_name"));
    }
}
