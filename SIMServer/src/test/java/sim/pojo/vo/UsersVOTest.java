package sim.pojo.vo;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class UsersVOTest {

    @Test
    public void getId1() {
        UsersVO user = new UsersVO();
        Assert.assertNull(user.getId());
    }

    @Test
    public void getId2() {
        UsersVO user = new UsersVO();
        try {
            Field id = UsersVO.class.getDeclaredField("id");
            id.set(user,"666");
            Assert.assertEquals("666", user.getId());
        }catch(Exception e){

        }

    }

    @Test
    public void setId1() {
        UsersVO user = new UsersVO();
        user.setId("dh89whd98weghf934h8493t4946y594tf146ges");
        Assert.assertEquals("dh89whd98weghf934h8493t4946y594tf146ges", user.getId());
    }


    @Test
    public void setId2() {
        UsersVO user = new UsersVO();
        user.setId("");
        Assert.assertEquals("", user.getId());
    }

    @Test
    public void getUsername1() {
        UsersVO user = new UsersVO();
        Assert.assertNull(user.getUsername());
    }

    @Test
    public void getUsername2() {
        UsersVO user = new UsersVO();
        try {
            Field id = UsersVO.class.getDeclaredField("username");
            id.set(user,"666");
            Assert.assertEquals("666", user.getUsername());
        }catch(Exception e){

        }
    }

    @Test
    public void setUsername1() {
        UsersVO user = new UsersVO();
        user.setUsername("dh89whd98weghf934h8493t4946y594tf146ges");
        Assert.assertEquals("dh89whd98weghf934h8493t4946y594tf146ges", user.getUsername());
    }

    @Test
    public void setUsername2() {
        UsersVO user = new UsersVO();
        user.setUsername("");
        Assert.assertEquals("", user.getUsername());
    }
    
    
    @Test
    public void getNickname1() {
        UsersVO user = new UsersVO();
        Assert.assertNull(user.getNickname());
    }

    @Test
    public void getNickname2() {
        UsersVO user = new UsersVO();
        try {
            Field id = UsersVO.class.getDeclaredField("nickname");
            id.set(user,"666");
            Assert.assertEquals("666", user.getNickname());
        }catch(Exception e){

        }
    }


    @Test
    public void setNickname1() {
        UsersVO user = new UsersVO();
        user.setNickname("dh89whd98weghf934h8493t4946y594tf146ges");
        Assert.assertEquals("dh89whd98weghf934h8493t4946y594tf146ges", user.getNickname());
    }


    @Test
    public void setNickname2() {
        UsersVO user = new UsersVO();
        user.setNickname("");
        Assert.assertEquals("", user.getNickname());
    }


    @Test
    public void getGpa1() {
        UsersVO user = new UsersVO();
        Assert.assertNull(user.getNickname());
    }

    @Test
    public void getGpa2() {
        UsersVO user = new UsersVO();
        try {
            Field id = UsersVO.class.getDeclaredField("gpa");
            id.set(user,6.6);
            Assert.assertEquals(6.6, (double)user.getGpa());
        }catch(Exception e){

        }
    }

    @Test
    public void setGpa1() {
        UsersVO user = new UsersVO();
        user.setGpa(0.0);
        Assert.assertEquals(0, (double)user.getGpa(),0);
    }

    @Test
    public void setGpa2() {
        UsersVO user = new UsersVO();
        user.setGpa(66.0);
        Assert.assertEquals(66, (double)user.getGpa(),0);
    }
}