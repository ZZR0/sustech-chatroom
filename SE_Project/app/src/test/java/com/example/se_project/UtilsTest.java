package com.example.se_project;

import org.junit.Test;

import static org.junit.Assert.*;




public class UtilsTest {
    @Test
    public void testonCheckchar1(){
        Utils u = new Utils();
        assertTrue(u.checkChar('a'));
    }

    @Test
    public void testonCheckchar2(){
        Utils u = new Utils();
        assertFalse(u.checkChar('牛'));
    }

    @Test
    public void testonCheckstring1(){
        Utils u = new Utils();
        assertTrue(u.checkString("I'm fine, thank you."));
    }
    @Test
    public void testonCheckstring2(){
        Utils u = new Utils();
        assertFalse(u.checkString("我很好谢谢。"));
    }

}
