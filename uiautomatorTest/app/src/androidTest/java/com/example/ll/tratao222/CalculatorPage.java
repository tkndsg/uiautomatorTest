package com.example.ll.tratao222;

import android.app.Instrumentation;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
/**
 * Created by ll on 2018/12/29.
 */
//@RunWith(AndroidJUnit4.class)

public class CalculatorPage {
    public Instrumentation instrumentation;
    public static UiDevice mUiDevice;

    public UiObject calc_title = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/real_rate_title");
    public UiObject calc_menu = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/search_image");
    public UiObject calc_back = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/menu");

    /*UiObject object = mUiDevice.findObject(new UiSelector().className("android.widget.ListView")).getChild(new UiSelector().className("android.widget.RelativeLayout").index(index));*/
//    public CalculatorPage() throws IOException, UiObjectNotFoundException {
//        this.instrumentation = instrumentation;
//        this.mUiDevice = UiDevice.getInstance(instrumentation);
//    }

    public CalculatorPage(UiDevice mUiDevice) throws IOException, UiObjectNotFoundException {
        this.mUiDevice = mUiDevice;
    }


    public UiObject calc_currency1 = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("1");

    public UiObject calc_currency1_local = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("1","local");
    public UiObject calc_currency1_name = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("1","name");
    public UiObject calc_currency1_value = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("1","value");
    public UiObject calc_currency1_formula = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("1","formula");
    public UiObject calc_currency1_icon = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("1","icon");

    public UiObject calc_currency2 = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("2");
    public UiObject calc_currency2_name = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("2","name");
    public UiObject calc_currency2_value = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("2","value");
    public UiObject calc_currency2_icon = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("2","icon");
    public UiObject calc_currency2_formula = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("2","formula");


    public UiObject calc_currency3 = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("3");
    public UiObject calc_currency3_name = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("3","name");
    public UiObject calc_currency3_value = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("3","value");
    public UiObject calc_currency3_icon = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("3","icon");
    public UiObject calc_currency3_formula = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("3","formula");


    public UiObject calc_currency4 = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("4");
    public UiObject calc_currency4_name = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("4","name");
    public UiObject calc_currency4_value = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("4","value");
    public UiObject calc_currency4_icon = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("4","icon");
    public UiObject calc_currency4_formula = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("4","formula");


    public UiObject calc_currency5 = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("5");
    public UiObject calc_currency5_name = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("5","name");
    public UiObject calc_currency5_value = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("5","value");
    public UiObject calc_currency5_icon = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("5","icon");
    public UiObject calc_currency5_formula = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("5","formula");


    public UiObject calc_currency6 = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("6");
    public UiObject calc_currency6_name = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("6","name");
    public UiObject calc_currency6_value = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("6","value");
    public UiObject calc_currency6_icon = tUiObjectUtil.getInstance(instrumentation).getCalculatorCurrency("6","icon");


    public UiObject calc_leftguide = tUiObjectUtil.getInstance(instrumentation).getObjectByText("向左滑，解锁汇率详情");
    public UiObject calc_rightguide = tUiObjectUtil.getInstance(instrumentation).getObjectByText("向右滑，解锁切换货币");


    public UiObject calc_button_1 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("1");
    public UiObject calc_button_2 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("2");
    public UiObject calc_button_3 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("3");
    public UiObject calc_button_4 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("4");
    public UiObject calc_button_5 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("5");
    public UiObject calc_button_6 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("6");
    public UiObject calc_button_7 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("7");
    public UiObject calc_button_8 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("8");
    public UiObject calc_button_9 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("9");
    public UiObject calc_button_0 = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("0");
    public UiObject calc_button_point = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber(".");
    public UiObject calc_button_delete = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("X");
    public UiObject calc_button_jia = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("+");
    public UiObject calc_button_jian = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("-");
    public UiObject calc_button_cheng = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("*");
    public UiObject calc_button_chu = tUiObjectUtil.getInstance(instrumentation).getCalculatorNumber("/");

//    public UiObject change_FirstCurrency = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/currency_layout");
    public UiObject change_FirstCurrency =tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/currency_list").getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
    public UiObject change_FirstCurrency_name = change_FirstCurrency.getChild(new UiSelector().resourceId("com.tratao.xcurrency.plus.beta:id/name"));
    public UiObject change_FirstCurrency_symbol = change_FirstCurrency.getChild(new UiSelector().resourceId("com.tratao.xcurrency.plus.beta:id/symbol"));
    public UiObject change_FirstCurrency_Selected =change_FirstCurrency.getChild(new UiSelector().resourceId("com.tratao.xcurrency.plus.beta:id/selected"));
    public UiObject change_FirstCurrency_local =change_FirstCurrency.getChild(new UiSelector().resourceId("com.tratao.xcurrency.plus.beta:id/location"));
    public UiObject change_indexBar =tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/index_view");

    public void click_indexBar(int index) throws UiObjectNotFoundException {
        int indexCenterX = change_indexBar.getVisibleBounds().centerX();
            tUiObjectUtil.getInstance(instrumentation).longClick(indexCenterX,getIndexPosition(index),30);
    }

    public void click_indexBar(int startposition,int endposition) throws UiObjectNotFoundException {
        int indexCenterX = change_indexBar.getVisibleBounds().centerX();
        mUiDevice.drag(indexCenterX,getIndexPosition(startposition),indexCenterX,getIndexPosition(endposition),80);
    }

    public int getIndexPosition(int index) throws UiObjectNotFoundException {
        int indexTop = change_indexBar.getVisibleBounds().top;
        int indexBottom = change_indexBar.getVisibleBounds().bottom;

        int hight = (indexBottom-indexTop)/23;

        int y = (int) (indexTop+0.5*hight+(index-1)*hight);
        return y;
    }
}
