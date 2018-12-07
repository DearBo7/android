package com.dan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dan.entity.Student;
import com.dan.util.DbSQLiteOpenHelper;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 2018/9/19 13:14
 */
public class StudentDao {

    private DbSQLiteOpenHelper dbSQLiteOpenHelper;

    private String tableName = "Student";

    public StudentDao(Context context) {
        dbSQLiteOpenHelper = new DbSQLiteOpenHelper(context);
    }

    public long insert(Student student) {
        long id = 0;
        ContentValues contentValues = new ContentValues();
        if (StringUtils.isNotBlank(student.getName())) {
            contentValues.put("name", student.getName());
        }
        if (StringUtils.isNotBlank(student.getSex())) {
            contentValues.put("sex", student.getSex());
        }
        if (student.getAge() != null) {
            contentValues.put("age", student.getAge());
        }
        SQLiteDatabase database = dbSQLiteOpenHelper.getWritableDatabase();
        //如果数据库打开，执行添加操作
        if (database.isOpen()) {
            id = database.insert(tableName, null, contentValues);
            //关闭数据库
            database.close();
        }
        return id;
    }

    public int delete(int id) {
        int row = 0;
        SQLiteDatabase database = dbSQLiteOpenHelper.getWritableDatabase();
        if (database.isOpen()) {
            //删除条件
            String whereClause = "id = ?";
            //执行删除方法
            row = database.delete(tableName, whereClause, new String[]{String.valueOf(id)});
            //关闭数据库
            database.close();
        }
        return row;
    }

    public List<Student> listStudent() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase database = dbSQLiteOpenHelper.getReadableDatabase();
        if (database.isOpen()) {
            //需要的列
            String[] columns = {"id", "name", "age", "sex"};
            //选择条件，给null查询所有  id = ?
            String selection = null;
            //条件里面的参数,会把?替换成数据中的值 id = 1
            String[] selectionArgs = null;
            //分组语句 name
            String groupBy = null;
            //过滤语句 name
            String having = null;
            //排序 Id Desc
            String orderBy = "Id";
            Cursor query = database.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);

            Student student = null;
            if (query != null && query.getCount() > 0) {
                while (query.moveToNext()) {
                    student = new Student();
                    student.setId(query.getInt(0));
                    student.setName(query.getString(1));
                    student.setAge(query.getInt(2));
                    student.setSex(query.getString(3));

                    studentList.add(student);
                }
            }
            //关闭数据库
            database.close();
        }
        return studentList;
    }

    public List<Student> listStudentLimit() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase database = dbSQLiteOpenHelper.getReadableDatabase();
        if (database.isOpen()) {
            //需要的列
            String[] columns = {"id", "name", "age", "sex"};
            //选择条件，给null查询所有  id = ?
            String selection = null;
            //条件里面的参数,会把?替换成数据中的值 id = 1
            String[] selectionArgs = null;
            //分组语句 name
            String groupBy = null;
            //过滤语句 name
            String having = null;
            //排序 Id Desc
            String orderBy = "Id";
            //分页条件
            String limit = null;
            Cursor query = database.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

            Student student = null;
            if (query != null && query.getCount() > 0) {
                while (query.moveToNext()) {
                    student = new Student();
                    student.setId(query.getInt(0));
                    student.setName(query.getString(1));
                    student.setAge(query.getInt(2));
                    student.setSex(query.getString(3));

                    studentList.add(student);
                }
            }
            //关闭数据库
            database.close();
        }
        return studentList;
    }

    public void insertSql(Student student) {
        SQLiteDatabase database = dbSQLiteOpenHelper.getWritableDatabase();
        //如果数据库打开，执行添加操作
        if (database.isOpen()) {
            try {
                //开启事务
                database.beginTransaction();

                database.execSQL("insert into student(name,sex,age) values(?,?,?);", new Object[]{student.getName(), student.getSex(), student.getAge()});

                //提交事务
                database.setTransactionSuccessful();
            } finally {
                //停止事务
                database.endTransaction();
            }

            //关闭数据库
            database.close();
        }
    }

    public void deleteSql(int id) {
        SQLiteDatabase database = dbSQLiteOpenHelper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("delete from Student where id = ?;", new Integer[]{id});
            //关闭数据库
            database.close();
        }
    }

    public int update(Student student) {
        int row = 0;
        ContentValues contentValues = new ContentValues();
        if (StringUtils.isNotBlank(student.getName())) {
            contentValues.put("name", student.getName());
        }
        if (StringUtils.isNotBlank(student.getSex())) {
            contentValues.put("sex", student.getSex());
        }
        if (student.getAge() != null) {
            contentValues.put("age", student.getAge());
        }
        SQLiteDatabase database = dbSQLiteOpenHelper.getWritableDatabase();
        if (database.isOpen()) {
            //修改条件
            String whereClause = "id=?";
            row = database.update(tableName, contentValues, whereClause, new String[]{String.valueOf(student.getId() == null ? -1 : student.getId())});
            //关闭数据库
            database.close();
        }
        return row;
    }

    public List<Student> listStudentSql() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase database = dbSQLiteOpenHelper.getReadableDatabase();
        if (database.isOpen()) {

            Cursor cursor = database.rawQuery("select id,name,age,sex from student", null);
            Student student = null;
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    student = new Student();
                    student.setId(cursor.getInt(0));
                    student.setName(cursor.getString(1));
                    student.setAge(cursor.getInt(2));
                    student.setSex(cursor.getString(3));

                    studentList.add(student);
                }
            }
            //关闭数据库
            database.close();
        }
        return studentList;
    }

    public Student getStudentSql(int id) {
        Student student = new Student();
        SQLiteDatabase database = dbSQLiteOpenHelper.getReadableDatabase();
        if (database.isOpen()) {

            Cursor cursor = database.rawQuery("select id,name,age,sex from student where id = ?", new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {

                student.setId(cursor.getInt(0));
                student.setName(cursor.getString(1));
                student.setAge(cursor.getInt(2));
                student.setSex(cursor.getString(3));
            }
            //关闭数据库
            database.close();
        }
        return student;
    }
}
