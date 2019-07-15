package com.bw.health.bean;

import java.util.List;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/12
 * @Description: CircleFindDepartmentBean 病友圈查询科室类
 */
public class CircleFindDepartmentBean {


    /**
     * departmentName : 内科
     * id : 7
     * pic : http://172.17.8.100/images/health/department_pic/nk.jpg
     * rank : 1
     */
    public Long id;
    public int type;
    public String departmentName;
    public String pic;
    public int rank;
    public boolean check;
    public CircleFindDepartmentBean(Long id, int type, String departmentName, String pic, int rank) {
        this.id = id;
        this.type = type;
        this.departmentName = departmentName;
        this.pic = pic;
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

