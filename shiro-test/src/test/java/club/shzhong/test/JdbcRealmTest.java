package club.shzhong.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {
    @Test
    public void testAuthentication() {
        DruidDataSource datasource = new DruidDataSource();

        {
            datasource.setUrl("jdbc:mysql://localhost:3306/shiro_test");
            datasource.setUsername("root");
            datasource.setPassword("123");
        }

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(datasource);
        //查询权限数据
        jdbcRealm.setPermissionsLookupEnabled(true);

//        使用自定义的数据表
        String sql = "select password from test_user where username = ?";
        jdbcRealm.setAuthenticationQuery(sql);



//        1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);


//        2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123");
        subject.login(token);

//        认证
        System.out.println("isAuth:" + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:select");

    }
}
