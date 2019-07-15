package com.example.ll.tratao222;

import android.app.Instrumentation;
import android.graphics.PixelFormat;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.content.Context.SYSTEM_HEALTH_SERVICE;
import static android.content.Context.WINDOW_SERVICE;
import static com.example.ll.tratao222.MainActivity.tv;

/**
 * Created by ll on 2018/12/29.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.JVM)

public class CalculatorTest {
    public Instrumentation instrumentation;
    public static UiDevice mUiDevice;
    CalculatorPage calculatorPage;
    CurrenyDetailPage currenyDetailPage;
    private static Boolean NOUIWATCHER = true;

    @BeforeClass
    public static void setUpBeforeClass() {
        tUiObjectUtil.writeLog(0);
    }

    @Before
    public void setUp() throws RemoteException, IOException, UiObjectNotFoundException {
        this.instrumentation = InstrumentationRegistry.getInstrumentation();
        this.mUiDevice = UiDevice.getInstance(instrumentation);
        tUiObjectUtil.getInstance(instrumentation).unClock();
        calculatorPage = new CalculatorPage(mUiDevice);
        currenyDetailPage = new CurrenyDetailPage(mUiDevice);
        if (NOUIWATCHER) {
            UiDevice.getInstance(instrumentation).registerWatcher("MyWatche", new MyUiWatcher(mUiDevice));                                                       //如果没有异常监视器，就注册一个
            tUiObjectUtil.writeLog(">>>>>>>>>>注册异常情况监视器MyUiWatcher...<<<<<<<<<<");
            NOUIWATCHER = false;
        }
    }

    @Test
    public void test000_Init() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"定位权限");

        SystemClock.sleep(1500);
        tUiObjectUtil.getInstance(instrumentation).launchApp();  //启动应用
        SystemClock.sleep(1000);
        tUiObjectUtil.getInstance(instrumentation).getLocationPermission(1);                                                                             //给定位权限
        SystemClock.sleep(2000);
        tUiObjectUtil.getInstance(instrumentation).assertExists(calculatorPage.calc_currency1_local,"calc_currency1_local");                       //检查定位图标是否存在
    }

    @Test
    public void test001_checkUi() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"默认列表");

        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("CNY", "USD", "HKD", "BTC");                                //检查货币列表的简称是否正确
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("人民币 ¥", "美元 $", "港币 HK$", "比特币 ¤");            //检查是中文，货币名字正确
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("向左滑，解锁汇率详情", "向右滑，解锁切换货币");                           //检查新手引导
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("极简汇率");                                                                          //检查是中文，标题正确
    }

    @Test
    public void test002_removeGuide() throws IOException, UiObjectNotFoundException {                                                                             //测试用例002---左右滑动，去掉新手指引
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"操作引导");

        tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches("向右滑.*").swipeRight(30);                                                    //向右滑进入切换货币页面
        tUiObjectUtil.getInstance(instrumentation).assertExistsByTextMatches(".*日元.*");                                                                 //检查切换货币页面元素
        if(tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*日元.*").exists()) tUiObjectUtil.getInstance(instrumentation).pressBack();   //从汇率详情页回到计算器页面
        tUiObjectUtil.getInstance(instrumentation).assertNoExistsByTextMatches("向右滑.*");                                                               //检查计算器页的引导是否被去除

        tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches("向左滑.*").swipeLeft(30);                                                    //向左滑进入切换货币页面
        tUiObjectUtil.getInstance(instrumentation).sleep(1000);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("最高");                                                                            //检查切换货币页面元素
        if(tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches(".*最高.*").exists()) tUiObjectUtil.getInstance(instrumentation).pressBack();  //从汇率详情页回到计算器页面
        tUiObjectUtil.getInstance(instrumentation).assertNoExistsByTextMatches("向左滑.*");                                                              //检查计算企业的引导是否被去除
    }

    @Test
    public void test003_Benchmarkcurrency() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"基准货币");                                                                      //检查是中文，标题正确
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"100");                                      //检查第一行是否为默认的基准货币
        calculatorPage.calc_currency2.click();                                                                                                                 //点击第二行，切换第二行为基准货币
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency2_value,"100");                                      //检查第二行是否为的基准货币
        calculatorPage.calc_currency3.click();                                                                                                                 //点击第三行，切换第三行为基准货币
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency3_value,"100");                                      //检查第三行是否为的基准货币
        calculatorPage.calc_currency4.click();                                                                                                                 //点击第四行，切换第四行为基准货币
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency4_value,"100");                                      //检查第四行是否为的基准货币
        calculatorPage.calc_currency5.click();                                                                                                                 //点击第五行，切换第五行为基准货币
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency5_value,"100");                                      //检查第五行是否为的基准货币
        calculatorPage.calc_currency1.click();
    }

    @Test
    public void test004_clickButton() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"单击遍历数字");
        calculatorClear();
        calculatorPage.calc_button_0.click();
        calculatorPage.calc_button_1.click();
        calculatorPage.calc_button_2.click();
        calculatorPage.calc_button_3.click();
        calculatorPage.calc_button_4.click();
        calculatorPage.calc_button_5.click();
        calculatorPage.calc_button_6.click();
        calculatorPage.calc_button_7.click();
        calculatorPage.calc_button_8.click();
        calculatorPage.calc_button_9.click();
        calculatorPage.calc_button_0.click();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("1,234,567,890");
        for (int i = 0; i <= 11; i++)
            calculatorPage.calc_button_delete.click();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("100");
    }

    @Test
    public void test005_inputFloat() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"输入浮点数");
        calculatorClear();
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("1.1");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"1.1");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("0.1");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.1");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator(".1");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.1");
        calculatorClear();
    }

    @Test
    public void test006_inputLongNumber() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"输入超长数字");
        calculatorClear();
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("123456789012345");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"1,234,567,890,123");

        tUiObjectUtil.getInstance(instrumentation).clickCalculator(".1234567890123");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"1,234,567,890,123.1234567890123");

        //CNY和定位图标应该被遮住了
        tUiObjectUtil.getInstance(instrumentation).assertNoExists(calculatorPage.calc_currency1_local,"定位图标");
        tUiObjectUtil.getInstance(instrumentation).assertNoExistsByText("CNY");

        calculatorClear();
    }

    @Test
    public void test007_sameCurrency() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance (instrumentation).writeLog(1,"货币重复存在");
        calculatorClear();
        SystemClock.sleep(300);
        tUiObjectUtil.getInstance(instrumentation).swipeRight(calculatorPage.calc_currency2_name);
//        tUiObjectUtil.getInstance(instrumentation).longClick();
    tUiObjectUtil.getInstance(instrumentation).clickByText("人民币");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("100.00");
        tUiObjectUtil.getInstance(instrumentation).swipeRight(calculatorPage.calc_currency2_name);
        tUiObjectUtil.getInstance(instrumentation).clickByText("美元");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("USD");
    }

    @Test
    public void test008_longClickButton() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"数字长按和全清");
        calculatorClear();
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_1);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_2);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_3);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_4);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_5);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_6);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_7);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_8);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_9);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_0);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_point);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("1,234,567,890.");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_delete);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("100");
    }

    @Test
    public void test009_hideFormula() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"算式隐藏逻辑");
        calculatorClear();
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("1+");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("1 + ");
        calculatorPage.calc_currency3.click();
        tUiObjectUtil.getInstance(instrumentation).assertNoExistsByText("1 + ");
        calculatorPage.calc_currency5.click();
        tUiObjectUtil.getInstance(instrumentation).assertNoExistsByText("1 + ");
    }

    @Test
    public void test010_clearFormula() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"算式清除逻辑");
        calculatorClear();
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("9+9");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("9 + 9");
        calculatorPage.calc_currency1.click();
        tUiObjectUtil.getInstance(instrumentation).assertNoExistsByText("1 + ");

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("1+");
        tUiObjectUtil.getInstance(instrumentation).swipeRight(calculatorPage.calc_currency1);
        tUiObjectUtil.getInstance(instrumentation).clickByText("人民币");
        tUiObjectUtil.getInstance(instrumentation).assertNoExistsByText("1 + ");
    }

    @Test
    public void test011_saveFormula() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"算式保留逻辑");
        calculatorClear();
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("9+9");
        tUiObjectUtil.getInstance(instrumentation).launchApp("com.tencent.mm/com.tencent.mm.ui.LauncherUI");
        SystemClock.sleep(1000);
        mUiDevice.pressHome();
        SystemClock.sleep(1000);
        tUiObjectUtil.getInstance(instrumentation).deskGetApp("极简汇率",4).click();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("9 + 9");
        SystemClock.sleep(500);
        tUiObjectUtil.getInstance(instrumentation).clickByText("CNY");
        tUiObjectUtil.getInstance(instrumentation).swipeDown();
        SystemClock.sleep(500);
        tUiObjectUtil.getInstance(instrumentation).clickById("com.tratao.xcurrency.plus.beta:id/calculator");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("9 + 9");

        tUiObjectUtil.getInstance(instrumentation).swipeRight(calculatorPage.calc_currency3);
        if(tUiObjectUtil.getInstance(instrumentation).getObjectByText("日元").exists()) tUiObjectUtil.getInstance(instrumentation).pressBack();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("9 + 9");

        tUiObjectUtil.getInstance(instrumentation).swipeLeft( calculatorPage.calc_currency2);
        tUiObjectUtil.getInstance(instrumentation).sleep(1000);
        if(tUiObjectUtil.getInstance(instrumentation).getObjectByText("最高").exists()) tUiObjectUtil.getInstance(instrumentation).pressBack();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("9 + 9");
        tUiObjectUtil.getInstance(instrumentation).clickByText("9 + 9");
    }

    @Test
    public void  test012_calculation() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"运算逻辑和结果");
        calculatorClear();
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3+3*3");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"12.0000");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3-3/3");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"2.0000");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3.-3.*3.");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"-6.0000");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3+3");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_cheng);
        calculatorPage.calc_button_3.click();
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"18.0000");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3-3");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_chu);
        calculatorPage.calc_button_3.click();
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.0000");
    }

    @Test
    public void test013_longClickOperator() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"长按运算符");
        calculatorClear();
        calculatorPage.calc_button_5.click();
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_jia);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("5 + ");
        calculatorClear();

        calculatorPage.calc_button_5.click();
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_jian);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("5 - ");
        calculatorClear();

        calculatorPage.calc_button_5.click();
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_cheng);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("(5) x ");
        calculatorClear();

        calculatorPage.calc_button_5.click();
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_chu);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("(5) ÷ ");
        calculatorClear();

        calculatorPage.calc_button_5.click();
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_point);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("5.");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_point);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("5.");
    }

    @Test
    public void test014_operatorReplace() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"运算符替换");
        calculatorClear();
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3+*3/-4");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("3 x 3 - 4");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3-+3*/4");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("3 + 3 ÷ 4");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3*+3");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_chu);
        calculatorPage.calc_button_jian.click();
        calculatorPage.calc_button_4.click();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("(3 + 3) - 4");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3-/3+");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_cheng);
        calculatorPage.calc_button_4.click();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("(3 ÷ 3) x 4");
        calculatorClear();

        calculatorPage.calc_button_3.click();
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_chu);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_cheng);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_chu);
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_cheng);
        calculatorPage.calc_button_3.click();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("(3) x 3");
    }

    @Test
    public void test015_hybridOperations() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"四则混合运算");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("3*11/1");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_jia);
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("1-2");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("32.0000");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("3 x 11 ÷ 1 + 1 - 2");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("1*9+8-7");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_chu);
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("5+6/2");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_jia);
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("0-4");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("1.0000");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("(1 x 9 + 8 - 7) ÷ 5 + 6 ÷ 2 + 0 - 4");
    }

    @Test
    public void test016_specialOperations() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"运算逻辑(边界值+极端值)");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("99999999999999+1");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"10,000,000,000,000.0000");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("-99999999999999-1");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"-10,000,000,000,000.0000");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("1/5000000");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.0000");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("1.+1.+");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"2.0000");
        calculatorClear();

//        tUiObjectUtil.getInstance(instrumentation).clickCalculator("99999999999999*999999");
//        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"9,999,989,999,999,000,001.0000");
    }

    @Test
    public void test017_illegalInput() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"非法输入");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("000");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("...");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("0.0.");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.0");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("0++");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_formula,"0 + ");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("0//");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_formula,"0 ÷ ");
    }

    @Test
    public void test018_negativeNumber() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"负数各种情况");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("-1");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"-1.0000");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_formula,"0 - 1");
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("-1*100-1+2");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_chu);
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("200");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"-0.4950");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_formula,"(0 - 1 x 100 - 1 + 2) ÷ 200");

        calculatorPage.calc_currency2.click();
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"-0.4950");

        tUiObjectUtil.getInstance(instrumentation).clickByText("BTC");
        calculatorPage.calc_button_delete.click();
        calculatorPage.calc_button_delete.click();
        calculatorPage.calc_button_delete.click();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("-0.00000");
    }

    @Test
    public void test019_aboutZero() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"零的显示");
        calculatorPage.calc_currency1.click();
        calculatorClear();

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("0/");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.0000");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_formula,"0 ÷ ");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency2_value,"0.0000");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency3_value,"0.0000");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("0.00000000");

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("-500");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"-500.0000");

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("+500");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.0000");

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("+500");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"500.0000");
        calculatorClear();

        //零的乘除
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("0*0*5*12.1/5");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"0.0000");
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("+5+0-0");
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_chu);
        tUiObjectUtil.getInstance(instrumentation).clickCalculator("/0/3");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_value,"1.6667");
    }

    @Test
    public void test020_currencyFresh() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"汇率刷新");

        tUiObjectUtil.getInstance(instrumentation).clickByText("极简汇率");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByTextMatches("更新时间.*");

        tUiObjectUtil.getInstance(instrumentation).clickCalculator("999");
        tUiObjectUtil.getInstance(instrumentation).clickByText("极简汇率");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByTextMatches("更新时间.*");

        tUiObjectUtil.pressKeyCode(KeyEvent.KEYCODE_BACK,3);
        SystemClock.sleep(500);
        tUiObjectUtil.getInstance(instrumentation).deskGetApp("极简汇率",3).click();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByTextMatches("更新时间.*");
    }

    @Test
    public void test021_closeCalculator() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"收起计算器");

        tUiObjectUtil.getInstance(instrumentation).clickById("com.tratao.xcurrency.plus.beta:id/menu");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("法定货币");
        tUiObjectUtil.getInstance(instrumentation).clickById("com.tratao.xcurrency.plus.beta:id/calculator");;
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("极简汇率");
        SystemClock.sleep(500);
        tUiObjectUtil.getInstance(instrumentation).swipeDown();
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("法定货币");
    }

    @Test
    public void test022_changeCurrencyPage() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"进入切换货币");

        tUiObjectUtil.getInstance(instrumentation).getObjectByTextMatches("CNY*").swipeRight(30);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("极简汇率");

        tUiObjectUtil.getInstance(instrumentation).swipeRight(calculatorPage.calc_currency3);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("切换货币");
    }

    @Test
    public void test023_changeCheckUi() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"默认显示");

        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("法定货币");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("切换货币");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("人民币");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("阿尔巴尼亚列克");

        tUiObjectUtil.getInstance(instrumentation).clickByText("数字货币");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("艾达币");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("ELF");

        tUiObjectUtil.getInstance(instrumentation).clickByText("自选");
    }

    @Test
    public void test024_changeIndex() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"法币点击索引");

        calculatorPage.click_indexBar(4);
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.change_FirstCurrency_name,"朝鲜币");

        calculatorPage.click_indexBar(21);
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.change_FirstCurrency_name,"西非法郎");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("新加坡元");
        calculatorPage.click_indexBar(23);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("中非法郎");
        calculatorPage.click_indexBar(1);
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("ALL");
    }

    @Test
    public void test024_changeIndex2() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"数字滑动索引");

        tUiObjectUtil.getInstance(instrumentation).clickByText("数字货币");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("艾达币");

        calculatorPage.click_indexBar(1,10);
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.change_FirstCurrency_name,"可汗币");
        calculatorPage.click_indexBar(5,23);
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.change_FirstCurrency_name,"伊莱克");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("以太币");
        calculatorPage.click_indexBar(23,2);
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.change_FirstCurrency_name,"B2G币");
        tUiObjectUtil.getInstance(instrumentation).assertExistsByText("比特币");
    }

    @Test
    public void test025_changeSuccess() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"成功切换1法币");

        tUiObjectUtil.getInstance(instrumentation).swipeRight();
        calculatorPage.click_indexBar(1);
        tUiObjectUtil.getInstance(instrumentation).clickByText("人民币");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency3_name,"CNY");
        tUiObjectUtil.getInstance(instrumentation).swipeRight(calculatorPage.calc_currency2);
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.change_FirstCurrency_name,"人民币");
        tUiObjectUtil.getInstance(instrumentation).assertExists(calculatorPage.change_FirstCurrency_local,"定位图标");
        tUiObjectUtil.getInstance(instrumentation).assertExists(calculatorPage.change_FirstCurrency_Selected,"选中图标");
        UiScrollable uiScrollable = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        uiScrollable.scrollIntoView(tUiObjectUtil.getInstance(instrumentation).getObjectByText("XAU"));
        tUiObjectUtil.getInstance(instrumentation).clickByText("XAU");

        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency1_name,"CNY");
        tUiObjectUtil.getInstance(instrumentation).assertExists(calculatorPage.calc_currency1_local,"定位图标");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency2_name,"XAU");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency3_name,"CNY");
    }

    @Test
    public void test025_changeSuccess2() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"成功切换2数币");

        tUiObjectUtil.getInstance(instrumentation).swipeRight(calculatorPage.calc_currency3);
        tUiObjectUtil.getInstance(instrumentation).clickByText("数字货币");
        tUiObjectUtil.getInstance(instrumentation).clickByText("艾达币");
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.calc_currency3_name,"ADA");

        tUiObjectUtil.getInstance(instrumentation).swipeRight(calculatorPage.calc_currency3);
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(calculatorPage.change_FirstCurrency_symbol,"ADA");
        tUiObjectUtil.getInstance(instrumentation).assertExists(calculatorPage.change_FirstCurrency_Selected,"选中图标");
        tUiObjectUtil.getInstance(instrumentation).assertNoExists(calculatorPage.change_FirstCurrency_local,"定位图标");

        tUiObjectUtil.getInstance(instrumentation).clickByText("数字货币");
        calculatorPage.click_indexBar(10);
        UiScrollable uiScrollable = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        uiScrollable.scrollIntoView(tUiObjectUtil.getInstance(instrumentation).getObjectByText("比特币"));
        tUiObjectUtil.getInstance(instrumentation).clickByText("比特币");
    }

    @Test
    public void test026_currencyDetail() throws IOException, UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).writeLog(1,"进入汇率详情");

        tUiObjectUtil.getInstance(instrumentation).swipeLeft(calculatorPage.calc_currency4);

        currenyDetailPage.detailTab1.click();
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(currenyDetailPage.detailTab1,"CNY");

        currenyDetailPage.detailTab2.click();
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(currenyDetailPage.detailTab2,"XAU");

        currenyDetailPage.detailTab3.click();
        tUiObjectUtil.getInstance(instrumentation).assertTextEquals(currenyDetailPage.detailTab3,"BTC");

    }

    @After
    public void tearDown() {
//        tUiObjectUtil.exitApp();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        tUiObjectUtil.writeLog(-1);
//        tUiObjectUtil.exitApp();
    }

    public void calculatorClear() throws UiObjectNotFoundException {
        tUiObjectUtil.getInstance(instrumentation).longClick(calculatorPage.calc_button_delete);
    }
}
