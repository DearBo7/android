package com.dan;

import android.content.Context;
import android.os.StatFs;
import android.text.format.Formatter;


/**
 * Created by Dan on 2018/9/18 14:26
 */
public class DanUtil {

    /**
     * 根据目录获取内存状态
     *
     * @param context
     * @param path
     * @return
     */
    public static String getMemoryInfo(Context context, String path) {

        //获得一个磁盘状态对象
        StatFs statFs = new StatFs(path);
        //获取一个扇区大小
        long blockSizeLong = statFs.getBlockSizeLong();

        //获取扇区总数
        long blockCountLong = statFs.getBlockCountLong();
        //获取可用扇区数量
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        //总空间
        String totalMemory = Formatter.formatFileSize(context, (blockCountLong * blockSizeLong));
        //可用空间
        String availableBlocksMemory = Formatter.formatFileSize(context, (availableBlocksLong * blockSizeLong));

        return "总空间：" + totalMemory + "，可用大小：" + availableBlocksMemory;
    }
}
