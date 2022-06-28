package com.bytedance.application.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.bytedance.application.AppUtils;
import com.bytedance.application.bean.NewsBean;
import com.bytedance.application.model.entity.CodeEntity;
import com.bytedance.application.model.entity.DataEntity;
import com.bytedance.application.net.DataApi;
import com.bytedance.application.net.NewsListApi;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppModel {
    private Context mContext;
    
    private static AppModel mInstance;
    private List<NewsBean> infoList;
    private List<CodeEntity> codeList;
    private List<NewsBean> newsBeans;

    NewsListApi newsInfoApi;

    public static AppModel getInstance(){
        if(mInstance == null){
            synchronized (AppModel.class){
                if(mInstance == null){
                    mInstance = new AppModel();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context){
        this.mContext = context;
        PreferenceHelper.init(context);
        DBHelper.init(context);
        newsInfoApi = new Retrofit.Builder()
                .baseUrl(AppUtils.LOCAL_NEWS_LIST_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsListApi.class);
    }

    public String getLocation(){
        return PreferenceHelper.getInstance().getLocation();
    }

    public void setLocation(String location){
        PreferenceHelper.getInstance().setLocation(location);
    }

    public List<DataEntity> getData(){
        return DBHelper.getInstance().getDataDao().getAllData();
    }

    public void setData(List<DataEntity> list){
        DBHelper.getInstance().getDataDao().insertAll(list);
    }

    public List<NewsBean> getInfoList(){
        if(infoList == null){

        }
        return infoList;
    }

    public Observable<List<NewsBean>> loadNewsData(){
        return newsInfoApi.getNewsList();
    }

    public int saveCode(String title, String uri) {
        int savedIndex = -1;
        CodeEntity entity = new CodeEntity(title, uri);
        for(int i = 0; i < codeList.size(); i ++){
            CodeEntity codeEntity = codeList.get(i);
            if(codeEntity.getTitle().equals(title)){
                codeList.remove(i);
                codeList.add(i, entity);
                savedIndex = i;
            }
        }
        DBHelper.getInstance().getCodeDao().insert(new CodeEntity(title, uri));
        if(savedIndex == -1){
            codeList.add(new CodeEntity(title, uri));
            savedIndex = codeList.size();
        }
        return savedIndex;
    }

    public void removeCode(int position){
        CodeEntity codeEntity = codeList.remove(position);
        DBHelper.getInstance().getCodeDao().delete(codeEntity);
    }

    public List<CodeEntity> loadCodeList(){
        if(codeList == null){
            codeList = DBHelper.getInstance().getCodeDao().getAllCode();
        }
        return codeList;
    }

    public int[] getWidgets(){
        return PreferenceHelper.getInstance().getWidgets();
    }

    public void setWidgets(int[] widgets){
        PreferenceHelper.getInstance().setWidgets(widgets);
    }

    public void setNewsList(List<NewsBean> newsBeans){
        this.newsBeans = newsBeans;
    }

    public List<NewsBean> getNewsList(){
        return newsBeans;
    }
}
