package com.example.zwj.mvpdemo.http.cache;



import com.example.zwj.mvpdemo.bean.TestBean;
import com.example.zwj.mvpdemo.http.api.ApiResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

public interface CacheProvider {

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ApiResponse<TestBean>> getDatas(Observable<ApiResponse<TestBean>> oRepos, EvictProvider evictDynamicKey);

}