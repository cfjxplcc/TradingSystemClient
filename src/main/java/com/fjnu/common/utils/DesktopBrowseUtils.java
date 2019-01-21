package com.fjnu.common.utils;

import org.apache.http.util.TextUtils;

import java.io.IOException;

/**
 * Created by LCC on 2018/6/12.
 */
public class DesktopBrowseUtils {

    public static boolean useDesktopBrowseOpenUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                //创建一个URI实例
                java.net.URI uri = java.net.URI.create(url);
                //获取当前系统桌面扩展
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                //判断系统桌面是否支持要执行的功能
                if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    //获取系统默认浏览器打开链接
                    desktop.browse(uri);
                    return true;
                }
            } catch (IllegalArgumentException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
