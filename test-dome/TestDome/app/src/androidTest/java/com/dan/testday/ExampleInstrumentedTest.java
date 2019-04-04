package com.dan.testday;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends Activity{
    @Test
    public void testProviderInsert() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Uri uri = Uri.parse("content://com.dan.testday3.provider.StudentContentProvider/student/insert");
        //内容提供者访问对象
        ContentResolver contentResolver = appContext.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "Provider名称1");
        contentValues.put("sex", "无");
        contentValues.put("age", 99);
        Uri insert = contentResolver.insert(uri, contentValues);
        if (insert != null) {

            long id = ContentUris.parseId(insert);
            System.out.println("添加成功:" + id);
        } else {
            System.out.println("出错了。。。。。。。。。");
        }
    }
}
