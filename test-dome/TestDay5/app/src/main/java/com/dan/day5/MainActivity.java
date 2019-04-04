package com.dan.day5;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dan.adapter.StudentBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //读取内存权限
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
        if (!checkPermission(Manifest.permission.READ_CONTACTS)) {
            getPermission(Manifest.permission.READ_CONTACTS, permissions);
        }
        if (!checkPermission(Manifest.permission.WRITE_CONTACTS)) {
            getPermission(Manifest.permission.WRITE_CONTACTS, permissions);
        }
        Button btnSelect = (Button) findViewById(R.id.btn_select);
        final ListView listView = (ListView) findViewById(R.id.li_list);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //List<String> dataList = queryContacts();
                ArrayList<String> dataList = new ArrayList<>();
                for (int i = 1; i <= 100; i++) {

                    dataList.add("平头" + String.valueOf(i));
                }
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, dataList.toArray(new String[0]));
                StudentBaseAdapter adapter = new StudentBaseAdapter(MainActivity.this, R.layout.text_view_dome, dataList);
                listView.setAdapter(adapter);
                listView.setSelection(ListView.FOCUS_DOWN);
            }
        });
    }

    public List<String> queryContacts() {
        // 1. 去raw_contacts表中取所有联系人的_id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        Cursor cursor = getContentResolver().query(uri, new String[]{"_id"}, null, null, null);
        //printCursor(cursor);
        List<String> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                // 2. 去data表中根据上面取到的_id查询对应id的数据.

                String selection = "raw_contact_id = ?";
                String[] selectionArgs = {String.valueOf(id)};
                Cursor c = getContentResolver().query(dataUri, new String[]{"data1", "mimetype"},
                        selection, selectionArgs, null);
                if (c != null && c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String sss = "{";
                        String mimetype = c.getString(1);        // 当前取的是mimetype的值
                        String data1 = c.getString(0);        // 当前取的是data1数据

                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            Log.i(TAG, "号码: " + data1);
                            sss += "号码:" + data1 + ",";
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            Log.i(TAG, "姓名: " + data1);
                            sss += "姓名:" + data1 + ",";
                        } else if ("vnd.android.cursor.item/email_v2".equals(mimetype)) {
                            Log.i(TAG, "邮箱: " + data1);
                            sss += "邮箱:" + data1 + ",";
                        }
                        sss += "}";
                        list.add(sss);
                    }
                    c.close();
                }
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 检查某个权限是否已经获得
     */
    private boolean checkPermission(String permission) {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        ActivityCompat.checkSelfPermission(MainActivity.this, permission);

        return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取权限
     */
    private void getPermission(String permission, String[] permissions) {
        //申请权限
        ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);

        //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
            Toast.makeText(MainActivity.this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
        }
    }
}
