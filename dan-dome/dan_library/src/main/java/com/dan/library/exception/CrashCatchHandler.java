package com.dan.library.exception;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.dan.library.util.DateUtil;
import com.dan.library.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Dan on 2019/2/20 11:43
 */
public class CrashCatchHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashCatchHandler";
    private static CrashCatchHandler INSTANCE;
    private Context mContext;
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/log/";
    private String thisPath;

    private CrashCatchHandler() {

    }

    public void init(Context c, String childPath) {
        Thread.setDefaultUncaughtExceptionHandler(INSTANCE);
        //这里的c应该是全局的。
        if (c == null) {
            Log.i(TAG, "CrashCatchHandler->the context cann't be empty ");
            return;
        }
        mContext = c;
        if (childPath != null && !"".equals(childPath.trim())) {
            this.thisPath = PATH + childPath + "/";
        } else {
            this.thisPath = PATH;
        }
        //清空之前日志文件
        try {
            clearLog(thisPath, 3);
        } catch (Exception e) {
            Log.e(TAG, "clearLog->清空日志失败!Path:" + thisPath + ",errMsg:" + e);
        }
    }

    public void init(Context c) {
        this.init(c, null);
    }

    public static CrashCatchHandler getInstance() {
        if (null == INSTANCE) {
            synchronized (CrashCatchHandler.class) {
                if (null == INSTANCE) {
                    INSTANCE = new CrashCatchHandler();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 把错误日志写到文件中。
     */
    private void writeToFile(Throwable ex) {
        //判断SD卡不存在或无法使用
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "SD卡不存在日志无法写入!error:" + ex.getMessage());
            return;
        }

        File dir = new File(thisPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //文件格式
        String fileTimeFormat = "yyyyMMdd";
        Date date = new Date();
        String fileTime = new SimpleDateFormat(fileTimeFormat).format(date);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        File file = new File(thisPath + fileTime + ".log");
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            //导出发生异常的时间
            pw.println("【" + time + "】");
            pw.println();
            //导出异常的调用栈信息
            ex.printStackTrace(pw);
            pw.println();
            pw.close();
        } catch (Exception e) {
            Log.e(TAG, "日志写入失败!fileName:" + file.getAbsolutePath() + ",error:" + e.getMessage());
        }
        Log.i(TAG, "日志写入!fileName:" + file.getAbsolutePath() + ",error:" + ex.getMessage() + ",printStackTrace:" + ex);
    }

    /**
     * 文件删除
     *
     * @param autoClearDay 文件保存天数
     */
    public void clearLog(String path, int autoClearDay) {
        File dirFile = new File(path);
        if (dirFile.exists()) {
            autoClearDay = autoClearDay < 0 ? 0 : autoClearDay;
            long thisTime = DateUtil.getTime();
            List<String> fileUrlList = FileUtil.readFileAndPath(path, true, null);
            if (fileUrlList != null && fileUrlList.size() > 0) {
                File thisFile = null;
                for (String fileUrl : fileUrlList) {
                    thisFile = new File(fileUrl);
                    long lastModified = thisFile.lastModified();
                    //相差天数
                    long day = (thisTime - lastModified) / DateUtil.DAY_MILLI;
                    //大于保留天数,删除掉
                    if (day > autoClearDay) {
                        //删除
                        FileUtil.removeFile(thisFile);
                    }
                }
            }
        } else {
            dirFile.mkdirs();
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //写入本地日志
        writeToFile(e);

        //杀死这个进程避免类似ios直接退出。
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
