package com.example.ll.tratao222;

import android.app.Instrumentation;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.ll.tratao222.tUiObjectUtil.getInstance;

import java.io.IOException;
import java.util.Calendar;

//import static com.example.ll.tratao222.tUiObjectUtil.*;


/**
 * Created by ll on 2018/12/29.
 */
@RunWith(AndroidJUnit4.class)


public class GetCoinTest {
    public Instrumentation instrumentation;
    public static UiDevice mUiDevice;

    @Before
    public void setUp() throws RemoteException, IOException {
        this.instrumentation = InstrumentationRegistry.getInstrumentation();
        this.mUiDevice = UiDevice.getInstance(instrumentation);
        getInstance(instrumentation).unClock();
    }

    @Test
    public void test001() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).pressBack(7);
        //com.yuncheapp.android.pearl/com.kuaishou.athena.MainActivity      快看点       com.jifen.qukan/com.jifen.qkbase.main.MainActivity   趣头条
        getInstance(instrumentation).launchApp("com.yuncheapp.android.pearl/com.kuaishou.athena.MainActivity");
        tUiObjectUtil.getInstance(instrumentation).sleep(11000);
        //打开应用
        Calendar calendar = Calendar.getInstance();
        Log.d("*******GETCOIN*******", "test001开始时间");
        runread(40);     //1200s  20分钟  40
        Log.d("*******GETCOIN*******", "test001结束时间 ");
        //开始读书
    }

    @Test
    public void test002() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).pressBack(7);
        //com.yuncheapp.android.pearl/com.kuaishou.athena.MainActivity      快看点       com.jifen.qukan/com.jifen.qkbase.main.MainActivity   趣头条
        getInstance(instrumentation).launchApp("com.jifen.qukan/com.jifen.qkbase.main.MainActivity");
        tUiObjectUtil.getInstance(instrumentation).sleep(11000);
        //打开应用
        runread(40);
        //开始读书
    }

    public void runread(int num) throws IOException, UiObjectNotFoundException {
        for(int i = 0 ; i<=num;i++) {
            tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*刷新.*||.*首页.*").click();
            tUiObjectUtil.getInstance(instrumentation).sleep(3000);
            //刷新列表
            tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*评论.*").click();
            tUiObjectUtil.getInstance(instrumentation).sleep(2000);
            read();
            //开始阅读，下拉五次然后退出阅读
        }
    }

    public void read(){
        for(int i=0;i<=7;i++) {
            tUiObjectUtil.getInstance(instrumentation).swipeUpHalf(100);
            tUiObjectUtil.getInstance(instrumentation).sleep(2000);
        }
        tUiObjectUtil.getInstance(instrumentation).pressBack();
    }

    public void read(int times){
        for(int i=0;i<=times;i++){
            tUiObjectUtil.getInstance(instrumentation).swipeUpHalf(100);
            tUiObjectUtil.getInstance(instrumentation).sleep(2000);
        }
        tUiObjectUtil.getInstance(instrumentation).pressBack();
    }

    @After
    public void teardown(){
//        tUiObjectUtil.exitApp();
    }
}
