package com.liyi.sample;

import java.io.Serializable;

/**
 * Created by albertlii on 2017/8/3.
 */

public class TestBean implements Serializable {
    private String msg;

    public TestBean(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
