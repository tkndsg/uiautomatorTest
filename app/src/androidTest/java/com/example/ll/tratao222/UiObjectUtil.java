package com.example.ll.tratao222;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.InstrumentationUiAutomatorBridge;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yangxuhui on 2017-07-25.
 */
public class UiObjectUtil {
    private static UiObjectUtil instance;
    private Instrumentation mInstrumentation;
    private UiDevice mUiDevice;
    private Context mContext;

    private long timeout = 5000L;

    private UiObjectUtil(Instrumentation instrumentation) {
        this.mInstrumentation = instrumentation;
        this.mUiDevice = UiDevice.getInstance(instrumentation);
        this.mContext = instrumentation.getContext();
    }

    public static UiObjectUtil getInstance(Instrumentation instrumentation) {
        if(instance == null) {
            instance = new UiObjectUtil(instrumentation);
        }
        return instance;
    }

    public void swipe(int startX, int startY, int endX, int endY, int steps) {
        this.mUiDevice.swipe(startX, startY, endX, endY, steps);
        SystemClock.sleep(1000L);
    }

    public void swipeUp() {
        this.swipeUp(50);
    }

    public void swipeUp(int setps) {
        this.swipe(this.mUiDevice.getDisplayWidth() / 2, this.mUiDevice.getDisplayHeight() / 2, this.mUiDevice.getDisplayWidth() / 2, 0, setps);
        SystemClock.sleep(1000L);
    }

    public void swipeDown() {
        this.swipeDown(50);
    }

    public void swipeDown(int setps) {
            this.swipe(this.mUiDevice.getDisplayWidth() / 2, this.mUiDevice.getDisplayHeight() / 2, this.mUiDevice.getDisplayWidth() / 2, this.mUiDevice.getDisplayHeight(), setps);
            SystemClock.sleep(1000L);
    }

    public void swipeLeft() {
        this.swipeLeft(50);
    }

    public void swipeLeft(int setps) {
        this.swipe(this.mUiDevice.getDisplayWidth() / 2, this.mUiDevice.getDisplayHeight() / 2, 0, this.mUiDevice.getDisplayHeight() / 2, setps);
        SystemClock.sleep(1000L);
    }

    public void swipeRight() {
        this.swipeRight(50);
    }

    public void swipeRight(int steps) {
        this.swipe(this.mUiDevice.getDisplayWidth() / 2, this.mUiDevice.getDisplayHeight() / 2, this.mUiDevice.getDisplayWidth(), this.mUiDevice.getDisplayHeight() / 2, steps);
        SystemClock.sleep(1000L);
    }

    public boolean longClick(int x, int y) {
        return this.longClick(x, y, 3000L);
    }

    public boolean longClick(int x, int y, long mTimeout) {
        long stepTmp = mTimeout * 45L / 1000L;
        int step = Integer.parseInt(String.valueOf(stepTmp));
        return this.mUiDevice.drag(x, y, x, y, step);
    }

    public boolean longClick(UiObject2 obj2) throws UiObjectNotFoundException {
        return this.longClick(obj2.getVisibleBounds().centerX(), obj2.getVisibleBounds().centerY());
    }

    public boolean longClick(UiObject2 object2, long mTimeout) {
        return this.longClick(object2.getVisibleBounds().centerX(), object2.getVisibleBounds().centerY(), mTimeout);
    }

    public boolean pressPower() {
        return this.mUiDevice.pressKeyCode(26);
    }

    public boolean pressVolumeeUp() {
        return this.mUiDevice.pressKeyCode(24);
    }

    public boolean pressVolumeDown() {
        return this.mUiDevice.pressKeyCode(25);
    }

    public boolean pressVolumeMute() {
        return this.mUiDevice.pressKeyCode(164);
    }

    public boolean pressBack(int times) {
        if(times <= 0) {
            return false;
        } else {
            int i = 0;

            while(i < times && this.mUiDevice.pressBack()) {
                ++i;
                SystemClock.sleep(1000L);
            }

            return true;
        }
    }

    public boolean pressBack() {
        return this.pressBack(1);
    }

    public boolean pressHome() {
        return this.mUiDevice.pressHome();
    }

    public boolean pressBackToHome() {
        String desktopPackage = "com.meizu.flyme.launcher";

        for(int defaultTime = 8; defaultTime > 0 && !this.isObject2Exists(By.pkg(desktopPackage), 500L); --defaultTime) {
            this.pressBack(1);
        }

        return this.isObject2Exists(By.pkg(desktopPackage), 500L);
    }

    public boolean searchText(String text, long timeout) {
        long startTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - startTime < timeout) {
            if(this.isTextExists(text, 500L)) {
                return true;
            }

            this.swipeUp();
        }

        return false;
    }

    public boolean searchTextByObject2(BySelector bySel, String text, long timeout) {
        long startTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - startTime < timeout) {
            if(this.isTextExists(text, 500L)) {
                return true;
            }

            this.getObject2(bySel).swipe(Direction.DOWN, 2.0F);
        }

        return false;
    }

    public boolean searchObject2(BySelector bySel, long timeout) {
        long startTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - startTime < timeout) {
            if(this.isObject2Exists(bySel, 500L)) {
                return true;
            }

            this.swipeUp();
        }

        return false;
    }

    private Rect intersect(Rect rect1, Rect rect2) {
        int top = rect1.top > rect2.top?rect1.top:rect2.top;
        int bottom = rect1.bottom < rect2.bottom?rect1.bottom:rect2.bottom;
        int left = rect1.left > rect2.left?rect1.left:rect2.left;
        int right = rect1.right < rect2.right?rect1.right:rect2.right;
        return new Rect(left, top, right, bottom);
    }

    public UiObject2 toRight(BySelector bySelector, int instance, BySelector targetBySelector) {
        int minDist = -1;
        UiObject2 found = null;
        UiObject2 object2 = (UiObject2)this.getObject2List(bySelector).get(instance);
        List targetObject2List = this.mUiDevice.findObjects(targetBySelector);
        Iterator var8 = targetObject2List.iterator();

        while(true) {
            UiObject2 targetOb2;
            int dist;
            do {
                do {
                    if(!var8.hasNext()) {
                        return found;
                    }

                    targetOb2 = (UiObject2)var8.next();
                    Rect bounds = this.intersect(object2.getVisibleBounds(), targetOb2.getVisibleBounds());
                    int top = bounds.top;
                    int bottom = bounds.bottom;
                    dist = top < bottom?targetOb2.getVisibleBounds().left - object2.getVisibleBounds().right:-1;
                } while(dist < 0);
            } while(minDist >= 0 && dist >= minDist);

            minDist = dist;
            found = targetOb2;
        }
    }

    public UiObject2 toLeft(BySelector bySelector, int instance, BySelector targetBySelector) {
        int minDist = -1;
        UiObject2 found = null;
        UiObject2 object2 = (UiObject2)this.getObject2List(bySelector).get(instance);
        List targetObject2List = this.mUiDevice.findObjects(targetBySelector);
        Iterator var8 = targetObject2List.iterator();

        while(true) {
            UiObject2 targetOb2;
            int dist;
            do {
                do {
                    if(!var8.hasNext()) {
                        return found;
                    }

                    targetOb2 = (UiObject2)var8.next();
                    Rect bounds = this.intersect(object2.getVisibleBounds(), targetOb2.getVisibleBounds());
                    int top = bounds.top;
                    int bottom = bounds.bottom;
                    dist = top < bottom?object2.getVisibleBounds().left - targetOb2.getVisibleBounds().right:-1;
                } while(dist < 0);
            } while(minDist >= 0 && dist >= minDist);

            minDist = dist;
            found = targetOb2;
        }
    }

    public UiObject2 toUp(BySelector bySelector, int instance, BySelector targetBySelector) {
        int minDist = -1;
        UiObject2 found = null;
        UiObject2 object2 = (UiObject2)this.getObject2List(bySelector).get(instance);
        List targetObject2List = this.mUiDevice.findObjects(targetBySelector);
        Iterator var8 = targetObject2List.iterator();

        while(true) {
            UiObject2 targetOb2;
            int dist;
            do {
                do {
                    if(!var8.hasNext()) {
                        return found;
                    }

                    targetOb2 = (UiObject2)var8.next();
                    Rect bounds = this.intersect(object2.getVisibleBounds(), targetOb2.getVisibleBounds());
                    int top = bounds.left;
                    int bottom = bounds.right;
                    dist = top < bottom?object2.getVisibleBounds().top - targetOb2.getVisibleBounds().bottom:-1;
                } while(dist < 0);
            } while(minDist >= 0 && dist >= minDist);

            minDist = dist;
            found = targetOb2;
        }
    }

    public UiObject2 toDown(BySelector bySelector, int instance, BySelector targetBySelector) {
        int minDist = -1;
        UiObject2 found = null;
        UiObject2 object2 = (UiObject2)this.getObject2List(bySelector).get(instance);
        List targetObject2List = this.mUiDevice.findObjects(targetBySelector);
        Iterator var8 = targetObject2List.iterator();

        while(true) {
            UiObject2 targetOb2;
            int dist;
            do {
                do {
                    if(!var8.hasNext()) {
                        return found;
                    }

                    targetOb2 = (UiObject2)var8.next();
                    Rect bounds = this.intersect(object2.getVisibleBounds(), targetOb2.getVisibleBounds());
                    int top = bounds.left;
                    int bottom = bounds.right;
                    dist = top < bottom?targetOb2.getVisibleBounds().top - object2.getVisibleBounds().bottom:-1;
                } while(dist < 0);
            } while(minDist >= 0 && dist >= minDist);

            minDist = dist;
            found = targetOb2;
        }
    }

    public void clickByText(String text) {
        this.getObject2(By.text(text)).click();
    }

    public void clickByText(String text, long mTimeout) {
        this.getObject2(By.text(text), mTimeout).click();
    }

    public void clickByText(Pattern regex) {
        this.getObject2(By.text(regex)).click();
    }

    public void clickByText(Pattern regex, long mTimeout) {
        this.getObject2(By.text(regex), mTimeout).click();
    }

    public void clickByTextInList(String text, int instance) {
        ((UiObject2)this.getObject2List(By.text(text)).get(instance)).click();
    }

    public void clickByTextInList(String text, int instance, long mTimeout) {
        ((UiObject2)this.getObject2List(By.text(text), mTimeout).get(instance)).click();
    }

    public void clickByTextInList(Pattern regex, int instance) {
        ((UiObject2)this.getObject2List(By.text(regex)).get(instance)).click();
    }

    public void clickByTextInList(Pattern regex, int instance, long mTimeout) {
        ((UiObject2)this.getObject2List(By.text(regex), mTimeout).get(instance)).click();
    }

    public void clickById(String res) {
        this.getObject2(By.res(res)).click();
    }

    public void clickById(String res, long mTimeout) {
        this.getObject2(By.res(res), mTimeout).click();
    }

    public void clickById(Pattern regex) {
        this.getObject2(By.res(regex)).click();
    }

    public void clickById(Pattern regex, long mTimeout) {
        this.getObject2(By.res(regex), mTimeout).click();
    }

    public void clickByIdInList(String res, int instance) {
        ((UiObject2)this.getObject2List(By.res(res)).get(instance)).click();
    }

    public void clickByIdInList(String res, int instance, long mTimeout) {
        ((UiObject2)this.getObject2List(By.res(res), mTimeout).get(instance)).click();
    }

    public void clickByIdInList(Pattern regex, int instance) {
        ((UiObject2)this.getObject2List(By.res(regex)).get(instance)).click();
    }

    public void clickByIdInList(Pattern regex, int instance, long mTimeout) {
        ((UiObject2)this.getObject2List(By.res(regex), mTimeout).get(instance)).click();
    }

    public void clickByDesc(String desc) {
        this.getObject2(By.desc(desc)).click();
    }

    public void clickByDesc(String desc, long mTimeout) {
        this.getObject2(By.desc(desc), mTimeout).click();
    }

    public void clickByDesc(Pattern regex) {
        this.getObject2(By.desc(regex)).click();
    }

    public void clickByDesc(Pattern regex, long mTimeout) {
        this.getObject2(By.desc(regex), mTimeout).click();
    }

    public void clickByDescInList(String desc, int instance) {
        ((UiObject2)this.getObject2List(By.desc(desc)).get(instance)).click();
    }

    public void clickByDescInList(String desc, int instance, long mTimeout) {
        ((UiObject2)this.getObject2List(By.desc(desc), mTimeout).get(instance)).click();
    }

    public void clickByDescInList(Pattern regex, int instance) {
        ((UiObject2)this.getObject2List(By.desc(regex)).get(instance)).click();
    }

    public void clickByDescInList(Pattern regex, int instance, long mTimeout) {
        ((UiObject2)this.getObject2List(By.desc(regex), mTimeout).get(instance)).click();
    }

    public void clickByClassInList(String clazz, int instance) {
        ((UiObject2)this.getObject2List(By.clazz(clazz)).get(instance)).click();
    }

    public void clickByObj(UiObject obj) throws UiObjectNotFoundException {
        obj.waitForExists(this.timeout);
        obj.click();
    }

    public void clickByObj(UiObject obj, long mTimeout) throws UiObjectNotFoundException {
        obj.waitForExists(mTimeout);
        obj.click();
    }

    public void clickByObj2(BySelector bySel) {
        this.getObject2(bySel).click();
    }

    public void clickByObj2(BySelector bySel, long mTimeout) {
        this.getObject2(bySel, mTimeout).click();
    }

    public void clickByObj2InList(BySelector bySel, int instance) {
        ((UiObject2)this.getObject2List(bySel).get(instance)).click();
    }

    public void clickByObj2InList(BySelector bySel, int instance, long mTimeout) {
        SystemClock.sleep(200L);
        ((UiObject2)this.getObject2List(bySel, mTimeout).get(instance)).click();
    }

    public boolean isTextExists(String text, long timeout) {
        return this.isObject2Exists(By.text(text), timeout);
    }

    public boolean isTextExists(Pattern regex, long timeout) {
        return this.isObject2Exists(By.text(regex), timeout);
    }

    public boolean isIdExists(String id, long timeout) {
        return this.isObject2Exists(By.res(id), timeout);
    }

    public boolean isIdExists(Pattern regex, long timeout) {
        return this.isObject2Exists(By.res(regex), timeout);
    }

    public boolean isDescExists(String desc, long timeout) {
        return this.isObject2Exists(By.desc(desc), timeout);
    }

    public boolean isDescExists(Pattern regex, long timeout) {
        return this.isObject2Exists(By.desc(regex), timeout);
    }

    public boolean isObjectExists(UiObject obj, long timeout) {
        return obj.waitForExists(timeout);
    }

    public boolean isObject2Exists(BySelector bySel, long timeout) {
        return ((Boolean)this.mUiDevice.wait(Until.hasObject(bySel), timeout)).booleanValue();
    }

    public boolean isObject2Exists(BySelector bySel, int instance, long timeout) {
        return ((List)this.mUiDevice.wait(Until.findObjects(bySel), timeout)).size() > instance;
    }

    public UiObject2 getObject2(BySelector bySel) {
        return (UiObject2)this.mUiDevice.wait(Until.findObject(bySel), this.timeout);
    }

    public UiObject2 getObject2(BySelector bySel, long mTimeout) {
        return (UiObject2)this.mUiDevice.wait(Until.findObject(bySel), mTimeout);
    }

    public List<UiObject2> getObject2List(BySelector bySel) {
        return (List)this.mUiDevice.wait(Until.findObjects(bySel), this.timeout);
    }

    public List<UiObject2> getObject2List(BySelector bySel, long mTimeout) {
        return (List)this.mUiDevice.wait(Until.findObjects(bySel), mTimeout);
    }

    public String getTextById(String id) {
        return this.getObject2(By.res(id)).getText();
    }

    public String getTextByDesc(String desc) {
        return this.getObject2(By.desc(desc)).getText();
    }

    public void setTextById(String content, String id) {
        this.getObject2(By.res(id)).setText(content);
    }

    public void setTextByText(String content, String text) {
        this.getObject2(By.text(text)).setText(content);
    }

    public void setTextByDesc(String content, String desc) {
        this.getObject2(By.desc(content)).setText(content);
    }

    public void longPressVolumeUp() {
        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
        final InstrumentationUiAutomatorBridge mUiAutomationBridge = new InstrumentationUiAutomatorBridge(mInstrumentation.getContext(), mInstrumentation.getUiAutomation());
        Runnable command = new Runnable() {
            public void run() {
                long eventTime = SystemClock.uptimeMillis();
                KeyEvent downEvent = new KeyEvent(eventTime, eventTime, 0, 24, 0, 0, -1, 0, 0, 257);

                for(int upEvent = 0; upEvent < 20; ++upEvent) {
                    mUiAutomationBridge.injectInputEvent(downEvent, true);

                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var6) {
                        var6.printStackTrace();
                    }
                }

                KeyEvent var7 = new KeyEvent(eventTime, eventTime, 1, 24, 0, 0, -1, 0, 0, 257);
                mUiAutomationBridge.injectInputEvent(var7, true);
            }
        };
        command.run();
    }

    public void longPressVolumeDown() {
        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
        final InstrumentationUiAutomatorBridge mUiAutomationBridge = new InstrumentationUiAutomatorBridge(mInstrumentation.getContext(), mInstrumentation.getUiAutomation());
        Runnable command = new Runnable() {
            public void run() {
                long eventTime = SystemClock.uptimeMillis();
                KeyEvent downEvent = new KeyEvent(eventTime, eventTime, 0, 25, 0, 0, -1, 0, 0, 257);

                for(int upEvent = 0; upEvent < 20; ++upEvent) {
                    mUiAutomationBridge.injectInputEvent(downEvent, true);

                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var6) {
                        var6.printStackTrace();
                    }
                }

                KeyEvent var7 = new KeyEvent(eventTime, eventTime, 1, 25, 0, 0, -1, 0, 0, 257);
                mUiAutomationBridge.injectInputEvent(var7, true);
            }
        };
        command.run();
    }

    public void longPressHome() {
        try {
            this.mUiDevice.executeShellCommand("input keyevent --longpress 3");
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void longPressPower() {
        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
        final InstrumentationUiAutomatorBridge mUiAutomationBridge = new InstrumentationUiAutomatorBridge(mInstrumentation.getContext(), mInstrumentation.getUiAutomation());
        Runnable command = new Runnable() {
            public void run() {
                long eventTime = SystemClock.uptimeMillis();
                KeyEvent downEvent = new KeyEvent(eventTime, eventTime, 0, 26, 0, 0, -1, 0, 0, 257);
                if(mUiAutomationBridge.injectInputEvent(downEvent, true)) {
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }

                    KeyEvent upEvent = new KeyEvent(eventTime, eventTime, 1, 26, 0, 0, -1, 0, 0, 257);
                    mUiAutomationBridge.injectInputEvent(upEvent, true);
                }

            }
        };
        command.run();
    }
}
