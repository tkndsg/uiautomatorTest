package com.example.ll.tratao222;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager packageManager = getApplicationContext().getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            //应用装时间

            long firstInstallTime = packageInfo.firstInstallTime;
            long lastUpdateTime = packageInfo.lastUpdateTime;

            Date InstallTime = new Date(firstInstallTime);
            Date UpdateTime = new Date(lastUpdateTime);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String InstallT = sd.format(InstallTime);
            String UpdateT = sd.format(UpdateTime);
            //应用最后一次更新时间
            Log.d("******showtime****","firstInstallTime" + InstallT + " last update time :" + UpdateT);
            Log.d("******showtime****","firstInstallTime" + InstallT + " last update time :" + UpdateT);

            TextView tv_updateTime = (TextView) findViewById(R.id.tv_updateTime);
            TextView tv_installTime = (TextView) findViewById(R.id.tv_installTime);
            tv_updateTime.setText(UpdateT);
            tv_installTime.setText(InstallT);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
            } else {
                //TODO do something you need
            }
        }

//        WindowManager wm = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY ;
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.format = PixelFormat.TRANSLUCENT;
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
//        tv = new TextView(this);
//
//        params.gravity = Gravity.TOP;
//        tv.setText("aaaa");
//        tv.setTextColor(Color.parseColor("#ff0000"));
//        tv.setTextSize(24);
//        wm.addView(tv, params);
    }
}
