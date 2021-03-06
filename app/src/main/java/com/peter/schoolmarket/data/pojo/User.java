package com.peter.schoolmarket.data.pojo;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by PetterChen on 2017/4/10.
 */

public class User extends RealmObject implements Serializable {
    @PrimaryKey
    private int id;//U001
    private String username;//用户名
    private String password;//用户密码
    private String phone;//手机号
    private String imgUrl;//头像地址

    public User() {
        //
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
