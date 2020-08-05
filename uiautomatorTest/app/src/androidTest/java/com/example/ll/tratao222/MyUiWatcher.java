package com.example.ll.tratao222;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.util.Log;

import java.io.IOException;

/**
 * Created by ll on 2019/6/12.
 */

public class MyUiWatcher implements UiWatcher {

    private UiDevice mUiDevice;
    private Instrumentation instrumentation;

    public MyUiWatcher(UiDevice mUiDevice) {
        this.instrumentation = instrumentation;
        this.mUiDevice = mUiDevice;
    }

    @Override
    public boolean checkForCondition() {
        try {
            if(mUiDevice.findObject(new UiSelector().textMatches(".*邀请你进行.*")).exists()&&mUiDevice.findObject(new UiSelector().textMatches(".*接听.*")).exists()){
                Log.d("*********UIWATCHER", "checkForCondition: *************有微信视频/语音邀请，立即挂断！");
                UiObject ringing = tUiObjectUtil.getInstance(instrumentation).getObjectByDes("挂断");
                ringing.click();
                SystemClock.sleep(3500);
                tUiObjectUtil.getInstance(instrumentation).pressBack();
                tUiObjectUtil.writeLog("有微信视频/语音邀请，立即挂断！");
                return true;
            }
            if(mUiDevice.findObject(new UiSelector().textMatches(".*静音.*")).exists()&&mUiDevice.findObject(new UiSelector().textMatches(".*信息.*")).exists()){
                UiObject ringing = tUiObjectUtil.getInstance(instrumentation).getObjectByDes("右滑接听，左滑挂断");
                Log.d("*********UIWATCHER", "checkForCondition: ************有电话呼入，立即挂断！");
                ringing.swipeLeft(50);
                tUiObjectUtil.writeLog("有MT电话呼入，立即挂断！");
                return true;
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
