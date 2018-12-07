package com.dan.httpasyn.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dan on 2018/10/18 13:50
 */
public class User implements Serializable {

    private String name;
    private Date createDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
