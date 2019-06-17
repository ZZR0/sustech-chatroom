package sim.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sim.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class SpringUtilsTest {

    @Test
    public void getApplicationContextTest(){
        Assert.assertNotNull(SpringUtil.getApplicationContext());
    }

    @Test
    public void getBeanTest(){
        Assert.assertNotNull(SpringUtil.getBean("userDaoImpl"));
    }
}
