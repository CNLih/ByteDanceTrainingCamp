package com.bytedance.application.net;

import com.bytedance.application.bean.NewsBean;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface NewsListApi {

    @GET("hello/getNews")
    Observable<List<NewsBean>> getNewsList();
}
