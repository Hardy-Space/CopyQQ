package com.hardy.person.copyqq.utils;

import android.app.Application;
import android.content.Context;

/**
 * @author 马鹏昊
 * @date {2016.6.27}
 * @des   获取应用程序Context的工具类,别忘了在配置文件里加上<application android:name=".utils.GetApplicationContext">...</application>
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class GetApplicationContext extends Application{
    private static Context applicationContext ;

    @Override
    public void onCreate() {
        applicationContext = getApplicationContext();
    }

    public static Context returnApplicationContext(){
        return applicationContext;
    }
}
