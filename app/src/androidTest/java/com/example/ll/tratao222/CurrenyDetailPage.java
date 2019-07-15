package com.example.ll.tratao222;

import android.app.Instrumentation;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.IOException;

/**
 * Created by ll on 2018/12/29.
 */
//@RunWith(AndroidJUnit4.class)

public class CurrenyDetailPage {
    public Instrumentation instrumentation;
    public static UiDevice mUiDevice;

    public UiObject calc_title = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/real_rate_title");
    public UiObject calc_menu = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/search_image");
    public UiObject calc_back = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/menu");

    public CurrenyDetailPage(UiDevice mUiDevice) throws IOException, UiObjectNotFoundException {
        this.mUiDevice = mUiDevice;
    }


    public UiObject detailTitle = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/title");

    public UiObject detailBack = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/menu");

    public UiObject detailTab1 = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/rate_detail_view").
            getChild(new UiSelector().className("android.support.v7.app.ActionBar$b").index(0)).getChild(new UiSelector().className("android.widget.TextView"));
    public UiObject detailTab2 = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/rate_detail_view").
            getChild(new UiSelector().className("android.support.v7.app.ActionBar$b").index(1)).getChild(new UiSelector().className("android.widget.TextView"));
    public UiObject detailTab3 = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/rate_detail_view").
            getChild(new UiSelector().className("android.support.v7.app.ActionBar$b").index(2)).getChild(new UiSelector().className("android.widget.TextView"));
    public UiObject detailTab4 = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/rate_detail_view").
            getChild(new UiSelector().className("android.support.v7.app.ActionBar$b").index(3)).getChild(new UiSelector().className("android.widget.TextView"));

    public UiObject detailbase = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/base");
    public UiObject detailquote_symbol = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/quote_symbol");
    public UiObject detailquote_value = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/quote_value");

    public UiObject detail7day = tUiObjectUtil.getInstance(instrumentation).getObjectByText("7天");
    public UiObject detail1month = tUiObjectUtil.getInstance(instrumentation).getObjectByText("1个月");
    public UiObject detail6month = tUiObjectUtil.getInstance(instrumentation).getObjectByText("6个月");
    public UiObject detail1year = tUiObjectUtil.getInstance(instrumentation).getObjectByText("1年");
    public UiObject detail3year = tUiObjectUtil.getInstance(instrumentation).getObjectByText("3年");

    public UiObject detailhighest = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/highest");
    public UiObject detaillowest = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/lowest");
    public UiObject detailavg = tUiObjectUtil.getInstance(instrumentation).getObjectById("com.tratao.xcurrency.plus.beta:id/average");


//    public void click_indexBar(int index) throws UiObjectNotFoundException {
//        int indexCenterX = change_indexBar.getVisibleBounds().centerX();
//            tUiObjectUtil.getInstance(instrumentation).longClick(indexCenterX,getIndexPosition(index),30);
//    }
}
