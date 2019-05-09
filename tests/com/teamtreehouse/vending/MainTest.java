package com.teamtreehouse.vending;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MainTest {

    private PrintStream console = null;          // 声明（为null）：输出流 (字符设备) console
    private ByteArrayOutputStream bytes = null;  // 声明（为null）：bytes 用于缓存console 重定向过来的字符流
    private Main MainClass;

    @Before
    public void setUp() throws Exception {
        MainClass = new Main();
        bytes = new ByteArrayOutputStream();    // 分配空间
        console = System.out;                   // 获取System.out 输出流的句柄
        System.setOut(new PrintStream(bytes));  // 将原本输出到控制台Console的字符流 重定向 到 bytes
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(console);
    }

    @Test
    public void testResult() throws Exception {
        String[] args = new String[]{};
        MainClass.main(args);
        String s = "Restocking\r\n"+
                "Adding money\r\n"+
                "Vending\r\n"+
                "Sold item Twinkies for 75\r\n"+
                "Restocking\r\n"+
                "java.lang.IllegalArgumentException: There are only 10 spots left\r\n"+
                "Adding money\r\n"+
                "java.lang.IllegalArgumentException: Money can't be negative\r\n" +
                "Vending\r\n" +
                "java.lang.NullPointerException\r\n" +
                "Vending\r\n" +
                "com.teamtreehouse.vending.InvalidLocationException: Invalid buttons\r\n" +
                "Vending\r\n" +
                "java.lang.NullPointerException\r\n";    // 注意：控制台的换行，这里用 '\n' 表示
        assertEquals(s, bytes.toString());          // bytes.toString() 作用是将 bytes内容 转换为字符流
    }
}
