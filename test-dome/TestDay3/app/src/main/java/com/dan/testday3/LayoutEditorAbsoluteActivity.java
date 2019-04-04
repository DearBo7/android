package com.dan.testday3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Dan on 2018/9/14 13:10
 */
public class LayoutEditorAbsoluteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_editor_absolute_xy);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        TextView textView = (TextView) findViewById(R.id.bt_b);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LayoutEditorAbsoluteActivity.this, LinearLayoutTestActivity.class);
                startActivity(intent);
            }
        });
    }

}
