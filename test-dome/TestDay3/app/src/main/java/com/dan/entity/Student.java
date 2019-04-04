package com.dan.entity;

import java.io.Serializable;

public class Student implements Serializable {

    private Integer id;
    private String name;
    private String sex;
    private Integer age;

    public Student(String name, String sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Student(Integer id, String name, String sex, Integer age) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student {" +
                "id=" + id +
                ", 名称='" + name + '\'' +
                ", 性别='" + sex + '\'' +
                ", 年龄=" + age +
                '}';
    }
}
