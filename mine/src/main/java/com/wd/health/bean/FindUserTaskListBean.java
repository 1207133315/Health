package com.wd.health.bean;

public class FindUserTaskListBean {
    private int id;
    private String taskName;
    private int taskType;
    private int reward;
    private int whetherFinish;
    private int whetherReceive;

    public FindUserTaskListBean(int id, String taskName, int taskType, int reward, int whetherFinish, int whetherReceive) {
        this.id = id;
        this.taskName = taskName;
        this.taskType = taskType;
        this.reward = reward;
        this.whetherFinish = whetherFinish;
        this.whetherReceive = whetherReceive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getWhetherFinish() {
        return whetherFinish;
    }

    public void setWhetherFinish(int whetherFinish) {
        this.whetherFinish = whetherFinish;
    }

    public int getWhetherReceive() {
        return whetherReceive;
    }

    public void setWhetherReceive(int whetherReceive) {
        this.whetherReceive = whetherReceive;
    }
}
