package com.example.se_project;

import android.util.Size;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

public class CompareSizesByAreaTest {

    @Test
    public void compare1() {
        CompareSizesByArea compareSizesByArea = new CompareSizesByArea();

        Size size1 = PowerMockito.mock(Size.class);
        Size size2 = PowerMockito.mock(Size.class);

        PowerMockito.when(size1.getWidth()).thenReturn(5);
        PowerMockito.when(size1.getHeight()).thenReturn(5);
        PowerMockito.when(size2.getWidth()).thenReturn(6);
        PowerMockito.when(size2.getHeight()).thenReturn(6);


        Assert.assertTrue(compareSizesByArea.compare(size1,size2) < 0);
    }

    @Test
    public void compare2() {
        CompareSizesByArea compareSizesByArea = new CompareSizesByArea();
        Size size1 = PowerMockito.mock(Size.class);
        Size size2 = PowerMockito.mock(Size.class);

        PowerMockito.when(size1.getWidth()).thenReturn(5);
        PowerMockito.when(size1.getHeight()).thenReturn(5);
        PowerMockito.when(size2.getWidth()).thenReturn(6);
        PowerMockito.when(size2.getHeight()).thenReturn(6);
        PowerMockito.when(size2.getHeight()).thenReturn(6);
        PowerMockito.when(size2.getHeight()).thenReturn(6);
        Assert.assertTrue(compareSizesByArea.compare(size2,size1) > 0);
    }
}