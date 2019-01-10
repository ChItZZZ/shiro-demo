import club.shzhong.dao.UserDao;
import club.shzhong.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*"})
@WebAppConfiguration
public class Demo {
    @Autowired
    private UserDao userDao;

    @Test
    public void test() throws NoSuchMethodException {
        User mark = userDao.getUserByUsername("mark");
        System.out.println(mark.toString());

        System.out.println(mark.getClass().getDeclaredMethod("toString"));
    }
}
