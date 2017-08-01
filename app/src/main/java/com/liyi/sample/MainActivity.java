package com.liyi.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liyi.sutil.utils.STimeUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String timeStr = STimeUtil.getTimeStr(System.currentTimeMillis(), null);
        long time1 = STimeUtil.getTimeStamp("2017-5-01 12:12:12", null);
        long time2 = STimeUtil.getTimeStamp("2017-7-01 12:12:12", null);
        long d = time2 - time1;
        long d1 = System.currentTimeMillis() - time2;
        int[] s = STimeUtil.caculateTimeDiffArray("2017-7-31 00:00:00", System.currentTimeMillis(), null);
        System.out.println("<<<<  time1 ::: " + time1 + "    " + "time2:::: " + time2 + "     {{{ " + d + "   d1::: " + d1);
        System.out.println("SSSSS " + s[0] + "  " + s[1] + "  " + s[2] + "  " + s[3]);
    }
}
