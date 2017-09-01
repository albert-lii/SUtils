package com.liyi.example;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.liyi.sutils.utils.app.SystemBarUtil;

/**
 * 系统状态栏测试
 */

public class SystemBarActivity extends Activity implements View.OnClickListener {
    private Button btn_showStatus, btn_hideStatus, btn_showNav, btn_hideNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systembar);
        SystemBarUtil.setupStatusBar(this, Color.RED);
        SystemBarUtil.setupNavBar(this, Color.TRANSPARENT);
        SystemBarUtil.setDisplayOption(this, true, false);
        SystemBarUtil.setStatusBarAlpha(this, 0.5f);
        initUI();
        addListeners();
    }

    private void initUI() {
        btn_showStatus = (Button) findViewById(R.id.btn_show_statusbar);
        btn_hideStatus = (Button) findViewById(R.id.btn_hide_statusbar);
        btn_showNav = (Button) findViewById(R.id.btn_show_navbar);
        btn_hideNav = (Button) findViewById(R.id.btn_hide_navbar);
    }

    private void addListeners() {
        btn_showStatus.setOnClickListener(this);
        btn_hideStatus.setOnClickListener(this);
        btn_showNav.setOnClickListener(this);
        btn_hideNav.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_statusbar:
                SystemBarUtil.showStatusBar(this, true);
                break;
            case R.id.btn_hide_statusbar:
                SystemBarUtil.showStatusBar(this, false);
                break;
            case R.id.btn_show_navbar:
                SystemBarUtil.showNavBar(this, true);
                break;
            case R.id.btn_hide_navbar:
                SystemBarUtil.showNavBar(this, false);
                break;
        }
    }
}
