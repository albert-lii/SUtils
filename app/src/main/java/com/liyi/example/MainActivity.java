package com.liyi.example;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liyi.sutils.utils.common.SReflectUtil;
import com.liyi.sutils.utils.app.SSystemBarUtil;
import com.liyi.sutils.utils.io.SFileUtil;
import com.liyi.sutils.utils.prompt.SToastUtil;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn_navbar, btn_statusbar;
    private EditText editT_saveSer, editT_saveStr;
    private Button btn_saveSer, btn_getSer, btn_saveStr, btn_getStr, btn_reflect1, btn_reflect2;
    private TextView tv_content, tv_reflect;

    private boolean isShowStatus = true;
    private boolean isShowNav = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SSystemBarUtil.setupStatusBar(this, Color.RED);
        SSystemBarUtil.setupNavBar(this, Color.TRANSPARENT);
        SSystemBarUtil.setDisplayOption(this, true, false);
        SSystemBarUtil.setStatusBarAlpha(this, 0.5f);

        btn_statusbar = (Button) findViewById(R.id.btn_statusbar);
        btn_navbar = (Button) findViewById(R.id.btn_navbar);
        editT_saveSer = (EditText) findViewById(R.id.editT_serializable);
        btn_saveSer = (Button) findViewById(R.id.btn_save_serializable);
        btn_getSer = (Button) findViewById(R.id.btn_get_serializable);
        editT_saveStr = (EditText) findViewById(R.id.editT_string);
        btn_saveStr = (Button) findViewById(R.id.btn_save_string);
        btn_getStr = (Button) findViewById(R.id.btn_get_string);
        tv_content = (TextView) findViewById(R.id.tv_test_content);

        btn_reflect1 = (Button) findViewById(R.id.btn_reflect1);
        btn_reflect2 = (Button) findViewById(R.id.btn_reflect2);
        tv_reflect = (TextView) findViewById(R.id.tv_reflect_content);

        btn_statusbar.setOnClickListener(this);
        btn_navbar.setOnClickListener(this);
        btn_saveSer.setOnClickListener(this);
        btn_getSer.setOnClickListener(this);
        btn_saveStr.setOnClickListener(this);
        btn_getStr.setOnClickListener(this);
        btn_reflect1.setOnClickListener(this);
        btn_reflect2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_statusbar:
                isShowStatus = !isShowStatus;
                SSystemBarUtil.showStatusBar(this, isShowStatus);
                break;
            case R.id.btn_navbar:
                isShowNav = !isShowNav;
                SSystemBarUtil.showNavBar(this, isShowNav);
                break;
            case R.id.btn_save_serializable:
                String contentSer = editT_saveSer.getText().toString();
                if (TextUtils.isEmpty(contentSer)) {
                    SToastUtil.show(this, "内容不能为空");
                    return;
                }
                TestBean bean = new TestBean(contentSer);
                SFileUtil.get().put(SFileUtil.get().getSDCardPath() + File.separator + "key1", bean);
                break;
            case R.id.btn_get_serializable:
                TestBean bean1 = (TestBean) SFileUtil.get().getAsObject(SFileUtil.get().getSDCardPath() + File.separator + "key1");
                tv_content.setText(bean1.getMsg());
                break;
            case R.id.btn_save_string:
                String contentStr = editT_saveStr.getText().toString();
                if (TextUtils.isEmpty(contentStr)) {
                    SToastUtil.show(this, "内容不能为空");
                    return;
                }
                SFileUtil.get().put(SFileUtil.get().getSDCardPath() + File.separator + "key2", contentStr);
                break;
            case R.id.btn_get_string:
                String text = SFileUtil.get().getAsString(SFileUtil.get().getSDCardPath() + File.separator + "key2");
                tv_content.setText(text);
                break;
            case R.id.btn_reflect1:
                SReflectUtil.modifyValNoFinal(TConstants.class, "TEST1", "HAPPY");
                System.out.println(">>> " + TConstants.TEST1);
                tv_reflect.setText(TConstants.TEST1);
                break;
            case R.id.btn_reflect2:
                tv_reflect.setText(TConstants.TEST2);
                SReflectUtil.modifyValsNoFinal(TConstants.class, "TEST2", "DAY");
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
