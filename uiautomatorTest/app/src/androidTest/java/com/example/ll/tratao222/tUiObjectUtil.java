package com.example.ll.tratao222;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import org.junit.Assert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import static com.example.ll.tratao222.CalculatorTest.mUiDevice;
//import static com.example.ll.tratao222.GetCoinTest.mUiDevice;
import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by ll on 2018/12/29.
 */

public class tUiObjectUtil {
    private static tUiObjectUtil instance;
    private Instrumentation mInstrumentation;
    private static UiDevice mUiDevice;//看看是否要去掉static
    private Context mContext;



    private tUiObjectUtil(Instrumentation instrumentation) {
        this.mInstrumentation = instrumentation;
        this.mUiDevice = UiDevice.getInstance(instrumentation);
        this.mContext = instrumentation.getContext();
    }

    public static tUiObjectUtil getInstance(Instrumentation instrumentation) {
        if (instance == null) {
            instance = new tUiObjectUtil(instrumentation);
        }
        return instance;
    }

    static Calendar calendar = Calendar.getInstance();//为截图和log方法做准备
    static int year = calendar.get(Calendar.YEAR);
    static int month = calendar.get(Calendar.MONTH) + 1;
    static int day = calendar.get(Calendar.DATE);
    static String today = year + "-" + month + "-" + day;
    static String datestr = calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + "-" + calendar.get(Calendar.MILLISECOND) + "-";
    static File file = new File("/mnt/sdcard/aaatestResult/" + year + "-" + month + "-" + day);

    public void unClock() throws RemoteException, IOException{
        if (!mUiDevice.isScreenOn()) {
            mUiDevice.wakeUp();//唤醒屏幕
            sleep(500);
            mUiDevice.click(30,30);

            if (getObjectByText("输入密码").exists()||getObjectByText("取消").exists()||getObjectByText("紧急呼叫").exists()) {
                mUiDevice.swipe(550, 500, 550, 1450, 20);
                sleep(100);
            }
        }
    }


    public static void writeLog(String str) {
        // 取得当前时间
        calendar.setTimeInMillis(System.currentTimeMillis());
        //新建文件夹
        if (!file.exists()) {//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        String path = file.toString();
        String logpathString = "/mnt/sdcard/aaatestResult/" + today + "/AutoTest_Log" + datestr + ".txt";

//        datestr
        String datestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date());

//        String datestr = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + ":"+calendar.get(Calendar.MILLISECOND) + ":";
        FileWriter fwlog = null;
        try {
            fwlog = new FileWriter(logpathString, true);
            fwlog.write(datestr + str + "\r\n");
            System.out.println(datestr + str);
            fwlog.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fwlog.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeLog(int step) {
        if (step == 0) {
            writeLog(">>>>>>>>>>自动化测试开始：" + Thread.currentThread().getStackTrace()[3].getClassName());
        } else if (step == 1) {
            writeLog(">>>执行用例：" + Thread.currentThread().getStackTrace()[3].getMethodName() + "  LineNo:" + Thread.currentThread().getStackTrace()[3].getLineNumber());
        } else if (step == -1) {
            writeLog(">>>>>>>>>>自动化测试结束：" + Thread.currentThread().getStackTrace()[3].getClassName());
        }
    }

    public static void writeLog(int step,String content) {
        if (step == 0) {
            writeLog(">>>>>>>>>>自动化测试开始：" + Thread.currentThread().getStackTrace()[3].getClassName() + "  " + content);
        } else if (step == 1) {
            writeLog(">>>执行用例：" + Thread.currentThread().getStackTrace()[3].getMethodName() + "  LineNo:" + Thread.currentThread().getStackTrace()[3].getLineNumber()+ "  " + content);
        } else if (step == -1) {
            writeLog(">>>>>>>>>>自动化测试结束：" + Thread.currentThread().getStackTrace()[3].getClassName()+ "  " + content);
        }
    }

    public String getMethodName() {
        Thread thread = Thread.currentThread(); // 获取当前线程

        StackTraceElement[] trace = thread.getStackTrace(); // 获取当前线程的栈快照(入栈方法的数据)

        String methodname = trace[3].getFileName() + trace[3].getLineNumber() + trace[3].getMethodName();

        return methodname;
    }

    public void Screenshot() throws IOException {
        Screenshot("aaatestResult", "截图", "");
    }

    public void Screenshot(String reason, String picName) throws IOException {
        Screenshot("aaatestResult", reason, picName);
    }

    public void Screenshot(String dirName, String reason, String picName) throws IOException {//截图的功能，自动放在今天日期命名的文件夹里
        // 取得当前时间
        calendar.setTimeInMillis(System.currentTimeMillis());
        String datestr = calendar.get(Calendar.HOUR_OF_DAY) + "_" + calendar.get(Calendar.MINUTE) + "_" + calendar.get(Calendar.SECOND);
        //生成shell命令
//        File file=new File("/mnt/sdcard/aaatestResult/"+year+"-"+month+"-"+day);
        File file = new File("/mnt/sdcard/" + dirName + "/" + year + "-" + month + "-" + day);
        System.out.println("***************************************" + file.toString());
        System.out.println("***************************************" + "健忘佬，截图功能记得动态开启读取文件的权限！！！");
        if (!file.exists()) {//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        String cmd = "screencap -p /mnt/sdcard/aaatestResult/" + year + "-" + month + "-" + day + "/" + datestr + reason + picName + ".png";
        System.out.println("***************************************" + cmd);
        //执行adb shell命令
        mUiDevice.executeShellCommand(cmd);
    }

    public void sleep(long paramLong) {
        SystemClock.sleep(paramLong);
    }//定义一个休眠方法

    public void getLocationPermission(int whether) throws IOException, UiObjectNotFoundException {
        //检查是否弹出权限弹框
        if (getObjectByTextMatches(".*定位.*||.*位置.*").exists() && getObjectByTextMatches("始终允许||允许||拒绝||禁止").exists() ) {
            if (whether == 0) {
                //不给权限，直接拒绝掉！
                getObjectByTextMatches(".*拒绝.*||.*禁止.*").click();
            } else {
                //给定位权限，可能出现各种文案，要区分处理
                if (mUiDevice.findObject(new UiSelector().text("始终允许")).exists()) {
                    clickByText("始终允许");
                } else {
                    clickByText("允许");
                }
            }
            Assert.assertTrue(true);
        } else {
            //没有弹定位权限框，要截图 写log 和 assert false
            writeLog("通过正则表达式查找" + "定位权限申请" + "控件，" + 5 + " 次查找无果，已截图");
            Screenshot("_no-find_", "定位权限申请");
            Assert.assertTrue(false);
        }
    }


    public static void pressKeyCode(int keyCode, int time) {
        for (int i = 0; i < time; i++) {
            mUiDevice.pressKeyCode(keyCode);
        }
    }

    public static void exitApp() {
        pressKeyCode(KeyEvent.KEYCODE_BACK, 5);
        SystemClock.sleep(300);
        pressKeyCode(KeyEvent.KEYCODE_HOME, 2);
    }

    public void launchApp() throws IOException {
        mUiDevice.executeShellCommand("am start com.tratao.xcurrency.plus.beta/com.tratao.xcurrency.plus.MainActivity");
    }

    public void launchApp(String packageName) throws IOException {
        String cmd1 = "am start ";
        String cmd = cmd1 + packageName;
        mUiDevice.executeShellCommand(cmd);
    }

    public void clickById(String id) throws IOException, UiObjectNotFoundException {
        getObjectById(id).click();
    }

    public void clickByDesc(String des) throws IOException, UiObjectNotFoundException {
        getObjectByDes(des).click();
    }

    public void clickByText(String text) throws IOException, UiObjectNotFoundException {
        getObjectByText(text).click();
    }

    public void clickObject(UiObject object) throws IOException, UiObjectNotFoundException {
        longClick(object.getVisibleBounds().centerX(), object.getVisibleBounds().centerY(), 50);
    }
    //obj.getVisibleBounds().centerX(), obj.getVisibleBounds().centerY()


    /*******************断言判断是否存在想找到的控件对象(正则)**************/
    public void assertExists(UiObject object,String con) throws IOException {
        if (!object.exists()) {
            Screenshot("_no-find_", con);
            writeLog("没有找到控件" + con);
        }
        Assert.assertTrue(object.exists());
    }

    public void assertNoExists(UiObject object,String con) throws IOException {
        if (object.exists()) {
            Screenshot("_shouldn't-found_", con);
            writeLog("不应存在控件" + con);
            Assert.assertTrue(false);
        }
        Assert.assertTrue(true);
    }

    public void assertTextEquals(UiObject object,String con) throws IOException, UiObjectNotFoundException {
        String objText = object.getText();
        if (!objText.equals(con)) {
            Screenshot("_"+objText +"_no-equal_", con);
            writeLog("_"+objText + "不等于" + con);
            Assert.assertTrue(false);
        }else {
        Assert.assertTrue(true);
        }
    }

//    public void assertTrue(Boolean bool) throws IOException {
//        if (bool) {
//            Screenshot("_no-true_", con);
//            writeLog("没有找到控件" + con);
//        }
//        Assert.assertTrue(bool);
//    }

    public void assertNoExistsByTextMatches(String con) throws IOException {
        if (mUiDevice.findObject(new UiSelector().textMatches(con)).exists()) {                                                                     //存在就有问题，有问题就 截图 + 写log + assert判定错误
            Screenshot("_shouldn't-find_", con);
            writeLog("控件不应存在" + con);
            Assert.assertTrue(false);
        } else {
            Assert.assertTrue(true);
        }
//        Assert.assertTrue(!mUiDevice.findObject(new UiSelector().textMatches(con)).exists());
    }

    public void assertExistsByTextMatches(String con) throws IOException {
        Assert.assertTrue(getObjectByTextMatches(con, 5).exists());
    }

    public void assertExistsByTextMatches(String con1, String con2) throws IOException {
        Assert.assertTrue(getObjectByTextMatches(con1, 5).exists() && getObjectByTextMatches(con2, 5).exists());
    }

    public void assertExistsByTextMatches(String con1, String con2, String con3) throws IOException {
        Assert.assertTrue(getObjectByTextMatches(con1, 5).exists() && getObjectByTextMatches(con2, 5).exists() && getObjectByTextMatches(con3, 5).exists());
    }

    public void assertExistsByTextMatches(String con1, String con2, String con3, String con4) throws IOException {
        Assert.assertTrue(getObjectByTextMatches(con1, 5).exists() && getObjectByTextMatches(con2, 5).exists() && getObjectByTextMatches(con3, 5).exists() && getObjectByTextMatches(con4, 5).exists());
    }

    public void assertExistsByTextMatches(String con1, String con2, String con3, String con4, String con5) throws IOException {
        Assert.assertTrue(getObjectByTextMatches(con1, 5).exists() && getObjectByTextMatches(con2, 5).exists() && getObjectByTextMatches(con3, 5).exists() && getObjectByTextMatches(con4, 5).exists() && getObjectByTextMatches(con5, 5).exists());
    }

    /*******************断言判断是否存在想找到的控件对象(完全匹配)**************/
    public void assertExistsByText(String con) throws IOException {
        Assert.assertTrue(getObjectByText(con, 5).exists());
    }

    public void assertNoExistsByText(String con) throws IOException {
        if (mUiDevice.findObject(new UiSelector().text(con)).exists()) {
            Screenshot("_shouldn't-find_", getObjectByText(con).toString());
            writeLog("控件不应存在" + getObjectByText(con).toString());
        }
        Assert.assertTrue(!mUiDevice.findObject(new UiSelector().text(con)).exists());
    }

    public void assertExistsByText(String con1, String con2) throws IOException {
        Assert.assertTrue(getObjectByText(con1, 5).exists() && getObjectByText(con2, 5).exists());
    }

    public void assertExistsByText(String con1, String con2, String con3) throws IOException {
        Assert.assertTrue(getObjectByText(con1, 5).exists() && getObjectByText(con2, 5).exists() && getObjectByText(con3, 5).exists());
    }

    public void assertExistsByText(String con1, String con2, String con3, String con4) throws IOException {
        Assert.assertTrue(getObjectByText(con1, 5).exists() && getObjectByText(con2, 5).exists() && getObjectByText(con3, 5).exists() && getObjectByText(con4, 5).exists());
    }

    public void assertExistsByText(String con1, String con2, String con3, String con4, String con5) throws IOException {
        Assert.assertTrue(getObjectByText(con1, 5).exists() && getObjectByText(con2, 5).exists() && getObjectByText(con3, 5).exists() && getObjectByText(con4, 5).exists() && getObjectByText(con5, 5).exists());
    }

    /***********************通过Text找到对象(完全匹配)******************/
    public UiObject getObjectByText(String text) throws IOException {
        //new个对象
        UiObject obj = mUiDevice.findObject(new UiSelector().text(text));
        return obj;
    }

    public UiObject deskGetApp(String appName,int page) throws IOException {
        mUiDevice.pressHome();
        mUiDevice.pressHome();
        UiObject appObj = getObjectByText(appName);
        for (int i=0;i<page*2;i++){
            if(appObj.exists()){
                break;
            }
            if (i<page) {
                swipeLeft();
            }else {
                swipeRight();
            }
        }
        return appObj;
    }

    /***********************通过Text找到对象(完全匹配)+找不到要截图的！！！******************/
    public UiObject getObjectByText(String text, int waittime) throws IOException {
        //new个对象
        UiObject obj = mUiDevice.findObject(new UiSelector().text(text));
        int i = 0;
        while (!obj.exists() && i <= waittime) {
            Log.d("ScreenShot", "getObjectByText:第 " + i + " 次尝试找到对象");
            sleep(1000);
            if (i == waittime) {
                Log.d("ScreenShot", "getObjectByText:第 " + i + " 次没找到，直接截图");
                writeLog("通过正则表达式查找" + text + "控件，" + i + " 次查找无果，已截图");
                Screenshot("_no-find_", text);
                return obj;
            }
            i++;
        }
        return obj;
    }

    /***********************通过Text找到对象(正则)******************/
    public UiObject getObjectByTextMatches(String text) throws IOException {
        UiObject obj = mUiDevice.findObject(new UiSelector().textMatches(text));
        int i = 0;
        while (!obj.exists() && i <= 3) {
            sleep(1000);
            Log.d("ScreenShot", "getObjectByText:第 " + i + " 次尝试找到" + text + "对象");
            i++;
        }
        return obj;//
    }

    /***********************通过Text找到对象(正则)+没找到要截图的！！！******************/
    public UiObject getObjectByTextMatches(String text, int waittime) throws IOException {
        UiObject obj = mUiDevice.findObject(new UiSelector().textMatches(text));
        int i = 0;
        while (!obj.exists() && i <= waittime) {
            Log.d("ScreenShot", "getObjectByText:第 " + i + " 次尝试找到" + text + "对象");
            sleep(1000);
            if (i == waittime) {
                Log.d("ScreenShot", "getObjectByText: " + i + " 次没找到" + text + "，已截图");
                writeLog("通过正则表达式查找" + text + "控件，" + i + " 次查找无果，已截图");
                Screenshot("_no-find_", text);
                return obj;
            }
            i++;
        }
        return obj;
    }

    /***********************通过des找到对象******************/
    public UiObject getObjectByDes(String des) {
        return mUiDevice.findObject(new UiSelector().description(des));
    }

    public UiObject getObjectById(String id) throws IOException {
        UiObject obj = mUiDevice.findObject(new UiSelector().resourceId(id));
        return obj;
    }

    public UiObject getObjectByTextClass(String text, String className) {
        return mUiDevice.findObject(new UiSelector().text(text).className(className));
    }

    public UiObject getCalculatorCurrency(String currencyrank) throws UiObjectNotFoundException {
        int index = Integer.parseInt(currencyrank) - 1;
        UiObject object = mUiDevice.findObject(new UiSelector().className("android.widget.ListView")).getChild(new
                UiSelector().className("android.widget.RelativeLayout").index(index));
        return object;
    }

    public UiObject getCalculatorCurrency(String currencyrank, String element) throws UiObjectNotFoundException {
        int index = Integer.parseInt(currencyrank) - 1;
        String id = "";
        switch (element) {
            case "flag":
                id = "com.tratao.xcurrency.plus.beta:id/imgFlag";
                break;
            case "name":
                id = "com.tratao.xcurrency.plus.beta:id/txtUnit";
                break;
            case "value":
                id = "com.tratao.xcurrency.plus.beta:id/txtResult";
                break;
            case "icon":
                id = "com.tratao.xcurrency.plus.beta:id/currency_name_text";
                break;
            case "local":
                id = "com.tratao.xcurrency.plus.beta:id/imgMap";//定位图标显示不对
                break;
            case "formula":
                id="com.tratao.xcurrency.plus.beta:id/txtCal";
                break;
        }
        UiObject object = mUiDevice.findObject(new UiSelector().className("android.widget.ListView")).getChild(new
                UiSelector().className("android.widget.RelativeLayout").index(index)).getChild(new UiSelector().resourceId(id));
        return object;
    }

    public void clickCalculator(String context) throws UiObjectNotFoundException, IOException {
        for (int i = 0; i < context.length(); i++) {
            String num = context.charAt(i) + "";
            getCalculatorNumber(num).click();
        }
    }

    public UiObject getCalculatorNumber(String num) throws UiObjectNotFoundException, IOException {
        int index;
        switch (num) {
            case "1":
                index = 8;
                break;
            case "2":
                index = 9;
                break;
            case "3":
                index = 10;
                break;
            case "4":
                index = 4;
                break;
            case "5":
                index = 5;
                break;
            case "6":
                index = 6;
                break;
            case "7":
                index = 0;
                break;
            case "8":
                index = 1;
                break;
            case "9":
                index = 2;
                break;
            case "0":
                index = 13;
                break;
            case "+":
                index = 3;
                break;
            case "-":
                index = 7;
                break;
            case "*":
                index = 11;
                break;
            case "/":
                index = 15;
                break;
            case ".":
                index = 12;
                break;
            case "X":
                index = 14;
                break;
            default:
                index = 999999;
        }
        UiObject object = mUiDevice.findObject(new UiSelector().className("android.widget.GridView")).getChild(new
                UiSelector().className("android.widget.RelativeLayout").index(index));
        return object;
    }

    public static void TakeScreen(UiDevice device, String descrip) {
        // 取得当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String datestr = calendar.get(Calendar.HOUR_OF_DAY) + "_" + calendar.get(Calendar.MINUTE) + "_" + calendar.get(Calendar.SECOND);

        // 保存文件
        File files = new File("/mnt/sdcard/tkntest/" + datestr + "_" + descrip + ".jpg");
        device.takeScreenshot(files);
    }

    public void swipe(int startX, int startY, int endX, int endY, int steps) {
        mUiDevice.swipe(startX, startY, endX, endY, steps);
        sleep(150);
    }

    public void swipeUp() {
        swipeUp(50);
    }

    public void swipeUp(int steps) {//上滑
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x / 2, y - 100, x / 2, 100, steps);
        sleep(100);
    }

    public void swipeUpHalf(int steps) {//上滑
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x / 2, y - 500, x / 2, 300, steps);
        sleep(350);
    }

    public void swipeDown() {
        swipeDown(60);
    }

    public void swipeDown(int steps) {//下滑
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x / 2, 100, x / 2, y - 100, steps);
        sleep(150);
    }

    public void swipeLeft() {
        swipeLeft(50);
    }

    public void swipeLeft(int steps) {//左滑
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(x - 100, y / 2, 100, y / 2, steps);
        sleep(150);
    }

    public void swipeLeft(UiObject obj) throws UiObjectNotFoundException {
        int x = obj.getVisibleBounds().centerX();
        int y = obj.getVisibleBounds().centerY();
        mUiDevice.drag(x,y,x-600,y, 50);
    }

    public void swipeRight() {
        swipeRight(50);
    }

    public void swipeRight(int steps) {//右滑
        int y = mUiDevice.getDisplayHeight();
        int x = mUiDevice.getDisplayWidth();
        mUiDevice.swipe(100, y / 2, x - 100, y / 2, steps);
        sleep(150);
    }

    public void swipeRight(UiObject obj) throws UiObjectNotFoundException {
        int x = obj.getVisibleBounds().centerX();
        int y = obj.getVisibleBounds().centerY();
        mUiDevice.drag(x,y,x+600,y, 50);
    }

    public boolean longClick(int x, int y) {
        return longClick(x, y, 3000L);
    }

    public boolean longClick(int x, int y, long mTimeout) {
        long stepTmp = mTimeout * 45L / 1000L;
        int step = Integer.parseInt(String.valueOf(stepTmp));
        return mUiDevice.drag(x, y, x, y, step);
    }

    public void longClick(UiObject obj) throws UiObjectNotFoundException {
        int x = obj.getVisibleBounds().centerX();
        int y = obj.getVisibleBounds().centerY();
        mUiDevice.drag(x,y,x,y, 50);
    }

    public boolean longClick(UiObject2 obj2) throws UiObjectNotFoundException {
        return longClick(obj2.getVisibleBounds().centerX(), obj2.getVisibleBounds().centerY());
    }

    public boolean longClick(UiObject2 object2, long mTimeout) {
        return longClick(object2.getVisibleBounds().centerX(), object2.getVisibleBounds().centerY(), mTimeout);
    }

    public boolean pressPower() {
        return mUiDevice.pressKeyCode(26);
    }

    public boolean pressVolumeeUp() {
        return mUiDevice.pressKeyCode(24);
    }

    public boolean pressVolumeDown() {
        return mUiDevice.pressKeyCode(25);
    }

    public boolean pressVolumeMute() {
        return mUiDevice.pressKeyCode(164);
    }

    public boolean pressBack(int times) {
        if (times <= 0) {
            return false;
        } else {
            int i = 0;

            while (i < times && mUiDevice.pressBack()) {
                ++i;
                SystemClock.sleep(1000L);
            }

            return true;
        }
    }

    public boolean pressBack() {
        return pressBack(1);
    }
}
