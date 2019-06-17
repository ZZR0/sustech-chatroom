package sim.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JsonUtilsTest {

    @Test
    public void objectToJson1() {
        Assert.assertEquals("null", JsonUtils.objectToJson(null));
    }

    @Test
    public void objectToJson2() {
       Assert.assertEquals("\"dasdoa\"", JsonUtils.objectToJson("dasdoa"));
    }

    @Test
    public void jsonToPojo1() {
        String str = JsonUtils.jsonToPojo("\"dasdoa\"",String.class);
        Assert.assertEquals("dasdoa", str);
    }

    @Test
    public void jsonToPojo2() {
        Object obj = JsonUtils.jsonToPojo("null",Object.class);
        Assert.assertNull(obj);
    }


    @Test
    public void jsonToList1() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(4978456);
        arr.add(-4949);
        ArrayList<Integer> ans = (ArrayList<Integer>) JsonUtils.jsonToList(arr.toString(),Integer.class);
        Assert.assertArrayEquals(arr.toArray(),ans.toArray());
    }

    @Test
    public void jsonToList2() {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("true");
        arr.add("false");
        ArrayList<String> ans = (ArrayList<String>) JsonUtils.jsonToList(arr.toString(),String.class);
        Assert.assertArrayEquals(arr.toArray(),ans.toArray());
    }
}