package com.bytedance.application.net;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface DataApi {

    @GET("newsqa/v1/query/inner/publish/modules/list?modules=diseaseh5Shelf")
    Observable<ResponseBody> getChinaInfo();

}
