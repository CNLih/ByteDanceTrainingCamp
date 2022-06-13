package com.bytedance.application;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.bytedance.application.base.BaseInitActivity;
import com.bytedance.application.bean.DataBean;
import com.bytedance.application.bean.User;
import com.bytedance.application.databinding.ActivityMainBinding;
import com.bytedance.application.model.AppModel;
import com.bytedance.application.model.entity.ChartEntity;
import com.bytedance.application.model.entity.DataEntity;
import com.bytedance.application.net.DataApi;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseInitActivity<ActivityMainBinding> {
    private static final String TAG = "MAIN_ACTIVITY_TAG";

    @Override
    protected ActivityMainBinding getViewBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViewsAndEvents() {
        binding.setUser(new User("冲突测试"));
        binding.wvMini.loadUrl("weixin://dl/business/?t=QDZVQEO2z9f");
        AppModel.getInstance().init(this);

        binding.btSend.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("weixin://dl/business/?t=QDZVQEO2z9f"));
            try {
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        DataApi dataApi = new Retrofit.Builder()
                .baseUrl(AppUtils.QQ_NEWS_API)
//                .addCallAdapterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DataApi.class);

        Log.d(TAG, "initViewsAndEvents: before");
        dataApi.getChinaInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(ResponseBody::string)
                .map(JSONObject::new)
                .map(jsonObject -> {
                    List<DataBean> dataBeans = null;
                    try {
                        JSONObject data = jsonObject.getJSONObject("data").getJSONObject("diseaseh5Shelf");
                        JSONObject area = data.getJSONArray("areaTree").getJSONObject(0);
                        area.put("lastUpdateTime", data.getString("lastUpdateTime"));
                        dataBeans = new ArrayList<>();
                        dataBeans.add(DataBean.fromJson(area));
                        dataBeans.addAll(dataBeans.get(0).getChildren());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return dataBeans;
                })
                .subscribe(new Observer<List<DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<DataBean> dataBeans) {
                        List<DataEntity> list = new ArrayList<>();
                        for(DataBean dataBean : dataBeans){
                            list.add(new DataEntity(dataBean));
                        }
                        AppModel.getInstance().setData(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Log.d(TAG, "initViewsAndEvents: after");
    }
}
