package com.tkndsg.tuoji;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "******MainActivity*****";

    private String flag = "0";
    private String command = "";
    private EditText et_times;

    Calendar calendar = Calendar.getInstance();//为截图和log方法做准备
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DATE);
    String today = year + "-" + month + "-" + day;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_times = (EditText)findViewById(R.id.et_times);
        et_times.setSelection(3);
    }

    public void clearApp(View view) {
        flag = "0";
        new UiautomatorThread().start();
        Toast.makeText(MainActivity.this, "正在清理极简汇率缓存...", Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "清理成功！！！", Toast.LENGTH_SHORT).show();
    }

    public void runMonkey(View view) throws IOException {
        file = new File("/mnt/sdcard/aaatestResult/" + year + "-" + month + "-" + day);
        if (!file.exists()) {//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        flag = "1";
        new UiautomatorThread().start();
        Toast.makeText(MainActivity.this, "开始跑Monkey！！！", Toast.LENGTH_SHORT).show();
    }

    public void runAutoTest(View view) throws IOException {
        flag = "0";
        new UiautomatorThread().start();
        SystemClock.sleep(1000);
        Toast.makeText(MainActivity.this, "清理极简汇率beta版缓存中...", Toast.LENGTH_SHORT).show();

        flag = "2";
        new UiautomatorThread().start();
        Toast.makeText(MainActivity.this, "开始执行自动化测试脚本...", Toast.LENGTH_LONG).show();
    }

    public void GetReadCoin(View view) {
        //刷阅读金币
        flag = "3";
        new UiautomatorThread().start();
        Toast.makeText(MainActivity.this, "开始刷金币！！！", Toast.LENGTH_SHORT).show();
    }

    public void GetTBCoin(View view) {
        //刷猫币
        flag = "4";
        new UiautomatorThread().start();
        Toast.makeText(MainActivity.this, "开始刷猫币！！！", Toast.LENGTH_SHORT).show();
    }

    public void outputLog(View view) {
        //一键把log导出到电脑上
        flag = "5";
        new UiautomatorThread().start();
        Toast.makeText(MainActivity.this, "导出log", Toast.LENGTH_SHORT).show();
    }

    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            switch (flag){
                case "0":
                    //清理缓存
                    command = "pm clear com.tratao.xcurrency.plus.beta";
                    break;
                case "1" :
                    String times = et_times.getText().toString().trim();
                    times = times.isEmpty() ? "200" : times;
                    //执行monkey测试
                    command = "monkey -p com.tratao.xcurrency.plus.beta --throttle 500 -s 225 --ignore-crashes --ignore-timeouts --ignore-security-exceptions --pct-touch 80 --pct-trackball 7 --pct-appswitch 7 --pct-syskeys 3 --pct-motion 3 --monitor-native-crashes -v -v -v " + times + " 1>/mnt/sdcard/aaatestResult/" + today + "/monkey_info.txt 2>/mnt/sdcard/aaatestResult/" + today + "/monkey_crashed.txt 3>/mnt/sdcard/aaatestResult/" + today + "/monkey_anr.txt";
                    break;
                case "2" :
                    //执行自动化测试
                    command = "am instrument -w -r   -e debu false -e class com.example.ll.tratao222.CalculatorTest com.example.ll.tratao222.test/android.support.test.runner.AndroidJUnitRunner";
                    break;
                case "3" :
                    //执行阅读功能

                    RadioButton rb_kkd = (RadioButton) findViewById(R.id.rb_kkd);
                    RadioButton rb_qtt = (RadioButton) findViewById(R.id.rb_qtt);
                    String testcase ="";//默认执行test001 快看点
                    if(rb_kkd.isChecked())
                        testcase = "#test001";
                    if(rb_qtt.isChecked())
                        testcase = "#test002";
                            //am instrument -w -r   -e debug false -e class com.example.ll.tratao222.GetCoin com.example.ll.tratao222.test/android.support.test.runner.AndroidJUnitRunner
                    command = "am instrument -w -r   -e debug false -e class com.example.ll.tratao222.GetCoinTest"+testcase+" com.example.ll.tratao222.test/android.support.test.runner.AndroidJUnitRunner";
                    break;

                case "4" :
                    //开始刷猫币
                    command = "am instrument -w -r   -e debug false -e class com.example.ll.tratao222.GetTBCoinTest com.example.ll.tratao222.test/android.support.test.runner.AndroidJUnitRunner";
                    break;
                case "5" :
                    //开始刷猫币
                    command = "pull /mnt/sdcard/aaatestResult C:\\Users\\ll\\Desktop\\tknMonkey\\log";
                    break;
                default:
                    command = "swipe 200 300 200 500 500";
                    Toast.makeText(getApplicationContext(), "不知道执行什么命令。。。", Toast.LENGTH_SHORT).show();
                    break;
            }

            CMDUtils.CMD_Result rs = CMDUtils.runCMD(command, true, true);
//            Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
        }
//        /**
//         * 生成命令
//         * @param pkgName uiautomator包名
//         * @param clsName uiautomator类名
//         * @param mtdName uiautomator方法名
//         * @return
//         */
//        public  String generateCommand(String pkgName, String clsName, String mtdName) {
//            String command = "am instrument -w -r -e debug false -e class "
//                    + pkgName + "." + clsName + "#" + mtdName + " "
//                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
//            Log.e("test1: ", command);
//            return command;
//        }第二种生成命令的方法
    }
}
