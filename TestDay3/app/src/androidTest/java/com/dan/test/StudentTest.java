package com.dan.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dan.StudentDao;
import com.dan.entity.Student;
import com.dan.util.DbSQLiteOpenHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by Dan on 2018/9/19 13:42
 * AndroidTestCase
 */
@RunWith(AndroidJUnit4.class)
public class StudentTest {

    private Context context;

    private StudentDao studentDao;

    @Before
    @Test
    public void init() {
        context = InstrumentationRegistry.getTargetContext();
        studentDao = new StudentDao(context);
        System.out.println("\n开始...\n");
    }

    @After
    @Test
    public void after() {
        context = null;
        studentDao = null;
        System.out.println("\n结束...");
    }

    @Test
    public void test01() {
        System.out.println("===test01=");
        DbSQLiteOpenHelper dbSQLiteOpenHelper = new DbSQLiteOpenHelper(context);
        //第一次执行时会执行onCreate方法
        SQLiteDatabase readableDatabase = dbSQLiteOpenHelper.getReadableDatabase();
        readableDatabase.close();
    }

    @Test
    public void testInsert() {
        long id = studentDao.insert(new Student("张三2", null, 19));
        System.out.println("添加成功?id:" + id);
    }

    @Test
    public void testDel() {
        int row = studentDao.delete(4);
        System.out.println("删除成功?" + row);
    }

    @Test
    public void testList() {
        List<Student> studentList = studentDao.listStudent();
        for (Student student : studentList) {
            System.out.println(student.toString());
        }
    }

    @Test
    public void testUpdate() {
        int row = studentDao.update(new Student(1, "哒哒", null, 28));
        System.out.println("修改成功?" + row);
    }

    @Test
    public void testInsertSql() {
        studentDao.insertSql(new Student("张三1", "女", 19));
    }

    @Test
    public void testDelSql() {
        studentDao.deleteSql(2);
    }

    @Test
    public void testGet() {
        Student student = studentDao.getStudentSql(1);
        System.out.println(student.toString());

    }

    @Test
    public void testListSql() {
        List<Student> studentList = studentDao.listStudentSql();
        for (Student student : studentList) {
            System.out.println(student.toString());
        }
    }
}
