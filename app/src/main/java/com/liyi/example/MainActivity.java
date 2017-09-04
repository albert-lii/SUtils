package com.liyi.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liyi.sutils.utils.app.AtyTransitionUtil;
import com.liyi.sutils.utils.other.QRCodeUtil;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn_systembar, btn_Atytransition,btn_qrcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        addListeners();
    }

    private void initUI() {
        btn_systembar = (Button) findViewById(R.id.btn_systembar);
        btn_Atytransition = (Button) findViewById(R.id.btn_aty_transition);
        btn_qrcode=(Button)findViewById(R.id.btn_qrcode);
    }

    private void addListeners() {
        btn_systembar.setOnClickListener(this);
        btn_Atytransition.setOnClickListener(this);
        btn_qrcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_systembar:
                Intent intent = new Intent(this, SystemBarActivity.class);
                startActivity(intent);
                AtyTransitionUtil.enterFromBottom(this);
                break;
            case R.id.btn_aty_transition:
                Intent intent1 = new Intent(this, TransitionActivity.class);
                startActivity(intent1);
                AtyTransitionUtil.enterFromRight(this);
                break;
            case R.id.btn_qrcode:
                Intent intent2 = new Intent(this, QRImageActivity.class);
                startActivity(intent2);
                AtyTransitionUtil.enterFromTop(this);
                break;
        }
    }
}
