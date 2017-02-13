package mf.andorid.com.mfinfo.Activity;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.himanshuvirmani.androidcache.CacheManager;
import com.himanshuvirmani.androidcache.DiskCache;
import com.himanshuvirmani.androidcache.Cache;

import java.io.File;

/**
 * Created by 8398 on 07/02/17.
 */
public class MyApplication extends Application {
    private  MyApplication singleton;
    CacheManager cacheManager;
    private static Application sApplication;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication=this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static MyApplication getInstance() {
        return new MyApplication();
    }
    public  void prepareCache() {
        final String cachePath = getContext().getCacheDir().getPath();
        final File cacheFile = new File(cachePath + File.separator );
        try {
            Cache diskCache = new DiskCache(cacheFile,1, 1024 * 1024 * 10);
            cacheManager = CacheManager.getInstance(diskCache);
            cacheManager.setDebug(true); //Do this if you want to see logs from cachemanager
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  CacheManager getCacheManager() {
        System.out.println(cacheManager);
        return cacheManager;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

}
