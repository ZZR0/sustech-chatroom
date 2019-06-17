package sim.DaoImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sim.Application;
import sim.Dao.UserDao;
import sim.netty.ChatMsg;
import sim.pojo.Users;
import sun.usagetracker.UsageTrackerClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void queryUsernameIsExistCorrect() {
        Assert.assertTrue(userDao.queryUsernameIsExist("test"));
    }

    @Test
    public void queryUsernameIsExistWrong() {
        Assert.assertFalse(userDao.queryUsernameIsExist("dgiwefdbiwebfi"));
    }

    @Test
    public void queryUserForLoginFail() {
        Assert.assertNotNull(userDao.queryUserForLogin("test","123456"));
    }


    @Test
    public void queryUserForLoginSuccess() {
        Assert.assertNotNull(userDao.queryUserForLogin("123","123"));
    }



    @Test
    public void createUser1() {

        Users user = new Users();
        user.setId("test");
        user.setUsername("test");
        user.setGpa(0.0);
        user.setNickname("test");
        user.setPassword("123456");
        try{
            userDao.createUser(user);
        }catch (Exception e){
            return;
        }
        Assert.fail();
    }

    @Test
    public void createUser2() {
        Random rnd = new Random();
        Users user = new Users();
        double num = rnd.nextInt(1000);
        user.setId("test" + num);
        user.setUsername("test"+ num);
        user.setGpa(0.0);
        user.setNickname("test" + num);
        user.setPassword("123456");
        userDao.createUser(user);
    }


    @Test
    public void updateUserInfo1() {
        Users user = userDao.queryUserById("0");
        Assert.assertEquals(user.getId(),userDao.updateUserInfo(user).getId());
    }

    @Test
    public void updateUserInfo2() {
        Users user = userDao.queryUserById("0");
        user.setNickname("666666");
        Assert.assertEquals(user.getNickname(),userDao.updateUserInfo(user).getNickname());
    }


    @Test
    public void queryUserById1() {
        Users user = userDao.queryUserById("0");
        Assert.assertNotNull(user);
    }

    @Test
    public void queryUserById2() {
        Users user = userDao.queryUserById("tesffefsfdsfdsdfst");
        Assert.assertNull(user);
    }

    @Test
    public void saveMsg1() {
        ChatMsg msgDB = new ChatMsg();
        msgDB.setReceiverId("1");
        msgDB.setSenderId("2");
        msgDB.setMsg("test");
        msgDB.setMsgId(userDao.saveMsg(msgDB));
        assertNotNull(msgDB.getMsgId());
    }

    @Test
    public void saveMsg2() {
        ChatMsg msgDB = new ChatMsg();
        msgDB.setReceiverId("2");
        msgDB.setSenderId("1");
        msgDB.setMsg("test2");
        msgDB.setMsgId(userDao.saveMsg(msgDB));
        assertNotNull(msgDB.getMsgId());
    }

    @Test
    public void getUnReadMsgList1(){
        assertTrue(userDao.getUnReadMsgList("1").size() > 0);
    }

    @Test
    public void getUnReadMsgList2(){
        assertTrue(userDao.getUnReadMsgList("2").size() > 0);
    }

    @Test
    public void updateMsgSigned(){
        ChatMsg msgDB = new ChatMsg();
        msgDB.setReceiverId("2");
        msgDB.setSenderId("1");
        msgDB.setMsg("test2");

        String msgId = userDao.saveMsg(msgDB);
        int a = userDao.getUnReadMsgList("2").size();
        List<String> msgIdList = new ArrayList<>();
        msgIdList.add(msgId);
        userDao.updateMsgSigned(msgIdList);
        int b = userDao.getUnReadMsgList("2").size();
        Assert.assertEquals(a-1,b);
    }

    @Test
    public void updateMsgSigned2(){
        ChatMsg msgDB = new ChatMsg();
        msgDB.setReceiverId("2");
        msgDB.setSenderId("1");
        msgDB.setMsg("test2");

        String msgId = userDao.saveMsg(msgDB);
        int a = userDao.getUnReadMsgList("2").size();
        int b = userDao.getUnReadMsgList("2").size();
        Assert.assertEquals(a,b);
    }
}