package com.dan.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dan on 2018/9/19 11:31
 */
public class DbSQLiteOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "dan_test_20180919.db";
    private static int version = 1;

    /**
     * 创建数据库
     *
     * @param context 上下文 name:数据库名称,factory:游标工程,version:版本
     */
    public DbSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    /**
     * 数据库第一次创建时回调此方法
     *
     * @param db 初始化一些表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String stu_table = "create table Student(id integer primary key autoincrement,name varchar(20),sex varchar(2),age integer)";
        db.execSQL(stu_table);
    }

    /**
     * 数据库更新是回调此方法
     *
     * @param db         更新数据库内容(删除表，新增表，修改表)
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
