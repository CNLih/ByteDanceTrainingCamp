package com.bytedance.application.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

@Entity(tableName = "qrcode")
public class CodeEntity {

    @NonNull
    @PrimaryKey
    private String title;

    private String uri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public CodeEntity(){

    }

    @Ignore
    public CodeEntity(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }
}
