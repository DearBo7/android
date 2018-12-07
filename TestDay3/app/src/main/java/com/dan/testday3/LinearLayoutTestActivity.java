package com.dan.testday3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dan on 2018/9/14 13:10
 */
public class LinearLayoutTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.linear_layout_test);
        Button btnTz = (Button) findViewById(R.id.btn_tz);
        btnTz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LinearLayoutTestActivity.this, DanMainActivity.class);

                startActivity(intent);
            }
        });
    }
}
