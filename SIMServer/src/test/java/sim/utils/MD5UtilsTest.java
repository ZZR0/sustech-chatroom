package sim.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MD5UtilsTest {

    @Test
    public void getMD5Str1() {
        try{
            Assert.assertNull(MD5Utils.getMD5Str(null));
        }catch(Exception e){
            System.err.println("error");
        }
    }


    @Test
    public void getMD5Str2() {
        try{
            Assert.assertEquals("+uCyfEUccohnpWfowbtOUw==", MD5Utils.getMD5Str("666"));
        }catch(Exception e){
            System.err.println("error");
        }
    }
}