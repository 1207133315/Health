package com.bw.health.core;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;

/**
 * com.bw.health.core
 *
 * @author 李宁康
 * @date 2019 2019/07/29 15:35
 */
public class GuanCha {
    public interface Observerable {
        void addObserver(Observer observer);        //新增一个观察者
        void deleteObserver(Observer observer);     //移除一个观察者
        void notifyObserver();      //通知观察者
    }

    /**
     * 抽象观察者接口
     */
    public interface Observer {
        void update(String message);    //接收到通知进行更新
    }

    /**
     * 被观察者 微信公众号
     */
    public static class WechatServer implements Observerable{
        private List<Observer> list;
        private String message;
        public WechatServer() {
            list = new ArrayList<>();
        }
        @Override
        public void addObserver(Observer observer) {
            this.list.add(observer);
        }
        @Override
        public void deleteObserver(Observer observer) {
            if (!this.list.isEmpty()){
                list.remove(observer);
            }
        }
        @Override
        public void notifyObserver() {
            for(Observer observer : list){
                observer.update(message);
            }
        }
        public void setInformation(String s){
            this.message = s;
            System.out.println("发布新消息：" + s);
            notifyObserver();
        }
    }

    /**
     * 观察者 具体用户
     */
    public static class User implements Observer{
        private String name;
        private String message;
        public User(String name) {
            this.name = name;
        }
        @Override
        public void update(String message) {
            this.message = message;
            read();
        }
        private void read() {
            System.out.println(this.name + "收到新消息：" + this.message);
        }
    }

    public static void main(String[] args) {
        WechatServer weixin = new WechatServer();
        User u1 = new User("张三");
        User u2 = new User("李四");
        User u3 = new User("王五");
        weixin.addObserver(u1);
        weixin.addObserver(u2);
        weixin.addObserver(u3);
        weixin.setInformation("发布第一条消息");   //发布新消息：发布第一条消息 张三收到新消息：发布第一条消息 李四收到新消息：发布第一条消息 王五收到新消息：发布第一条消息

        weixin.deleteObserver(u2);
        System.out.println("李四退出订阅");   //李四退出订阅
        weixin.setInformation("发布第二条消息");   //发布新消息：发布第二条消息 张三收到新消息：发布第二条消息 王五收到新消息：发布第二条消息

    }
}
