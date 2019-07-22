package com.bw.health.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * com.bw.health.bean
 *
 * @author 李宁康
 * @date 2019 2019/07/22 11:12
 */
@Entity
public class RemenBean {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    @Generated(hash = 661720680)
    public RemenBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1248348186)
    public RemenBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
