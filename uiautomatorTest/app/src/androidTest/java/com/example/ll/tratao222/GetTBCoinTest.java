package com.example.ll.tratao222;

import android.app.Instrumentation;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

//import static com.example.ll.tratao222.tUiObjectUtil.*;


/**
 * Created by ll on 2018/12/29.
 */
@RunWith(AndroidJUnit4.class)


public class GetTBCoinTest {
    public Instrumentation instrumentation;
    public static UiDevice mUiDevice;

    @Before
    public void setUp() throws RemoteException, IOException {
        this.instrumentation = InstrumentationRegistry.getInstrumentation();
        this.mUiDevice = UiDevice.getInstance(instrumentation);
        tUiObjectUtil.getInstance(instrumentation).unClock();
    }

    @Test
    public void test001() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).launchApp("com.taobao.taobao/com.taobao.tao.TBMainActivity");
        tUiObjectUtil.getInstance(instrumentation).sleep(10000);
        tUiObjectUtil.getInstance(instrumentation).longClick(888,1234,50);
        tUiObjectUtil.getInstance(instrumentation).sleep(1000);
        if (tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*召唤.*").exists()) {
            tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*召唤.*").click();
        }
        tUiObjectUtil.getInstance(instrumentation).sleep(500);

        run(40);
    }

    public void run(int num) throws IOException, UiObjectNotFoundException {
        for(int i = 0 ; i<=num;i++) {
            //领猫币
            tUiObjectUtil.getInstance(instrumentation).longClick(950,1700,50);
            tUiObjectUtil.getInstance(instrumentation).sleep(500);
            tUiObjectUtil.getInstance(instrumentation).longClick(950,1700,50);
            tUiObjectUtil.getInstance(instrumentation).sleep(500);

            //去逛店
            tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*去逛店.*").click();
            tUiObjectUtil.getInstance(instrumentation).sleep(1300);

            if ( tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*明天再来.*").exists()){
                return;
            }

            //猫猫出现啦
            tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*猫猫出现啦.*").click();
            tUiObjectUtil.getInstance(instrumentation).sleep(1000);

            //开心收下
            tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*开心收下.*").click();
            tUiObjectUtil.getInstance(instrumentation).sleep(500);

            //back返回
            tUiObjectUtil.getInstance(instrumentation).pressBack();
            tUiObjectUtil.getInstance(instrumentation).sleep(1000);
        }
    }

    @After
    public void teardown(){
//        tUiObjectUtil.exitApp();
    }
}
