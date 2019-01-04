package club.shzhong.test;

import club.shzhong.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {

    @Test
    public void testAuthentication() {
        CustomRealm customRealm = new CustomRealm();
//        1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//        算法名称
        matcher.setHashAlgorithmName("md5");
//        加密次数
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

//        2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123");
        subject.login(token);

//        认证
        System.out.println("isAuth:" + subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermissions("user:add", "user:delete");
    }
}
