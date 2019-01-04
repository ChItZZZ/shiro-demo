package club.shzhong.dao;

import club.shzhong.domain.User;

import java.util.List;

public interface UserDao {
    User getUserByUsername(String username);

    List<String> queryRolesByUsername(String username);
}
