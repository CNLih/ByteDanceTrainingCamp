package com.bytedance.application.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.room.Room;

import com.bytedance.application.model.entity.ChartEntity;
import com.bytedance.application.model.entity.CodeEntity;
import com.bytedance.application.model.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

public class AppModel {
    private Context mContext;
    
    private static AppModel mInstance;
    private List<String> infoList;
    private List<CodeEntity> codeList;

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

    public List<String> getInfoList(){
        if(infoList == null){
            infoList = new ArrayList<String>(){{add("123");add("345");add("456");
                add("123");add("345");add("456");
                add("123");add("345");add("456");}};
        }
        return infoList;
    }

    public void loadInfoData(){

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
}
