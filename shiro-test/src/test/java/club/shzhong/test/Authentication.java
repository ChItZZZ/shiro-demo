package club.shzhong.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class Authentication {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
//        授权
        simpleAccountRealm.addAccount("mark", "123", "admin", "user");
    }

    @Test
    public void testAuthentication() {
//        1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
//        2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123");
        subject.login(token);

//        认证
        System.out.println("isAuth:" + subject.isAuthenticated());

//        subject.logout();
//
//        System.out.println("isAuth:" + subject.isAuthenticated());

//        检查主体角色 认证
        subject.checkRoles("admin", "user");
    }
}
