package sim.pojo;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class UsersTest {

    @Test
    public void getId1() {
        Users user = new Users();
        Assert.assertNull(user.getId());
    }

    @Test
    public void getId2() {
        Users user = new Users();
        try {
            Field id = Users.class.getDeclaredField("id");
            id.set(user,"666");
            Assert.assertEquals("666", user.getId());
        }catch(Exception e){

        }

    }

    @Test
    public void setId1() {
        Users user = new Users();
        user.setId("dh89whd98weghf934h8493t4946y594tf146ges");
        Assert.assertEquals("dh89whd98weghf934h8493t4946y594tf146ges", user.getId());
    }


    @Test
    public void setId2() {
        Users user = new Users();
        user.setId("");
        Assert.assertEquals("", user.getId());
    }

    @Test
    public void getUsername1() {
        Users user = new Users();
        Assert.assertNull(user.getUsername());
    }

    @Test
    public void getUsername2() {
        Users user = new Users();
        try {
            Field id = Users.class.getDeclaredField("username");
            id.set(user,"666");
            Assert.assertEquals("666", user.getUsername());
        }catch(Exception e){

        }
    }

    @Test
    public void setUsername1() {
        Users user = new Users();
        user.setUsername("dh89whd98weghf934h8493t4946y594tf146ges");
        Assert.assertEquals("dh89whd98weghf934h8493t4946y594tf146ges", user.getUsername());
    }

    @Test
    public void setUsername2() {
        Users user = new Users();
        user.setUsername("");
        Assert.assertEquals("", user.getUsername());
    }


    @Test
    public void getPassword1() {
        Users user = new Users();
        Assert.assertNull(user.getPassword());
    }


    @Test
    public void getPassword2() {
        Users user = new Users();
        try {
            Field id = Users.class.getDeclaredField("password");
            id.set(user,"666");
            Assert.assertEquals("666", user.getPassword());
        }catch(Exception e){

        }
    }


    @Test
    public void setPassword1() {
        Users user = new Users();
        user.setPassword("dh89whd98weghf934h8493t4946y594tf146ges");
        Assert.assertEquals("dh89whd98weghf934h8493t4946y594tf146ges", user.getPassword());
    }

    @Test
    public void setPassword2() {
        Users user = new Users();
        user.setPassword("");
        Assert.assertEquals("", user.getPassword());
    }


    @Test
    public void getNickname1() {
        Users user = new Users();
        Assert.assertNull(user.getNickname());
    }

    @Test
    public void getNickname2() {
        Users user = new Users();
        try {
            Field id = Users.class.getDeclaredField("nickname");
            id.set(user,"666");
            Assert.assertEquals("666", user.getNickname());
        }catch(Exception e){

        }
    }


    @Test
    public void setNickname1() {
        Users user = new Users();
        user.setNickname("dh89whd98weghf934h8493t4946y594tf146ges");
        Assert.assertEquals("dh89whd98weghf934h8493t4946y594tf146ges", user.getNickname());
    }


    @Test
    public void setNickname2() {
        Users user = new Users();
        user.setNickname("");
        Assert.assertEquals("", user.getNickname());
    }


    @Test
    public void getGpa1() {
        Users user = new Users();
        Assert.assertNull(user.getNickname());
    }

    @Test
    public void getGpa2() {
        Users user = new Users();
        try {
            Field id = Users.class.getDeclaredField("gpa");
            id.set(user,6.6);
            Assert.assertEquals(6.6, (double)user.getGpa());
        }catch(Exception e){

        }
    }

    @Test
    public void setGpa1() {
        Users user = new Users();
        user.setGpa(0.0);
        Assert.assertEquals(0, (double)user.getGpa(),0);
    }

    @Test
    public void setGpa2() {
        Users user = new Users();
        user.setGpa(66.0);
        Assert.assertEquals(66, (double)user.getGpa(),0);
    }
}