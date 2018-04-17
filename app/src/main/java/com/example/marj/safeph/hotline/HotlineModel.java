package com.example.marj.safeph.hotline;

/**
 * Created by Marj on 3/13/2018.
 */

public class HotlineModel {
    private String name, phone;

    public HotlineModel(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
