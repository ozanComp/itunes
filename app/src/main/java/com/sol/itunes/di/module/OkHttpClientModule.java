package com.sol.itunes.di.module;

import android.content.Context;

import com.ncornette.cache.OkCacheControl;
import com.sol.itunes.Utility;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module (includes = ContextModule.class)
public class OkHttpClientModule {

    @Provides
    public OkCacheControl.NetworkMonitor provideNetworkMonitor(Context context){
        return () -> Utility.isNetworkAvailable(context);
    }

    @Provides
    public Cache provideCache(Context context){
        return new Cache(context.getCacheDir(), 1024 * 1024);
    }

    @Provides
    public OkHttpClient provideOkHttpClient(Cache cache, OkCacheControl.NetworkMonitor networkMonitor){
        return OkCacheControl.on(new OkHttpClient.Builder())
                .overrideServerCachePolicy(1, TimeUnit.MINUTES)
                .forceCacheWhenOffline(networkMonitor)
                .apply()
                .cache(cache)
                .build();
    }
}
