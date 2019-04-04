package com.dan.service;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dan on 2018/9/18 10:48
 * com.dan.testday3:包名,Environment:读取手机内存卡类
 * context.getExternalFilesDir("ddd") ===> /storage/emulated/0/Android/data/com.dan.testday3/files/ddd   获取手机内存Android目录
 * Environment.getExternalStorageDirectory() ===> /storage/emulated/0   获取手机内存目录
 * Environment.getRootDirectory() ===> /system   获取根目录
 * context.getFilesDir()  ===>  获取安装目录
 * context.getFileStreamPath("ddd") ===> /data/data/com.dan.testday3/files/ddd
 *
 * SharedPreferences 方式增删改查  储存路径：data/data/com.dan.testday3/shared_prefs
 */
public class UserService {

    private final static String PATH = "/data/data/com.dan.testday3/dan_pwd.txt";

    private final static String STR_SPLIT = " ";

    private final static String FILE_NAME = "system_dan_pwd.txt";

    /**
     * 保存用户信息
     *
     * @param number
     * @param pwd
     * @return
     */
    public static boolean saveUserInfo(String number, String pwd) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(PATH);
            String data = number + STR_SPLIT + pwd;
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 获取用户信息
     */
    public static Map<String, String> getUserInfo() {
        Map<String, String> map = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(PATH);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String readLine = bufferedReader.readLine();
            if (StringUtils.isNotBlank(readLine)) {
                String[] split = readLine.split(STR_SPLIT);
                if (split.length > 1) {
                    map = new HashMap<>();
                    map.put("number", split[0]);
                    map.put("pwd", split[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public static boolean saveUserInfo(Context context, String number, String pwd) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            String data = number + STR_SPLIT + pwd;
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static Map<String, String> getUserInfo(Context context) {
        //判断文件是否存在
        if (!context.getFileStreamPath(FILE_NAME).exists() || context.getFileStreamPath(FILE_NAME).isDirectory()) {
            return null;
        }
        Map<String, String> map = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String readLine = bufferedReader.readLine();
            if (StringUtils.isNotBlank(readLine)) {
                String[] split = readLine.split(STR_SPLIT);
                if (split.length > 1) {
                    map = new HashMap<>();
                    map.put("number", split[0]);
                    map.put("pwd", split[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public static boolean deleteUserInfo(Context context) {

        return context.deleteFile(FILE_NAME);
    }

    /**
     * SharedPreferences 方式增删改查  储存路径：data/data/com.dan.testday3/shared_prefs
     */
    public static boolean saveUserInfoSharedPreferences(Context context, String number, String pwd) {
        //获取SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        //获取一个编辑对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //存放数据
        editor.putString("number", number);
        editor.putString("pwd", pwd);

        //提交
        return editor.commit();
    }

    public static Map<String, String> getUserInfoSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String number = sharedPreferences.getString("number", null);
        String pwd = sharedPreferences.getString("pwd", null);
        if (StringUtils.isNotBlank(number) && StringUtils.isNotBlank(pwd)) {
            Map<String, String> map = new HashMap<>();
            map.put("number", number);
            map.put("pwd", pwd);
            return map;
        }
        return null;
    }

    public static boolean deleteUserInfoSharedPreferences(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();

        //清空整个文件
        //return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().clear().commit();
        edit.remove("number");
        edit.remove("pwd");
        return edit.commit();
    }
}
