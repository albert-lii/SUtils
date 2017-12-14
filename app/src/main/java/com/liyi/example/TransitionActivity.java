package com.liyi.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liyi.sutils.utils.AtyTransitionUtil;

/**
 * Activity过渡动画测试
 */

public class TransitionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systembar);
    }

    @Override
    public void finish() {
        super.finish();
        AtyTransitionUtil.exitToRight(this);
    }
}
