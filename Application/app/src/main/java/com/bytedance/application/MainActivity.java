package com.bytedance.application;

import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.bytedance.application.base.BaseInitActivity;
import com.bytedance.application.bean.StatisticsBean;
import com.bytedance.application.bean.User;
import com.bytedance.application.databinding.ActivityMainBinding;
import com.bytedance.application.model.AppModel;
import com.bytedance.application.model.entity.DataEntity;
import com.bytedance.application.net.DataApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainActivity extends BaseInitActivity<ActivityMainBinding> {
    private static final String TAG = "MAIN_ACTIVITY_TAG";
    @Override
    protected ActivityMainBinding getViewBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViewsAndEvents() {
        binding.setUser(new User("请在桌面添加组件~"));

        DataApi dataApi = new Retrofit.Builder()
                .baseUrl(AppUtils.QQ_NEWS_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DataApi.class);

        Log.d(TAG, "initViewsAndEvents: before");
        dataApi.getChinaInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseBody::string)
                .map(JSONObject::new)
                .map(jsonObject -> {
                    List<StatisticsBean> statisticsBeans = null;
                    try {
                        JSONObject data = jsonObject.getJSONObject("data").getJSONObject("diseaseh5Shelf");
                        JSONObject area = data.getJSONArray("areaTree").getJSONObject(0);
                        area.put("lastUpdateTime", data.getString("lastUpdateTime"));
                        statisticsBeans = new ArrayList<>();
                        statisticsBeans.add(StatisticsBean.fromJson(area));
                        statisticsBeans.addAll(statisticsBeans.get(0).getChildren());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return statisticsBeans;
                })
                .subscribe(new Observer<List<StatisticsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<StatisticsBean> statisticsBeans) {
                        List<DataEntity> list = new ArrayList<>();
                        for(StatisticsBean statisticsBean : statisticsBeans){
                            list.add(new DataEntity(statisticsBean));
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
