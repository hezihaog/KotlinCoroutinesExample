package com.zh.android.kotlincoroutinesexample.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.util <br>
 * <b>Create Date:</b> 2020-01-08  09:49 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
public class AppUtil {
    private AppUtil() {
    }

    /**
     * 获取当前应用的VersionName
     *
     * @param context 上下文
     */
    public static String getAppVersionName(Context context) {
        try {
            String packageName = context.getPackageName();
            return context.getPackageManager().getPackageInfo(
                    packageName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取App名字
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return "";
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    /**
     * 获取已经安装的应用
     *
     * @param hasSystemApp 是否包含系统App
     */
    public static List<PackageInfo> getAppList(Context context, boolean hasSystemApp) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> packages = manager.getInstalledPackages(0);
        List<PackageInfo> resultList = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            //非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                resultList.add(packageInfo);
            } else {
                //系统应用
                if (hasSystemApp) {
                    resultList.add(packageInfo);
                }
            }
        }
        return resultList;
    }

    /**
     * 获取指定PackageInfo的App图标
     */
    public static Drawable getAppIcon(Context context, PackageInfo packageInfo) {
        return packageInfo.applicationInfo.loadIcon(context.getPackageManager());
    }

    /**
     * 获取指定packageName的App名称
     */
    public static String getAppName(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageInfo(packageName, 0)
                    .applicationInfo.loadLabel(packageManager).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定PackageInfo的App图标
     */
    public static String getAppName(Context context, PackageInfo packageInfo) {
        return packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
    }

    /**
     * 获取指定PackageInfo的App版本名称
     */
    public static String getAppVersionName(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 卸载App
     *
     * @param packageName 要卸载的App包名
     */
    public static void uninstallApp(Activity activity, String packageName) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        activity.startActivityForResult(intent, 1);
    }

    /**
     * 分享App
     */
    public static void shareApp(Context context, String apkPath) {
        File file = new File(apkPath);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider7.getUriForFile(context, file));
        context.startActivity(intent);
    }
}