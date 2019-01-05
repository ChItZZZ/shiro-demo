package club.shzhong.shiro.realm;

import club.shzhong.dao.UserDao;
import club.shzhong.domain.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.*;

public class CustomRealm extends AuthorizingRealm {

//    Map<String, String> userMap = new HashMap<String, String>(16);
//
//    {
//
//        //123 md5
//        userMap.put("mark", "6d295738eb6579053ac46a9ca1902583");
//        super.setName("customRealm");
//    }

    @Resource
    private UserDao userDao;

//    授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        Set<String> roles = getRoleByUsername(username);
        Set<String> permissions = getPermissionByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionByUsername(String username) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }


    //    模拟从数据库获取缓存中获取用户角色
    private Set<String> getRoleByUsername(String username) {
        List<String> list = userDao.queryRolesByUsername(username);
        Set<String> sets = new HashSet<String>(list);
        return sets;
    }

//    认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        从主体传过来的认证信息中 获取用户名
        String username = (String) token.getPrincipal();

//        通过用户名到数据库获取凭证
        String password = getPasswordByUsername(username);
        if (password == null) {
            return null;
        }
        //认证，登录的username，数据库的password，根据username获取登录的password进行对比
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "customRealm");
//        加盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return authenticationInfo;
    }

    //    模拟数据库
    private String getPasswordByUsername(String username) {
        User user = userDao.getUserByUsername(username);
        if(user != null){
            return user.getPassword();
        }
        return null;
    }

//    public static void main(String[] args) {
////        source 原始密码 salt 盐
//        Md5Hash md5Hash = new Md5Hash("123", "mark");
//        System.out.println(md5Hash);
//    }
}
