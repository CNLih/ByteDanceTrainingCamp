package com.bytedance.application.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataBean {

    private String area;
    private int confirm;
    private int suspect;
    private int cure;
    private int dead;
    private List<DataBean> children;

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public int getConfirm()
    {
        return confirm;
    }

    public void setConfirm(int confirm)
    {
        this.confirm = confirm;
    }

    public int getSuspect()
    {
        return suspect;
    }

    public void setSuspect(int suspect)
    {
        this.suspect = suspect;
    }

    public int getCure()
    {
        return cure;
    }

    public void setCure(int cure)
    {
        this.cure = cure;
    }

    public int getDead()
    {
        return dead;
    }

    public void setDead(int dead)
    {
        this.dead = dead;
    }

    public List<DataBean> getChildren()
    {
        return children;
    }

    public void setChildren(List<DataBean> children)
    {
        this.children = children;
    }

    public static DataBean fromJson(JSONObject json)
    {
        DataBean info = new DataBean();
        info.setArea(json.optString("name"));
        JSONObject total = json.optJSONObject("total");
        if (total != null)
        {
            info.setConfirm(total.optInt("nowConfirm"));
            info.setSuspect(total.optInt("suspect"));
            info.setCure(total.optInt("heal"));
            info.setDead(total.optInt("dead"));
        }
        JSONArray array = json.optJSONArray("children");
        if (array != null)
        {
            List<DataBean> children = new ArrayList<>();
            for (int i = 0; i < array.length(); i++)
            {
                DataBean child = fromJson(array.optJSONObject(i));
                children.add(child);
            }
            info.setChildren(children);
        }
        return info;
    }
}
