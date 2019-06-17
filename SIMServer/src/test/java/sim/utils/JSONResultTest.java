package sim.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JSONResultTest {

    @Test
    public void testBuild1() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        JSONResult jsonResult = JSONResult.build(200,"data",arr);
        Assert.assertEquals(200,(int)jsonResult.getStatus());
        Assert.assertEquals("data",jsonResult.getMsg());
        Assert.assertSame(arr, jsonResult.getData());
    }

    @Test
    public void testBuild2() {
        JSONResult jsonResult = JSONResult.build(555,"grewgtgtrfdsgffdsgffd",null);
        Assert.assertEquals(555,(int)jsonResult.getStatus());
        Assert.assertEquals("grewgtgtrfdsgffdsgffd",jsonResult.getMsg());
        Assert.assertNull(jsonResult.getData());
    }

    @Test
    public void okWithData1() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        JSONResult jsonResult = JSONResult.ok(arr);
        Assert.assertEquals(200,(int)jsonResult.getStatus());
        Assert.assertEquals("OK",jsonResult.getMsg());
        Assert.assertSame(arr, jsonResult.getData());
    }

    @Test
    public void okWithData2() {
        JSONResult jsonResult = JSONResult.ok(null);
        Assert.assertEquals(200,(int)jsonResult.getStatus());
        Assert.assertEquals("OK",jsonResult.getMsg());
        Assert.assertNull(jsonResult.getData());
    }


    @Test
    public void okWithoutData1() {
        JSONResult jsonResult = JSONResult.ok();
        Assert.assertNull(jsonResult.getData());
    }


    @Test
    public void okWithoutData2() {
        JSONResult jsonResult = JSONResult.ok();
        Assert.assertEquals(200,(int)jsonResult.getStatus());
        Assert.assertEquals("OK",jsonResult.getMsg());
    }


    @Test
    public void errorMsg1() {
        JSONResult jsonResult = JSONResult.errorMsg(null);
        Assert.assertNull(jsonResult.getData());
        Assert.assertNull(jsonResult.getMsg());
        Assert.assertEquals(500,(int)jsonResult.getStatus());
    }

    @Test
    public void errorMsg2() {
        JSONResult jsonResult = JSONResult.errorMsg("error error");
        Assert.assertNull(jsonResult.getData());
        Assert.assertEquals("error error", jsonResult.getMsg());
        Assert.assertEquals(500,(int)jsonResult.getStatus());
    }


    @Test
    public void errorMap1() {
        JSONResult jsonResult = JSONResult.errorMap(null);
        Assert.assertEquals(501,(int)jsonResult.getStatus());
        Assert.assertEquals("error",jsonResult.getMsg());
        Assert.assertNull(jsonResult.getData());
    }


    @Test
    public void errorMap2() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        JSONResult jsonResult = JSONResult.errorMap(arr);
        Assert.assertEquals(501,(int)jsonResult.getStatus());
        Assert.assertEquals("error",jsonResult.getMsg());
        Assert.assertSame(arr, jsonResult.getData());
    }

    @Test
    public void errorTokenMsg1() {
        JSONResult jsonResult = JSONResult.errorTokenMsg(null);
        Assert.assertEquals(502,(int)jsonResult.getStatus());
        Assert.assertEquals(null,jsonResult.getMsg());
        Assert.assertNull(jsonResult.getData());
    }

    @Test
    public void errorTokenMsg2() {
        JSONResult jsonResult = JSONResult.errorTokenMsg("fihwebfiu78ght9rb5f465wef￥%……&*");
        Assert.assertEquals(502,(int)jsonResult.getStatus());
        Assert.assertEquals("fihwebfiu78ght9rb5f465wef￥%……&*",jsonResult.getMsg());
        Assert.assertNull(jsonResult.getData());

    }


    @Test
    public void errorException1() {
        JSONResult jsonResult = JSONResult.errorException("exception");
        Assert.assertEquals(555,(int)jsonResult.getStatus());
        Assert.assertNull(jsonResult.getData());
        Assert.assertEquals("exception",jsonResult.getMsg());
    }


    @Test
    public void errorException2() {
        JSONResult jsonResult = JSONResult.errorException(null);
        Assert.assertEquals(555,(int)jsonResult.getStatus());
        Assert.assertNull(jsonResult.getData());
        Assert.assertNull(jsonResult.getMsg());
    }


    @Test
    public void isOKValid() {
        JSONResult jsonResult = new JSONResult(null);
        Assert.assertTrue(jsonResult.isOK());
    }

    @Test
    public void isOKNValid() {
        JSONResult jsonResult = JSONResult.errorException(null);
        Assert.assertFalse(jsonResult.isOK());
    }


    @Test
    public void getStatus1() {
        JSONResult jsonResult = new JSONResult(null);
        Assert.assertEquals(200, (int)jsonResult.getStatus());
    }


    @Test
    public void getStatus2() {
        JSONResult jsonResult = new JSONResult(500, "error", new ArrayList<Integer>());
        Assert.assertEquals(500, (int)jsonResult.getStatus());
    }


    @Test
    public void setStatus1() {
        JSONResult jsonResult = new JSONResult(null);
        jsonResult.setStatus(4894);
        Assert.assertEquals(4894, (int)jsonResult.getStatus());
    }


    @Test
    public void setStatus2() {
        JSONResult jsonResult = new JSONResult(null);
        jsonResult.setStatus(500);
        Assert.assertEquals(500, (int)jsonResult.getStatus());
    }


    @Test
    public void getMsg1() {
        JSONResult jsonResult = new JSONResult(null);
        Assert.assertEquals("OK", jsonResult.getMsg());
    }


    @Test
    public void getMsg2() {
        JSONResult jsonResult = new JSONResult(500, null, new ArrayList<Integer>());
        Assert.assertNull(jsonResult.getMsg());
    }


    @Test
    public void setMsg1() {
        JSONResult jsonResult = new JSONResult(null);
        jsonResult.setMsg(null);
        Assert.assertNull(jsonResult.getMsg());
    }


    @Test
    public void setMsg2() {
        JSONResult jsonResult = new JSONResult(null);
        jsonResult.setMsg("fhiwerfioohweofhweofhowehfoweaf%$&^Y(GF*R*&GVTD^%FCV YTRCX^FVF&");
        Assert.assertEquals("fhiwerfioohweofhweofhowehfoweaf%$&^Y(GF*R*&GVTD^%FCV YTRCX^FVF&", jsonResult.getMsg());
    }


    @Test
    public void getData1() {
        JSONResult jsonResult = new JSONResult(null);
        Assert.assertNull(jsonResult.getData());
    }


    @Test
    public void getData2() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(464);
        arr.add(89799);
        JSONResult jsonResult = new JSONResult(arr);
        Assert.assertSame(arr,jsonResult.getData());
    }

    @Test
    public void setData1() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(464);
        arr.add(89799);
        JSONResult jsonResult = new JSONResult(arr);
        jsonResult.setData(null);
        Assert.assertNull(jsonResult.getData());
    }


    @Test
    public void setData2() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(464);
        arr.add(89799);
        JSONResult jsonResult = new JSONResult(null);
        jsonResult.setData(arr);
        Assert.assertSame(arr,jsonResult.getData());
    }
}