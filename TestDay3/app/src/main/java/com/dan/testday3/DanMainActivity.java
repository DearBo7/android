package com.dan.testday3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DanMainActivity extends AppCompatActivity {

    //定义一个startActivityForResult（）方法用到的整型值
    private final int requestCode = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dan_main_activity);

        Button btnTz = (Button) findViewById(R.id.btn_tz);
        btnTz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, LayoutEditorAbsoluteActivity.class);

                startActivity(intent);
            }
        });

        Button btnA = (Button) findViewById(R.id.btn_a);
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, Test1Activity.class);

                startActivity(intent);
            }
        });

        Button btnB = (Button) findViewById(R.id.btn_b);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, Test2Activity.class);

                startActivity(intent);
            }
        });

        Button btnC = (Button) findViewById(R.id.btn_c);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, ListViewActivity.class);

                startActivity(intent);
            }
        });
        Button btnD = (Button) findViewById(R.id.btn_d);
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, RelativelayoutDomeActitity.class);

                startActivity(intent);
            }
        });
        Button btnE = (Button) findViewById(R.id.btn_e);
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, FrameLayoutDomeActivity.class);

                startActivity(intent);
            }
        });
        Button btnF = (Button) findViewById(R.id.btn_f);
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, LinearLayoutDomeActivity.class);

                startActivity(intent);
            }
        });
        Button btnG = (Button) findViewById(R.id.btn_g);
        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, StudentManagementActivity.class);

                startActivity(intent);
            }
        });

        Button btnH = (Button) findViewById(R.id.btn_h);
        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanMainActivity.this, WebImageActivity.class);

                startActivity(intent);
            }
        });
    }
}
