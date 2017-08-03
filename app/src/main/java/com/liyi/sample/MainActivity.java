package com.liyi.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liyi.sutil.utils.SReflectUtil;
import com.liyi.sutil.utils.io.SFileUtil;
import com.liyi.sutil.utils.prompt.SToastUtil;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editT_saveSer, editT_saveStr;
    private Button btn_saveSer, btn_getSer, btn_saveStr, btn_getStr, btn_reflect1, btn_reflect2;

    private TextView tv_content, tv_reflect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editT_saveSer = findViewById(R.id.editT_serializable);
        btn_saveSer = findViewById(R.id.btn_save_serializable);
        btn_getSer = findViewById(R.id.btn_get_serializable);
        editT_saveStr = findViewById(R.id.editT_string);
        btn_saveStr = findViewById(R.id.btn_save_string);
        btn_getStr = findViewById(R.id.btn_get_string);
        tv_content = findViewById(R.id.tv_test_content);

        btn_reflect1 = findViewById(R.id.btn_reflect1);
        btn_reflect2 = findViewById(R.id.btn_reflect2);
        tv_reflect = findViewById(R.id.tv_reflect_content);

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
            case R.id.btn_save_serializable:
                String contentSer = editT_saveSer.getText().toString();
                if (TextUtils.isEmpty(contentSer)) {
                    SToastUtil.show(this, "内容不能为空");
                    return;
                }
                TestBean bean = new TestBean(contentSer);
                SFileUtil.get().put(SFileUtil.get().getSDCardPath(), "key1", bean);
                break;
            case R.id.btn_get_serializable:
                TestBean bean1 = (TestBean) SFileUtil.get().getAsObject(SFileUtil.get().getSDCardPath(), "key1");
                tv_content.setText(bean1.getMsg());
                break;
            case R.id.btn_save_string:
                String contentStr = editT_saveStr.getText().toString();
                if (TextUtils.isEmpty(contentStr)) {
                    SToastUtil.show(this, "内容不能为空");
                    return;
                }
                try {
                    SFileUtil.get().put(SFileUtil.get().getSDCardPath(), "key2", contentStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_get_string:
                String text = SFileUtil.get().getAsString(SFileUtil.get().getSDCardPath(), "key2");
                tv_content.setText(text);
                break;
            case R.id.btn_reflect1:
                SReflectUtil.modifyVal(Constants.class, "TEST1", "HAPPY");
                tv_reflect.setText(Constants.TEST1);
                break;
            case R.id.btn_reflect2:
                tv_reflect.setText(Constants.TEST2);
                SReflectUtil.modifyVals(Constants.class, "TEST2", "DAY");
                break;
        }
    }
}
