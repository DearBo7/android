package com.dan.testday3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dan.entity.Student;
import com.dan.util.JsonUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 2018/9/18 16:50
 * 学生管理->线形布局（权重）
 */
public class StudentManagementActivity extends AppCompatActivity {

    private List<Student> studentList;
    private LinearLayout linearLayout;
    private Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_management);
        if (studentList == null) {
            studentList = new ArrayList<>();
        } else {
            //studentList.clear();
        }

        linearLayout = (LinearLayout) findViewById(R.id.li_list_data);

        //添加按钮
        Button btnAdd = (Button) findViewById(R.id.btn_add);
        //保存数据
        Button btnSaveDate = (Button) findViewById(R.id.btn_save_data);
        //恢复数据
        Button btnHfData = (Button) findViewById(R.id.btn_hf_data);
        //删除
        Button btnDelData = (Button) findViewById(R.id.btn_del_data);
        //名称
        final EditText etName = (EditText) findViewById(R.id.et_name);
        //年龄
        final EditText etAge = (EditText) findViewById(R.id.et_age);
        //性别
        final EditText etSex = (EditText) findViewById(R.id.et_sex);

        //添加
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent(etName, etAge, etSex);
            }
        });
        //保存数据
        btnSaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentList == null || studentList.size() == 0) {
                    Toast.makeText(StudentManagementActivity.this, "没有要保存的数据!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存
                if (saveStudent(StudentManagementActivity.this, studentList)) {
                    Toast.makeText(StudentManagementActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentManagementActivity.this, "保存失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //恢复
        btnHfData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean studentFlag = getStudent(StudentManagementActivity.this);
                if (studentList == null || studentList.size() == 0) {
                    Toast.makeText(StudentManagementActivity.this, "没有保存记录!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (studentFlag) {
                    Toast.makeText(StudentManagementActivity.this, "恢复成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentManagementActivity.this, "恢复失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //删除
        btnDelData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteStudent(StudentManagementActivity.this)) {
                    Toast.makeText(StudentManagementActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentManagementActivity.this, "删除失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addStudent(EditText exName, EditText exAge, EditText exSex) {
        String name = exName.getText().toString();
        String age = exAge.getText().toString();
        String sex = exSex.getText().toString();
        String msg = "";
        for (int i = 0; i < 1; i++) {
            if (StringUtils.isBlank(name)) {
                msg = "名称不能为空";
                break;
            }
            if (StringUtils.isBlank(age)) {
                msg = "年龄不能为空";
                break;
            }
            if (StringUtils.isBlank(sex)) {
                msg = "性别不能为空";
                break;
            }
        }
        if (StringUtils.isNotBlank(msg)) {
            Toast.makeText(StudentManagementActivity.this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        student = new Student();
        student.setName(name);
        student.setSex(sex);
        student.setAge(Integer.parseInt(age));

        TextView textView = new TextView(StudentManagementActivity.this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f);
        textView.setTextColor(Color.RED);
        textView.setText(student.toString());
        linearLayout.addView(textView);
        studentList.add(student);
    }

    private boolean saveStudent(Context context, List<Student> studentList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("student", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("studentList", JsonUtil.toJson(studentList));
        return edit.commit();
    }

    private boolean getStudent(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("student", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("studentList")) {
            String strStudentList = sharedPreferences.getString("studentList", null);
            if (StringUtils.isNotBlank(strStudentList)) {
                List<Student> thisStudentList = JsonUtil.fromListJson(strStudentList, Student.class);
                if (thisStudentList.size() > 0) {
                    studentList.addAll(thisStudentList);
                    //恢复
                    return refreshStudentList(thisStudentList);
                }
            }
        }
        return false;
    }

    private boolean refreshStudentList(List<Student> studentList) {
        for (Student thisStudent : studentList) {
            TextView textView = new TextView(StudentManagementActivity.this);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f);
            textView.setTextColor(Color.RED);
            textView.setText(thisStudent.toString());
            linearLayout.addView(textView);
        }
        return true;
    }

    private boolean deleteStudent(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("student", Context.MODE_PRIVATE);

        if (!sharedPreferences.contains("studentList")) {
            return false;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();

        return edit.clear().commit();
    }

}
